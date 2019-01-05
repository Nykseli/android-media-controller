package stream.mediacontroller.command;

public class ConfigCommand extends AbstractCommand{

    /**
     * @return String (command asks server to send config to client)
     */
    public static String getConfig(){
        String commandString = "getConfig";
        return getCommandString(CONFIG_INTERFACE, commandString);
    }
}
