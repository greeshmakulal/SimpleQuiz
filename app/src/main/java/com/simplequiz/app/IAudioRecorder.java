package com.simplequiz.app;

public interface IAudioRecorder {
    void onAudioData(short[] buffer, int amplitude);
}
