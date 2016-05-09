package us.petrolog.plungersandmore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.petrolog.plungersandmore.R;

public class LoginActivity extends FirebaseLoginBaseActivity {
    @Bind(R.id.buttonLogin)
    Button mButtonLogin;
    private Firebase mFirebaseRef;

    private boolean isActive = true;

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
        // All providers are optional! Remove any you don't want.
//        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
//        setEnabledAuthProvider(AuthProviderType.TWITTER);
//        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mFirebaseRef = new Firebase("https://plungersandmore.firebaseio.com");

//        if (isActive) {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showFirebaseLoginPrompt();
//                }
//            }, 2500);
//        }
    }

    @Override
    protected Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "There is a connection error, please try again", Toast.LENGTH_SHORT).show();
        showFirebaseLoginPrompt();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "Non valid credentials, please try again", Toast.LENGTH_SHORT).show();
        showFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Toast.makeText(this, "Hi ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFirebaseLoggedOut() {
        // TODO: Handle logout
    }


    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    @OnClick(R.id.buttonLogin)
    public void onClick() {
        showFirebaseLoginPrompt();
    }
}
