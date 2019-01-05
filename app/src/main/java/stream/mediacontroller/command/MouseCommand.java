package stream.mediacontroller.command;

import org.json.JSONException;
import org.json.JSONObject;

public class MouseCommand extends AbstractCommand {

    // defines default mouse movement in pixels
    private static final int PIXEL_AMOUNT = 10;
    private static final int NEGATIVE_PIXEL_AMOUNT = PIXEL_AMOUNT * -1;

    /* Socket command implementations start */
    public static String moveMouseX(int amount){
        String commandString = "moveMouseX";

        JSONObject optionalInfo = new JSONObject();
        try {
            optionalInfo.put("amount", amount);
        } catch (JSONException e) {/* Ignore */}

        return getCommandString(MOUSE_INTERFACE, commandString, optionalInfo);
    }

    public static String moveMouseY(int amount){
        String commandString = "moveMouseY";

        JSONObject optionalInfo = new JSONObject();
        try {
            optionalInfo.put("amount", amount);
        } catch (JSONException e) {/* Ignore */}

        return getCommandString(MOUSE_INTERFACE, commandString, optionalInfo);
    }

    public static String leftMouseClick(){
        String commandString = "leftMouseClick";

        return getCommandString(MOUSE_INTERFACE, commandString);
    }

    public static String setMousePosition(int x, int y){
        String commandString = "setMousePosition";

        JSONObject optionalInfo = new JSONObject();
        try {
            optionalInfo.put("x", x);
            optionalInfo.put("y", y);
        } catch (JSONException e) {/* Ignore */}


        return getCommandString(MOUSE_INTERFACE, commandString, optionalInfo);
    }

    /* Socket command implementations end */

    /* Methods for client */
    public  static String mouseUp(){
        return moveMouseY(NEGATIVE_PIXEL_AMOUNT);
    }

    public static String mouseDown(){
        return moveMouseY(PIXEL_AMOUNT);
    }

    public static String mouseLeft(){
        return moveMouseX(NEGATIVE_PIXEL_AMOUNT);
    }

    public static String mouseRight(){
        return moveMouseX(PIXEL_AMOUNT);
    }

    public static String skipNetflixIntro(){
        JSONObject positionCommand = null;
        JSONObject clickCommand = null;
        try {
            positionCommand = new JSONObject(setMousePosition(1748, 884));
            clickCommand = new JSONObject(leftMouseClick());
        } catch (JSONException e) {/* Ignore */}

        return getCommandArrayString(positionCommand, clickCommand);
    }

    public static String nextNetflixEpisode(){
        JSONObject positionCommand = null;
        JSONObject clickCommand = null;
        try {
            positionCommand = new JSONObject(setMousePosition(1487, 1013));
            clickCommand = new JSONObject(leftMouseClick());
        } catch (JSONException e) {/* Ignore */}

        return getCommandArrayString(positionCommand, clickCommand);
    }

    public static String rewindNetflixBackward(){
        JSONObject positionCommand = null;
        JSONObject clickCommand = null;
        try {
            positionCommand = new JSONObject(setMousePosition(205, 1011));
            clickCommand = new JSONObject(leftMouseClick());
        } catch (JSONException e) {/* Ignore */}

        return getCommandArrayString(positionCommand, clickCommand);
    }

    public static String fastNetflixForward(){
        JSONObject positionCommand = null;
        JSONObject clickCommand = null;
        try {
            positionCommand = new JSONObject(setMousePosition(324, 1020));
            clickCommand = new JSONObject(leftMouseClick());
        } catch (JSONException e) {/* Ignore */}

        return getCommandArrayString(positionCommand, clickCommand);
    }
}
