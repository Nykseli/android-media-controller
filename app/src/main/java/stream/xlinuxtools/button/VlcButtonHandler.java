package stream.xlinuxtools.button;

import android.view.View;
import android.widget.Button;

import stream.xlinuxtools.MainActivity;
import stream.xlinuxtools.R;
import stream.xlinuxtools.websocket.Commands;
import stream.xlinuxtools.websocket.VlcCommands;

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
            this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_browse_files), Commands.GET_FILES_AND_FOLDERS, filePath);
        }else{
            //TODO: error message that tells user to fix config on server side
        }
    }


}
