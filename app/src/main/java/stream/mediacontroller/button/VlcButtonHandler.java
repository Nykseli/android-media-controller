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
        this.setStopMediaButton();
        this.setPlayNextButton();
        this.setPlayPreviousButton();
        this.setFastForwardButton();
        this.setRewindButton();
        this.setIncreaseVolumeButton();
        this.setDecreaseVolumeButton();
        this.setMuteButton();
        this.setCycleAudio();
        this.setCycleSubtitle();
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

    private void setStopMediaButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_stop), Commands.VLC_STOP_MEDIA);
    }

    private void setPlayNextButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_play_next_media), Commands.VLC_PLAY_NEXT_MEDIA);
    }

    private void setPlayPreviousButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_play_previous_media), Commands.VLC_PLAY_PREVIOUS_MEDIA);
    }

    private void setFastForwardButton(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.vlc_fast_forward), Commands.VLC_FAST_FORWARD);
    }

    private void setRewindButton(){
        this.setHoldableButton((Button) this.view.findViewById(R.id.vlc_rewind), Commands.VLC_REWIND);
    }

    private void setIncreaseVolumeButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_increase_volume), Commands.VLC_INCREASE_VOLUME);
    }

    private void setDecreaseVolumeButton(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_decrease_volume), Commands.VLC_DECREASE_VOLUME);
    }

    private void setMuteButton() {
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_mute_volume), Commands.VLC_MUTE_VOLUME);
    }

    private void setCycleAudio(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_cycle_audio), Commands.VLC_CYCLE_AUDIO_TRACK);
    }

    private void setCycleSubtitle(){
        this.setSingleClickButton((Button) this.view.findViewById(R.id.vlc_cycle_subtitle), Commands.VLC_CYCLE_SUBTITLE_TRACK);
    }


}
