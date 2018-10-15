package co.edu.unal.triqui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfiguracionFragmento extends PreferenceFragment {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
