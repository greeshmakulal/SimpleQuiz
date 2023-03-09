package com.simplequiz.app;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.annotation.NonNull;

public class AudioRecorder {

    private final String TAG = "AudioRecorder";

    public final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    public final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public final int SAMPLE_RATE = 16000;
    public final int SAMPLE_PER_CHANNEL = 1024;

    public AudioRecord _recorder;
    public Thread _recorderThread;
    public IAudioRecorder _listener;

    public boolean _isRunning = false;
    public boolean isRunning() {
        return _isRunning;
    }


    public AudioRecorder(@NonNull IAudioRecorder listener) {
        this._listener = listener;
    }

    private void recorderThread() {
        if (_recorder == null || _recorder.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "AudioRecord could not be initialized");
            return;
        }

        _recorder.startRecording();
        _isRunning = true;

        while (_recorder != null && _isRunning) {
            short[] buffer = new short[SAMPLE_PER_CHANNEL];
            int audioData = _recorder.read(buffer, 0, SAMPLE_PER_CHANNEL);

            // Calc amplitude of the wav
            int amplitude = 0;
            for (int i = 0; i < audioData / 2; i++) {
                short sample = buffer[i * 2];
                if (sample > amplitude) {
                    amplitude = sample;
                }
            }

            _listener.onAudioData(buffer, amplitude);
        }
    }

    public int get_amplitude(short[] buffer){
        int temp=0;
        try {

            return _recorder.read(buffer, 0, SAMPLE_PER_CHANNEL);
        }catch (Exception e){
            Log.e(TAG, ":::::FATAL FUNCTION::::: "+e.getMessage());
        }
        Log.e(TAG,"Returning :"+temp);
        return temp;
    }


    @SuppressLint("MissingPermission")
    public void start() {
        int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT) * 2;
        try {
            _recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);
            if (_recorder == null || _recorder.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "AudioRecord could not be initialized");
                return;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        _recorderThread = new Thread(this::recorderThread);
        _recorderThread.start();
    }


    public void stop() {
        if (_recorder != null && _recorder.getState() != AudioRecord.STATE_UNINITIALIZED) {
            _recorder.stop();
            _recorder.release();
            _isRunning = false;
        }

        if (_recorderThread != null) {
            _recorderThread.interrupt();
        }
    }
}
