package stream.xlinuxtools.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public  class PreferenceStorage {

    //TODO: make this a singleton
    private static final String STORAGE_NAME = "PREFERENCE_STORAGE";

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public PreferenceStorage(Context context){
        this.preference = context.getSharedPreferences(STORAGE_NAME, 0);
        this.editor = this.preference.edit();
    }

    public void saveString(String keyName, String value){
        this.editor.putString(keyName, value);
        this.editor.commit();
    }

    public String getString(String keyName){
        return this.preference.getString(keyName, "");
    }


    public void saveBoolean(String keyName, boolean value){
        this.editor.putBoolean(keyName, value);
        this.editor.commit();
    }

    public boolean getBoolean(String keyName){
        return this.preference.getBoolean(keyName, true);
    }

    public void deleteValue(String keyName){
        this.editor.remove(keyName);
        this.editor.commit();
    }




}
