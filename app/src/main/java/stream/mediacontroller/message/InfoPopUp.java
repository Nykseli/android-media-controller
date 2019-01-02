package stream.mediacontroller.message;

import android.support.design.widget.Snackbar;
import android.view.View;

public class InfoPopUp {

    private View view;

    public InfoPopUp(View view){
        this.view = view;
    }

    public void showShortMessage(String message){
        Snackbar snackbar = Snackbar.make(this.view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
