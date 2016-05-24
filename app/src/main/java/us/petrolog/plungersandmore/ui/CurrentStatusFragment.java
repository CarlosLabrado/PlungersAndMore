package us.petrolog.plungersandmore.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.CurrentStatus;
import us.petrolog.plungersandmore.model.Well;
import us.petrolog.plungersandmore.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentStatusFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_WELL_REF = "wellRef";
    @Bind(R.id.textViewCyclesCompleted)
    TextView mTextViewCyclesCompleted;
    @Bind(R.id.textViewCyclesMissed)
    TextView mTextViewCyclesMissed;
    @Bind(R.id.textViewPc)
    TextView mTextViewPc;
    @Bind(R.id.textViewPl)
    TextView mTextViewPl;
    @Bind(R.id.textViewPt)
    TextView mTextViewPt;
    @Bind(R.id.textViewState)
    TextView mTextViewState;
    @Bind(R.id.textViewTimeStamp)
    TextView mTextViewTimeStamp;

    private String mParamWellRef;
    DatabaseReference mFirebaseRef;

    private GoogleMap mMap;

    public CurrentStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param wellRef well reference
     * @return A new instance of fragment CurrentStatusFragment.
     */
    public static CurrentStatusFragment newInstance(String wellRef) {
        CurrentStatusFragment fragment = new CurrentStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WELL_REF, wellRef);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamWellRef = getArguments().getString(ARG_WELL_REF);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_status, container, false);

        ButterKnife.bind(this, view);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLite);
        mapFragment.getMapAsync(this);

        mFirebaseRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_WELLS).child(mParamWellRef);
        mFirebaseRef.child("currentStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    CurrentStatus currentStatus = dataSnapshot.getValue(CurrentStatus.class);
                    if (isFragmentUIActive()) {
                        mTextViewCyclesCompleted.setText(String.valueOf(currentStatus.getCyclesCompleted()));
                        mTextViewCyclesMissed.setText(String.valueOf(currentStatus.getCyclesMissed()));
                        mTextViewPc.setText(String.valueOf(currentStatus.getPc()));
                        mTextViewPl.setText(String.valueOf(currentStatus.getPl()));
                        mTextViewPt.setText(String.valueOf(currentStatus.getPt()));
                        mTextViewState.setText(String.valueOf(currentStatus.getState()));
                        mTextViewTimeStamp.setText(String.valueOf(currentStatus.getTimeStamp()));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        DatabaseReference wellRef = mFirebaseRef;
        wellRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Well well = dataSnapshot.getValue(Well.class);
                    updateLiteMap(well);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        return view;
    }

    public boolean isFragmentUIActive() {
        return isAdded() && !isDetached() && !isRemoving();
    }

    private void updateLiteMap(Well well) {

        LatLng latLng = new LatLng(well.getLocation().getLat(), well.getLocation().getLon());

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title((String) well.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
//        mFirebaseRef.removeEventListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
