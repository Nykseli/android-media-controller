package stream.mediacontroller.command;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.util.PreferenceStorage;

import static android.content.ContentValues.TAG;

public class KeyboardCommand extends AbstractCommand {


    public static String inputString(JSONObject additionalInfo){
        String commandString = "inputString";

        return getCommandString(KEYBOARD_INTERFACE, commandString, additionalInfo);
    }

    public static String inputString(String input){
        String commandString = "inputString";

        JSONObject additionalInfo = new JSONObject();
        try {
            additionalInfo.put("input", input);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getCommandString(KEYBOARD_INTERFACE, commandString, additionalInfo);
    }

    public static String pressEnter(){
        String commandString = "pressEnter";

        return getCommandString(KEYBOARD_INTERFACE, commandString);
    }

    public static String login(){

        String password = MainActivity.preferenceStorage.getString(PreferenceStorage.LOG_IN_PASSWORD, "");
        JSONObject inputCommand = null;
        JSONObject enterCommand = null;
        JSONObject additionalInfo = new JSONObject();
        try {
            additionalInfo.put("input", password);
            inputCommand = new JSONObject(inputString(additionalInfo));
            enterCommand = new JSONObject(pressEnter());
        } catch (JSONException e) {/* Ignore */}

        return getCommandArrayString(new JSONObject[]{inputCommand, enterCommand});

    }


    public static String rewindNetflixBackward(){
        String commandString = "pressArrowLeft";

        return getCommandString(KEYBOARD_INTERFACE, commandString);
    }

    public static String fastNetflixForward(){
        String commandString = "pressArrowRight";

        return getCommandString(KEYBOARD_INTERFACE, commandString);
    }

    public static String netflixVolumeUp(){
        String commandString = "pressArrowUp";

        return getCommandString(KEYBOARD_INTERFACE, commandString);
    }

    public static String netflixVolumeDown(){
        String commandString = "pressArrowDown";

        return getCommandString(KEYBOARD_INTERFACE, commandString);
    }

    public static String netflixMuteToggle(){
        // Netflix toggles mute with m key
        String input = "m";

        return inputString(input);
    }

    public static String netflixTogglePlayPause(){
        return pressEnter();
    }
}
