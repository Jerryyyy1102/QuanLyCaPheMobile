package com.example.myapplication.Database;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("qlcprealm.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(config);


    }
}
