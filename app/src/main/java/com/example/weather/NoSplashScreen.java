package com.example.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.SplashScreen;

public class NoSplashScreen implements SplashScreen {
    @Override
    @Nullable
    public View createSplashView(
            @NonNull Context context,
            @Nullable Bundle savedInstanceState
    ) {
        // Return a new MySplashView without saving a reference, because it
        // has no state that needs to be tracked or controlled.
        return null;
    }

    @Override
    public void transitionToFlutter(@NonNull Runnable onTransitionComplete) {
        // Immediately invoke onTransitionComplete because this SplashScreen
        // doesn't display a transition animation.
        //
        // Every SplashScreen *MUST* invoke onTransitionComplete at some point
        // for the splash system to work correctly.
        onTransitionComplete.run();
    }
}

