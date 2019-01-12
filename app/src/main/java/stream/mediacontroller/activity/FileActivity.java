package stream.mediacontroller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.fragment.FileFragment;
import stream.mediacontroller.lib.FilesystemClass;
import stream.mediacontroller.lib.FilesystemViewAdapter;
import stream.mediacontroller.websocket.Commands;
import stream.mediacontroller.websocket.WebSocket;
import stream.mediacontroller.websocket.WebSocketDataGetter;

public class FileActivity extends AppCompatActivity implements WebSocketDataGetter {
    protected static final String TAG = "FileActivity";

    public static final String INTENT_STRING = "FILE_ACTIVITY_STRING";

    private JSONObject filesAndFolders;
    private FilesystemViewAdapter fileViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        Toolbar toolbar = (Toolbar) findViewById(R.id.file_toolbar);
        setSupportActionBar(toolbar);
        // Set back button to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        // Set web socket dataGetter to this class implementation.
        // This class uses the parseJson to show the files from server
        WebSocket.dataGetter = this;

        // Set margin to top so file items don't go under the toolbar
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen.activity_vertical_margin) + 20, getResources().getDisplayMetrics());
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.file_activity_container);
        layout.setPadding(0, topMargin, 0, 0);


        // Setup file fragment with data from MainActivity
        this.fileViewAdapter = new FilesystemViewAdapter(new ArrayList<FilesystemClass>());
        try {
            this.filesAndFolders = new JSONObject(intent.getStringExtra(INTENT_STRING));

            Fragment fragment = new FileFragment(this, this.filesAndFolders, this.fileViewAdapter);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //this is where the id of the FrameLayout is being mentioned. Hence the fragment would be loaded into the framelayout
            ft.replace(R.id.file_activity_container, fragment);
            ft.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.file, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: " + id);
        switch (id){
            case R.id.action_remove_files:
                this.fileViewAdapter.clearSelections();
                break;
            case R.id.action_send_files:
                JSONObject additionalInfo = new JSONObject();
                try {
                    additionalInfo.put("absolutePaths", this.fileViewAdapter.getSelectedFiles());
                    MainActivity.webSocket.sendCommand(Commands.VLC_PLAY_FILES, additionalInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            // If back button is pressed, go back to MainActivitys previous state
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress: " + keyCode);
        return super.onKeyLongPress(keyCode, event);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void showVlcFragment(JSONObject json){
        Fragment fragment = new FileFragment(this, json, this.fileViewAdapter);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //this is where the id of the FrameLayout is being mentioned. Hence the fragment would be loaded into the framelayout
        ft.replace(R.id.file_activity_container, fragment);
        ft.commit();

    }

    /**
     * Parse data from web socket. <br />
     * This parseJson currently only gets data about files so we only need to check that
     * @param data String in json format from web socket
     */
    @Override
    public void parseJson(String data) {
        try {
            JSONObject json = new JSONObject(data);
            if(!json.isNull("files") && !json.isNull("folders")){
                    showVlcFragment(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
