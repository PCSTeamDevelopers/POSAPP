package jo.com.pcstores.rpos.pos.Classes;

import android.app.Application;
import android.os.StrictMode;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class PCStoresPOS extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = null;
        try {
            Realm.init(this);
            config = new RealmConfiguration.Builder()
                    .name(Realm.DEFAULT_REALM_NAME)
                    .schemaVersion(0)
                    //.deleteRealmIfMigrationNeeded()
                    .migration(new Migration())
                    .build();
            Realm.setDefaultConfiguration(config);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();

            Realm realm = Realm.getInstance(config);
            realm.close();
        } catch (RealmMigrationNeededException e) {
            Realm.deleteRealm(config);
        }
    }
}
