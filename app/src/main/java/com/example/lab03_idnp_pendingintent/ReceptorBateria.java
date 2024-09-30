package com.example.lab03_idnp_pendingintent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class ReceptorBateria extends BroadcastReceiver {

    /*
     *OnReceive():
     *   - Se pasa un objeto Context y un objeto Intent.
     *   - El Intent contiene los datos enviados en el broadcast.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * level: obtenemos el nivel actual de la bateria
         * scale: Obtenemos el nivel maximo de la bateria
         *  batteryPCT: Calcula el porcentaje de batería
         */
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPct = (int) ((level / (float) scale) * 100);

        String message = "Nivel de Bateria: " + batteryPct + "%";

        //Actualizamos el textView pero  esta vez usando un singleton
        UIUpdater.getInstance().updateBatteryLevel(message);
    }
}
