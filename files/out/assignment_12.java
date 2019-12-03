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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAgentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String post_url = Constants.server_url + "addNewAgent.php";

    private String image_upload_url = Constants.server_url + "uploadImages.php";

    private String unused_variable = "This is never used";

    private MaterialDialog imageDialog;

    private EditText mName, mMobile, mDob, mEmail, mPassword;

    private EditText mPincode, mState, mAddress, mLocality, mCity;

    private EditText mCommission2W, mCommission4W;

    private String unused_variable = "This is never used";

    private RadioButton mPerm, mTemp;

    private TextView mOutletNameText;

    private Spinner mOutletSpinner, mBankSpinner;

    private EditText mBankAccount, mIFSC, mIMEI, mPancard, mAadhaar;

    private ImageView mAadhaarF, mAadhaarB;

    private String unused_variable = "This is never used";

    private int aadhaarImageType = 0;

    private Uri mCropImageUri;

    private Uri mAadhaarFrontUri = null;

    private Uri mAadhaarBackUri = null;

    private LinearLayout mCommissionLayout;

    private FloatingActionButton mDatePicker;

    private FloatingActionButton mFABAadhaarF, mFABAadhaarB;

    private RadioGroup mRadioGroup;

    private Button btnSaveChanges;

    private String unused_variable = "This is never used";

    private String agentType;

    private String errorMessage = "";

    private List<EditText> editTextList = new ArrayList<>();

    private List<String> spinnerList = new ArrayList<>();

    private List<String> outletIdList = new ArrayList<>();

    private String unused_variable = "This is never used";

    private ArrayAdapter<String> spinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);
        String unused_variable = "This is never used";
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

    private void registerAgentToDB(final String aadhaarFront, final String aadhaarBack) {
        final MaterialDialog dialog = new MaterialDialog.Builder(AddAgentActivity.this).title("Adding Agent").content("Please wait...").progress(true, 0).show();
        final int outletID = mOutletSpinner.getSelectedItemPosition();
        agentType = "0";
        if (mTemp.isChecked()) {
            agentType = "1";
        }
        final String bank = mBankSpinner.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
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
                    } else if (status.equals("false")) {
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
        }, new Response.ErrorListener() {

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
                params.put("agentType", agentType);
                params.put("name", mName.getText().toString().trim());
                params.put("mobile", mMobile.getText().toString().trim());
                params.put("dob", mDob.getText().toString().trim());
                params.put("email", mEmail.getText().toString().trim());
                params.put("password", mPassword.getText().toString().trim());
                params.put("pinCode", mPincode.getText().toString().trim());
                params.put("state", mState.getText().toString().trim());
                params.put("address", mAddress.getText().toString().trim());
                params.put("locality", mLocality.getText().toString().trim());
                params.put("city", mCity.getText().toString().trim());
                if (agentType.matches("0")) {
                    params.put("outletID", outletIdList.get(outletID));
                } else {
                    params.put("outletID", "-1");
                }
                params.put("bankName", bank);
                params.put("commission2W", mCommission2W.getText().toString().trim());
                params.put("commission4W", mCommission4W.getText().toString().trim());
                params.put("bankAccount", mBankAccount.getText().toString().trim());
                params.put("ifsc", mIFSC.getText().toString().trim());
                params.put("uniqueID", mIMEI.getText().toString().trim());
                params.put("panNumber", mPancard.getText().toString().trim());
                params.put("aadharNumber", mPancard.getText().toString().trim());
                params.put("aadharFrontImage", aadhaarFront);
                params.put("aadharBackImage", aadhaarBack);
                return params;
            }
        };
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private UploadStatusDelegate mUploadDelegate = new UploadStatusDelegate() {

        public void onProgress(Context context, UploadInfo uploadInfo) {
            imageDialog.setProgress(uploadInfo.getProgressPercent());
        }

        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                if (jsonObject.getString("status").equals("false")) {
                    String title = "Fail";
                    String contentFront = jsonObject.getString("errorFront");
                    String contentBack = jsonObject.getString("errorBack");
                    dialogBoxWithMessage(title, contentFront + "\n" + contentBack);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            imageDialog.hide();
        }

        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
            imageDialog.hide();
            try {
                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                String status = jsonObject.getString("status");
                String aadhaarFront = jsonObject.getString("imageNameFront");
                String aadhaarBack = jsonObject.getString("imageNameBack");
                registerAgentToDB(aadhaarFront, aadhaarBack);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onCancelled(Context context, UploadInfo uploadInfo) {
            imageDialog.hide();
        }
    };

    public void uploadMultipart(final Context context) {
        try {
            File front = new File(mAadhaarFrontUri.getPath());
            File back = new File(mAadhaarBackUri.getPath());
            String uploadId = new MultipartUploadRequest(context, image_upload_url).addFileToUpload(front.getAbsolutePath(), "AADHAARFRONT").addFileToUpload(back.getAbsolutePath(), "AADHAARBACK").setNotificationConfig(null).setMaxRetries(1).setDelegate(mUploadDelegate).startUpload();
            imageDialog = new MaterialDialog.Builder(this).title("Uploading image").content("Please wait...").progress(false, 100, true).canceledOnTouchOutside(false).show();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (aadhaarImageType == 0) {
                mAadhaarFrontUri = imageUri;
                mAadhaarF.setImageURI(mAadhaarFrontUri);
            } else if (aadhaarImageType == 1) {
                mAadhaarBackUri = imageUri;
                mAadhaarB.setImageURI(mAadhaarBackUri);
            }
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE }, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (aadhaarImageType == 0) {
                    mAadhaarFrontUri = resultUri;
                    mAadhaarF.setImageURI(mAadhaarFrontUri);
                } else if (aadhaarImageType == 1) {
                    mAadhaarBackUri = resultUri;
                    mAadhaarB.setImageURI(mAadhaarBackUri);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("TAG", error.getMessage());
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("OFA", "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setInitialCropWindowPaddingRatio(0).start(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        Date d = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.US);
        String selectedDate = sdf.format(d);
        mDob.setText(selectedDate);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }

    private boolean ifValidInput() {
        boolean valid = true;
        if (!isEmailValid(mEmail.getText().toString().trim())) {
            errorMessage += "Email not valid\n";
            mEmail.setError("Email not valid");
            valid = false;
        }
        if (mMobile.getText().toString().trim().length() != 10) {
            errorMessage += "Mobile must be 10 digits long\n";
            mMobile.setError("Invalid length");
            valid = false;
        }
        if (mPincode.getText().toString().trim().length() != 6) {
            errorMessage += "Pin code must be 6 digits long\n";
            mPincode.setError("Invalid length");
            valid = false;
        }
        if (mIFSC.getText().toString().trim().length() != 11) {
            errorMessage += "IFSC must be 11 digits long\n";
            mIFSC.setError("Invalid length");
            valid = false;
        }
        if (mAadhaar.getText().toString().trim().length() != 12) {
            errorMessage += "Aadhaar must be 12 digits long\n";
            mAadhaar.setError("Invalid length");
            valid = false;
        }
        if (mPancard.getText().toString().trim().length() != 10) {
            errorMessage += "Pan must be 10 digits long\n";
            mPancard.setError("Invalid length");
            valid = false;
        }
        return valid;
    }

    private boolean ifFieldsEmpty() {
        boolean checkVal = false;
        for (EditText e : editTextList) {
            if (e.getText().toString().matches("")) {
                checkVal = true;
                break;
            }
        }
        if (mTemp.isChecked()) {
            if (mCommission2W.getText().toString().trim().matches("") || mCommission4W.getText().toString().trim().matches("")) {
                checkVal = true;
            }
        }
        return checkVal;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void dialogBoxWithMessage(String title, String message) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(AddAgentActivity.this).title(title).content(message).negativeText("Close").show();
    }

    public void dialogBoxWithIntent(String title, String content) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(AddAgentActivity.this).title(title).content(content).positiveText("Close").dismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                AddAgentActivity.this.finish();
            }
        }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
