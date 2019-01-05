package stream.mediacontroller.command;

public class ConfigCommand extends AbstractCommand{

    public static String getConfig(){
        String commandString = "getConfig";
        return getCommandString(CONFIG_INTERFACE, commandString);
    }
}
