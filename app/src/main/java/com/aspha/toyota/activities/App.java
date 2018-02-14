package com.aspha.toyota.activities;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by Kajiva Kinsley on 2/5/2018.
 */

public class App extends Application {
    @Override
    public void onCreate () {
        super.onCreate ();
        Realm.init ( this );
        Realm.setDefaultConfiguration (
                new RealmConfiguration.Builder ()
                        .deleteRealmIfMigrationNeeded ()
                        .build ()
        );
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
