package stream.mediacontroller.button;

import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import stream.mediacontroller.R;
import stream.mediacontroller.websocket.Commands;

public class DefaultButtonHandler extends AbstractButton {

    public DefaultButtonHandler(View view){
        super(view);
    }

    @Override
    public void initButtons() {
        this.setMouseDown();
        this.setMouseUp();
        this.setMouseRight();
        this.setMouseLeft();
        this.setLeftClick();
        this.setIncreaseMasterVolume();
        this.setDecreaseMasterVolume();
        this.setLogIn();
    }

    private void setLeftClick(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.left_click), Commands.MOUSE_LEFT_CLICK);
    }

    private void setMouseRight(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_right), Commands.MOUSE_MOUSE_RIGHT);
    }

    private void setMouseLeft(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_left), Commands.MOUSE_MOUSE_LEFT);
    }

    private void setMouseDown(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_down), Commands.MOUSE_MOUSE_DOWN);
    }

    private void setMouseUp(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_up), Commands.MOUSE_MOUSE_UP);
    }

    private void setIncreaseMasterVolume(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.master_volume_up), Commands.AUDIO_INCREASE_MASTER_VOLUME);
    }

    private void setDecreaseMasterVolume(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.master_volume_down), Commands.AUDIO_DECREASE_MASTER_VOLUME);
    }

    private void setLogIn(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.default_log_in), Commands.KEYBOARD_LOGIN);
    }
}
