package stream.mediacontroller.command;

import org.json.JSONException;
import org.json.JSONObject;

public class VlcCommands extends AbstractCommand {

    public static String pauseFile(){
        String commandString = "pauseFile";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String playFile(String absolutePath){
        String commandString = "playFile";

        JSONObject optionalInfo = new JSONObject();
        try {
            optionalInfo.put("absolutePath", absolutePath);
        } catch (JSONException e) {/* Ignore */}

        return getCommandString(VLC_INTERFACE, commandString, optionalInfo);
    }

}
