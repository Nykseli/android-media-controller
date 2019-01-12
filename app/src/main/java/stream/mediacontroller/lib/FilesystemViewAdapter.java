package stream.mediacontroller.lib;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.R;
import stream.mediacontroller.websocket.Commands;

import static android.content.ContentValues.TAG;

public class FilesystemViewAdapter extends RecyclerView.Adapter<FilesystemViewAdapter.MyViewHolder> {

    public ArrayList<FilesystemClass> fileList;

    /**
     * Contains info whether item in x position is selected or not
     */
    private SparseBooleanArray selectedItems;

    public FilesystemViewAdapter(ArrayList<FilesystemClass> filesList){
        this.fileList = filesList;
    }

    @NonNull
    @Override
    public FilesystemViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.selectedItems = new SparseBooleanArray();

        View fileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filesystem_layout, parent, false);

        return new MyViewHolder(fileView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilesystemViewAdapter.MyViewHolder holder, final int position) {
        final FilesystemClass file = this.fileList.get(position);
        holder.title.setText(file.getName());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + holder.getAdapterPosition() );
                Log.d(TAG, "onClick itemCount: " + getItemCount());
                FilesystemClass clickedFile = fileList.get(holder.getAdapterPosition());
                JSONObject additionalInfo = new JSONObject();
                try {
                    additionalInfo.put("absolutePath", clickedFile.getFullPath());
                } catch (JSONException e){/* Ignore */ }

                if(clickedFile.isDirectory()){
                    MainActivity.webSocket.sendCommand(Commands.GENERAL_GET_FILES_AND_FOLDERS, additionalInfo);
                }else{
                    //TODO: send command to play the file
                    MainActivity.webSocket.sendCommand(Commands.VLC_PLAY_FILE, additionalInfo);
                }
            }
        });
        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLongClick: " + position);

                // Disable selecting items in list that are directories.
                // Since you can only send file names to server.
                if(fileList.get(position).isDirectory()){
                    return  false;
                }

                // TODO: set selected value with notifyItemChanged instead of holder.itemView.setSelected
                holder.itemView.setSelected(selectedItems.get(position, false));
                if(holder.itemView.isSelected()){
                    holder.itemView.setSelected(false);
                }else{
                    holder.itemView.setSelected(true);
                }

                toggleSelection(position);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.fileList.size();
    }

    /**
     * Toggle selection of item on/off
     * @param pos position of the list item
     */
    public void toggleSelection(int pos){
        // Toggle selection of it item is already selected
        if(this.selectedItems.get(pos, false)){
            this.selectedItems.delete((pos));

        }else{
            // Else set item as selected
            this.selectedItems.put(pos, true);
        }
        // Update item
        // TODO: set selected value with notifyItemChanged instead of holder.itemView.setSelected
//        notifyItemChanged(pos);

    }

    /**
     * Clear all selected items in list and update it.
     */
    public void clearSelections(){
        this.selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * @return size of the selected items
     */
    public int getSelectedItemCount(){
        return this.selectedItems.size();
    }

    /**
     * @return int[] of positions of the selected items
     */
    public int[] getSelectedItems(){
        int selectedSize = this.getSelectedItemCount();
        int[] itemList = new int[selectedSize];
        for(int i = 0; i < selectedSize; i++){
            itemList[i] = this.selectedItems.keyAt(i);
        }

        return itemList;
    }

    /**
     * Get all of the selected items fullPaths and parse them to JSONArray that can be send to server
     * @return JSONArray containing selected items full file paths
     */
    public JSONArray getSelectedFiles(){
        int[] positions = getSelectedItems();
        JSONArray jArray = new JSONArray();

        for(int i=0; i<positions.length; i++){
            jArray.put(this.fileList.get(positions[i]).getFullPath());
        }

        return jArray;
    }

    public void initFileList(){
        this.fileList = new ArrayList<>();
    }

    public void addToFileList(FilesystemClass file){
        this.fileList.add(file);
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
