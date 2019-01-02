package stream.mediacontroller.fragment;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import java.net.URI;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.websocket.WebSocket;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_general, rootKey);

        final EditTextPreference webSocketUri = (EditTextPreference) findPreference("web_socket_uri");
        webSocketUri.setSummary(webSocketUri.getText());

        webSocketUri.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d(TAG, "onPreferenceChange: " + preference.toString());
                Log.d(TAG, "onPreferenceChange: " + newValue.toString());
                webSocketUri.setSummary(newValue.toString());
                webSocketUri.setText(newValue.toString());
                //TODO: WEB_SOCKET_URL needs to be set to a default value somewhere
                try {
                    MainActivity.webSocket.close();
                    MainActivity.webSocket = new WebSocket(new URI(newValue.toString()));
                    MainActivity.webSocket.connect();
                    Log.d(TAG, "onPreferenceChange: connect to socket" );
                    MainActivity.preferenceStorage.saveString("WEB_SOCKET_URL", newValue.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }
}