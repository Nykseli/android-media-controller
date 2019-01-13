package stream.mediacontroller.button;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.websocket.Commands;
import stream.mediacontroller.lib.RepeatListener;

public abstract class AbstractButton {
    /**
     * AbstractButton has View variable since it's needed to bind the buttons from ui to logic in java
     */
    View view;

    /**
     * Defines how long button needs to be pressed before it starts to repeat every NORMAL_INTERVAL milliseconds
     */
    public static final int INITIAL_INTERVAL = 400;

    /**
     * Defines how often hold button sends data to server after INITIAL_INTERVAL
     */
    public static final int NORMAL_INTERVAL = 100;

    public AbstractButton(View view){
        this.view = view;
    }

    /**
     * setSingleClickButton binds WebSocket command to button that sends command every time it's clicked
     * Use this if the button doesn't need to send any additional info to server
     * @param button The button that you want bind the command
     * @param command The command enum. List of commands: in websocket.Commands.java
     */
    public void setSingleClickButton(Button button, final Commands command){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.webSocket.sendCommand(command, null);
            }
        });
    }

    /**
     * setSingleClickButton binds WebSocket command to button that sends command every time it's clicked
     * Use this if the button needs to send any additional info to server
     * @param button The button that you want bind the command
     * @param command The command enum. List of commands: in websocket.Commands.java
     */
    public void setSingleClickButton(Button button, final Commands command, final JSONObject additionalInfo){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.webSocket.sendCommand(command, additionalInfo);
            }
        });
    }

    /**
     * setHoldableButton binds WebSocket command to button that sends command every time it's clicked
     * and when it's hold for INITIAL_INTERVAL milliseconds, it starts to sen commands to server every NORMAL_INTERVAL milliseconds
     * Use this if the button needs to send any additional info to server
     * @param button The button that you want bind the command
     * @param command The command enum. List of commands: in websocket.Commands.java
     */
    @SuppressLint("ClickableViewAccessibility")
    public void setHoldableButton(Button button, final Commands command){
        button.setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, new View.OnClickListener() {
            public void onClick(View v){
                MainActivity.webSocket.sendCommand(command, null);
            }
        }));
    }

    /**
     * Every button class should use same function to init buttons.
     */
    public abstract void initButtons();

}
