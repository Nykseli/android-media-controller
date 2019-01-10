package stream.mediacontroller;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import stream.mediacontroller.fragment.DefaultFragment;
import stream.mediacontroller.fragment.FileFragment;
import stream.mediacontroller.fragment.NetflixFragment;
import stream.mediacontroller.fragment.SettingsFragment;
import stream.mediacontroller.fragment.VlcFragment;
import stream.mediacontroller.message.InfoPopUp;
import stream.mediacontroller.message.InfoPopUpInterface;
import stream.mediacontroller.util.ConfigManager;
import stream.mediacontroller.util.PreferenceStorage;
import stream.mediacontroller.websocket.WebSocket;
import stream.mediacontroller.websocket.WebSocketDataGetter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WebSocketDataGetter,
        InfoPopUpInterface {

    public static WebSocket webSocket;
    public static PreferenceStorage preferenceStorage;
    public static ConfigManager configManager;
    public static InfoPopUp infoPopUp;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Init public static classes here
        configManager = new ConfigManager();
        infoPopUp = new InfoPopUp(this);
        preferenceStorage = new PreferenceStorage(this);

        try {
            webSocket = new WebSocket(new URI(preferenceStorage.getString(PreferenceStorage.WEB_SOCKET_URL, "")), this);
            webSocket.asyncConnect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //showing default fragment
        displaySelectedFragment(R.id.nav_default);


    }

    @Override
    public void onResume(){
        super.onResume();
        if(webSocket.isClosed())
            webSocket.asyncReConnect();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, new SettingsFragment());
            ft.commit();

            // Set topMargin to container so the the toolbar doesn't overlap with the first item
            int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen.activity_vertical_margin) + 20, getResources().getDisplayMetrics());
            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.container);
            layout.setPadding(0, topMargin, 0, 0);

            getSupportActionBar().setTitle("Settings");

            return true;
        }else if(id == R.id.action_connect_socket) {
            webSocket.asyncReConnect();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // item id is being passed into the method here
        displaySelectedFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setScreenTitle(int itemId){
        String title = "";
        switch (itemId){
            case R.id.nav_default:
                title = this.getString(R.string.menu_default);
                break;
            case R.id.nav_netflix:
                title = this.getString(R.string.menu_netflix);
                break;
            case R.id.nav_vlc:
                title = "Vlc";
            default:
                break;
        }

        getSupportActionBar().setTitle(title);
    }

    public void displaySelectedFragment(int itemId){


        Fragment fragment = null;

        switch (itemId){

            case R.id.nav_default:
                fragment = new DefaultFragment();
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.nav_netflix:
                fragment = new NetflixFragment();
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
                //TODO: add volumecontrol fragmet
            case R.id.nav_vlc:
                int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen.activity_vertical_margin) + 20, getResources().getDisplayMetrics());
                ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.container);
                layout.setPadding(0, topMargin, 0, 0);

                //getVlcFragment();
                fragment = new VlcFragment();
                navigationView.getMenu().getItem(2).setCheckable(true);
                //return;
                //break;

        }
        if( fragment!=null ){

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //this is where the id of the FrameLayout is being mentioned. Hence the fragment would be loaded into the framelayout
            ft.replace(R.id.container, fragment);
            ft.commit();
        }

        /** setting title to the screen **/
        setScreenTitle(itemId);

    }

    public void showVlcFragment(JSONObject json){
        Fragment fragment = new FileFragment(this, json);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //this is where the id of the FrameLayout is being mentioned. Hence the fragment would be loaded into the framelayout
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    // This is from WebSocketDataGetter interface
    @Override
    public void parseJson(String data) {
        //TODO: use better json library org.json is not a good library; bad code and bad performance
        Log.d("parseJson", "parseJson: " + data);
        try {
            JSONObject json = new JSONObject(data);
            if(!json.isNull("files") && !json.isNull("folders")){
                showVlcFragment(json);
            }else if(!json.isNull("config")){
                configManager.loadConfig(json);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // This is from InfoPopUpInterface. We want InfoPopUp to be static class so it cannot
    // have any context variables.
    // This is the workaround for now
    @Override
    public void showMessage(String message, int snackBarLength) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
