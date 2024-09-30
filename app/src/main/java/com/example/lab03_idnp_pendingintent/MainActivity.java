package com.example.lab03_idnp_pendingintent;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*
    *   PendingIntent: se lanza junto con un broadcast
    *   AlarmManager: Se declara para manejar el lanzamiento del pendingIntent
    *   TextView: Parte de la inerfaz
    */

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private TextView batteryLevelText;

    /*
     *OnCreate: inicializa los componentes para manejar la bateria
     *   - setContentView: comunicacion con los complementos de interfaz
     *   - batteryLevelText: muestra el texto de la bateria
     *   - UIUpdater: Creamos un singleton para mostrar el nivel de bateria
     *   - intent: lo usamos solo para lanzar el receptor de bateria, es decir su informacion
     *   - pendingIntent: crea el pendingIntent donde
     *      - FLAG_UPDATE_CURRENT: actualiza en pending en lugar de crear uno nuevo
     *      - FLAG_IMMUTABLE: Indica que el pending no debe ser modificado
     *   - alarmManager:se programa una ejecucion periodica en el Pending Intent
     *   - interval y triggerAtMillis: Configura una alarma que se disparará periódicamente cada 60 segundos
     *   - log: Imprime los registros de la APP
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevelText = findViewById(R.id.batteryLevelText);
        UIUpdater.getInstance().setBatteryTextView(batteryLevelText);
        Intent intent = new Intent(this, ReceptorBateria.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long interval = 60 * 1000; // 60 segundos
        long triggerAtMillis = System.currentTimeMillis() + interval;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, interval, pendingIntent);

        Log.i("MainActivity", "PendingIntent: Registrado satisfactoriamente.");
    }

    /*
    *On Destroy: Libera recursos cuando la app no se esta usando
    *   -alarmManager.cancel: Cancela la alarma de actualizacion
    *   -Esto evita que el PendingIntent no se siga actualizando en segundo plano
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.i("MainActivity", "PendingIntent:  Cancelado satisfactoriamente.");
        }
    }
}
