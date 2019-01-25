package stream.mediacontroller.button;

import android.view.View;
import android.widget.Button;

import stream.mediacontroller.R;
import stream.mediacontroller.websocket.Commands;

public class NetflixButtonHandler extends AbstractButton {

    public NetflixButtonHandler(View view){
        super(view);
    }

    @Override
    public void initButtons() {
        this.setSkipNetflixIntro();
        this.setNextNetflixEpisode();
        this.setRewindNetflixBackward();
        this.setFastNetflixForward();
        this.setNetflixPlayPause();
        this.setNetflixVolumeUp();
        this.setNetflixVolumeDown();
        this.setNetflixVolumeMute();
    }

    private void setSkipNetflixIntro(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_intro), Commands.MOUSE_SKIP_NETFLIX_INTRO);
    }

    private void setNextNetflixEpisode(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_episode), Commands.MOUSE_NEXT_NETFLIX_EPISODE);
    }

    private void setRewindNetflixBackward(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.netflix_backward), Commands.KEYBOARD_REWIND_NETFLIX_BACKWARD);
    }

    private void setFastNetflixForward(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.netflix_forward), Commands.KEYBOARD_FAST_NETFLIX_FORWARD);
    }

    private void setNetflixPlayPause(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_play_pause), Commands.KEYBOARD_NETFLIX_TOGGLE_PAUSE);
    }

    private void setNetflixVolumeUp(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.netflix_volume_up), Commands.KEYBOARD_NETFLIX_VOLUME_UP);
    }

    private void setNetflixVolumeDown(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.netflix_volume_down), Commands.KEYBOARD_NETFLIX_VOLUME_DOWN);
    }

    private void setNetflixVolumeMute(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_volume_mute), Commands.KEYBOARD_NETFLIX_VOLUME_MUTE);
    }
}
