package stream.mediacontroller.fragment;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import java.net.URI;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.util.PreferenceStorage;
import stream.mediacontroller.websocket.WebSocket;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_general, rootKey);

        this.initWebSocketOptions();
        this.initSecurityOptions();
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initWebSocketOptions(){

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
                    MainActivity.webSocket.asyncConnect();
                    Log.d(TAG, "onPreferenceChange: connect to socket" );
                    MainActivity.preferenceStorage.saveString(PreferenceStorage.WEB_SOCKET_URL, newValue.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    private void initSecurityOptions(){
        final SwitchPreferenceCompat enableEncryption = (SwitchPreferenceCompat) findPreference("enable_encryption");

        enableEncryption.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String encryptionKey = MainActivity.preferenceStorage.getString(PreferenceStorage.SECURITY_ENCRYPTION_KEY, "");
                // 128 bit AES encryption needs exactly 16 character key
                if(encryptionKey.length() == 16) {
                    enableEncryption.setChecked((boolean) newValue);
                    MainActivity.preferenceStorage.saveBoolean(PreferenceStorage.SECURITY_ENABLE_ENCRYPTION, (boolean) newValue);
                }else if (encryptionKey.length() == 0){
                    MainActivity.infoPopUp.showShortMessage("Encryption key needs to be set!");

                }else{
                    MainActivity.infoPopUp.showShortMessage("Encryption key needs to be 16 characters long!");
                }
                return false;
            }
        });

        final EditTextPreference encryptionKey = (EditTextPreference) findPreference("encryption_key");

        encryptionKey.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String valueString = newValue.toString();
                // 128 bit AES encryption needs exactly 16 character key
                if(valueString.length() == 16) {
                    encryptionKey.setText(valueString);
                    MainActivity.preferenceStorage.saveString(PreferenceStorage.SECURITY_ENCRYPTION_KEY, valueString);
                }else {
                    MainActivity.infoPopUp.showShortMessage("Encryption key needs to be 16 characters long!");
                }
                return false;
            }
        });
    }
}