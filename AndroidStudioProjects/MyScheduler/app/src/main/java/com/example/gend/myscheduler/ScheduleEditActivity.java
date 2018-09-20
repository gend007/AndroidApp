package com.example.gend.myscheduler;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScheduleEditActivity extends AppCompatActivity {
    private Realm mRealm;
    EditText mDateEdit;
    EditText mTitleEdit;
    EditText mDatileEdit;
    Button mDalete;

    //初期画面処理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        mRealm = Realm.getDefaultInstance();
        mDateEdit = (EditText) findViewById(R.id.dateText);
        mTitleEdit = (EditText) findViewById(R.id.titleEdit);
        mDatileEdit = (EditText) findViewById(R.id.detailEdit);
        mDalete = (Button) findViewById(R.id.delete);

        long scheduleId = getIntent().getLongExtra("schedule_id",-1);
        if (scheduleId != -1){
            RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            Schedule schedule = results.first();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdf.format(schedule.getDate());
            mDateEdit.setText(date);
            mTitleEdit.setText(schedule.getTitle());
            mDatileEdit.setText(schedule.getDetail());
            mDalete.setVisibility(View.VISIBLE);
        }else {
            mDalete.setVisibility(View.INVISIBLE);
        }
    }

    //保存ボタン処理（登録/更新）
    public void onSaveTapped(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dataParse = new Date();
        try {
            dataParse = sdf.parse(mDateEdit.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date date = dataParse;
        long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        //-1 以外の場合、画面更新
        if (scheduleId != -1){
            final RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule = results.first();
                    schedule.setDate(date);
                    schedule.setTitle(mTitleEdit.getText().toString());
                    schedule.setDetail(mDatileEdit.getText().toString());
                }
            });
            Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)
                    .show();
        }else {
            //-1 の場合。画面登録
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Schedule.class).max("id");
                    long nextId = 0;
                    if (maxId != null) nextId = maxId.longValue() + 1;
                    Schedule schedule = realm.createObject(Schedule.class, new Long(nextId));
                    schedule.setDate(date);
                    schedule.setTitle(mTitleEdit.getText().toString());
                    schedule.setDetail(mDatileEdit.getText().toString());
                }
            });
            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //削除ボタン処理
    public void onDeleteTapped(View view){
        final long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1){
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule = realm.where(Schedule.class)
                            .equalTo("id", scheduleId).findFirst();
                    schedule.deleteFromRealm();
                }
            });
            Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
