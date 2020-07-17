package com.supercasual.braintrainer.utils;

import android.content.Context;

class Level1 {

    private Context context;
    private static Level1 instance;

    private Level1(Context context) {
        this.context = context;
    }

    public static synchronized Level1 get(Context context) {
        if (instance == null) instance = new Level1(context);
        return instance;
    }

    public void getExample() {

    }
}
