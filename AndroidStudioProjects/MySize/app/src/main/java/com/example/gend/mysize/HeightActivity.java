package com.example.gend.mysize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class HeightActivity extends AppCompatActivity {
    public static final String HEIGHT = "HEIGHT";
    private TextView mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        mHeight = (TextView) findViewById(R.id.height);

        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(this);
        int heiht = perf.getInt(HEIGHT,160);
        mHeight.setText(String.valueOf(heiht));

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                String item = (String) spinner.getSelectedItem();
                if (!item.isEmpty()) {
                    mHeight.setText(item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(heiht);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String valus = String.valueOf(i);
                mHeight.setText(valus);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) findViewById(i);
                String value = radiobutton.getText().toString();
                mHeight.setText(value);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences perf = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = perf.edit();
        editor.putInt(HEIGHT, Integer.parseInt(mHeight.getText().toString()));
        editor.commit();
    }
}
