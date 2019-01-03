package stream.mediacontroller.message;

import android.support.design.widget.Snackbar;

public class InfoPopUp {

    private InfoPopUpInterface popUpInterface;

    public InfoPopUp(InfoPopUpInterface infoPopUpInterface){
        this.popUpInterface = infoPopUpInterface;
    }

    public void showShortMessage(String message){
        this.popUpInterface.showMessage(message, Snackbar.LENGTH_SHORT);
    }
}
