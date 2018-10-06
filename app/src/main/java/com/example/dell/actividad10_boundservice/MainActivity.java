package com.example.dell.actividad10_boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    LocalService localService;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // NOTA: Cada vez que inicie la aplicacion esta activado el onStart por lo que se podrá mostrar el numero random
    // hasta que se detenga el servicio.

    //Servicio que se inicia cuando el usuario ejecuta la aplicacion hasta que se detenga el servicio
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE); //*
    }

    @Override
    //Se ejecuta cuando el usuario no utiliza la aplicacion, en este caso se detendrá cuando el usuario
    // le de clic al boton necesarios.
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(mConnection);  //*
            isBound = false;
        }
    }

    //Este es el método que se llamará cuando el usuario le de clic al boton de detener y dentro de este método
    //se manda a llamar el servicio de onStop() para cada vez que este se ejecute el otro servicio tambien.
    public void detenerServicio(View view) {
        Toast.makeText(this, "Servicio Detenido", Toast.LENGTH_SHORT).show();
        onStop();
    }

    //Este es el método que se llamará cuando el usuario le de clic al boton de iniciar y dentro de este método
    //se manda a llamar el servicio de onStart() para cada vez que este se ejecute el otro servicio tambien.
    public void iniciarServicio(View view) {
        Toast.makeText(this, "Servicio Iniciado", Toast.LENGTH_SHORT).show();
        onStart();
    }

    //Este se ejecuta cuando se le da clic al boton de mostrar numero random, es por ello que este maandará a
    // llamar el método .getRandomNumber() para poder mostrar el número.
    public void numeroRandom(View view) {
        if (isBound) {
            int num = localService.getRandomNumber();
            Toast.makeText(this, "number: "+num, Toast.LENGTH_SHORT).show();
        }
        // Si el servicio no esta activado, se mostrará el siguiente mensaje
        else{
            Toast.makeText(this, "Active el servicio para mostrar el número aleatorio", Toast.LENGTH_SHORT).show();
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            localService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}