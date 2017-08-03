package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.AllSportsAdapter;
import com.softuvo.ipundit.adapters.SearchTeamSportsAdapter;
import com.softuvo.ipundit.adapters.SearchUserSportsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class BroadcastListenerMainActivity extends BaseActivity {
    private Activity mContext;
    private List<SportsNameModel.Sports> sportsItemList;
    AllSportsAdapter allsportsadapter;
    private SearchUserSportsAdapter searchUserSportsAdapter;
    private SearchTeamSportsAdapter searchTeamSportsAdapter;
    String selectedSearchType = "sports";

    @BindView(R.id.rv_game_items)
    RecyclerView rvGameItems;

    @BindView(R.id.ll_main_sports)
    CustomLinearLayout llMainSports;

    @BindView(R.id.ed_search_game)
    EditText edSearchGame;

    @BindView(R.id.iv_close_search)
    ImageView ivCloseSearch;

    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;

    @BindView(R.id.progress_bar_broadcastlistner_mainactivity)
    ProgressBar progressBar;

    @BindView(R.id.rl_search_type_container)
    RelativeLayout rlSearchTypeContainer;

    @BindView(R.id.btn_user)
    Button btnUser;

    @BindView(R.id.btn_team)
    Button btnTeam;

    @BindView(R.id.btn_sports)
    Button btnSports;


    private SwipeRefreshLayout swipeDownRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_listner_main);
        mContext = BroadcastListenerMainActivity.this;
        ButterKnife.bind(mContext);
        rvGameItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        swipeDownRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeDownRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeDownRefresh.setRefreshing(false);

                        checkConnection();
                    }
                }, 2000);
            }
        });
        checkConnection();
    }

    private void checkConnection() {
        if (ConnectivityReceivers.isConnected()) {
            getSportsAndLeaugesData();
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getSportsAndLeaugesData() {
        progressBar.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(llMainSports);
            mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            App.getApiHelper().getSportsAndLeauges(new ApiCallBack<SportsNameModel>() {
                @Override
                public void onSuccess(final SportsNameModel sportsNameModel) {
                    progressBar.setVisibility(View.GONE);
                    enableUserIntraction();
                    if (sportsNameModel.getData() != null)
                        sportsItemList = sportsNameModel.getData();
                    allsportsadapter = new AllSportsAdapter(mContext, sportsItemList, new AllSportsAdapter.ItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            if (sportsNameModel.getData().get(position).getId() != null)
                                AppPreferences.init(mContext).putString(AppConstant.SPORTS_ID, sportsNameModel.getData().get(position).getId());
                            Intent intent = new Intent(mContext, SubCategotyActivity.class);
                            if (sportsNameModel.getData().get(position).getLeague() != null) {
                                intent.putExtra("leagueData", sportsNameModel.getData().get(position));
                                intent.putExtra("sportsId", sportsNameModel.getData().get(position).getId());
                                intent.putExtra("leagueSlected", position);

                            } else {
                                intent.putExtra("sportsId", sportsNameModel.getData().get(position).getId());
                                intent.putExtra("leagueData", "");
                                intent.putExtra("leagueSlected", position);
                            }
                            startActivity(intent);
                        }
                    });
                    rvGameItems.setAdapter(allsportsadapter);

                    edSearchGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchGame.setFocusable(true);
                            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST))
                                rlSearchTypeContainer.setVisibility(View.GONE);
                            else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER))
                                rlSearchTypeContainer.setVisibility(View.VISIBLE);

                        }
                    });
                    ivSearchIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchGame.setFocusable(true);
                        }
                    });
                    edSearchGame.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text = null;
                            text = edSearchGame.getText().toString();
                            allsportsadapter.filter(text);
                            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                                if (selectedSearchType.equalsIgnoreCase("user")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "user");
                                    mountMap.put("search_text", edSearchGame.getText().toString());
                                    mountMap.put("live", "0");
                                } else if (selectedSearchType.equalsIgnoreCase("team")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "user");
                                    mountMap.put("search_text", edSearchGame.getText().toString());
                                    mountMap.put("live", "0");

                                } else if (selectedSearchType.equalsIgnoreCase("sports")) {
                                    text = edSearchGame.getText().toString();
                                    allsportsadapter.filter(text);
                                }
                            } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
                                if (selectedSearchType.equalsIgnoreCase("user")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "user");
                                    mountMap.put("search_text", edSearchGame.getText().toString());
                                    mountMap.put("live", "1");
                                    getUserSearchDetailsList(mountMap);
                                } else if (selectedSearchType.equalsIgnoreCase("team")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "team");
                                    mountMap.put("search_text", edSearchGame.getText().toString());
                                    mountMap.put("live", "1");
                                    getTeamSearchDetailsList(mountMap);

                                } else if (selectedSearchType.equalsIgnoreCase("sports")) {
                                    text = edSearchGame.getText().toString();
                                    allsportsadapter.filter(text);
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    ivCloseSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchGame.setText("");
                            checkConnection();
                            hideKeyboard();
                        }
                    });

                }

                @Override
                public void onFailure(String message) {
                    progressBar.setVisibility(View.GONE);
                    enableUserIntraction();
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    //    @OnClick(R.id.btn_user)
//    public void onClickBtnUser(){
//        selectedSearchType="user";
//    }
//    @OnClick(R.id.btn_team)
//    public void onClickBtnTeam(){
//        selectedSearchType="team";
//    }
//
//    @OnClick(R.id.btn_sports)
//    public void onClickBtnSports(){
//        selectedSearchType="sports";
//        checkConnection();
//    }
    @OnClick({R.id.btn_user, R.id.btn_team, R.id.btn_sports})
    public void onClickBtn(View view) {
        if (view.getId() == R.id.btn_user) {
            selectedSearchType = "user";
            rvGameItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            btnUser.setTextColor(getResources().getColor(R.color.colorPurple));
            btnSports.setTextColor(getResources().getColor(R.color.colorWhite));
            btnTeam.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        if (view.getId() == R.id.btn_team) {
            selectedSearchType = "team";
            rvGameItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            btnUser.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSports.setTextColor(getResources().getColor(R.color.colorWhite));
            btnTeam.setTextColor(getResources().getColor(R.color.colorPurple));
        }
        if (view.getId() == R.id.btn_sports) {
            selectedSearchType = "sports";
            btnUser.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSports.setTextColor(getResources().getColor(R.color.colorPurple));
            btnTeam.setTextColor(getResources().getColor(R.color.colorWhite));
            checkConnection();
        }
    }

    private void getUserSearchDetailsList(Map<String, String> mountMap) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().searchSportsUser(mountMap, new ApiCallBack<UserSearchSportsModel>() {
                @Override
                public void onSuccess(UserSearchSportsModel userSearchSportsModel) {
                    if (userSearchSportsModel != null) {
                        rvGameItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        final List<UserSearchSportsModel.UserDatum> searchUserDetailsList = userSearchSportsModel.getData();
                        searchUserSportsAdapter = new SearchUserSportsAdapter(mContext, searchUserDetailsList, new SearchUserSportsAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                intent.putExtra("userComingFrom", "sprotsUserSearch");
                                intent.putExtra("mUserSearchDatum", searchUserDetailsList.get(position));
                                startActivity(intent);
                            }
                        });
                        rvGameItems.setAdapter(searchUserSportsAdapter);
                        searchUserSportsAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String message) {
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
    }

    private void getTeamSearchDetailsList(Map<String, String> mountMap) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().searchSportsTeam(mountMap, new ApiCallBack<TeamSearchSportsModel>() {
                @Override
                public void onSuccess(TeamSearchSportsModel teamSearchSportsModel) {
                    if (teamSearchSportsModel != null) {
                        rvGameItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        final List<TeamSearchSportsModel.Datum> searchTeamDetailsList = teamSearchSportsModel.getData();
                        searchTeamSportsAdapter = new SearchTeamSportsAdapter(mContext, searchTeamDetailsList, new SearchTeamSportsAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                                intent.putExtra("userComingFrom", "sprotsTeamSearch");
                                intent.putExtra("mTeamSearchDatum", searchTeamDetailsList.get(position));
                                startActivity(intent);
                            }
                        });
                        rvGameItems.setAdapter(searchTeamSportsAdapter);
                        searchTeamSportsAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String message) {
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
    }


    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
        checkConnection();
    }
}
