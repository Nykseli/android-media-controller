package stream.mediacontroller.command;

import org.json.JSONException;
import org.json.JSONObject;

public class MouseCommand extends AbstractCommand {

    // defines default mouse movement in pixels
    private static final int PIXEL_AMOUNT = 10;
    private static final int NEGATIVE_PIXEL_AMOUNT = PIXEL_AMOUNT * -1;

    /* Socket command implementations start */

    /**
     * @param additionalInfo {"amount": int}
     * @return String (command moves mouse on y axis by x amount that is defined by amount. amount can be negative. )
     */
    public static String moveMouseX(JSONObject additionalInfo){
        String commandString = "moveMouseX";

        return getCommandString(MOUSE_INTERFACE, commandString, additionalInfo);
    }

    /**
     * @param additionalInfo {"amount": int}
     * @return String (command moves mouse on y axis by x amount that is defined by amount. amount can be negative. )
     */
    public static String moveMouseY(JSONObject additionalInfo){
        String commandString = "moveMouseY";

        return getCommandString(MOUSE_INTERFACE, commandString, additionalInfo);
    }

    /**
     * @return String (command that clicks with left mouse button )
     */
    public static String leftMouseClick(){
        String commandString = "leftMouseClick";

        return getCommandString(MOUSE_INTERFACE, commandString);
    }

    /**
     * @param additionalInfo {"x": int, "y": int}
     * @return String (command that sets mouse position to x,y coordinate )
     */
    public static String setMousePosition(JSONObject additionalInfo){
        String commandString = "setMousePosition";

        return getCommandString(MOUSE_INTERFACE, commandString, additionalInfo);
    }

    /* Socket command implementations end */

    /* Methods for client */

    private static JSONObject mouseMoveObject(int amount){
        JSONObject additionalInfo = new JSONObject();
        try {
            additionalInfo.put("amount", amount);
        } catch (JSONException e) {/* Ignore */}
        return  additionalInfo;
    }

    public  static String mouseUp(){
        return moveMouseY(mouseMoveObject(NEGATIVE_PIXEL_AMOUNT));
    }

    public static String mouseDown(){
        return moveMouseY(mouseMoveObject(PIXEL_AMOUNT));
    }

    public static String mouseLeft(){
        return moveMouseX(mouseMoveObject(NEGATIVE_PIXEL_AMOUNT));
    }

    public static String mouseRight(){
        return moveMouseX(mouseMoveObject(PIXEL_AMOUNT));
    }

    private static JSONObject[] getPositionClick(int x, int y){
        JSONObject positionCommand = null;
        JSONObject clickCommand = null;
        JSONObject additionalInfo = new JSONObject();
        try {
            additionalInfo.put("x",x);
            additionalInfo.put("y",y);
            positionCommand = new JSONObject(setMousePosition(additionalInfo));
            clickCommand = new JSONObject(leftMouseClick());
        } catch (JSONException e) {/* Ignore */}

        return new JSONObject[]{positionCommand, clickCommand};
    }

    public static String skipNetflixIntro(){
        return getCommandArrayString(getPositionClick(1748, 884));
    }

    public static String nextNetflixEpisode(){
        return getCommandArrayString(getPositionClick(1487, 1013));

    }

    public static String rewindNetflixBackward(){
        return getCommandArrayString(getPositionClick(205, 1011));

    }

    public static String fastNetflixForward(){
        return getCommandArrayString(getPositionClick(324, 1020));

    }
}
