package com.mutiara.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvOnceTime, tvOnceDate, tvRepeatingTime;
    private ImageButton ibOnceTIme, ibOnceDate, ibRepeatingTime;
    private EditText etOnceMessage, etRepeatMessage;
    private Button btnSetOnceAlarm, btnSetRepeatAlarm, btnCancelRepeatingAlarm;

    private AlarmReceiver alarmReceiver;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private  int mHourRepeat, mMinuteRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOnceTime = findViewById(R.id.tv_Time);
        tvOnceDate = findViewById(R.id.tv_Date);
        tvRepeatingTime = findViewById(R.id.tv_Time2);

        ibOnceTIme = findViewById(R.id.ib_Time);
        ibOnceDate = findViewById(R.id.ib_Date);
        ibRepeatingTime = findViewById(R.id.ibTime2);

        etOnceMessage = findViewById(R.id.ET1);
        etRepeatMessage = findViewById(R.id.ET2);

        btnSetOnceAlarm = findViewById(R.id.btn1);
        btnSetRepeatAlarm = findViewById(R.id.btn2);
        btnCancelRepeatingAlarm = findViewById(R.id.btn3);

        alarmReceiver = new AlarmReceiver();

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        mHourRepeat = mHour;
        mMinuteRepeat = mMinute;

        ibOnceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvOnceDate.setText(String.format("%04d-%02d-%02d",year,month + 1, dayOfMonth));
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        ibOnceTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvOnceTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        mHour = hourOfDay;
                        mMinute = minute;
                    }
                },mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
        ibRepeatingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvRepeatingTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        mHourRepeat = hourOfDay;
                        mMinuteRepeat = minute;
                    }
                },mHourRepeat, mMinuteRepeat, true);
                timePickerDialog.show();
            }
        });
        btnSetOnceAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvOnceDate.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Date Is Empety", Toast.LENGTH_SHORT).show();
                }else if (tvOnceTime.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Time Is Empety", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(etOnceMessage.getText().toString())){
                    etOnceMessage.setError("Message Can't be empety");
                }else {
                    alarmReceiver.setOntimeAlarm(MainActivity.this, AlarmReceiver.TYPE_ONE_TIME,
                            tvOnceDate.getText().toString(), tvOnceTime.getText().toString(),
                            etOnceMessage.getText().toString());
                }
            }
        });

        btnSetRepeatAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvRepeatingTime.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Time Is Empety", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(etRepeatMessage.getText().toString())){
                    etRepeatMessage.setError("Message Can't be Empety");
                }else {
                    alarmReceiver.setRepeatAlarm(MainActivity.this, alarmReceiver.TYPE_REPEATING,
                            tvRepeatingTime.getText().toString(),
                            etRepeatMessage.getText().toString());
                }
            }
        });
        btnCancelRepeatingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmReceiver.isAlarmSet(MainActivity.this, AlarmReceiver.TYPE_REPEATING)){
                    tvRepeatingTime.setText("");
                    etRepeatMessage.setText("");
                    alarmReceiver.cancelAlarm(MainActivity.this, AlarmReceiver.TYPE_REPEATING);
                }

            }
        });

    }
}