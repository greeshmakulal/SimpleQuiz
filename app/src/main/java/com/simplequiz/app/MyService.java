package com.simplequiz.app;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.filament.Engine;
//import com.vivoka.csdk.asr.Engine;

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

public class MyService extends Service {

    // declaring object of MediaPlayer
    private MediaPlayer player;
    int _initCounter = 0;
    public Recognizer _recognizer = null;
    String ASR_APPLICATION = "pick_final";
    public AudioRecorder _audioRecorder = null;
    Boolean is_asr_initialized = false;
    int ASR_CONFIDENCE_THRESHOLD = 4500;
    public IBinder mBinder = new MyBinder();
    final static int  REQUEST_RECORD_PERMISSION=1;
    Airoplane_Mode_Changed am=new Airoplane_Mode_Changed();

    @Override
    public void onCreate() {
        super.onCreate();


        IntentFilter filter = new IntentFilter("RESULT");
        this.registerReceiver(am,filter);
    }
    private void init() {
        final Context mContext = this;
        Vsdk.init(this, "config/vsdk_2000.json", success -> {
            if (success) {
                Engine.getInstance().init(mContext, bool -> {
                    if (bool) {
                        _initCounter++;
                        make();
                    } else {
                        System.out.println( "Cannot initialize the ASR engine");
                    }
                });
                // TTS
                com.vivoka.csdk.tts.Engine.getInstance().init(mContext, bool -> {
                    if (bool) {
                        _initCounter++;
                        make();
                    } else {
                        System.out.println( "Cannot initialize the TTS engine");
                    }
                });

            } else {
                System.out.println("Cannot initialize the VSDK engine");
            }
        });
    }
    private void make() {
        if (_initCounter != 2) {
            return;
        }
        startRecognition();
    }
    private void startRecognition() {
        IRecognizerListener recognizerListener = new IRecognizerListener() {
            @Override
            public void onEvent(RecognizerEventCode eventCode, int timeMarker, String message) {

            }

            @Override
            public void onResult(String result, RecognizerResultType resultType, boolean isFinal) {
                Log.e(TAG,"onResult"+resultType.name() + " " + result);
                process_RecognitionResult(result, resultType);
            }

            @Override
            public void onError(RecognizerErrorCode error, String message) {
//
            }

            @Override
            public void onWarning(RecognizerErrorCode error, String message) {
//
            }
        };

        try {
            _recognizer = Engine.getInstance().makeRecognizer("rec", recognizerListener);
            _recognizer.setModel(ASR_APPLICATION);
//            writeLog(getLineNumber(), 1, "Added application");

            _audioRecorder = new AudioRecorder((buffer, amplitude) -> {
                try {
                    _recognizer.process(BufferUtils.convertShortsToBytes(buffer), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            _audioRecorder.start();
//            sendData("START", "started");
            is_asr_initialized = true;
        } catch (Exception e) {
//            sendData("START", "no");
//            writeLog(getLineNumber(), 5, "CATCH :" + e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
//            writeLog(getLineNumber(), 5, "CATCH in start_recognizer :" + sw.toString());
            e.printStackTrace();
        }
    }
    public void process_RecognitionResult(String result, RecognizerResultType resultType) {
        try {
            _recognizer.setModel(ASR_APPLICATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //writeLog(getLineNumber(),1,"RESULT :"+result);
        String final_result = "";
        String rec_tag = "";
        if (resultType == RecognizerResultType.ASR) {
            AsrResult asrResult = AsrResultParser.parseResult(result);
            for (AsrResultHypothesis asrResultHypothesis : asrResult.hypotheses) {
                if (asrResultHypothesis.confidence >= ASR_CONFIDENCE_THRESHOLD) {
                    final_result = asrResultHypothesis.text;
                    rec_tag = asrResultHypothesis.tags.get(0);
                }
            }
        }

        if (final_result == null || final_result.isEmpty()) {
            //Log.e(TAG, "result is null or empty");
            return;
        }

        try {
            String[] temp = rec_tag.split("#");
            //Log.e(TAG,"All tags :"+ Arrays.toString(temp));
            rec_tag = temp[1];
        } catch (Exception e) {
//            writeLog(getLineNumber(), 5, "Error in voice service :" + e.getMessage());
        }

        //play_tts(final_result);

        Log.e(TAG, "ASR Result :" + final_result);
//        Log.e(TAG, "ASR Result :" + rec_tag);

//        send_live_feed(final_result, "STT");
        if (final_result.length() > 0) {
            if (rec_tag != null && !rec_tag.isEmpty()) {
                switch (rec_tag) {
                    case "NUMBER":
                        sendData("NUMBER", final_result);
                        break;
                    case "YESNO":
                        sendData("YESNO", final_result);
                        break;
                    case "OVERFLOW":
                        sendData("OVERFLOW", final_result);
                        break;
                    case "ANYWHERE":
                        sendData("ANYWHERE", final_result);
                        break;
                    case "READY":
                        sendData("READY", final_result);
                        break;
                    case "NUMBERS":
                        sendData("NUMBERS", final_result);
                        break;
                    case "OPERATIONS":
                        sendData("OPERATIONS", final_result);
                        break;
                    case "ENQUIRY":
                        sendData("ENQUIRY", final_result);
                        break;
                    case "ALPHA":
                        sendData("ALPHA", final_result);
                        break;
                    default:
                        //CoreEngine.getInstance().asrEngine.changeState(AsrState.resume);
                        break;
                }
            }
        }

    }
    private void sendData(String tag, String result) {
        try {
            Intent sendResult = new Intent();
            sendResult.setAction("RESULT");
            sendResult.putExtra("TAG", tag.trim());
            sendResult.putExtra("VOICE", result.trim());
            sendBroadcast(sendResult);
        } catch (Exception e) {
            System.out.println ("error");
        }
    }
    @Override

    // execution of service will start
    // on calling this method

    public int onStartCommand(Intent intent, int flags, int startId) {

        // creating a media player which
        // will play the audio of Default
        // ringtone in android device
        init ();
        player = MediaPlayer.create( this, Settings.System.DEFAULT_RINGTONE_URI );

        // providing the boolean
        // value as true to play
        // the audio on loop
        player.setLooping( true );

        // starting the process
        player.start();


        // returns the status
        // of the program
        return START_STICKY;
    }

    @Override

    // execution of the service will
    // stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
        unregisterReceiver(am);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
