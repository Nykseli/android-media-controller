package stream.xlinuxtools.util;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigManager {

    // config contains the full config object
    private JSONObject config;

    // subconfigs e.g. vlcConfig goes here
    private JSONObject vlcConfig;

    public ConfigManager(){
        this.config = null;
        this.vlcConfig = null;
    }

    public void loadConfig(JSONObject jsonObject){

        try {
            this.config = jsonObject.getJSONObject("config");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(this.isConfigLoaded()){
            this.loadVlcConfig();
        }
    }
    public void loadConfig(String jsonString){
        try {
            this.config = new JSONObject(jsonString).getJSONObject("config");
        } catch (Exception e) {
            // Ignore
        }

        if(this.isConfigLoaded()){
            this.loadVlcConfig();
        }
    }

    public boolean isConfigLoaded(){
        return this.config != null;
    }

    public boolean isVlcConfigLoaded(){
        return this.vlcConfig != null;
    }

    public void loadVlcConfig(){
        try {
            this.vlcConfig = this.config.getJSONObject("vlc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAllowedFilePath(){
        //TODO: support multiple file paths
        String filePath = null;
        try {
            filePath = this.vlcConfig.getJSONArray("allowedFilePaths").getString(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    // This is not currently used maybe used later
    public JSONObject getVlcFolderObject(){
        JSONObject fileObject = null;

        // Files are empty since we only list files
        JSONArray files = new JSONArray();
        // Path is empty since the folder name is absolute path
        String path = "";

        try{
            JSONArray folders = this.vlcConfig.getJSONArray("allowedFilePaths");

            fileObject.put("files", files);
            fileObject.put("currentPath", path);
            fileObject.put("folders", folders);
        } catch (JSONException e) {
            // Ignore
        }

        return fileObject;
    }
}
