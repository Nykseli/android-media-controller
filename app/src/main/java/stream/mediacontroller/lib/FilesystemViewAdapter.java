package stream.mediacontroller.lib;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.websocket.Commands;

import static android.content.ContentValues.TAG;

public class FilesystemViewAdapter extends RecyclerView.Adapter<FilesystemViewAdapter.MyViewHolder> {

    private ArrayList<FilesystemClass> fileList;


    public FilesystemViewAdapter(ArrayList<FilesystemClass> filesList){
        this.fileList = filesList;
    }

    @NonNull
    @Override
    public FilesystemViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View fileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filesystem_layout, parent, false);

        return new MyViewHolder(fileView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilesystemViewAdapter.MyViewHolder holder, int position) {
        FilesystemClass file = this.fileList.get(position);
        holder.title.setText(file.getName());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + holder.getAdapterPosition() );
                Log.d(TAG, "onClick itemCount: " + getItemCount());
                FilesystemClass clickedFile = fileList.get(holder.getAdapterPosition());
                if(clickedFile.isDirectory()){
                    MainActivity.webSocket.sendCommand(Commands.GENERAL_GET_FILES_AND_FOLDERS, clickedFile.getFullPath());
                }else{
                    //TODO: send command to play the file
                    MainActivity.webSocket.sendCommand(Commands.VLC_PLAY_FILE, clickedFile.getFullPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.fileList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, genre, year;
        public MyViewHolder(View view) {
            super(view);
            Log.v("ViewHolder","in View Holder");
            title = (TextView) view.findViewById(R.id.title);

        }


    }
}
