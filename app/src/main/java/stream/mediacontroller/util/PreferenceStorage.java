package stream.mediacontroller.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public  class PreferenceStorage {

    //TODO: make this a singleton
    /**
     * Name of the storage
     */
    private static final String STORAGE_NAME = "PREFERENCE_STORAGE";

    // Web Socket settings
    /**
     * Name of the storage key that is used to save url of the web socket connection
     */
    public static final String WEB_SOCKET_URL= "WEB_SOCKET_URL_STORAGE";

    // Security settings
    /**
     * Name of the storage key that is used to save encryption key
     */
    public static final String SECURITY_ENCRYPTION_KEY = "SECURITY_ENCRYPTION_KEY_STORAGE";
    /**
     * Name of the storage key that is used to save boolean that indicates whether the encryption is enabled
     */
    public static final String SECURITY_ENABLE_ENCRYPTION = "SECURITY_ENABLE_ENCRYPTION_STORAGE";

    // Setting values that are kept in ram since they are used really often
    /**
     * Variable that is used to keep boolean that indicates whether the encryption is enabled in ram.
     */
    // Since we check if encryption is enabled every time we send a message
    // it's good to save the boolean to ram so we don't need to read it from storage every time
    public static boolean SECURITY_IS_ENCRYPTION_ENABLED = false;
    /**
     * Variable that is used to keep encryption key in ram.
     */
    // We also need ne encryption key every time we crypt or decrypt message so to ram it goes!
    public static String SECURITY_CURRENT_ENCRYPTION_KEY = "";

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public PreferenceStorage(Context context){
        this.preference = context.getSharedPreferences(STORAGE_NAME, 0);
        this.editor = this.preference.edit();
        this.setRamValues();
    }

    /**
     * When object is created set values that we need constantly to variables
     * so we can get them from ram instead of reading from storage
     */
    private void setRamValues(){
        SECURITY_IS_ENCRYPTION_ENABLED = this.getBoolean(SECURITY_ENABLE_ENCRYPTION, false);
        SECURITY_CURRENT_ENCRYPTION_KEY = this.getString(SECURITY_ENCRYPTION_KEY, "");
    }

    /**
     * Save String value to storage with reference key <br />
     * If keyName is equal to  SECURITY_ENCRYPTION_KEY, redefine SECURITY_CURRENT_ENCRYPTION_KEY variable
     * @param keyName name of they storage key that can be used to get or delete the value later
     * @param value value that is saved to storage
     */
    public void saveString(String keyName, String value){
        // Save the value
        this.editor.putString(keyName, value);
        this.editor.commit();

        // Redefine SECURITY_CURRENT_ENCRYPTION_KEY variable if keyName is equal to SECURITY_ENCRYPTION_KEY
        if(keyName.equals(SECURITY_ENCRYPTION_KEY)){
            SECURITY_CURRENT_ENCRYPTION_KEY = value;
        }

    }

    /**
     * Get String value from the storage
     * @param keyName name of they storage key
     * @param defaultValue String that is returned if no value is found with keyName
     * @return value in storage or defaultValue
     */
    public String getString(String keyName, String defaultValue){
        return this.preference.getString(keyName, defaultValue);
    }

    /**
     * Save boolean  value to storage with reference key <br />
     * If keyName is equal to SECURITY_ENABLE_ENCRYPTION, redefine SECURITY_IS_ENCRYPTION_ENABLED variable
     * @param keyName name of they storage key that can be used to get or delete the value later
     * @param value value that is saved to storage
     */
    public void saveBoolean(String keyName, boolean value){
        // Save the value
        this.editor.putBoolean(keyName, value);
        this.editor.commit();

        // Redefine SECURITY_IS_ENCRYPTION_ENABLED variable if keyName is equal to SECURITY_ENABLE_ENCRYPTION
        if(keyName.equals(SECURITY_ENABLE_ENCRYPTION)){
            SECURITY_IS_ENCRYPTION_ENABLED = value;
        }
    }

    /**
     * Get boolean value from the storage
     * @param keyName name of they storage key
     * @param defaultValue boolean that is returned if no value is found with keyName
     * @return value in storage or defaultValue
     */
    public boolean getBoolean(String keyName, boolean defaultValue){
        return this.preference.getBoolean(keyName, defaultValue);
    }

    /**
     * Delete key value pair from storage
     * @param keyName name of the key that you want to delete
     */
    public void deleteValue(String keyName){
        this.editor.remove(keyName);
        this.editor.commit();
    }



}
