package us.petrolog.plungersandmore.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.Well;

public class MainActivity extends FirebaseLoginBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Firebase mFirebaseRef;
    private RecyclerView mRecyclerViewWell;
    private FirebaseRecyclerAdapter<Well, WellListHolder> mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseRef = new Firebase("https://plungersandmore.firebaseio.com");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateTestWells();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerViewWell = (RecyclerView) findViewById(R.id.recyclerViewWellList);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        mRecyclerViewWell.setHasFixedSize(false);
        mRecyclerViewWell.setLayoutManager(manager);

        Firebase wellRef = new Firebase("https://plungersandmore.firebaseio.com/wells");

        mRecycleViewAdapter = new FirebaseRecyclerAdapter<Well, WellListHolder>(Well.class, R.layout.item_well, WellListHolder.class, wellRef) {
            @Override
            public void populateViewHolder(WellListHolder listView, Well well, int position) {
                listView.setName(well.getName());
                listView.setState(well.getState());
            }
        };

        mRecyclerViewWell.setAdapter(mRecycleViewAdapter);
    }

    private void generateTestWells() {
        String userEmail = (String) mFirebaseRef.getAuth().getProviderData().get("email");
        Well well = new Well("uno", "ok", "first", 28.6752758, -106.1404511, true, "PDT", userEmail);
        Well well2 = new Well("dos", "ok", "first", 28.6802217, -106.1182286, true, "PDT", userEmail);
        Well well3 = new Well("tres", "ok", "first", 28.6756834, -106.1366093, true, "PDT", userEmail);
        Well well4 = new Well("cuatro", "ok", "first", 28.6802217, -106.1182286, true, "PDT", userEmail);
        Well well5 = new Well("cinco", "ok", "first", 28.6735469, -106.1384236, true, "PDT", userEmail);
        Well well6 = new Well("seis", "ok", "first", 28.6738751, -106.1303234, true, "PDT", userEmail);
        mFirebaseRef.child("wells").push().setValue(well);
        mFirebaseRef.child("wells").push().setValue(well2);
        mFirebaseRef.child("wells").push().setValue(well3);
        mFirebaseRef.child("wells").push().setValue(well4);
        mFirebaseRef.child("wells").push().setValue(well5);
        mFirebaseRef.child("wells").push().setValue(well6);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "There is a connection error, please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "Non valid credentials, please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Toast.makeText(this, "Hi ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirebaseLoggedOut() {
        finish();
        // TODO: Handle logout
    }

    /**
     * For the recycler view
     */
    public static class WellListHolder extends RecyclerView.ViewHolder {
        View mView;

        public WellListHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView field = (TextView) mView.findViewById(R.id.textViewItemName);
            field.setText(name);
        }

        public void setState(String text) {
            TextView field = (TextView) mView.findViewById(R.id.textViewitemState);
            field.setText(text);
        }
    }
}
