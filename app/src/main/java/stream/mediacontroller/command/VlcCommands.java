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

    /**
     * @param additionalInfo {"absolutePaths": String[]}
     * @return String (command that plays all the files in String array with vlc)
     */
    public static  String playFiles(JSONObject additionalInfo){
        String commandString = "playFiles";

        return getCommandString(VLC_INTERFACE, commandString, additionalInfo);
    }

    public static  String stopMedia(){
        String commandString = "stopMedia";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String playNextMedia(){
        String commandString = "playNextMedia";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String playPreviousMedia(){
        String commandString = "playPreviousMedia";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String increaseVolume(){
        String commandString = "increaseVolume";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String decreaseVolume(){
        String commandString = "decreaseVolume";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String muteVolume(){
        String commandString = "muteVolume";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String fastForward(){
        String commandString = "fastForward";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String rewind(){
        String commandString = "rewind";

        return getCommandString(VLC_INTERFACE, commandString);
    }

    public static String cycleAudio(){
        String commandString = "cycleAudioTrack";

        return getCommandString(VLC_INTERFACE, commandString);
    }
    public static String cycleSubtitle(){
        String commandString = "cycleSubtitleTrack";

        return getCommandString(VLC_INTERFACE, commandString);
    }

}
