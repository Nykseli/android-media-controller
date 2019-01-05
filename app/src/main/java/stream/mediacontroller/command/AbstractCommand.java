package stream.mediacontroller.command;

import org.json.JSONArray;
import org.json.JSONObject;

abstract class AbstractCommand {

    static final String AUDIO_INTERFACE = "audio";
    static final String CONFIG_INTERFACE= "config";
    static final String GENERAL_INTERFACE= "general";
    static final String MOUSE_INTERFACE= "mouse";
    static final String VLC_INTERFACE = "vlc";

    static String getCommandString(String commandInterface, String commandString, JSONObject optionalInfo){

        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);
            commandObject.put("optionalInfo", optionalInfo);

            return commandObject.toString();

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }

    static JSONObject getCommandJson(String commandInterface, String commandString, JSONObject optionalInfo){

        try{
            JSONObject commandObject = new JSONObject();
            commandObject.put("interface", commandInterface);
            commandObject.put("command", commandString);
            commandObject.put("optionalInfo", optionalInfo);

            return commandObject;

        }catch (Exception e){/*Ignore*/}
        // return null if something goes wrong
        return null;
    }

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

    static String getCommandArrayString(JSONObject... jsonObjects){
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
