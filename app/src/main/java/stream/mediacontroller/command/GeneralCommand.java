package stream.mediacontroller.command;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneralCommand extends AbstractCommand {

    public static String getFilesAndFolders(String absolutePath) {
        String commandString = "getFilesAndFolders";

        JSONObject optionalInfo = new JSONObject();
        try {
            optionalInfo.put("absolutePath", absolutePath);
        } catch (JSONException e) {/* Ignore */}

        return getCommandString(GENERAL_INTERFACE, commandString, optionalInfo);
    }
}
