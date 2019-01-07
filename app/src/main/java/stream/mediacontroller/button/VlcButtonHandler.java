package stream.mediacontroller.button;

import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

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
        this.setPauseButton();
    }

    public void setBrowserFiles(){
        String filePath = MainActivity.configManager.getAllowedFilePath();
        if(filePath != null) {
            JSONObject additionalInfo = new JSONObject();
            try {
                additionalInfo.put("absolutePath", filePath);
            } catch (JSONException e) {/* Ignore */ }

            this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_browse_files), Commands.GENERAL_GET_FILES_AND_FOLDERS, additionalInfo);
        }else{
            //TODO: error message that tells user to fix config on server side
        }
    }

    private void setPauseButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_pause), Commands.VLC_PAUSE_FILE);
    }


}
