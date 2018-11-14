package com.example.edu.audiorecord;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button play, record, stop;
    MediaRecorder myAudioRecorder = null;
    String outputFile = null;
    MediaPlayer m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.buttonPlay);
        record = (Button) findViewById(R.id.buttonRecord);
        stop = (Button) findViewById(R.id.buttonStop);

        play.setOnClickListener(this);
        record.setOnClickListener(this);
        stop.setOnClickListener(this);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.reset();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonRecord:
                try {
                    myAudioRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAudioRecorder.start();
                record.setEnabled(false);
                stop.setEnabled(true);
                break;

            case R.id.buttonStop:
                stop.setEnabled(false);
                play.setEnabled(true);
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                break;

            case R.id.buttonPlay:
                m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                m.start();
                break;

        }

}
}
