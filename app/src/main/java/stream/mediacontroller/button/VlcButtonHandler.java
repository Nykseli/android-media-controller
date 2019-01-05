package stream.mediacontroller.button;

import android.view.View;
import android.widget.Button;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.websocket.Commands;

public class VlcButtonHandler extends AbstractButton{


    public VlcButtonHandler(View view) {
        super(view);
    }

    @Override
    public void initButtons() {
        this.setBrowserFiles();
    }

    public void setBrowserFiles(){
        String filePath = MainActivity.configManager.getAllowedFilePath();
        if(filePath != null) {
            this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_browse_files), Commands.GENERAL_GET_FILES_AND_FOLDERS, filePath);
        }else{
            //TODO: error message that tells user to fix config on server side
        }
    }


}
