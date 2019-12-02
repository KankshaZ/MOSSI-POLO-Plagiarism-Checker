package com.sssnm.sygadmin;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAgentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText mName, mMobile, mDob, mEmail, mPassword;

    private EditText mPincode, mState, mAddress, mLocality, mCity;

    private TextView mOutletNameText;

    private EditText mBankAccount, mIFSC, mIMEI, mPancard, mAadhaar;

    private Uri mAadhaarFrontUri = null;

    private Uri mAadhaarBackUri = null;

    private LinearLayout mCommissionLayout;

    private FloatingActionButton mDatePicker;

    private FloatingActionButton mFABAadhaarF, mFABAadhaarB;

    private RadioGroup mRadioGroup;

    private Button btnSaveChanges;

    private String errorMessage = "";

    private List<EditText> editTextList = new ArrayList<>();

    private List<String> spinnerList = new ArrayList<>();

    private List<String> outletIdList = new ArrayList<>();

    private ArrayAdapter<String> spinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Agent");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            Log.d("AAA", "null action bar");
        }
        mCommissionLayout = findViewById(R.id.commission_ll_id);
        mCommissionLayout.setVisibility(View.GONE);
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
            public void onClick(View view) {
                registerAgent();
            }
        });
        spinnerArrayAdapter = new ArrayAdapter<>(AddAgentActivity.this, android.R.layout.simple_spinner_item, spinnerList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOutletSpinner.setAdapter(spinnerArrayAdapter);
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
        setSpinnerOutlet();
    }

    private void setSpinnerOutlet() {
        final MaterialDialog dialog = new MaterialDialog.Builder(AddAgentActivity.this).title("Retrieving outlets").content("Please wait...").progress(true, 0).show();
        String json_url = Constants.server_url + "availableOutlet.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String) null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                try {
                    String status = response.getJSONObject(count).getString("status");
                    count++;
                    if (status.equals("false")) {
                        String title = "Database Failure";
                        String content = "Failed to load outlets.";
                        dialogBoxWithMessage(title, content);
                    } else if (status.equals("true")) {
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                String outletID = jsonObject.getString("outletID");
                                String name = jsonObject.getString("name");
                                spinnerList.add(name);
                                outletIdList.add(outletID);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        spinnerArrayAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.hide();
            }
        });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void registerAgent() {
        if (ifFieldsEmpty()) {
            String title = "Input Errors";
            String content = "One or more fields are left empty";
            dialogBoxWithMessage(title, content);
        } else if (!ifValidInput()) {
            String title = "Input Errors";
            dialogBoxWithMessage(title, errorMessage);
            errorMessage = "";
        } else if (mAadhaarFrontUri == null || mAadhaarBackUri == null) {
            String title = "Emplty Images";
            String content = "Please upload aadhaar images.";
            dialogBoxWithMessage(title, content);
        } else {
            uploadMultipart(getApplicationContext());
        }
    }
}
