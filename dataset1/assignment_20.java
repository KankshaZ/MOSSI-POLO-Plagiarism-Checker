package com.sssnm.sygadmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddAgentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //TODO: set image upload url in php script when hosting the scripts
    private String post_url = Constants.server_url + "addNewAgent.php";
    private String image_upload_url = Constants.server_url + "uploadImages.php";

    private MaterialDialog imageDialog;

    private EditText mName, mMobile, mDob, mEmail, mPassword;
    private EditText mPincode, mState, mAddress, mLocality, mCity;
    private EditText mCommission2W, mCommission4W;
    private RadioButton mPerm, mTemp;
    private TextView mOutletNameText;
    private Spinner mOutletSpinner, mBankSpinner;
    private EditText mBankAccount, mIFSC, mIMEI, mPancard, mAadhaar;
    private ImageView mAadhaarF, mAadhaarB;

    private int aadhaarImageType = 0; // 0 = front || 1 = back
    private Uri mCropImageUri;
    private Uri mAadhaarFrontUri = null;
    private Uri mAadhaarBackUri = null;

    private LinearLayout mCommissionLayout;

    private FloatingActionButton mDatePicker;
    private FloatingActionButton mFABAadhaarF, mFABAadhaarB;

    private RadioGroup mRadioGroup;
    private Button btnSaveChanges;

    private String agentType;
    private String errorMessage = "";
    private List<EditText> editTextList = new ArrayList<>(); //list of all edit text

    private List<String> spinnerList = new ArrayList<>();
    private List<String> outletIdList = new ArrayList<>();

    private ArrayAdapter<String> spinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);

        //set back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Agent");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            Log.d("AAA","null action bar");
        }

        mCommissionLayout = findViewById(R.id.commission_ll_id);
        mCommissionLayout.setVisibility(View.GONE);
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        mOutletNameText = findViewById(R.id.outlet_spinner_name_id);

        mDatePicker = findViewById(R.id.fab_date_picker_id);
        mFABAadhaarF = findViewById(R.id.fab_aadhaar_front_id);
        mFABAadhaarB = findViewById(R.id.fab_aadhaar_back_id);

        mName = findViewById(R.id.edxName);
        editTextList.add(mName);
        mMobile = findViewById(R.id.edxMobile);
        editTextList.add(mMobile);
        mDob = findViewById(R.id.edxDOB);
        editTextList.add(mDob);
        mEmail = findViewById(R.id.edxMail);
        editTextList.add(mEmail);
        mPassword = findViewById(R.id.edxPassword);
        editTextList.add(mPassword);

        mPincode = findViewById(R.id.edxPincode);
        editTextList.add(mPincode);
        mState = findViewById(R.id.edxState);
        editTextList.add(mState);
        mAddress = findViewById(R.id.edxAddress);
        editTextList.add(mAddress);
        mLocality = findViewById(R.id.edxLocality);
        editTextList.add(mLocality);
        mCity = findViewById(R.id.edxCity);
        editTextList.add(mCity);
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        mOutletSpinner = findViewById(R.id.aaa_spinner_outlet_id);
        mBankSpinner = findViewById(R.id.aaa_spinner_bank_id);

        mCommission2W = findViewById(R.id.edxCommission2W);
        mCommission4W = findViewById(R.id.edxCommission4W);

        mRadioGroup = findViewById(R.id.radioAgentType);
        mPerm = findViewById(R.id.radioButton);
        mTemp = findViewById(R.id.radioButton2);

        mBankAccount = findViewById(R.id.edxBankAccount);
        editTextList.add(mBankAccount);
        mIFSC = findViewById(R.id.edxIFSC);
        editTextList.add(mIFSC);
        mIMEI = findViewById(R.id.edxIMEI);
        editTextList.add(mIMEI);
        mPancard = findViewById(R.id.edxPan);
        editTextList.add(mPancard);
        mAadhaar = findViewById(R.id.edxAadhar);
        editTextList.add(mAadhaar);

        mAadhaarF = findViewById(R.id.imgFront);
        mAadhaarB = findViewById(R.id.imgBack);

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), "\'Date\'");
            }
        });
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        mFABAadhaarF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadhaarImageType = 0;
                CropImage.startPickImageActivity(AddAgentActivity.this);
            }
        });
        mFABAadhaarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadhaarImageType = 1;
                CropImage.startPickImageActivity(AddAgentActivity.this);
            }
        });


        btnSaveChanges = findViewById(R.id.btnRegister);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //register the agents
                registerAgent();
            }
        });

        spinnerArrayAdapter = new ArrayAdapter<>
                (AddAgentActivity.this, android.R.layout.simple_spinner_item, spinnerList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOutletSpinner.setAdapter(spinnerArrayAdapter);
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton) {
                    if (mCommissionLayout.getVisibility() == View.VISIBLE) {
                        mCommissionLayout.setVisibility(View.GONE);
                        mOutletSpinner.setVisibility(View.VISIBLE);
                        mOutletNameText.setVisibility(View.VISIBLE);
                    }

                } else if (i == R.id.radioButton2) {
                    if (mCommissionLayout.getVisibility() == View.GONE) {
                        mCommissionLayout.setVisibility(View.VISIBLE);
                        mOutletSpinner.setVisibility(View.GONE);
                        mOutletNameText.setVisibility(View.GONE);
                    }
                }
            }
        });

        setSpinnerOutlet(); //set outlet spinner
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
        System.out.print("");
    }
}