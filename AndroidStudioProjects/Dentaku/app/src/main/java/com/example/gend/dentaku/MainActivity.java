package com.example.gend.dentaku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    //定数定義
    private final int KEY_0 = 0;
    private final int KEY_1 = 1;
    private final int KEY_2 = 2;
    private final int KEY_3 = 3;
    private final int KEY_4 = 4;
    private final int KEY_5 = 5;
    private final int KEY_6 = 6;
    private final int KEY_7 = 7;
    private final int KEY_8 = 8;
    private final int KEY_9 = 9;
    private final int KEY_PLUS = 10;
    private final int KEY_MINUS = 11;
    private final int KEY_EQUAL = 13;
    private final int KEY_TEN = 12;
    private final int KEY_CLEAR = 14;
    TextView mTextView;
    Button mButton[];
    int mId[] = {R.id.button0,R.id.button1,R.id.button2,
            R.id.button3, R.id.button4,R.id.button5,
            R.id.button6,R.id.button7, R.id.button8,
            R.id.button9,R.id.buttonPlas,R.id.buttonMinus,
            R.id.buttonTen,R.id.buttonEqual,R.id.buttonClear};
    int beforstatus = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = new Button[mId.length];
        for (int i = 0; i < mId.length; i++){
            mButton[i] = (Button) findViewById(mId[i]);
            mButton[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        mTextView = (TextView) findViewById(R.id.textView);
        for (int i = 0; i < mId.length; i++){
            if(view.equals(mButton[i])){
                if(i == KEY_CLEAR){
                    mTextView.setText("");
                    total = 0;
                    beforstatus = 0;
                }else if(i < 10){
                    String newValue = mTextView.getText().toString();
                    newValue = newValue + i;
                    mTextView.setText(newValue);
                    beforstatus = i;
                }else if (i == KEY_PLUS){
                    String nowValue = mTextView.getText().toString();
                    int value = Integer.parseInt(nowValue);
                    total += value;
                    mTextView.setText(Integer.toString(total));
                    beforstatus = i;
                }else if(i == KEY_EQUAL){
                    mTextView.setText(Integer.toString(total));
                }
                break;
            }
        }
    }
}
