package com.example.secureuiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SecurityDemoActivity extends AppCompatActivity {

    private SwitchMaterial switchFlagSecure; // Switch pour activer ou desactiver FLAG_SECURE.
    private TextView txtSecurityStatus; // Texte qui affiche l'etat de la protection.
    private TextView txtSensitiveData; // Texte contenant des donnees sensibles.
    private String originalSensitiveData; // Copie des donnees pour les restaurer.
    private final Handler handler = new Handler(Looper.getMainLooper()); // Handler principal pour restaurer le texte apres un delai.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appelle le comportement Android standard.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); // Active FLAG_SECURE par defaut.
        setContentView(R.layout.activity_security_demo); // Charge l'interface XML securite.

        switchFlagSecure = findViewById(R.id.switchFlagSecure); // Recupere le switch FLAG_SECURE.
        txtSecurityStatus = findViewById(R.id.txtSecurityStatus); // Recupere le texte de statut.
        txtSensitiveData = findViewById(R.id.txtSensitiveData); // Recupere le texte sensible.
        originalSensitiveData = txtSensitiveData.getText().toString(); // Sauvegarde les donnees originales.
        MaterialButton btnCheckOverlay = findViewById(R.id.btnCheckOverlay); // Bouton de verification overlay.
        MaterialButton btnOpenOverlaySettings = findViewById(R.id.btnOpenOverlaySettings); // Bouton des parametres overlay.
        MaterialButton btnSimulateTalkback = findViewById(R.id.btnSimulateTalkback); // Bouton simulation TalkBack.
        MaterialButton btnBack = findViewById(R.id.btnBackSecurity); // Bouton retour.

        updateSecurityStatus(); // Affiche le statut initial.

        switchFlagSecure.setOnCheckedChangeListener((buttonView, isChecked) -> { // Detecte le changement du switch.
            if (isChecked) { // Si l'utilisateur active la protection.
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); // Bloque les captures.
            } else { // Si l'utilisateur desactive la protection.
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE); // Autorise de nouveau les captures.
            }
            updateSecurityStatus(); // Met a jour le texte de statut.
        });

        btnCheckOverlay.setOnClickListener(view -> checkForOverlayPermission()); // Lance la detection TapJacking.
        btnOpenOverlaySettings.setOnClickListener(view -> openOverlaySettings()); // Ouvre directement les reglages overlay.
        btnSimulateTalkback.setOnClickListener(view -> simulateTalkbackFocus()); // Simule un focus sur une zone sensible.
        btnBack.setOnClickListener(view -> finish()); // Retourne a l'ecran precedent.
    }

    private boolean checkForOverlayPermission() {
        if (Settings.canDrawOverlays(this)) { // Verifie si l'application peut dessiner par-dessus d'autres apps.
            new MaterialAlertDialogBuilder(this) // Cree un dialogue Material.
                    .setTitle("Risque de sécurité détecté") // Titre clair pour l'alerte.
                    .setMessage("Une permission d'overlay est active. Cela peut permettre a une fenetre de couvrir l'interface et de tromper l'utilisateur par TapJacking.") // Explique le risque UX.
                    .setPositiveButton("Ouvrir les paramètres", (dialog, which) -> openOverlaySettings()) // Propose une action directe.
                    .setNegativeButton("Ignorer", (dialog, which) -> dialog.dismiss()) // Permet de fermer l'alerte.
                    .show(); // Affiche l'alerte.
            return true; // Indique qu'un risque a ete detecte.
        }

        Toast.makeText(this, "Aucun risque d'overlay détecté.", Toast.LENGTH_SHORT).show(); // Informe que tout est correct.
        return false; // Indique qu'aucun risque n'a ete detecte.
    }

    private void openOverlaySettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION); // Prepare l'intention des reglages overlay.
        intent.setData(Uri.parse("package:" + getPackageName())); // Cible cette application.
        startActivity(intent); // Ouvre les parametres Android.
    }

    private void simulateTalkbackFocus() {
        txtSensitiveData.requestFocus(); // Donne le focus au texte sensible pour la demonstration.
        txtSensitiveData.setText("Données sensibles protégées temporairement."); // Remplace les donnees par un message sur.
        handler.postDelayed(() -> txtSensitiveData.setText(originalSensitiveData), 5000); // Restaure apres 5 secondes.
    }

    private void updateSecurityStatus() {
        boolean flagSecureEnabled = (getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_SECURE) != 0; // Lit l'etat actuel du flag.
        switchFlagSecure.setChecked(flagSecureEnabled); // Met le switch dans le bon etat.
        if (flagSecureEnabled) { // Si la protection est active.
            txtSecurityStatus.setText("FLAG_SECURE actif: les captures d'écran sont bloquées."); // Message positif.
        } else { // Si la protection est inactive.
            txtSecurityStatus.setText("FLAG_SECURE inactif: les captures d'écran sont possibles."); // Message d'avertissement.
        }
    }
}
