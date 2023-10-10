package com.imera.censo;

import android.app.Application;
import android.content.Context;

/**
 * clase creada para obtener el contexto en cualquier lugar
 */
public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext(){
        return mContext;
    }
}
