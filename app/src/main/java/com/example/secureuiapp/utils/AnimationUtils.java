package com.example.secureuiapp.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public final class AnimationUtils {

    private AnimationUtils() {
        // Constructeur prive car cette classe contient seulement des methodes utiles.
    }

    public static void pulseAnimation(View view, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.12f, 1f); // Agrandit puis reduit la largeur.
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.12f, 1f); // Agrandit puis reduit la hauteur.
        AnimatorSet set = new AnimatorSet(); // Groupe les animations de pulsation.
        set.playTogether(scaleX, scaleY); // Joue les deux animations ensemble.
        set.setDuration(duration); // Utilise la duree donnee par l'activite.
        set.setInterpolator(new AccelerateDecelerateInterpolator()); // Rend le mouvement plus naturel.
        set.start(); // Lance l'animation.
    }

    public static void bounceAnimation(View view) {
        ObjectAnimator bounce = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -40f, 0f); // Deplace la carte vers le haut puis la remet.
        bounce.setDuration(450); // Duree courte pour un effet rebond.
        bounce.setInterpolator(new AccelerateDecelerateInterpolator()); // Mouvement doux.
        bounce.start(); // Lance l'animation.
    }

    public static void fadeInAnimation(View view, long delay) {
        view.setAlpha(0f); // Cache la vue au debut.
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f); // Anime l'apparition progressive.
        alpha.setStartDelay(delay); // Attend avant de commencer.
        alpha.setDuration(500); // Duree de l'apparition.
        alpha.start(); // Lance l'animation.
    }

    public static void enterAnimation(View view) {
        view.setTranslationY(80f); // Place la vue un peu plus bas au debut.
        view.setAlpha(0f); // Rend la vue invisible au debut.
        ObjectAnimator move = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 80f, 0f); // Ramene la vue a sa position normale.
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f); // Rend la vue visible.
        AnimatorSet set = new AnimatorSet(); // Groupe les animations d'entree.
        set.playTogether(move, alpha); // Joue le deplacement et l'apparition ensemble.
        set.setDuration(500); // Duree lisible pour l'utilisateur.
        set.setInterpolator(new AccelerateDecelerateInterpolator()); // Mouvement fluide.
        set.start(); // Lance l'animation.
    }

    public static void combinedMaterialAnimation(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 12f, -8f, 0f); // Combine une rotation visible.
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -35f, 0f); // Combine un deplacement vertical.
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.1f, 1f); // Combine un changement de largeur.
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.1f, 1f); // Combine un changement de hauteur.
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.8f, 1f); // Ajoute une legere variation d'opacite.
        AnimatorSet set = new AnimatorSet(); // Groupe toutes les animations.
        set.playTogether(rotation, translationY, scaleX, scaleY, alpha); // Lance tout en meme temps.
        set.setDuration(400); // Duree demandee autour de 400 ms.
        set.setInterpolator(new AccelerateDecelerateInterpolator()); // Interpolation douce.
        set.start(); // Demarre l'animation combinee.
    }
}
