package stream.xlinuxtools.button;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import stream.xlinuxtools.MainActivity;
import stream.xlinuxtools.R;
import stream.xlinuxtools.websocket.Commands;

public class NetflixButtonHandler extends AbstractButton {

    public NetflixButtonHandler(View view){
        super(view);
    }

    @Override
    public void initButtons() {
        this.setSkipNetflixIntro();
    }

    private void setSkipNetflixIntro(){
        Button netflix = (Button) this.view.findViewById(R.id.netflix_intro);
        netflix.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("Netflix Button", "onClick: skip netflix");
                MainActivity.webSocket.sendCommand(Commands.SKIP_NETFLIX_INTRO);
            }
        });
    }
}
