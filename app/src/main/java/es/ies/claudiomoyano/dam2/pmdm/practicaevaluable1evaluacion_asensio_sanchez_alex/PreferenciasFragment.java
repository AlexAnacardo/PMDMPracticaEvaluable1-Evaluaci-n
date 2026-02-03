package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Locale;

public class PreferenciasFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat pref = findPreference("modo_oscuro");
        ListPreference idiomaPref = findPreference("idioma");

        if (pref != null) {
            pref.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean activado = (boolean) newValue;

                AppCompatDelegate.setDefaultNightMode(activado ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            });
        }

        if (idiomaPref != null) {
            idiomaPref.setOnPreferenceChangeListener((preference, newValue) -> {
                Toast.makeText(this.getContext(), R.string.toastCambioIdioma, Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }

}
