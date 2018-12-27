package stream.xlinuxtools;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;

import stream.xlinuxtools.fragment.DefaultFragment;
import stream.xlinuxtools.fragment.NetflixFragment;
import stream.xlinuxtools.websocket.WebSocket;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static WebSocket webSocket;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            // desktop
            webSocket = new WebSocket(new URI("ws://192.168.0.19:9000"));
            // nuc
            // webSocket = new WebSocket(new URI("ws://192.168.0.13:9000"));
            webSocket.connect();
        } catch (Exception e) {
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
//            case R.id.nav_three:
//                fragment = new Three();
//                navigationView.getMenu().getItem(2).setChecked(true);
//                break;


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

}
