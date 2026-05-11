package com.example.secureuiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ThemeTestActivity extends AppCompatActivity {

    private SwitchMaterial switchDarkMode; // Interrupteur pour changer le theme.
    private TextView txtThemeStatus; // Texte qui affiche le mode actuel.
    private MaterialCardView demoCard; // Carte Material pour montrer le style.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appelle le cycle normal Android.
        setContentView(R.layout.activity_theme_test); // Charge le layout XML du test des themes.

        switchDarkMode = findViewById(R.id.switchDarkMode); // Recupere le switch du mode sombre.
        txtThemeStatus = findViewById(R.id.txtThemeStatus); // Recupere le texte de statut.
        demoCard = findViewById(R.id.demoThemeCard); // Recupere la carte de demonstration.
        MaterialButton btnBack = findViewById(R.id.btnBackTheme); // Recupere le bouton retour.

        switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES); // Synchronise le switch.
        updateThemeStatus(); // Affiche le statut au demarrage.

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> { // Detecte le changement du switch.
            if (isChecked) { // Si le switch active le mode sombre.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Active le mode sombre.
            } else { // Sinon le switch demande le mode clair.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Active le mode clair.
            }
            updateThemeStatus(); // Met a jour le texte.
        });

        btnBack.setOnClickListener(view -> finish()); // Ferme l'activite et revient a l'ecran precedent.
    }

    private void updateThemeStatus() {
        boolean darkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES; // Verifie le mode choisi.
        String status = darkMode ? "Mode actuel: sombre" : "Mode actuel: clair"; // Prepare le texte a afficher.
        txtThemeStatus.setText(status); // Affiche le statut du theme.
        animateCardAlpha(demoCard); // Anime la carte pour montrer le changement.
    }

    private void animateCardAlpha(View view) {
        view.setAlpha(0.4f); // Rend la carte legerement transparente au debut.
        view.animate().alpha(1f).setDuration(350).start(); // Restaure l'opacite avec une animation simple.
    }
}
