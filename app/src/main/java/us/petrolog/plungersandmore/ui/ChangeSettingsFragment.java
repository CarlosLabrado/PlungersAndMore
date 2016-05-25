package us.petrolog.plungersandmore.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.Settings;
import us.petrolog.plungersandmore.model.SplitTime;
import us.petrolog.plungersandmore.utils.Constants;
import us.petrolog.plungersandmore.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeSettingsFragment extends Fragment {
    private static final String ARG_WELL_REF = "wellRef";

    @Bind(R.id.editTextFallTimeWriteHH)
    EditText mEditTextFallTimeWriteHH;
    @Bind(R.id.editTextFallTimeWriteMM)
    EditText mEditTextFallTimeWriteMM;
    @Bind(R.id.editTextFallTimeReadHH)
    EditText mEditTextFallTimeReadHH;
    @Bind(R.id.editTextFallTimeReadMM)
    EditText mEditTextFallTimeReadMM;
    @Bind(R.id.editTextFallTimeReadSS)
    EditText mEditTextFallTimeReadSS;
    @Bind(R.id.buttonFallTimeChange)
    Button mButtonFallTimeChange;
    @Bind(R.id.buttonTubingPressureOpenValve)
    Button mButtonTubingPressureOpenValve;
    @Bind(R.id.editTextTubingPressureWrite)
    EditText mEditTextTubingPressureWrite;
    @Bind(R.id.editTextTubingPressureRead)
    EditText mEditTextTubingPressureRead;
    @Bind(R.id.editTextCasingPressureWrite)
    EditText mEditTextCasingPressureWrite;
    @Bind(R.id.editTextCasingPressureRead)
    EditText mEditTextCasingPressureRead;
    @Bind(R.id.buttonTubingPressureChange)
    Button mButtonTubingPressureChange;
    @Bind(R.id.editTextShutInWriteHH)
    EditText mEditTextShutInWriteHH;
    @Bind(R.id.editTextShutInWriteMM)
    EditText mEditTextShutInWriteMM;
    @Bind(R.id.editTextShutInReadHH)
    EditText mEditTextShutInReadHH;
    @Bind(R.id.editTextShutInReadMM)
    EditText mEditTextShutInReadMM;
    @Bind(R.id.editTextShutInReadSS)
    EditText mEditTextShutInReadSS;
    @Bind(R.id.buttonShutInChange)
    Button mButtonShutInChange;
    @Bind(R.id.editTextSalesTimeWriteHH)
    EditText mEditTextSalesTimeWriteHH;
    @Bind(R.id.editTextSalesTimeWriteMM)
    EditText mEditTextSalesTimeWriteMM;
    @Bind(R.id.editTextSalesTimeReadHH)
    EditText mEditTextSalesTimeReadHH;
    @Bind(R.id.editTextSalesTimeReadMM)
    EditText mEditTextSalesTimeReadMM;
    @Bind(R.id.editTextSalesTimeReadSS)
    EditText mEditTextSalesTimeReadSS;
    @Bind(R.id.buttonSalesTimeChange)
    Button mButtonSalesTimeChange;
    @Bind(R.id.editTextOpenTimeWriteHH)
    EditText mEditTextOpenTimeWriteHH;
    @Bind(R.id.editTextOpenTimeWriteMM)
    EditText mEditTextOpenTimeWriteMM;
    @Bind(R.id.editTextOpenTimeReadHH)
    EditText mEditTextOpenTimeReadHH;
    @Bind(R.id.editTextOpenTimeReadMM)
    EditText mEditTextOpenTimeReadMM;
    @Bind(R.id.editTextOpenTimeReadSS)
    EditText mEditTextOpenTimeReadSS;
    @Bind(R.id.buttonOpenTimeChange)
    Button mButtonOpenTimeChange;
    @Bind(R.id.editTextRecoveryTimeWriteHH)
    EditText mEditTextRecoveryTimeWriteHH;
    @Bind(R.id.editTextRecoveryTimeWriteMM)
    EditText mEditTextRecoveryTimeWriteMM;
    @Bind(R.id.editTextRecoveryTimeReadHH)
    EditText mEditTextRecoveryTimeReadHH;
    @Bind(R.id.editTextRecoveryTimeReadMM)
    EditText mEditTextRecoveryTimeReadMM;
    @Bind(R.id.editTextRecoveryTimeReadSS)
    EditText mEditTextRecoveryTimeReadSS;
    @Bind(R.id.buttonRecoveryTimeChange)
    Button mButtonRecoveryTimeChange;
    @Bind(R.id.buttonSettingsCloseValve)
    Button mButtonSettingsCloseValve;
    @Bind(R.id.buttonSettingsOpenValve)
    Button mButtonSettingsOpenValve;

    private String mParamWellRefString;
    DatabaseReference mFirebaseRef;
    DatabaseReference mFirebaseRefSettings;

    public ChangeSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param wellRef well reference
     * @return A new instance of fragment ChangeSettingsFragment.
     */
    public static ChangeSettingsFragment newInstance(String wellRef) {
        ChangeSettingsFragment fragment = new ChangeSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WELL_REF, wellRef);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamWellRefString = getArguments().getString(ARG_WELL_REF);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_settings, container, false);

        ButterKnife.bind(this, view);

        mFirebaseRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_WELLS)
                .child(mParamWellRefString);

        mFirebaseRefSettings = mFirebaseRef.child(Constants.FIREBASE_NAME_SETTINGS);
        mFirebaseRefSettings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Settings settings = dataSnapshot.getValue(Settings.class);
                    updateUI(settings);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void updateUI(Settings settings) {

        SplitTime fallTime = Utils.splitTimeUtil(settings.getFallTime());
        mEditTextFallTimeReadHH.setText(fallTime.getHours());
        mEditTextFallTimeReadMM.setText(fallTime.getMinutes());
        mEditTextFallTimeReadSS.setText(fallTime.getSeconds());

        SplitTime shutInTime = Utils.splitTimeUtil(settings.getShutInTime());
        mEditTextShutInReadHH.setText(shutInTime.getHours());
        mEditTextShutInReadMM.setText(shutInTime.getMinutes());
        mEditTextShutInReadSS.setText(shutInTime.getSeconds());


        SplitTime salesTime = Utils.splitTimeUtil(settings.getSalesTime());
        mEditTextSalesTimeReadHH.setText(salesTime.getHours());
        mEditTextSalesTimeReadMM.setText(salesTime.getMinutes());
        mEditTextSalesTimeReadSS.setText(salesTime.getSeconds());


        SplitTime openTime = Utils.splitTimeUtil(settings.getOpenTime());
        mEditTextOpenTimeReadHH.setText(openTime.getHours());
        mEditTextOpenTimeReadMM.setText(openTime.getMinutes());
        mEditTextOpenTimeReadSS.setText(openTime.getSeconds());


        SplitTime recoveryTime = Utils.splitTimeUtil(settings.getRecoveryTime());
        mEditTextRecoveryTimeReadHH.setText(recoveryTime.getHours());
        mEditTextRecoveryTimeReadMM.setText(recoveryTime.getMinutes());
        mEditTextRecoveryTimeReadSS.setText(recoveryTime.getSeconds());

        mEditTextCasingPressureRead.setText(settings.getCp());
        mEditTextTubingPressureRead.setText(settings.getTp());

        if (settings.getCloseValve().equalsIgnoreCase("0")) {
            mButtonSettingsCloseValve.setEnabled(false);
        } else if (settings.getCloseValve().equalsIgnoreCase("1")) {
            mButtonSettingsCloseValve.setEnabled(true);
        }

        if (settings.getOpenValve().equalsIgnoreCase("0")) {
            mButtonSettingsOpenValve.setEnabled(false);
        } else if (settings.getOpenValve().equalsIgnoreCase("1")) {
            mButtonSettingsOpenValve.setEnabled(true);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.buttonFallTimeChange, R.id.buttonTubingPressureOpenValve, R.id.buttonTubingPressureChange, R.id.buttonShutInChange, R.id.buttonOpenTimeChange, R.id.buttonRecoveryTimeChange, R.id.buttonSettingsCloseValve, R.id.buttonSettingsOpenValve, R.id.buttonSalesTimeChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFallTimeChange:
                writeTimeToFirebase(Constants.SETTINGS_FALL_TIME, mEditTextFallTimeWriteHH.getText().toString(), mEditTextFallTimeWriteMM.getText().toString());
                break;
            case R.id.buttonTubingPressureOpenValve:
                break;
            case R.id.buttonTubingPressureChange:
                mFirebaseRefSettings.child(Constants.SETTINGS_TP).setValue(mEditTextTubingPressureWrite.getText().toString());
                break;
            case R.id.buttonShutInChange:
                writeTimeToFirebase(Constants.SETTINGS_SHUT_IN_TIME, mEditTextShutInWriteHH.getText().toString(), mEditTextShutInWriteMM.getText().toString());
                break;
            case R.id.buttonOpenTimeChange:
                writeTimeToFirebase(Constants.SETTINGS_OPEN_TIME, mEditTextOpenTimeWriteHH.getText().toString(), mEditTextOpenTimeWriteMM.getText().toString());
                break;
            case R.id.buttonSalesTimeChange:
                writeTimeToFirebase(Constants.SETTINGS_SALES_TIME, mEditTextSalesTimeWriteHH.getText().toString(), mEditTextSalesTimeWriteMM.getText().toString());
                break;
            case R.id.buttonRecoveryTimeChange:
                writeTimeToFirebase(Constants.SETTINGS_RECOVERY_TIME, mEditTextRecoveryTimeWriteHH.getText().toString(), mEditTextRecoveryTimeWriteMM.getText().toString());
                break;
            case R.id.buttonSettingsCloseValve:
                mFirebaseRefSettings.child(Constants.SETTINGS_CLOSE_VALUE).setValue("1");
                mFirebaseRefSettings.child(Constants.SETTINGS_OPEN_VALVE).setValue("0");
                break;
            case R.id.buttonSettingsOpenValve:
                mFirebaseRefSettings.child(Constants.SETTINGS_OPEN_VALVE).setValue("1");
                mFirebaseRefSettings.child(Constants.SETTINGS_CLOSE_VALUE).setValue("0");
                break;
        }
    }

    private void writeTimeToFirebase(String settingsField, String stringHours, String stringMinutes) {
        SplitTime splitTime = new SplitTime(stringHours, stringMinutes, "0");
        String time = Utils.unifyTime(splitTime);
        mFirebaseRefSettings.child(settingsField).setValue(time);
    }
}
