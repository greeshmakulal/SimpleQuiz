package com.simplequiz.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivoka.csdk.asr.Engine;
import com.vivoka.csdk.asr.models.AsrResult;
import com.vivoka.csdk.asr.models.AsrResultHypothesis;
import com.vivoka.csdk.asr.recognizer.IRecognizerListener;
import com.vivoka.csdk.asr.recognizer.Recognizer;
import com.vivoka.csdk.asr.recognizer.RecognizerErrorCode;
import com.vivoka.csdk.asr.recognizer.RecognizerEventCode;
import com.vivoka.csdk.asr.recognizer.RecognizerResultType;
import com.vivoka.csdk.asr.utils.AsrResultParser;
import com.vivoka.vsdk.Vsdk;
import com.vivoka.vsdk.util.BufferUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText et_username,et_password;
    String username, password;
    Button btn_login,save,btn_start,btn_stop;
    RelativeLayout relativeLayout;
    TextView new_user;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int _initCounter = 0;
    public Recognizer _recognizer = null;
    String ASR_APPLICATION = "pick_final";
    public AudioRecorder _audioRecorder = null;
    Boolean is_asr_initialized = false;
    int ASR_CONFIDENCE_THRESHOLD = 4500;
   final static int  REQUEST_RECORD_PERMISSION=1;
    BroadcastReceiver br_tts;
    public static final String SPEECH_RATE = "com.almasons.this_is_where_the_sceech_rate_is_saved";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.editTextUserName);
        et_password = findViewById(R.id.editTextPassword);
        btn_login = findViewById((R.id.buttonSubmit));
        relativeLayout = findViewById(R.id.relative_layout);
        new_user = findViewById(R.id.tv_new_user);
        btn_start=findViewById(R.id.btn_testService);
        btn_stop=findViewById(R.id.btn_stopService);

        ActivityCompat.requestPermissions
                (MainActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_PERMISSION);
1
//        init_tts_broadcast_receiver();
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this,MyService.class));
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this,MyService.class));
            }
        });
        save=findViewById(R.id.buttonsave);
        sp=getSharedPreferences("userDetails",MODE_PRIVATE);
        String check=sp.getString("isLoggedIn","false");
        String currentDate=currentDate();
       boolean date=compareDate("8/10/2022 9:54:22 AM","8/10/2022 7:54:22 PM",currentDate);
       System.out.println("date:"+date);
        Log.i("check value",check);
        if(check.equals("true"))
        {
          callSecondScreen(username);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                if (username.isEmpty()) {
//                    Snackbar.make(relativeLayout, "Please enter your Username", Snackbar.LENGTH_SHORT).show();

                } else if (password.isEmpty()) {
//                    Snackbar.make(relativeLayout, "Please enter your Password", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.white)).setBackgroundTint(getResources().getColor(R.color.purple)).show();

                } else {
                    Toast.makeText(MainActivity.this, "Hey " + username, Toast.LENGTH_SHORT).show();
                    callSecondScreen(username);

                }
            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register_screen.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sp = getSharedPreferences("userDetails", MODE_PRIVATE);
                editor = sp.edit();
                editor.putString("name", username);
                editor.putString("password", password);
                editor.putString("isLoggedIn", "true");
                editor.apply();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }




    public void callSecondScreen(String username)
                    {
                        Intent intent=new Intent(MainActivity.this,Second_Quizscreen.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public String currentDate(String firstDate, String secondDate)
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
                        return formatter.format(calendar.getTime());

                    }

                    public String currentDate()
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
                        return  formatter.format(calendar.getTime());
                    }
                    private boolean compareDate(String first,String second,String current)
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
                        boolean flag=false;
                        try {
                            Date start=formatter.parse(first);
                            Date end=formatter.parse(second);
                            Date currentDate=formatter.parse(current);
                            boolean isWithin=currentDate.getTime() >= start.getTime() &&
                                    currentDate.getTime() <= end.getTime();
                            if(isWithin)
                            {
                                flag=true;

                            }
                        } catch (Exception e) {
                            flag=false;
                        }
                        return flag;

                    }

                }


