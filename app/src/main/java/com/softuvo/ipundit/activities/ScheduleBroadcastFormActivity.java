package com.softuvo.ipundit.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.ScheduleFormModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduleBroadcastFormActivity extends BaseActivity {
    private Activity mContext;
    int year,month,day,hour,minute;
    private String newtime;
    Calendar c = Calendar.getInstance();

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.ed_title)
    EditText edTitle;

    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_broadcast_form);
        ButterKnife.bind(this);
        mContext=ScheduleBroadcastFormActivity.this;
    }


    @OnClick(R.id.tv_date)
    public void onClicktvDate(){
        datePicker();
    }

    @OnClick(R.id.tv_time)
    public void onClicktvTime(){
        timePicker();
    }

    @OnClick(R.id.tv_save)
    public void onClicktvSave(){
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("user_id", AppPreferences.init(mContext).getString(AppConstant.USER_ID));
        mountMap.put("title", edTitle.getText().toString());
        mountMap.put("schedule_date", tvDate.getText().toString());
        mountMap.put("time", tvTime.getText().toString());
        mountMap.put("match_id", getIntent().getStringExtra("matchid"));
        mountMap.put("channel_type", getIntent().getStringExtra("channelType"));
        Log.e("mountMap",mountMap.toString());
        scheduleForm(mountMap);
    }

    private void scheduleForm(Map<String, String> mountMap) {
        App.getApiHelper().scheduleBroadcastForm(mountMap, new ApiCallBack<ScheduleFormModel>() {
            @Override
            public void onSuccess(ScheduleFormModel map) {
                Log.e("Apiiiii",map.toString());
                Log.e("Apiii","Successfully updtaed");
                if(map.getMessage().equalsIgnoreCase("Broadcast scheduled successfully")) {
                    String msg = AppPreferences.init(mContext).getString(AppConstant.USER_NAME) + "has scheduled a broadcast on match @" + getIntent().getStringExtra("matchname") + " on " + tvDate.getText().toString() + " at " + tvTime.getText().toString();
                    createMap(msg);
                }
                finish();
            }

            @Override
            public void onFailure(String message) {
                SnackbarUtil.showWarningShortSnackbar(mContext,getString(R.string.unable));

            }
        });
    }

    private void createMap(String msg) {
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("id", AppPreferences.init(mContext).getString(AppConstant.USER_ID));
        mountMap.put("msg", msg);
        followNotificationMessage(mountMap);
    }
    private void followNotificationMessage(Map<String, String> mountMap) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followNotification(mountMap, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {

                }

                @Override
                public void onFailure(String message) {

                }
            });
        }

    }

    private void datePicker() {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void timePicker() {
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                        newtime = String.format("%02d:%02d:00",hourOfDay,minuteOfDay);
                        tvTime.setText(newtime);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

}
