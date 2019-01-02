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
    }

    private void setSkipNetflixIntro(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_intro), Commands.SKIP_NETFLIX_INTRO);
    }

    private void setNextNetflixEpisode(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_episode), Commands.NEXT_NETFLIX_EPISODE);
    }

    private void setRewindNetflixBackward(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_backward), Commands.REWIND_NETFLIX_BACKWARD);
    }

    private void setFastNetflixForward(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.netflix_forward), Commands.FAST_NETFLIX_FORWARD);
    }
}
