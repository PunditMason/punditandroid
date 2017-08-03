package com.softuvo.ipundit.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.datetimepicker.date.DatePickerDialog;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.MatchListListnerAdapter;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.adapters.MatchListBroadcastAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.custom_calander.RWeekCalendar;
import com.softuvo.ipundit.custom_calander.listener.CalenderListener;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;
import org.joda.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class MatchListActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private Activity mContext;
    private RWeekCalendar rCalendarFragment;
    private MatchListBroadcastAdapter matchListBroadcastAdapter;
    private MatchListListnerAdapter matchListListnerAdapter;
    private SportsNameModel.Sports.League subCatDetail;

    @BindView(R.id.rv_match_list)
    RecyclerView rvMatchList;

    @BindView(R.id.progress_barmatch_list)
    ProgressBar progressBar;

    @BindView(R.id.tv_no_match_message)
    TextView tvNoMatchMessage;

    @BindView(R.id.container)
    LinearLayout llContainer;

    @BindView(R.id.rl_match_listing_main)
    CustomRelativeLayout rlMatcListingMain;

    @BindView(R.id.ll_match_header)
    LinearLayout llMatchHeader;

    @BindView(R.id.ll_match_header_listner)
    LinearLayout llMatchHeaderListner;

    @BindView(R.id.tv_breaking_news_match_list)
    CustomTextView tvBreakingNewsMatchList;

    @BindView(R.id.tv_leauge_table_teams)
    TextView tvleaugetableTemas;

    @BindView(R.id.iv_leauge_icon)
    ImageView ivLeaugeIcon;

    @BindView(R.id.tv_leauge_name)
    TextView tvLeaugeName;

    @BindView(R.id.crl_team_broadcasters_count)
    CustomRelativeLayout crlTeamBroadcastersCount;

    @BindView(R.id.tv_team_broadcasters_count)
    TextView tvTeamBroadcastersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        mContext = MatchListActivity.this;
        ButterKnife.bind(mContext);
        setData();
        initCal();
        getNewsFromServer();
    }

    @OnClick(R.id.tv_leauge_table_teams)
    public void leagueTable() {
        if(subCatDetail.getMarkImage()!=null)
        AppPreferences.init(mContext).putString(AppConstant.LEAGUE_IMAGE_URL, ApiConstants.LEAGUE_IMAGE_BASE_URL + subCatDetail.getMarkImage());
        Intent intent = new Intent(mContext, MatchStandingActivity.class);
        intent.putExtra("sportsLeagueId", getIntent().getStringExtra("sportsLeagueId"));
        intent.putExtra("sportsLeagueName", subCatDetail.getName());
        startActivity(intent);
    }

    private void initCal() {
        rvMatchList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        rCalendarFragment = new RWeekCalendar();
        rCalendarFragment.startDate(1989, 9, 1);//Start date
        rCalendarFragment.endDate(2018, 12, 31);//Ending date
        Bundle args = new Bundle();
        args.putString(RWeekCalendar.PACKAGENAME, getApplicationContext().getPackageName());
        args.putString(RWeekCalendar.DATE_SELECTOR_BACKGROUND, String.valueOf(R.drawable.circle));
        rCalendarFragment.setArguments(args);
        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.container, rCalendarFragment);
        t.commit();

        CalenderListener listener = new CalenderListener() {
            @Override
            public void onSelectPicker() {
                //User can use any type of pickers here the below picker is only Just a example
                DatePickerDialog.newInstance(MatchListActivity.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }

            @Override
            public void onSelectDate(LocalDateTime mSelectedDate) {
                String selectedDate = "" + mSelectedDate.getYear() + "-" + mSelectedDate.getMonthOfYear() + "-" + mSelectedDate.getDayOfMonth();
                progressBar.setVisibility(View.VISIBLE);
                disableUserIntraction();
                if (ConnectivityReceivers.isConnected()) {
                    getMatchData(getIntent().getStringExtra("sportsLeagueId") + "/" + selectedDate);
                } else {
                    SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
                }
            }
        };
        rCalendarFragment.setCalenderListener(listener);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        rCalendarFragment.setDateWeek(calendar);//Sets the selected date from Picker
    }

    // getMatchListing from server
    private void getMatchData(String stringPath) {
        if (ConnectivityReceivers.isConnected()) {
            //while Use comimg from Braodcasting screen

            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                App.getApiHelper().getBrodcastMatchList(stringPath, new ApiCallBack<BroadcastMatchlistModel>() {
                    @Override
                    public void onSuccess(BroadcastMatchlistModel broadcastMatchlistModel) {
                        if (broadcastMatchlistModel != null) {
                            progressBar.setVisibility(View.GONE);
                            enableUserIntraction();
                            final List<BroadcastMatchlistModel.Datum> matchListList = broadcastMatchlistModel.getData();
                            if (matchListList.size() > 0)
                                tvNoMatchMessage.setVisibility(View.GONE);
                            else
                                tvNoMatchMessage.setVisibility(View.VISIBLE);
                            matchListBroadcastAdapter = new MatchListBroadcastAdapter(mContext, matchListList, new MatchListBroadcastAdapter.ItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    AppPreferences.init(mContext).putString(AppConstant.LEAGUE_IMAGE_URL, ApiConstants.LEAGUE_IMAGE_BASE_URL + subCatDetail.getMarkImage());
                                    openBottomSheet(matchListList.get(position));
                                }
                            });
                            rvMatchList.setAdapter(matchListBroadcastAdapter);
                            matchListBroadcastAdapter.notifyDataSetChanged();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            enableUserIntraction();
                            SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        SnackbarUtil.showErrorLongSnackbar(mContext, message);
                    }
                });
            }
            //while Use comimg from Listening screen

            else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {

                App.getApiHelper().getListnerMatchList(stringPath, new ApiCallBack<MatchListListnerModel>() {
                    @Override
                    public void onSuccess(MatchListListnerModel matchListListnerModel) {
                        if (matchListListnerModel != null) {
                            progressBar.setVisibility(View.GONE);
                            enableUserIntraction();
                            if(matchListListnerModel.getTeamBroadcasterCount()!= null) {
                                if (!matchListListnerModel.getTeamBroadcasterCount().equalsIgnoreCase("0")) {
                                    crlTeamBroadcastersCount.setVisibility(View.VISIBLE);
                                    tvTeamBroadcastersCount.setText(matchListListnerModel.getTeamBroadcasterCount());
                                }
                            }
                            final List<MatchListListnerModel.ListenMatchList> matchListenList = matchListListnerModel.getData();
                            if (matchListenList.size() > 0)
                                tvNoMatchMessage.setVisibility(View.GONE);
                            else
                                tvNoMatchMessage.setVisibility(View.VISIBLE);
                                 matchListListnerAdapter = new MatchListListnerAdapter(mContext, matchListenList, new MatchListListnerAdapter.ItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    AppPreferences.init(mContext).putString(AppConstant.LEAGUE_IMAGE_URL, ApiConstants.LEAGUE_IMAGE_BASE_URL + subCatDetail.getMarkImage());
                                    Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                                    intent.putExtra("userComingFrom", "matchList");
                                    intent.putExtra("mMatchDatum", matchListenList.get(position));
                                    startActivity(intent);
                                }
                            });
                            rvMatchList.setAdapter(matchListListnerAdapter);
                            matchListListnerAdapter.notifyDataSetChanged();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            enableUserIntraction();
                            SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        SnackbarUtil.showErrorLongSnackbar(mContext, message);
                    }
                });
            }
        } else {
            progressBar.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

    }

    private void setData() {
        if(AppPreferences.init(mContext).getString(APP_BACKGROUND)!=null)
        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlMatcListingMain);
        if(getIntent().getSerializableExtra("subCatDetail")!=null)
        subCatDetail = (SportsNameModel.Sports.League) getIntent().getSerializableExtra("subCatDetail");
        if(subCatDetail.getMarkImage()!=null)
        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + subCatDetail.getMarkImage()).into(ivLeaugeIcon);
        if(subCatDetail.getName()!=null)
        tvLeaugeName.setText(subCatDetail.getName());
        if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
            llMatchHeader.setVisibility(View.VISIBLE);
            llMatchHeaderListner.setVisibility(View.GONE);
        } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
            llMatchHeader.setVisibility(View.GONE);
            llMatchHeaderListner.setVisibility(View.VISIBLE);
        }

    }

    // Getting News From Servr Every 20 sec.
    private void getNewsFromServer() {
        int apiHitTimeInterval = 20000;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        App.getApiHelper().getBreakingNews(new ApiCallBack<BreakingNewsParentModel>() {
                            @Override
                            public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                if (breakingNewsParentModel != null) {
                                    ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                    List<String> breakingNews = new ArrayList<>();
                                    for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                        if(breakingNewsResponse.get(i).getTitle()!=null)
                                        breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                    }
                                    String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                    tvBreakingNewsMatchList.setText(SubTitle);
                                    tvBreakingNewsMatchList.setSelected(true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                SnackbarUtil.showErrorLongSnackbar(mContext, message);
                            }
                        });
                    }
                });
            }
        }, 0, apiHitTimeInterval);
    }

    public void openBottomSheet(final BroadcastMatchlistModel.Datum mBrDatum) {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_fragment, null);
        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        view.findViewById(R.id.rl_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = checkPermission();
                if (result) {
                    mBottomSheetDialog.dismiss();
                    Intent intent = new Intent(mContext, LiveBroadCastingActivity.class);
                    intent.putExtra("userComingFrom", "matchList");
                    intent.putExtra("mBrDatum", mBrDatum);
                    startActivity(intent);
                }
                }
        });
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.RECORD_AUDIO)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("RECORD AUDIO Permission is necessary to Broadacst");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.RECORD_AUDIO}, AppConstant.MY_PERMISSIONS_REQUEST_MIC);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.RECORD_AUDIO}, AppConstant.MY_PERMISSIONS_REQUEST_MIC);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_MIC:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //code for deny
                }
                break;
        }
    }

}

