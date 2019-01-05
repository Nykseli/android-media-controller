package stream.mediacontroller.command;

public class AudioCommand extends AbstractCommand {

    /**
     * @return String (command that decreases system master volume)
     */
    public static String decreaseMasterVolume(){
        String commandString = "decreaseMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }

    /**
     * @return String (command that increases system master volume)
     */
    public static String increaseMasterVolume(){
        String commandString = "increaseMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }

    /**
     * @return String (command that mutes system master volume)
     */
    public static String muteMasterVolume(){
        String commandString = "muteMasterVolume";

        return getCommandString(AUDIO_INTERFACE, commandString);
    }
}
