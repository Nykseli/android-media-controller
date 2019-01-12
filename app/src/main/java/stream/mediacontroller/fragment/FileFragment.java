package stream.mediacontroller.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import stream.mediacontroller.R;
import stream.mediacontroller.lib.FilesystemClass;
import stream.mediacontroller.lib.FilesystemViewAdapter;

@SuppressLint("ValidFragment")
public class FileFragment extends Fragment {
    private Context context;
    private JSONObject json;
    private FilesystemViewAdapter adapter;

    @SuppressLint("ValidFragment")
    public FileFragment(Context context, JSONObject json, FilesystemViewAdapter adapter){
        this.context = context;
        this.json = json;
        this.adapter = adapter;

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filesystem, container, false);
        //NetflixButtonHandler buttonHandler = new NetflixButtonHandler(view);
       // buttonHandler.initButtons();
        setVlc(view);
        return view;
    }

    public void setVlc(View view){

        this.adapter.initFileList();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        Log.d("", "showVlcFragment: " + recyclerView.toString());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.adapter);

        try {
            JSONArray fileArr = this.json.getJSONArray("files");
            JSONArray folderArr = this.json.getJSONArray("folders");
            String path = this.json.getString("currentPath");

            // We want to list folders first
            for(int i = 0; i < folderArr.length(); i++){
                this.adapter.addToFileList(new FilesystemClass(true, folderArr.getString(i), path));
            }

            for(int i = 0; i < fileArr.length(); i++) {
                this.adapter.addToFileList(new FilesystemClass(false, fileArr.getString(i), path));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        this.adapter.notifyDataSetChanged();
    }

}
