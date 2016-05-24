package us.petrolog.plungersandmore.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.User;
import us.petrolog.plungersandmore.utils.Constants;
import us.petrolog.plungersandmore.utils.LogUtil;

public class LoginActivity extends Activity {
    @Bind(R.id.loginContainer)
    View mView;
    @Bind(R.id.buttonLogin)
    Button mButtonLogin;

    private static final String GOOGLE_TOS_URL =
            "https://www.google.com/policies/terms/";
    private static final int RC_SIGN_IN = 100;

    private static final String UNCHANGED_CONFIG_VALUE = "CHANGE-ME";

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(MainActivity.createIntent(this));
            finish();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }


    @MainThread
    private boolean isGoogleConfigured() {
        return !UNCHANGED_CONFIG_VALUE.equals(
                getResources().getString(R.string.default_web_client_id));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.buttonLogin)
    public void onClick() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setProviders(AuthUI.EMAIL_PROVIDER)
                        .setTosUrl(GOOGLE_TOS_URL)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }

        showSnackbar(R.string.unknown_response);
    }

    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            registerUser();
        }

        if (resultCode == RESULT_CANCELED) {
            showSnackbar(R.string.sign_in_cancelled);
            return;
        }

        showSnackbar(R.string.unknown_sign_in_response);
    }

    private void registerUser() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor spe = sp.edit();
        final String unprocessedEmail;

        unprocessedEmail = user.getEmail();


        final String userName = user.getDisplayName();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference userLocation = database.getReference(Constants.FIREBASE_LOCATION_USERS);

        Query query = userLocation.child(user.getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // User does not exists, so we create it
                if (dataSnapshot.getValue() == null) {
                    HashMap<String, Object> timestampJoined = new HashMap<>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    User newUser = new User(userName, unprocessedEmail, timestampJoined);
                    userLocation.child(user.getUid()).setValue(newUser);
                    spe.putString(Constants.KEY_USER_PID, user.getUid()).apply();
                    startMainActivity();

                } else {
                    //String userPID = (String) ((Map.Entry) ((HashMap) dataSnapshot.getValue()).entrySet().toArray()[0]).getKey();
                    spe.putString(Constants.KEY_USER_PID, user.getUid()).apply();
                    startMainActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                LogUtil.logD(TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });
    }

    @MainThread
    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, LoginActivity.class);
        return in;
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
