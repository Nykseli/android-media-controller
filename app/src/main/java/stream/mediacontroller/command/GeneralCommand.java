package stream.mediacontroller.command;

import org.json.JSONObject;

public class GeneralCommand extends AbstractCommand {

    /**
     * @param additionalInfo {"absolutePath": String }
     * @return String (command that plays file in absolutePath with vlc)
     */
    public static String getFilesAndFolders(JSONObject additionalInfo) {
        String commandString = "getFilesAndFolders";

        return getCommandString(GENERAL_INTERFACE, commandString, additionalInfo);
    }
}
