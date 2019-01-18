package stream.mediacontroller.command;

import org.json.JSONArray;
import org.json.JSONObject;

abstract class AbstractCommand {

    static final String AUDIO_INTERFACE = "audio";
    static final String CONFIG_INTERFACE= "config";
    static final String GENERAL_INTERFACE= "general";
    static final String KEYBOARD_INTERFACE = "keyboard";
    static final String MOUSE_INTERFACE= "mouse";
    static final String VLC_INTERFACE = "vlc";


    /**
     * Return command string that contains additional info with following structure
     * <p>
     * {
     *      "interface": String,
     *      "command": String,
     *      "additionalInfo": JSONObject
     * }
     * @param commandInterface String that contains what interface client wants to use
     * @param commandString String that tells what interface function client wants to use
     * @param additionalInfo Object that contains parameters that the interface function uses
     * @return String
     */
    static String getCommandString(String commandInterface, String commandString, JSONObject additionalInfo){

        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);
            commandObject.put("additionalInfo", additionalInfo);

            return commandObject.toString();

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }

    static JSONObject getCommandJson(String commandInterface, String commandString, JSONObject additionalInfo){

        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);
            commandObject.put("additionalInfo", additionalInfo);

            return commandObject;

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }
    /**
     * Return command string without additional info with following structure
     * <p>
     * {
     *      "interface": String,
     *      "command": String,
     * }
     * @param commandInterface String that contains what interface client wants to use
     * @param commandString String that tells what interface function client wants to use
     * @return String
     */
    static String getCommandString(String commandInterface, String commandString){
        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);

            return commandObject.toString();

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }

    static JSONObject getCommandJson(String commandInterface, String commandString){
        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);

            return commandObject;

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }

    static String getCommandArrayString(JSONObject[] jsonObjects){
        try {
            JSONArray commandArray = new JSONArray();
            for (JSONObject jObj: jsonObjects) {
                commandArray.put(jObj);
            }

            return commandArray.toString();

        }catch (Exception e){ /* Ignore*/ }
        // return null if something goes wrong
        return null;
    }
}
