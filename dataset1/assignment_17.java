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
            public void onClick(View view) { //register the agents
                registerAgent();
            }
        });

        spinnerArrayAdapter = new ArrayAdapter<>
                (AddAgentActivity.this, android.R.layout.simple_spinner_item, spinnerList);
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

        setSpinnerOutlet(); //set outlet spinner
    }

    private void setSpinnerOutlet() {
        final MaterialDialog dialog = new MaterialDialog.Builder(AddAgentActivity.this)
                .title("Retrieving outlets")
                .content("Please wait...")
                .progress(true, 0)
                .show();

        String json_url = Constants.server_url + "availableOutlet.php";
        //set spinner array
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,json_url,(String)null,
                new Response.Listener<JSONArray>() {
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
                                spinnerArrayAdapter.notifyDataSetChanged(); //notify spinner of newly added value
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        dialog.hide();
                    }
                });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void registerAgent() {
        if (ifFieldsEmpty()) {  //check if any field is empty
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

        } else { //everything is fine
            //start image upload
            uploadMultipart(getApplicationContext()); //upload aadhaar images

        }
    }

    private void registerAgentToDB(final String aadhaarFront, final String aadhaarBack) {
        final MaterialDialog dialog = new MaterialDialog.Builder(AddAgentActivity.this)
                .title("Adding Agent")
                .content("Please wait...")
                .progress(true, 0)
                .show();

        final int outletID = mOutletSpinner.getSelectedItemPosition();
        agentType = "0";
        if (mTemp.isChecked()) {
            agentType = "1";
        }
        final String bank = mBankSpinner.getSelectedItem().toString();

        //make query
        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                new Response.Listener<String>() { //response listener
                    @Override
                    public void onResponse(String response) {
                        //converting the string to json array object
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("true")) {
                                String title = "Success";
                                String content = "Agent Successfully added.\nYou will be redirected to Agent screen";
                                dialogBoxWithIntent(title, content);

                            } else if (status.equals("1")) {
                                String message = "Fail";
                                String content = "Agent's address fields could not be added.\nPlease check your network and try again.";
                                dialogBoxWithMessage(message, content);

                            } else if (status.equals("2")) {
                                String message = "Fail";
                                String content = "Agent could not be added.\nPlease check your network and try again.";
                                dialogBoxWithMessage(message, content);

                            } else if (status.equals("false")) { //db error
                                String message = "Fail";
                                String content = "Server side database error.";
                                dialogBoxWithMessage(message, content);

                            } else {
                                String title = "Fail";
                                dialogBoxWithMessage(title, status);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.hide();
                    }
                },
                new Response.ErrorListener() { //error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() != null) {
                            error.printStackTrace();
                            Log.d("AAA", error.getMessage());
                        } else {
                            Log.d("AAA", "error null");
                        }
                        dialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("agentType", agentType); //insert
                params.put("name", mName.getText().toString().trim()); //insert
                params.put("mobile", mMobile.getText().toString().trim()); //insert
                params.put("dob", mDob.getText().toString().trim()); //insert
                params.put("email", mEmail.getText().toString().trim()); //insert
                params.put("password", mPassword.getText().toString().trim()); //insert
                params.put("pinCode", mPincode.getText().toString().trim()); //insert
                params.put("state", mState.getText().toString().trim()); //insert
                params.put("address", mAddress.getText().toString().trim()); //insert
                params.put("locality", mLocality.getText().toString().trim()); //insert
                params.put("city", mCity.getText().toString().trim()); //insert
                if (agentType.matches("0")) { //perm
                    params.put("outletID", outletIdList.get(outletID)); //insert
                } else {
                    params.put("outletID", "-1"); //insert
                }
                params.put("bankName", bank); //insert
                params.put("commission2W", mCommission2W.getText().toString().trim()); //insert
                params.put("commission4W", mCommission4W.getText().toString().trim()); //insert
                params.put("bankAccount", mBankAccount.getText().toString().trim()); //insert
                params.put("ifsc", mIFSC.getText().toString().trim()); //insert
                params.put("uniqueID", mIMEI.getText().toString().trim()); //insert
                params.put("panNumber", mPancard.getText().toString().trim()); //insert
                params.put("aadharNumber", mPancard.getText().toString().trim()); //insert
                params.put("aadharFrontImage", aadhaarFront); //insert
                params.put("aadharBackImage", aadhaarBack); //insert

                return params;
            }
        };
        //add the string req to teh request queue using singleton
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}
