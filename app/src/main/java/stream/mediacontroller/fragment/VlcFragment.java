package stream.mediacontroller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stream.mediacontroller.R;
import stream.mediacontroller.button.VlcButtonHandler;

public class VlcFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vlc, container, false);
        VlcButtonHandler buttonHandler = new VlcButtonHandler(view);
        buttonHandler.initButtons();
        return view;
    }
}
