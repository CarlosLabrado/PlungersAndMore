package us.petrolog.plungersandmore.ui.custom;

import android.app.Activity;
import android.view.View;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by Vazh on 9/5/2016.
 */
public class CustomFirebaseAdapter extends FirebaseListAdapter {
    public CustomFirebaseAdapter(Activity activity, Class modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }


    @Override
    protected void populateView(View view, Object o, int i) {

    }
}
