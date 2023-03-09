package com.simplequiz.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.simplequiz.app.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DashboardFragment extends Fragment
{
    FragmentDashboardBinding dashbinding;
    ImageView imageView;
    TextView tv_display;
    Button btn_login;
    EditText et_username,et_password;
    String url = "http://40.117.74.209:19019/androidapi/login/Authenticate";

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashbinding=FragmentDashboardBinding.inflate(getLayoutInflater());
        View view=inflater.inflate(R.layout.fragment_dashboard,container,false);
        imageView=view.findViewById(R.id.iv_glide_img);
        et_username=view.findViewById(R.id.et_user);
        et_password=view.findViewById(R.id.et_pass);
        btn_login=view.findViewById(R.id.btn_login);
        DataHeader d=new DataHeader();
        SecurityHeader s=new SecurityHeader();
        DataPayload dp=new DataPayload();
        Gson gson=new Gson();
        ArrayList<Object> list = new ArrayList<>();
        Json_Serialization js=new Json_Serialization();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Object> main_parameters=new ArrayList<>();
                HashMap<String,String> key_value_parameters=new HashMap<>();
                ArrayList<Object> sub_parameters=new ArrayList<>();
                main_parameters.add(et_password.getText().toString());
                //Assigning values to dataHeader
                d.setAdditionalInfo("");
                d.setChannelID("VAI");
                d.setClientID("SMD");
                d.setFlags("android");
                d.setLang("");
                d.setRequestDateTime("2022-07-19 16:54:07");
                d.setVersion("CT60");
                d.setWhid("0200");

                //Assigning values to securityHeader
                s.setApiVersionNumber("0.0.0.1");
                s.setDeviceOrProgramID("GI-847826b7ca120fbd");
                s.setHashValue("");
                s.setIsEncrypted(false);
                s.setToken("");
                s.setUserID(et_username.getText().toString());
                //Assigning values to dataPayLoad
                dp.setApiMethodName("Login");
                dp.setKeyValueParameters(key_value_parameters);
                dp.setSubParameters(sub_parameters);
                dp.setMainParameters(main_parameters);

                js.setDataHeader(d);
                js.setDataPayload(dp);
                js.setSecurityHeader(s);
                String requestBody=gson.toJson(js).toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.has("resultPayload"))
                            {
                                jsonObject=jsonObject.getJSONObject("resultPayload");
                                JSONArray jsonArray = jsonObject.getJSONArray("results");

                                if(jsonArray!=null && jsonArray.length()>0)
                                {
                                    Log.e("response:",jsonArray.toString ());
                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        Glide.with(getActivity().getApplicationContext()).load("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Smith_Drug_Company_Logo_RGB.svg/800px-Smith_Drug_Company_Logo_RGB.svg.png").into(imageView);
        return view;
    }
}