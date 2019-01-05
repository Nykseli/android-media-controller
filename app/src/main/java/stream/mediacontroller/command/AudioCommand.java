package stream.mediacontroller.command;

public class AudioCommand extends AbstractCommand {

    public static String decreaseMasterVolume(){
        String commandString = "decreaseMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }

    public static String increaseMasterVolume(){
        String commandString = "increaseMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }

    public static String muteMasterVolume(){
        String commandString = "muteMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }
}
