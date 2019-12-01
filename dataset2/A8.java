package com.sssnm.sygadmin;

import android.Manifest;
import android.animation.Animator;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentIndividualActivity extends AppCompatActivity {

    private String post_url = Constants.server_url + "displayAgentIndividual.php";
    private String excel_url = Constants.server_excel_url + "contacts_excel.php";

    private DownloadManager mDownloadManager;
    private long reference;
    private String file_name = "dummy";

    private static String amount, rupees;
    private static final String NA = "NA";

    private String agentIDGlobal, agentTypeGlobal;

    private JSONObject agentObject, agentOGState = null;

    private  TextView mAgentName, mAgentMobile, mOutletName;
    private LinearLayout mCommissionLayout;

    private TextView rstrial_tv, amt_tv, comission_tv, commission_amt_tv;
    private TextView m2CPPolicy, m2TPPolicy, m4TPPolicy, mPPTotalPolicy;
    private TextView m2CPImg, m2TPImg, m4TPImg, mPPTotalImg;

    private LinearLayout ll2cp, ll2tp, ll4tp;

    private EditText et_name, et_email, et_mobile, et_bank, et_target, et_ifsc;
    private EditText et_address, et_state, et_city, et_pincode, et_locality;
    private EditText et_commission_2W, et_commission_4W;
    private AVLoadingIndicatorView mProgress;
    private CardView mCardView;

    private FloatingActionButton fab_edit, fab_cancel, fab_phone;
    private Button saveBtn;

    private List<EditText> editTextList = new ArrayList<>(); //list of all edit text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_individual);

        //set back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            actionBar.setTitle("Agent Individual");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            Log.d("AIA","null action bar");
        }

        mCommissionLayout = findViewById(R.id.aai_commission_ll_id);

        mCardView = findViewById(R.id.aai_card_commission_id);

        mProgress = findViewById(R.id.avl_agent_indv_loading_id);

        fab_edit = findViewById(R.id.fab_edit_aip_id);
        fab_cancel = findViewById(R.id.fab_cancel_aip_id);
        fab_phone = findViewById(R.id.fab_phone_aip_id);
        saveBtn = findViewById(R.id.btn_save_changes_id);

        ll2cp = findViewById(R.id.ll_2cp_aai_id);
        ll2tp = findViewById(R.id.ll_2tp_aai_id);
        ll4tp = findViewById(R.id.ll_4tp_aai_id);

        mAgentName = findViewById(R.id.aai_agent_name_id);
        mAgentMobile = findViewById(R.id.aai_agent_mobile_id);
        mOutletName = findViewById(R.id.aai_outlet_name_id);

        m2CPPolicy = findViewById(R.id.aai_2cp_id);
        m2CPPolicy.setText(NA);
        m2TPPolicy = findViewById(R.id.aai_2tp_id);
        m2TPPolicy.setText(NA);
        m4TPPolicy = findViewById(R.id.aai_4tp_id);
        m4TPPolicy.setText(NA);
        mPPTotalPolicy = findViewById(R.id.aai_pp_total_id);
        mPPTotalPolicy.setText(NA);

        m2CPImg = findViewById(R.id.aai_2cp_img_id);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        m2CPImg.setText(NA);
        m2TPImg = findViewById(R.id.aai_2tp_img_id);
        m2TPImg.setText(NA);
        m4TPImg = findViewById(R.id.aai_4tp_img_id);
        m4TPImg.setText(NA);
        mPPTotalImg = findViewById(R.id.aai_pp_total_img_id);
        mPPTotalImg.setText(NA);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");

        rstrial_tv = findViewById(R.id.rstrial_id);
        amt_tv = findViewById(R.id.amt_total_aip_id);
        comission_tv = findViewById(R.id.commission_total_aip_id);

        rupees = getResources().getString(R.string.Rs);
        amt_tv.setText(NA);
        comission_tv.setText(NA);
        rstrial_tv.setText(NA);

        commission_amt_tv = findViewById(R.id.total_commission_amt_id);
        commission_amt_tv.setText(NA);

        et_name = findViewById(R.id.name_aip_id);
        editTextList.add(et_name);
        et_email = findViewById(R.id.email_aip_id);
        editTextList.add(et_email);
        et_mobile = findViewById(R.id.mobile_aip_id);
        editTextList.add(et_mobile);
        et_bank = findViewById(R.id.bank_aip_id);
        editTextList.add(et_bank);
        et_ifsc = findViewById(R.id.ifcs_aip_id);
        editTextList.add(et_ifsc);
        et_target = findViewById(R.id.target_aip_id);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        editTextList.add(et_target);
        et_address = findViewById(R.id.address_aip_id);
        editTextList.add(et_address);
        et_state = findViewById(R.id.state_aip_id);
        editTextList.add(et_state);
        et_city = findViewById(R.id.city_aip_id);
        editTextList.add(et_city);
        et_pincode = findViewById(R.id.pincode_aip_id);
        editTextList.add(et_pincode);
        et_locality = findViewById(R.id.locality_aip_id);
        editTextList.add(et_locality);
        et_commission_2W = findViewById(R.id.aai_commission_2W_id);
        et_commission_4W = findViewById(R.id.aai_commission_4W_id);

        fab_cancel.setVisibility(View.GONE);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextState(true); //enable edit on click
            }
        });

        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");//cancel the edits, back to normal state
                if (agentOGState != null) {
                    setAgentEditableFields(agentOGState);
                }System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                editTextState(false); System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");//make edit text read mode
            }
        });

        fab_phone.setOnClickListener(new View.OnClickListener() { //open call dialog
            @Override
            public void onClick(View view) {
                String phone = mAgentMobile.getText().toString();
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                //open dialog
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        try {
            agentObject = new JSONObject(getIntent().getStringExtra("AgentObj"));
            String name = agentObject.getString("name");
            mAgentName.setText(name);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            String mobile = agentObject.getString("mobile");
            mAgentMobile.setText(mobile);
            String outlet_code = agentObject.getString("outlet_code");
            mOutletName.setText(outlet_code);

            String agentType = agentObject.getString("agentType");
            String agentID = agentObject.getString("agent_id");
            agentIDGlobal = agentID;
            agentTypeGlobal = agentType;

            getAgentDetailsFromDB(agentID, agentType); //get the agent details

        } catch (JSONException e) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            //TODO: NA all fields and disable the edit option
            e.printStackTrace();
        }
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        ll2cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AgentIndividualActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                startGalleryActivity(0, 0, "2CP");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            }
        });

        ll2tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGalleryActivity(0, 1, "2TP");
            }
        });

        ll4tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                startGalleryActivity(2, 1, "4TP");
            }
        });

        //disable the edit text
        editTextState(false);

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long broadcastedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (broadcastedDownloadId == reference) {
                    if (getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL) {
                        Toast.makeText(AgentIndividualActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AgentIndividualActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        //TODO: unregister the broadcast receiver somewhere
//        unregisterReceiver(broadcastReceiver);

    }

    private void startGalleryActivity(int i1, int i2, String name) { //id, vehicle, insurance (2tp = 0, 1)
        Intent intent = new Intent(AgentIndividualActivity.this, PolicyGalleryActivity.class);
        intent.putExtra("agentID", agentIDGlobal);
        intent.putExtra("vehicleType", String.valueOf(i1));
        intent.putExtra("insuranceType", String.valueOf(i2));
        intent.putExtra("policyName", name);
        startActivity(intent);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
    }

    private void getAgentDetailsFromDB(final String agentID, final String agentType) {
        if (agentType.equals("0")) { //permanent
            //hide the commission box
            mCardView.setVisibility(View.GONE);
            mCommissionLayout.setVisibility(View.GONE);

        }

        //make request for dashboard data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                new Response.Listener<String>() { //response listener
                    @Override
                    public void onResponse(String response) {
                        mProgress.show();

                        try {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                            JSONArray array = new JSONArray(response);

                            JSONObject jsonObject0 = array.getJSONObject(0);
                            String status = jsonObject0.getString("status");

                            if (status.equals("true") && array.length() == 3) {
                                JSONObject jsonObject1 = array.getJSONObject(1);
                                agentOGState = jsonObject1; //save instance of OG object

                                String policyTotal = rupees + " " +jsonObject1.getString("policyTOTAL");
                                amt_tv.setText(policyTotal);

                                //set policy 2tp, 2tp, 4tp, total
                                String twoCP = jsonObject1.getString("twoCP");
                                m2CPPolicy.setText(twoCP);
                                String twoTP = jsonObject1.getString("twoTP");
                                m2TPPolicy.setText(twoTP);
                                String fourTP = jsonObject1.getString("fourTP");
                                m4TPPolicy.setText(fourTP);
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                                //total policy amount
                                int policyPP = Integer.parseInt(twoCP) + Integer.parseInt(twoTP) + Integer.parseInt(fourTP);
                                mPPTotalPolicy.setText(String.valueOf(policyPP));

                                //set commission for temporary agent
                                if (agentType.equals("1")) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                                    et_commission_2W.setText(jsonObject1.getString("commission2W"));
                                    et_commission_4W.setText(jsonObject1.getString("commission4W"));

                                    String commissionTotal = rupees + " " + jsonObject1.getString("commissionTotal");
                                    commission_amt_tv.setText(commissionTotal);
                                }

                                //service charge breakdown
                                String sc2 = rupees + " " + jsonObject1.getString("scTwoWheeler");
                                rstrial_tv.setText(sc2);
                                String sc4 = rupees + " " + jsonObject1.getString("scFourWheeler");
                                comission_tv.setText(sc4);

                                JSONObject jsonObject2 = array.getJSONObject(2);

                                //set img 2tp, 2tp, 4tp, total
                                twoCP = jsonObject2.getString("twoCPimg");
                                m2CPImg.setText(twoCP);
                                twoTP = jsonObject2.getString("twoTPimg");
                                m2TPImg.setText(twoTP);
                                fourTP = jsonObject2.getString("fourTPimg");
                                m4TPImg.setText(fourTP);

                                policyPP = Integer.parseInt(twoCP) + Integer.parseInt(twoTP) + Integer.parseInt(fourTP);
                                mPPTotalImg.setText(String.valueOf(policyPP));

                                setAgentEditableFields(jsonObject1); //set editable fields
                            }

                        } catch (JSONException e) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                            e.printStackTrace();
                        }
                        mProgress.hide();
                    }
                },
                new Response.ErrorListener() { //error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: disable the edit and cancel fab
                        if (error.getMessage() != null) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                            Log.d("AIA",error.getMessage() + "error");
                        }
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("agentID", agentID); //temporary agentSystem.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                params.put("agentType", agentType); //temporary agent

                return params;
            }
        };
        //add the string req to teh request queue using singleton
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void editTextState(boolean b) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        et_name.setEnabled(b);
        et_email.setEnabled(b);
        et_mobile.setEnabled(b);
        et_bank.setEnabled(b);
        et_ifsc.setEnabled(b);
        et_target.setEnabled(b);
        et_address.setEnabled(b);
        et_state.setEnabled(b);
        et_city.setEnabled(b);
        et_pincode.setEnabled(b);
        et_locality.setEnabled(b);
        if (agentTypeGlobal != null && agentTypeGlobal.equals("1")) {
            et_commission_2W.setEnabled(b);
            et_commission_4W.setEnabled(b);
        }
        fab_phone.setEnabled(!b);

        if (b) { //if fab clicked make it visibleSystem.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            saveBtn.setVisibility(View.VISIBLE);

            //makes animate button invisible
            fab_edit.animate().alpha(0.0f);
            fab_cancel.setVisibility(View.VISIBLE); //make cancel button visible

        } else { //active when save button is clickedSystem.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
            saveBtn.setVisibility(View.GONE);
            fab_edit.animate().alpha(1.0f); //makes animate button visible
            fab_cancel.setVisibility(View.GONE);
        }
    }


    private void excelDownloadManager(String file_name) {System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();

        //download manager
        String excel_link = Constants.server_excel_download + file_name;
        mDownloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(excel_link);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,file_name);
        reference = mDownloadManager.enqueue(request);System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
    }

}
