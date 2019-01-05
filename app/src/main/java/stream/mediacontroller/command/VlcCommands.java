package stream.mediacontroller.command;

import org.json.JSONObject;

public class VlcCommands extends AbstractCommand {

    /**
     * @return String (command that toggles vlc media pause on/off)
     */
    public static String pauseFile(){
        String commandString = "pauseFile";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    /**
     * @param additionalInfo {"absolutePath": String }
     * @return String (command that plays file in absolutePath with vlc)
     */
    public static String playFile(JSONObject additionalInfo){
        String commandString = "playFile";

        return getCommandString(VLC_INTERFACE, commandString, additionalInfo);
    }

}
