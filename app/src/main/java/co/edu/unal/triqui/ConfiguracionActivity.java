package co.edu.unal.triqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ConfiguracionFragmento())
                .commit();
    }
}
