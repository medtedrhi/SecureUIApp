package com.example.secureuiapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editUsername; // Champ pour lire le nom d'utilisateur.
    private TextInputEditText editPassword; // Champ pour lire le mot de passe.
    private MaterialButton btnLogin; // Bouton principal de connexion.
    private ImageView imgSettings; // Icone qui change le theme clair ou sombre.
    private TextView txtSensitiveInfo; // Texte sensible protege contre TalkBack.
    private String originalSensitiveText; // Copie du texte sensible pour le restaurer.
    private final Handler handler = new Handler(Looper.getMainLooper()); // Handler principal pour retarder la restauration du texte.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appelle le comportement normal de l'activite.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); // Bloque les captures d'ecran.
        setContentView(R.layout.activity_main); // Charge l'interface XML principale.

        editUsername = findViewById(R.id.editUsername); // Recupere le champ utilisateur.
        editPassword = findViewById(R.id.editPassword); // Recupere le champ mot de passe.
        btnLogin = findViewById(R.id.btnLogin); // Recupere le bouton connexion.
        imgSettings = findViewById(R.id.imgSettings); // Recupere l'icone de reglages.
        txtSensitiveInfo = findViewById(R.id.txtSensitiveInfo); // Recupere le texte sensible.
        originalSensitiveText = txtSensitiveInfo.getText().toString(); // Sauvegarde le texte original.

        btnLogin.setOnClickListener(view -> handleLogin()); // Lance la verification quand l'utilisateur clique.
        imgSettings.setOnClickListener(view -> toggleTheme()); // Change le theme au clic sur l'icone.
        setupSensitiveTextProtection(); // Active la protection du texte sensible.
        setupDemoButtons(); // Active les boutons vers les autres ecrans.
    }

    private void handleLogin() {
        boolean overlayDetected = checkForOverlayPermission(); // Verifie le risque TapJacking avant la connexion.
        animateButton(btnLogin); // Anime le bouton pour donner un retour visuel.

        if (overlayDetected) { // Si un overlay est possible, on stoppe la connexion.
            return; // L'utilisateur doit d'abord lire l'avertissement.
        }

        String username = editUsername.getText() == null ? "" : editUsername.getText().toString().trim(); // Lit le nom sans espaces inutiles.
        String password = editPassword.getText() == null ? "" : editPassword.getText().toString().trim(); // Lit le mot de passe sans espaces inutiles.

        if (username.isEmpty() || password.isEmpty()) { // Verifie que les deux champs sont remplis.
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show(); // Informe l'utilisateur.
            return; // Stoppe la connexion si une information manque.
        }

        Toast.makeText(this, "Connexion en cours...", Toast.LENGTH_SHORT).show(); // Message de succes pour la demo.
    }

    private void toggleTheme() {
        int currentMode = AppCompatDelegate.getDefaultNightMode(); // Lit le mode actuel choisi par l'application.
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) { // Si le mode sombre est actif.
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Passe en mode clair.
        } else { // Sinon le mode clair ou systeme est actif.
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Passe en mode sombre.
        }
    }

    private void setupSensitiveTextProtection() {
        txtSensitiveInfo.setOnFocusChangeListener((view, hasFocus) -> { // Detecte un focus possible sur le texte sensible.
            if (hasFocus) { // Si le texte recoit le focus.
                txtSensitiveInfo.setText("Information protégée temporairement."); // Remplace par un message non sensible.
                handler.postDelayed(() -> txtSensitiveInfo.setText(originalSensitiveText), 3000); // Restaure apres 3 secondes.
            }
        });
    }

    private boolean checkForOverlayPermission() {
        if (Settings.canDrawOverlays(this)) { // Detecte si une application peut afficher une fenetre par-dessus.
            new MaterialAlertDialogBuilder(this) // Cree une boite de dialogue Material.
                    .setTitle("Risque de sécurité détecté") // Titre clair pour le risque.
                    .setMessage("Une permission d'overlay est active. Une application malveillante pourrait couvrir l'écran et provoquer un TapJacking.") // Explique le danger.
                    .setPositiveButton("Ouvrir les paramètres", (dialog, which) -> openOverlaySettings()) // Ouvre les reglages Android.
                    .setNegativeButton("Ignorer", (dialog, which) -> dialog.dismiss()) // Ferme l'avertissement si l'utilisateur ignore.
                    .show(); // Affiche la boite de dialogue.
            return true; // Signale qu'un risque existe.
        }
        return false; // Aucun risque overlay detecte.
    }

    private void openOverlaySettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION); // Prepare l'ecran Android des overlays.
        intent.setData(Uri.parse("package:" + getPackageName())); // Ouvre les reglages pour cette application.
        startActivity(intent); // Lance les parametres Android.
    }

    private void animateButton(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, View.SCALE_X, 1f, 1.08f, 1f); // Anime la largeur.
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, View.SCALE_Y, 1f, 1.08f, 1f); // Anime la hauteur.
        ObjectAnimator rotation = ObjectAnimator.ofFloat(button, View.ROTATION, 0f, 4f, 0f); // Anime une petite rotation.
        ObjectAnimator translation = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, 0f, -8f, 0f); // Anime un petit deplacement vertical.
        AnimatorSet set = new AnimatorSet(); // Groupe les animations.
        set.playTogether(scaleX, scaleY, rotation, translation); // Lance toutes les animations ensemble.
        set.setDuration(400); // Duree courte et visible.
        set.setInterpolator(new AccelerateDecelerateInterpolator()); // Rend le mouvement doux.
        set.start(); // Demarre l'animation.
    }

    private void setupDemoButtons() {
        MaterialButton btnThemeDemo = findViewById(R.id.btnThemeDemo); // Bouton vers les themes.
        MaterialButton btnAnimationDemo = findViewById(R.id.btnAnimationDemo); // Bouton vers les animations.
        MaterialButton btnSecurityDemo = findViewById(R.id.btnSecurityDemo); // Bouton vers la securite UX.

        btnThemeDemo.setOnClickListener(view -> startActivity(new Intent(this, ThemeTestActivity.class))); // Ouvre l'ecran theme.
        btnAnimationDemo.setOnClickListener(view -> startActivity(new Intent(this, AnimationDemoActivity.class))); // Ouvre l'ecran animation.
        btnSecurityDemo.setOnClickListener(view -> startActivity(new Intent(this, SecurityDemoActivity.class))); // Ouvre l'ecran securite.
    }
}
