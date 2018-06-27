package jo.com.pcstores.rpos.pos.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import jo.com.pcstores.rpos.R;

public class MyPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pospreference);
    }
}
