package com.example.secureuiapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.secureuiapp.utils.AnimationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class AnimationDemoActivity extends AppCompatActivity {

    private MaterialCardView demoCard; // Carte qui recoit les animations.
    private TextView txtDemoCard; // Texte affiche dans la carte.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appelle le comportement Android normal.
        setContentView(R.layout.activity_animation_demo); // Charge le layout XML des animations.

        demoCard = findViewById(R.id.demoCard); // Recupere la carte de demo.
        txtDemoCard = findViewById(R.id.txtDemoCard); // Recupere le texte de la carte.
        MaterialButton btnPulse = findViewById(R.id.btnPulse); // Bouton pulsation.
        MaterialButton btnBounce = findViewById(R.id.btnBounce); // Bouton rebond.
        MaterialButton btnFadeIn = findViewById(R.id.btnFadeIn); // Bouton apparition.
        MaterialButton btnEnter = findViewById(R.id.btnEnter); // Bouton entree.
        MaterialButton btnCombined = findViewById(R.id.btnCombined); // Bouton animation combinee.
        MaterialButton btnBack = findViewById(R.id.btnBackAnimation); // Bouton retour.

        btnPulse.setOnClickListener(view -> { // Detecte le clic sur le bouton pulsation.
            resetDemoCard(); // Remet la carte dans son etat normal.
            AnimationUtils.pulseAnimation(demoCard, 400); // Lance une pulsation.
        });

        btnBounce.setOnClickListener(view -> { // Detecte le clic sur le bouton rebond.
            resetDemoCard(); // Remet la carte dans son etat normal.
            AnimationUtils.bounceAnimation(demoCard); // Lance un rebond.
        });

        btnFadeIn.setOnClickListener(view -> { // Detecte le clic sur le bouton apparition.
            resetDemoCard(); // Remet la carte dans son etat normal.
            AnimationUtils.fadeInAnimation(demoCard, 0); // Lance une apparition.
        });

        btnEnter.setOnClickListener(view -> { // Detecte le clic sur le bouton entree.
            resetDemoCard(); // Remet la carte dans son etat normal.
            AnimationUtils.enterAnimation(demoCard); // Lance une entree.
        });

        btnCombined.setOnClickListener(view -> { // Detecte le clic sur le bouton animation combinee.
            resetDemoCard(); // Remet la carte dans son etat normal.
            AnimationUtils.combinedMaterialAnimation(demoCard); // Lance rotation, translation et scale.
        });
        btnBack.setOnClickListener(view -> finish()); // Retourne a l'ecran precedent.

        animateEntrance(); // Anime la carte au demarrage.
    }

    private void resetDemoCard() {
        demoCard.animate().cancel(); // Stoppe les animations Android simples en cours.
        demoCard.setAlpha(1f); // Restaure l'opacite normale.
        demoCard.setScaleX(1f); // Restaure la largeur normale.
        demoCard.setScaleY(1f); // Restaure la hauteur normale.
        demoCard.setRotation(0f); // Restaure la rotation normale.
        demoCard.setTranslationY(0f); // Restaure la position verticale normale.
        txtDemoCard.setText("Carte de démonstration"); // Remet le texte de base.
    }

    private void animateEntrance() {
        resetDemoCard(); // Assure un depart propre.
        AnimationUtils.enterAnimation(demoCard); // Lance une animation d'entree au chargement.
    }
}
