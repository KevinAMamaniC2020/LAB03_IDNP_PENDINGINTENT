package com.example.lab03_idnp_pendingintent;


import android.widget.TextView;

public class UIUpdater {
    private static UIUpdater instance;
    private TextView batteryTextView;

    private UIUpdater() {}

    public static UIUpdater getInstance() {
        if (instance == null) {
            instance = new UIUpdater();
        }
        return instance;
    }

    public void setBatteryTextView(TextView batteryTextView) {
        this.batteryTextView = batteryTextView;
    }

    public void updateBatteryLevel(String batteryLevel) {
        if (batteryTextView != null) {
            batteryTextView.setText(batteryLevel);
        }
    }
}
