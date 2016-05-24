package us.petrolog.plungersandmore.ui;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.Well;
import us.petrolog.plungersandmore.utils.Constants;
import us.petrolog.plungersandmore.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabContainerActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {

    @Bind(R.id.bottomNavigation)
    BottomNavigation mBottomNavigation;
    @Bind(R.id.toolbar2)
    Toolbar toolbar;

    private static final String TAG = TabContainerActivity.class.getSimpleName();
    private static Stack<Integer> mTabStack;

    public String mCurrentWellStringRef;

    Well mWell;

    public TabContainerActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);

        /** toolBar **/
        setUpToolBar();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            mCurrentWellStringRef = null;
        } else {
            mCurrentWellStringRef = extras.getString(Constants.EXTRA_WELL_KEY_REFERENCE);
        }

        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_WELLS).child(mCurrentWellStringRef);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mWell = dataSnapshot.getValue(Well.class);
                setActionBarTitle(mWell.getName().toString(), null, true);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        mTabStack = new Stack<>();

        initializeBottomNavigation(savedInstanceState);
    }

    /**
     * sets up the top bar
     */
    public void setUpToolBar() {
        setSupportActionBar(toolbar);
        setActionBarTitle(getResources().getString(R.string.app_name), null, false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * Gets called from the fragments onResume and its because only the first doesn't have the up
     * button on the actionBar
     *
     * @param title          The title to show on the ActionBar
     * @param subtitle       The subtitle to show on the ActionBar
     * @param showNavigateUp if true, shows the up button
     */
    public void setActionBarTitle(String title, String subtitle, boolean showNavigateUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            if (subtitle != null) {
                getSupportActionBar().setSubtitle(subtitle);
            } else {
                getSupportActionBar().setSubtitle(null);
            }
            if (showNavigateUp) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }


    protected void initializeBottomNavigation(final Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            mBottomNavigation.setOnMenuItemClickListener(this);
            mBottomNavigation.setDefaultSelectedIndex(0);
            displayView(0);
        }
    }

    @Override
    public void onMenuItemSelect(final int itemId, int position) {
        LogUtil.logI(TAG, "onMenuItemSelect(" + itemId + ", " + position + ")");
        displayView(position);
    }

    @Override
    public void onMenuItemReselect(@IdRes int itemId, int position) {
        LogUtil.logI(TAG, "onMenuItemReselect(" + itemId + ", " + position + ")");

    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        mTabStack.push(position);
        switch (position) {
            case 0:
                new CurrentStatusFragment();
                fragment = CurrentStatusFragment.newInstance(mCurrentWellStringRef);
                break;
            case 1:
                fragment = new HistoricalCyclesFragment();
                break;
            case 2:
                fragment = new ChangeSettingsFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit();
            LogUtil.logD(TAG, "fragment added " + fragment.getTag());
        } else {
            // error in creating fragment
            LogUtil.logE(TAG, "Error in creating fragment");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * If we press back we kill the fragment and the last fragment shows on the container, so we
     * also have to show the tab that that container had.
     * So we use a tab stack.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (!mTabStack.empty()) {
                mTabStack.pop();
                if (!mTabStack.empty()) {
                    mBottomNavigation.setSelectedIndex(mTabStack.peek(), true);
                } else {
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }
}
