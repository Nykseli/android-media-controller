package stream.xlinuxtools.button;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import stream.xlinuxtools.MainActivity;
import stream.xlinuxtools.websocket.Commands;
import stream.xlinuxtools.websocket.RepeatListener;

public abstract class AbstractButton {
    View view;
    // Defines how long button needs to be pressed before it starts to repeat every NORMAL_INTERVAL milliseconds
    public static final int INITIAL_INTERVAL = 400;
    public static final int NORMAL_INTERVAL = 100;

    public AbstractButton(View view){
        this.view = view;
    }

    public void setSingleClickButton(Button button, final Commands command){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.webSocket.sendCommand(command);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setHoldableButton(Button button, final Commands command){
        button.setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, new View.OnClickListener() {
            public void onClick(View v){
                MainActivity.webSocket.sendCommand(command);
            }
        }));
    }

    public abstract void initButtons();

}