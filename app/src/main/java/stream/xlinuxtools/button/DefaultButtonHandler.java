package stream.xlinuxtools.button;

import android.view.View;
import android.widget.Button;

import stream.xlinuxtools.R;
import stream.xlinuxtools.websocket.Commands;

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
        this.setSkipNetflixIntro();
        this.setLeftClick();
    }

    private void setSkipNetflixIntro(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_intro), Commands.SKIP_NETFLIX_INTRO);

    }

    private void setLeftClick(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.left_click), Commands.LEFT_CLICK);
    }

    private void setMouseRight(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_right), Commands.MOUSE_RIGHT);
    }

    private void setMouseLeft(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_left), Commands.MOUSE_LEFT);
    }

    private void setMouseDown(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_down), Commands.MOUSE_DOWN);
    }

    private void setMouseUp(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.mouse_up), Commands.MOUSE_UP);

    }
}
