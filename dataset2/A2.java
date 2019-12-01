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
        if (actionBar != null) {
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            actionBar.setTitle("Agent Individual");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
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
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        m2TPPolicy = findViewById(R.id.aai_2tp_id);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        m2TPPolicy.setText(NA);
        m4TPPolicy = findViewById(R.id.aai_4tp_id);
        m4TPPolicy.setText(NA);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        mPPTotalPolicy = findViewById(R.id.aai_pp_total_id);
        mPPTotalPolicy.setText(NA);

        m2CPImg = findViewById(R.id.aai_2cp_img_id);
        m2CPImg.setText(NA);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        m2TPImg = findViewById(R.id.aai_2tp_img_id);
        m2TPImg.setText(NA);
        m4TPImg = findViewById(R.id.aai_4tp_img_id);
        m4TPImg.setText(NA);
        mPPTotalImg = findViewById(R.id.aai_pp_total_img_id);
        mPPTotalImg.setText(NA);

        rstrial_tv = findViewById(R.id.rstrial_id);
        amt_tv = findViewById(R.id.amt_total_aip_id);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        comission_tv = findViewById(R.id.commission_total_aip_id);

        rupees = getResources().getString(R.string.Rs);
        amt_tv.setText(NA);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        comission_tv.setText(NA);
        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        rstrial_tv.setText(NA);

        commission_amt_tv = findViewById(R.id.total_commission_amt_id);
        commission_amt_tv.setText(NA);
System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        et_name = findViewById(R.id.name_aip_id);
        editTextList.add(et_name);
        et_email = findViewById(R.id.email_aip_id);
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
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
        editTextList.add(et_email);System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
        et_mobile = findViewById(R.id.mobile_aip_id);
        editTextList.add(et_mobile);System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
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
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
System.out.println("");
System.out.println("");

        et_bank = findViewById(R.id.bank_aip_id);
        editTextList.add(et_bank);
        et_ifsc = findViewById(R.id.ifcs_aip_id);
        editTextList.add(et_ifsc);
        et_target = findViewById(R.id.target_aip_id);
        editTextList.add(et_target);
        et_address = findViewById(R.id.address_aip_id);
        editTextList.add(et_address);System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
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
            public void onClick(View view) { //cancel the edits, back to normal state
                if (agentOGState != null) {
                    setAgentEditableFields(agentOGState);
                }
                editTextState(false); //make edit text read mode
            }
        });

        fab_phone.setOnClickListener(new View.OnClickListener() { //open call dialog
            @Override
            public void onClick(View view) {
                String phone = mAgentMobile.getText().toString();
System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                //open dialog
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                startActivity(intent);
            }
        });

        try {
            agentObject = new JSONObject(getIntent().getStringExtra("AgentObj"));
            String name = agentObject.getString("name");
            mAgentName.setText(name);
            String mobile = agentObject.getString("mobile");
            mAgentMobile.setText(mobile);
            System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
            System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            String outlet_code = agentObject.getString("outlet_code");
            mOutletName.setText(outlet_code);System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");

            String agentType = agentObject.getString("agentType");
            String agentID = agentObject.getString("agent_id");
            agentIDGlobal = agentID;
            agentTypeGlobal = agentType;System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");

            getAgentDetailsFromDB(agentID, agentType); //get the agent details

        } catch (JSONException e) {System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            //TODO: NA all fields and disable the edit option
            e.printStackTrace();
        }

        ll2cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AgentIndividualActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                startGalleryActivity(0, 0, "2CP");
            }
        });

        ll2tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
                startGalleryActivity(0, 1, "2TP");
            }
        });

        ll4tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            @Override
            public void onReceive(Context context, Intent intent) {
                long broadcastedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (broadcastedDownloadId == reference) {System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                    if (getDownloadStatus() == DownloadManager.STATUS_SUCCESSFUL) {
                        System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
                                System.out.print("");
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
        startActivity(intent);
    }

    private void getAgentDetailsFromDB(final String agentID, final String agentType) {
        if (agentType.equals("0")) { //permanent
            //hide the commission box
            mCardView.setVisibility(View.GONE);System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
            mCommissionLayout.setVisibility(View.GONE);

        }

        //make request for dashboard data
        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                new Response.Listener<String>() { //response listener
                    @Override
                    public void onResponse(String response) {
                        mProgress.show();

                        try {System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        ////////////////////////
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
                        System.out.println("I CAN BEAT YOU HAHAHHAHAHAHA");
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

                                //total policy amount
                                int policyPP = Integer.parseInt(twoCP) + Integer.parseInt(twoTP) + Integer.parseInt(fourTP);
                                mPPTotalPolicy.setText(String.valueOf(policyPP));

                                //set commission for temporary agent
                                if (agentType.equals("1")) {
                                    et_commission_2W.setText(jsonObject1.getString("commission2W"));
                                    et_commission_4W.setText(jsonObject1.getString("commission4W"));
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
System.out.println("");
System.out.print("");
System.out.print("");
System.out.print("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.print("");
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
                                m2CPImg.setText(twoCP);System.out.println("");
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
                                twoTP = jsonObject2.getString("twoTPimg");
                                m2TPImg.setText(twoTP);
                                fourTP = jsonObject2.getString("fourTPimg");
                                m4TPImg.setText(fourTP);

                                policyPP = Integer.parseInt(twoCP) + Integer.parseInt(twoTP) + Integer.parseInt(fourTP);
                                mPPTotalImg.setText(String.valueOf(policyPP));

                                setAgentEditableFields(jsonObject1); //set editable fields
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mProgress.hide();
                    }
                },
                new Response.ErrorListener() { //error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: disable the edit and cancel fab
                        if (error.getMessage() != null) {
                            Log.d("AIA",error.getMessage() + "error");System.out.println("");
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
                        }
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("agentID", agentID); //temporary agent
                params.put("agentType", agentType); //temporary agent

                return params;
            }
        };
        //add the string req to teh request queue using singleton
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void setAgentEditableFields(JSONObject jsonObject1) {
        try {
            et_name.setText(jsonObject1.getString("name"));
            et_email.setText(jsonObject1.getString("email"));
            et_mobile.setText(jsonObject1.getString("mobile"));
            et_bank.setText(jsonObject1.getString("bankAccount"));
            et_ifsc.setText(jsonObject1.getString("ifsc"));
            et_target.setText(jsonObject1.getString("target"));
            et_address.setText(jsonObject1.getString("address"));
            et_city.setText(jsonObject1.getString("city"));
            et_state.setText(jsonObject1.getString("state"));
            et_pincode.setText(jsonObject1.getString("pinCode"));
            et_locality.setText(jsonObject1.getString("locality"));
            if (agentTypeGlobal.equals("1")) {
                et_commission_2W.setText(jsonObject1.getString("commission2W"));
                et_commission_4W.setText(jsonObject1.getString("commission4W"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void editTextState(boolean b) {
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

        if (b) { //if fab clicked make it visible
            saveBtn.setVisibility(View.VISIBLE);

            //makes animate button invisible
            fab_edit.animate().alpha(0.0f);
            fab_cancel.setVisibility(View.VISIBLE); //make cancel button visible

        } else { //active when save button is clicked
            saveBtn.setVisibility(View.GONE);
            fab_edit.animate().alpha(1.0f); //makes animate button visible
            fab_cancel.setVisibility(View.GONE);
        }
    }

    public void onSaveChanges(View view) {
        String json_url = Constants.server_url + "updateAgentFields.php";

        if (ifFieldsEmpty()) {
            String title = "Invalid input";
            String content = "One or more fields are empty.";
            dialogBoxWithMessage(title, content);

            setAgentEditableFields(agentOGState);

        } else {
            final String name = et_name.getText().toString().trim();
            final String email = et_email.getText().toString().trim();
            final String mobile = et_mobile.getText().toString().trim();
            final String bank = et_bank.getText().toString().trim();
            final String ifsc = et_ifsc.getText().toString().trim();
            final String target = et_target.getText().toString().trim();
            final String address = et_address.getText().toString().trim();
            final String state = et_state.getText().toString().trim();
            final String city = et_city.getText().toString().trim();
            final String pincode = et_pincode.getText().toString().trim();
            final String locality = et_locality.getText().toString().trim();
            final String commission2W = et_commission_2W.getText().toString().trim();
            final String commission4W = et_commission_4W.getText().toString().trim();

            Log.d("OFA", String.valueOf(commission2W.length()) + ":" + commission4W);

            final MaterialDialog dialog = new MaterialDialog.Builder(AgentIndividualActivity.this)
                    .title("Updating Database")
                    .content("Please wait...")
                    .progress(true, 0)
                    .show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, json_url,
                    new Response.Listener<String>() { //response listener
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");

                                String title, content;
                                if (status.equals("true")) {
                                    title = "Success";
                                    content = "Agent fields successfully updated.";
                                    dialogBoxWithMessage(title, content);

                                    mAgentName.setText(name);
                                    mAgentMobile.setText(mobile);

                                    updateAgentJSON(name, email, mobile, bank, ifsc, target, address,
                                            state, city, pincode, locality, commission2W, commission4W, 0);

                                } else if (status.equals("1")) {
                                    title = "Partial failure";
                                    content = "Address fields could not be updated.";
                                    dialogBoxWithMessage(title, content);

                                    updateAgentJSON(name, email, mobile, bank, ifsc, target, address,
                                            state, city, pincode, locality, commission2W, commission4W, 1);

                                } else if (status.equals("2")) {
                                    title = "Partial failure";
                                    content = "Agent personal fields could not be updated. Address fields updated.";
                                    dialogBoxWithMessage(title, content);

                                    updateAgentJSON(name, email, mobile, bank, ifsc, target, address,
                                            state, city, pincode, locality, commission2W, commission4W, 2);

                                } else {
                                    title = "Fail";
                                    content = "Server side database error.";
                                    dialogBoxWithMessage(title, content);

                                    setAgentEditableFields(agentOGState);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                //TODO: alert user

                                setAgentEditableFields(agentOGState);
                            }

                            dialog.hide();
                        }
                    },
                    new Response.ErrorListener() { //error listener
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.getMessage() != null) {
                                Log.d("AIA",error.getMessage() + "error");
                            }
                            error.printStackTrace();
                            dialog.hide();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("agentID", agentIDGlobal);
                    params.put("agentType", agentTypeGlobal);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("mobile", mobile);
                    params.put("bankAccount", bank);
                    params.put("ifsc", ifsc);
                    params.put("target", target);
                    params.put("address", address);
                    params.put("state", state);
                    params.put("city", city);
                    params.put("pinCode", pincode);
                    params.put("locality", locality);
                    params.put("commission2W", commission2W);
                    params.put("commission4W", commission4W);

                    return params;
                }
            };
            //add the string req to teh request queue using singleton
            MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        }

        editTextState(false); //make edit text read mode
    }

    private void updateAgentJSON(String name, String email, String mobile, String bank, String ifsc,
                                 String target, String address, String state, String city, String pincode,
                                 String locality, String commission2W, String commission4W, int i) throws JSONException {
        if (i == 0 || i == 1) { //personal fields
            agentOGState.put("name",name);
            agentOGState.put("email",email);
            agentOGState.put("mobile",mobile);
            agentOGState.put("bankAccount",bank);
            agentOGState.put("ifsc",ifsc);
            agentOGState.put("target",target);
            if (agentTypeGlobal.equals("1")) {
                agentOGState.put("commission2W",commission2W);
                agentOGState.put("commission4W",commission4W);
            }

        }
        if (i == 0 || i == 2) { //address fields
            agentOGState.put("address",address);
            agentOGState.put("state",state);
            agentOGState.put("city",city);
            agentOGState.put("pinCode",pincode);
            agentOGState.put("locality",locality);

        }
        setAgentEditableFields(agentOGState);

    }

    public boolean ifFieldsEmpty() {
        boolean checkVal = false;
        for (EditText e : editTextList) { //traverse all the edit texts
            if (e.getText().toString().trim().matches("")) { //string is null
                checkVal = true;
                break;
            }
        }
        if (agentTypeGlobal.equals("1")) {
            if (et_commission_2W.getText().toString().trim().matches("")
                    || et_commission_4W.getText().toString().trim().matches("")) {
                checkVal = true;
            }
        }
        return checkVal;
    }

    private void excelDownload() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, excel_url,
                new Response.Listener<String>() { //response listener
                    @Override
                    public void onResponse(String response) {
                        //TODO: add progress bar with message or toast
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = String.valueOf(jsonObject.getString("status"));
                            if (status.equals("true")) {
                                file_name = String.valueOf(jsonObject.getString("file_name"));
                                Log.d("AIA",file_name);

                                //check permission
                                if (isStoragePermissionGranted()) {
                                    excelDownloadManager(file_name);
                                } else {
                                    Log.d("AIA","WRITE permission was false");
                                }
                            } else if (status.equals("false")) {
                                //TODO: alert the user
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { //error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage() != null) {
                            Log.d("AIA",error.getMessage() + "error");
                        }
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("agentID", agentIDGlobal);

                return params;
            }
        };
        //add the string req to teh request queue using singleton
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void excelDownloadManager(String file_name) {
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();

        //download manager
        String excel_link = Constants.server_excel_download + file_name;
        mDownloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(excel_link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,file_name);
        reference = mDownloadManager.enqueue(request);
    }

    private int getDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(reference);
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            Cursor cursor = downloadManager.query(query);

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);

                return status;
            }
        }

        return DownloadManager.ERROR_UNKNOWN;
    }

}
