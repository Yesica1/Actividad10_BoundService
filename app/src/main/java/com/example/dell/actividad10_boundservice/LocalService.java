package com.example.dell.actividad10_boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class LocalService extends Service
{
    private final IBinder lBinder = new LocalBinder();

    private final Random randomGenerator = new Random();

    public class LocalBinder extends Binder
    {
        LocalService getService()
        {
            return LocalService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return lBinder;
    }

    //Método que se llamara para mostrar el numero random
    public int getRandomNumber()
    {
        return randomGenerator.nextInt(100);
    }
}
