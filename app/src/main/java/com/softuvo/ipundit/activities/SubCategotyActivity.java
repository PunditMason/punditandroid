package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelAddMemberTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.AllLeagueAdapter;
import com.softuvo.ipundit.adapters.SearchTeamSportsAdapter;
import com.softuvo.ipundit.adapters.SearchUserLeaguesAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;
import static com.softuvo.ipundit.config.AppConstant.FB_ID;

public class SubCategotyActivity extends BaseActivity {
    private Activity mContext;
    private AllLeagueAdapter allLeagueAdapter;
    private AllLeagueAdapter allLeagueAdapters;
    private SearchUserLeaguesAdapter searchUserLeaguesAdapter;
    private List<SportsNameModel.Sports.League> leaguesItemLists;
    private List<SportsNameModel.Sports.League> leaguesItemList;
    private List<UserSearchLeagueModel.Datum> searchUserDetailsList;
    private List<TeamSearchSportsModel.Datum> searchTeamDetailsList;
    private SearchTeamSportsAdapter searchTeamSportsAdapter;
    private String chatChannelId, chatChannelName, matchid;

    private int position;
    String selectedSearchType = "leagues";
    String text = null;
    private Timer timer;
    String formattedDate;

    @BindView(R.id.iv_user_profile_image)
    ImageView ivUserProfileImage;

    @BindView(R.id.rv_league_items)
    RecyclerView rvLeagueItem;

    @BindView(R.id.ll_sub_cat_main)
    CustomRelativeLayout llSubCatMain;

    @BindView(R.id.tv_sub_cat_breaking_news)
    CustomTextView tvSubCatBreakingNews;

    @BindView(R.id.iv_search_league_icon)
    ImageView ivSearchLeagueIcon;

    @BindView(R.id.iv_close_league_search)
    ImageView closeLeagueSearch;

    @BindView(R.id.ed_search_league)
    EditText edSearchLeague;

    @BindView(R.id.rl_search_type_container)
    RelativeLayout rlSearchTypeContainer;

    @BindView(R.id.btn_user)
    Button btnUser;

    @BindView(R.id.btn_team)
    Button btnTeam;

    @BindView(R.id.btn_leagues)
    Button btnLeagues;


    private SwipeRefreshLayout swipeDownRefreshSubCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoty);
        mContext = SubCategotyActivity.this;
        Date date = new Date();  // to get the date
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // getting date in this format
        formattedDate = df.format(date.getTime());
        Log.e("cureent date:",formattedDate);

        ButterKnife.bind(mContext);
    }

    private void checkConnection() {
        if (ConnectivityReceivers.isConnected()) {
            getNewsFromServer(formattedDate);
            setData();
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void setData() {
        if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
            llSubCatMain.setBackground(getResources().getDrawable(R.drawable.screen_image));
            if (AppPreferences.init(mContext).getString(AppConstant.USER_PROFILE_PIC) != null) {
                ivUserProfileImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + AppPreferences.init(mContext).getString(AppConstant.USER_PROFILE_PIC)).into(ivUserProfileImage);
            }
        }
        else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
            llSubCatMain.setBackground(getResources().getDrawable(R.drawable.screen_image));
            ivUserProfileImage.setVisibility(View.GONE);
        }

       /* if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
            Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(llSubCatMain);*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rvLeagueItem.setLayoutManager(gridLayoutManager);
        if (!mContext.getIntent().getSerializableExtra("leagueData").equals("") && mContext.getIntent().getSerializableExtra("leagueData") != null) {
            position = mContext.getIntent().getIntExtra("leagueSlected", 0);
            final SportsNameModel.Sports sportsNameModel = (SportsNameModel.Sports) mContext.getIntent().getSerializableExtra("leagueData");
            leaguesItemLists = sportsNameModel.getLeague();
            allLeagueAdapters = new AllLeagueAdapter(mContext, leaguesItemLists, new AllLeagueAdapter.ItemClickListener() {
                @Override
                public void onClick(int position) {
                    if (sportsNameModel.getLeague().get(position).getId() != null)
                        AppPreferences.init(mContext).putString(AppConstant.LEAGUE_ID, sportsNameModel.getLeague().get(position).getId());
                    if (leaguesItemLists.get(position).getMarkImage() != null)
                        AppPreferences.init(mContext).putString(AppConstant.LEAGUE_IMAGE_URL, leaguesItemLists.get(position).getMarkImage());
                    Intent intent = new Intent(mContext, MatchListActivity.class);
                    intent.putExtra("sportsLeagueId", getIntent().getStringExtra("sportsId") + "/" + sportsNameModel.getLeague().get(position).getId());
                    intent.putExtra("subCatDetail", sportsNameModel.getLeague().get(position));
                    startActivity(intent);
                }
            });
            rvLeagueItem.setAdapter(allLeagueAdapters);
            edSearchLeague.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edSearchLeague.setFocusable(true);
                    if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST))
                        rlSearchTypeContainer.setVisibility(View.GONE);
                    else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER))
                        rlSearchTypeContainer.setVisibility(View.VISIBLE);
                }
            });
            ivSearchLeagueIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edSearchLeague.setFocusable(true);
                }
            });
            edSearchLeague.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cancelTimer();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    text = edSearchLeague.getText().toString();
                    allLeagueAdapters.filter(text);
                    if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                            text = edSearchLeague.getText().toString();
                            allLeagueAdapters.filter(text);

                    } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
                        if (selectedSearchType.equalsIgnoreCase("leagues")) {
                            text = edSearchLeague.getText().toString();
                            allLeagueAdapters.filter(text);
                        }
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (selectedSearchType.equalsIgnoreCase("user")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "user");
                                    mountMap.put("search_text", edSearchLeague.getText().toString());
                                    mountMap.put("live", "1");
                                    getUserSearchDetailsList(mountMap);
                                } else if (selectedSearchType.equalsIgnoreCase("team")) {
                                    Map<String, String> mountMap = new HashMap<>();
                                    mountMap.put("search_type", "team");
                                    mountMap.put("search_text", edSearchLeague.getText().toString());
                                    mountMap.put("live", "1");
                                    mountMap.put("sport_id", getIntent().getStringExtra("sportsId"));
                                    getTeamSearchDetailsList(mountMap);

                                }
                            }
                        }, 800);
                    }
                }
            });
            closeLeagueSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelTimer();
                    edSearchLeague.setText("");
                    selectedSearchType = "leagues";
                    rlSearchTypeContainer.setVisibility(View.GONE);
                    if (searchUserDetailsList != null) {
                        searchUserDetailsList.clear();
                        searchUserLeaguesAdapter.notifyDataSetChanged();
                    }
                    if (searchTeamDetailsList != null) {
                        searchTeamDetailsList.clear();
                        searchTeamSportsAdapter.notifyDataSetChanged();
                    }
                    getSportsAndLeaugesData();
                    rlSearchTypeContainer.setVisibility(View.GONE);
                    btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                    btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
                    btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                    hideKeyboard();
                }
            });
        }
    }

    // Getting News From Servr Every 20 sec.
    private void getNewsFromServer(final String date) {
        int apiHitTimeInterval = 40000;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       /* App.getApiHelper().getBreakingNews(new ApiCallBack<BreakingNewsParentModel>() {
                            @Override
                            public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                if (breakingNewsParentModel != null) {
                                    ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                    List<String> breakingNews = new ArrayList<>();
                                    for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                        if (breakingNewsResponse.get(i).getTitle() != null)
                                            breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                    }
                                    String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                    tvSubCatBreakingNews.setText(SubTitle);
                                    tvSubCatBreakingNews.setSelected(true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                SnackbarUtil.showErrorLongSnackbar(mContext, message);
                            }
                        });*/
                        App.getApiHelper().getBreakingNewsList("null/"+date , new ApiCallBack<BreakingNewsParentModel>() {
                            @Override
                            public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                if (breakingNewsParentModel != null) {
                                    ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                    List<String> breakingNews = new ArrayList<>();
                                    for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                        if (breakingNewsResponse.get(i).getTitle() != null)
                                            breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                    }
                                    String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                    tvSubCatBreakingNews.setText(SubTitle);
                                    tvSubCatBreakingNews.setSelected(true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {

                            }
                        });
                    }
                });
            }
        }, 0, apiHitTimeInterval);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            SnackbarUtil.showSuccessLongSnackbar(mContext, getResources().getString(R.string.internet_connected_text));
            getNewsFromServer(formattedDate);
            setData();
        } else {
            SnackbarUtil.showSuccessLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));

        }
    }

    @OnClick(R.id.iv_user_profile_image)
    public void goProfileScreen() {
        Intent intent = new Intent(mContext, UserProfileActivity.class);
        startActivity(intent);

    }

    @OnClick({R.id.btn_user, R.id.btn_team, R.id.btn_leagues})
    public void onClickBtn(View view) {
        if (view.getId() == R.id.btn_user) {
            selectedSearchType = "user";
            if (leaguesItemList != null) {
                leaguesItemList.clear();
                cancelTimer();
                allLeagueAdapters.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if (leaguesItemList != null) {
                leaguesItemList.clear();
                cancelTimer();
                allLeagueAdapter.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if (searchTeamDetailsList != null) {
                searchTeamDetailsList.clear();
                searchTeamSportsAdapter.notifyDataSetChanged();
                cancelTimer();
                rvLeagueItem.setAdapter(null);
            }
            if(edSearchLeague.getText()!=null)
                edSearchLeague.getText().clear();
            rvLeagueItem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            swipeDownRefreshSubCat.setEnabled(false);
        }
        if (view.getId() == R.id.btn_team) {
            selectedSearchType = "team";
            if (leaguesItemLists != null) {
                leaguesItemLists.clear();
                cancelTimer();
                allLeagueAdapters.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if (leaguesItemList != null) {
                leaguesItemList.clear();
                cancelTimer();
                allLeagueAdapter.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if (searchUserDetailsList != null) {
                searchUserDetailsList.clear();
                cancelTimer();
                searchUserLeaguesAdapter.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if(edSearchLeague.getText()!=null)
                edSearchLeague.getText().clear();
            rvLeagueItem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            swipeDownRefreshSubCat.setEnabled(false);
        }
        if (view.getId() == R.id.btn_leagues) {
            selectedSearchType = "leagues";
            if (searchTeamDetailsList != null) {
                searchTeamDetailsList.clear();
                cancelTimer();
                searchTeamSportsAdapter.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            if (searchUserDetailsList != null) {
                searchUserDetailsList.clear();
                cancelTimer();
                searchUserLeaguesAdapter.notifyDataSetChanged();
                rvLeagueItem.setAdapter(null);
            }
            getSportsAndLeaugesData();
            if(edSearchLeague.getText()!=null)
                edSearchLeague.getText().clear();
            btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
            btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        }
    }

    private void cancelTimer() {
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedSearchType = "leagues";
        cancelTimer();
        btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
        btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        checkConnection();
        edSearchLeague.setText("");
        rlSearchTypeContainer.setVisibility(View.GONE);
        Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + AppPreferences.init(mContext).getString(AppConstant.USER_PROFILE_PIC)).into(ivUserProfileImage);
        swipeDownRefreshSubCat = findViewById(R.id.swiperefreshSubcat);
        swipeDownRefreshSubCat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeDownRefreshSubCat.setRefreshing(false);
                        getSportsAndLeaugesData();
                    }
                }, 2000);
            }
        });
    }

    private void getSportsAndLeaugesData() {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getSportsAndLeauges(new ApiCallBack<SportsNameModel>() {
                @Override
                public void onSuccess(final SportsNameModel sportsNameModel) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return position == 0 ? 2 : 1;
                        }
                    });
                    rvLeagueItem.setLayoutManager(gridLayoutManager);
                    swipeDownRefreshSubCat.setEnabled(true);
                    leaguesItemList = sportsNameModel.getData().get(position).getLeague();
                    allLeagueAdapter = new AllLeagueAdapter(mContext, leaguesItemList, new AllLeagueAdapter.ItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            AppPreferences.init(mContext).putString(AppConstant.LEAGUE_IMAGE_URL, leaguesItemList.get(position).getMarkImage());
                            Intent intent = new Intent(mContext, MatchListActivity.class);
                            intent.putExtra("sportsLeagueId", getIntent().getStringExtra("sportsId") + "/" + leaguesItemList.get(position).getId());
                            intent.putExtra("subCatDetail", leaguesItemList.get(position));
                            startActivity(intent);
                        }
                    });
                    rvLeagueItem.setAdapter(allLeagueAdapter);
                    edSearchLeague.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchLeague.setFocusable(true);
                            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST))
                                rlSearchTypeContainer.setVisibility(View.GONE);
                            else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER))
                                rlSearchTypeContainer.setVisibility(View.VISIBLE);
                        }
                    });
                    ivSearchLeagueIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchLeague.setFocusable(true);
                        }
                    });
                    edSearchLeague.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (timer != null) {
                                timer.cancel();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            text = edSearchLeague.getText().toString();
                            allLeagueAdapter.filter(text);
                            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                                    text = edSearchLeague.getText().toString();
                                    allLeagueAdapter.filter(text);
                            } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
                                if (selectedSearchType.equalsIgnoreCase("leagues")) {
                                    text = edSearchLeague.getText().toString();
                                    allLeagueAdapter.filter(text);
                                }
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (selectedSearchType.equalsIgnoreCase("user")) {
                                            Map<String, String> mountMap = new HashMap<>();
                                            mountMap.put("search_type", "user");
                                            mountMap.put("search_text", edSearchLeague.getText().toString());
                                            mountMap.put("live", "1");
                                            mountMap.put("sport_id", getIntent().getStringExtra("sportsId"));
                                            mountMap.put("user_id",AppPreferences.init(mContext).getString(AppConstant.USER_ID));
                                            getUserSearchDetailsList(mountMap);
                                        } else if (selectedSearchType.equalsIgnoreCase("team")) {
                                            Map<String, String> mountMap = new HashMap<>();
                                            mountMap.put("search_type", "team");
                                            mountMap.put("search_text", edSearchLeague.getText().toString());
                                            mountMap.put("live", "1");
                                            mountMap.put("user_id",AppPreferences.init(mContext).getString(AppConstant.USER_ID));
                                            mountMap.put("sport_id", getIntent().getStringExtra("sportsId"));
                                            getTeamSearchDetailsList(mountMap);

                                        }
                                    }
                                }, 500);
                            }
                        }
                    });
                    closeLeagueSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edSearchLeague.setText("");
                            selectedSearchType = "leagues";
                            if (searchUserDetailsList != null) {
                                searchUserDetailsList.clear();
                                searchUserLeaguesAdapter.notifyDataSetChanged();
                            }
                            if (searchTeamDetailsList != null) {
                                searchTeamDetailsList.clear();
                                searchTeamSportsAdapter.notifyDataSetChanged();
                            }
                            getSportsAndLeaugesData();
                            rlSearchTypeContainer.setVisibility(View.GONE);
                            btnUser.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                            btnLeagues.setTextColor(ContextCompat.getColor(mContext, R.color.colorPurple));
                            btnTeam.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                            hideKeyboard();
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getUserSearchDetailsList(Map<String, String> mountMap) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().searchLeaguesUser(mountMap, new ApiCallBack<UserSearchLeagueModel>() {
                @Override
                public void onSuccess(UserSearchLeagueModel userSearchLeagueModel) {
                    if (userSearchLeagueModel != null) {
                        rvLeagueItem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        searchUserDetailsList=new ArrayList<>();
                        searchUserDetailsList = userSearchLeagueModel.getData();
                        swipeDownRefreshSubCat.setEnabled(false);
                        searchUserLeaguesAdapter = new SearchUserLeaguesAdapter(mContext, searchUserDetailsList, new SearchUserLeaguesAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(mContext, PunditsProfileActivity.class);
                                intent.putExtra("userComingFrom", "leaguesUserSearch");
                                intent.putExtra("switch","No");
                                intent.putExtra("mUserDatum", searchUserDetailsList.get(position));
                                startActivity(intent);
                            }
                        });
                        rvLeagueItem.setAdapter(searchUserLeaguesAdapter);
                        searchUserLeaguesAdapter.notifyDataSetChanged();
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
            App.getApiHelper().searchLeaguesTeam(mountMap, new ApiCallBack<TeamSearchSportsModel>() {
                @Override
                public void onSuccess(TeamSearchSportsModel teamSearchSportsModel) {
                    if (teamSearchSportsModel != null) {
                        rvLeagueItem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        searchTeamDetailsList=new ArrayList<>();
                        searchTeamDetailsList = teamSearchSportsModel.getData();
                        swipeDownRefreshSubCat.setEnabled(false);
                        searchTeamSportsAdapter = new SearchTeamSportsAdapter(mContext, searchTeamDetailsList, new SearchTeamSportsAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                matchid=searchTeamDetailsList.get(position).getContestantId();
                                chatChannelId=searchTeamDetailsList.get(position).getChatChannelid();
                                chatChannelName=searchTeamDetailsList.get(position).getContestantName();
                                if(chatChannelId.equalsIgnoreCase("0")){
                                   getChannelId();
                                }
                                else{
                                    ApplozicChannelAddMemberTask.ChannelAddMemberListener channelAddMemberListener =  new ApplozicChannelAddMemberTask.ChannelAddMemberListener() {
                                        @Override
                                        public void onSuccess(String response, Context context) {
                                            Log.i("ApplozicChannelMember","Add Response:" + response);
                                        }
                                        @Override
                                        public void onFailure(String response, Exception e, Context context) {

                                        }
                                    };
                                    ApplozicChannelAddMemberTask applozicChannelAddMemberTask =  new ApplozicChannelAddMemberTask(mContext,Integer.parseInt(chatChannelId),AppPreferences.init(mContext).getString(FB_ID),channelAddMemberListener);//pass channel key and userId whom you want to add to channel
                                    applozicChannelAddMemberTask.execute((Void)null);
                                }

                                Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                                intent.putExtra("userComingFrom", "leaguesTeamSearch");
                                intent.putExtra("mTeamSearchDatum", searchTeamDetailsList.get(position));
                                intent.putExtra("chatChannelKey",chatChannelId);
                                startActivity(intent);
                            }
                        });
                        rvLeagueItem.setAdapter(searchTeamSportsAdapter);
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

    private void getChannelId() {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> channelMembersList = new ArrayList<>();
                    channelMembersList.add(AppPreferences.init(mContext).getString(FB_ID));
                    ChannelInfo channelInfo = new ChannelInfo(chatChannelName, channelMembersList);
                    channelInfo.setType(Channel.GroupType.PUBLIC.getValue().intValue());
                    ChannelService service = ChannelService.getInstance(mContext);
                    Channel channel = service.createChannel(channelInfo);
                    Log.i("Channel", "Channel respone is:" + channel);
                    if (channel!=null && channel.getKey() != null) {
                        chatChannelId = String.valueOf(channel.getKey());
                        updateChatChannelId();
                    }
                }
            }).start();
        }catch (Exception e){e.printStackTrace();}
    }

    @SuppressLint("StaticFieldLeak")
    private class createChannel extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
//            ChannelMetadata channelMetadata = new ChannelMetadata();
//            channelMetadata.setCreateGroupMessage(ChannelMetadata.ADMIN_NAME + " created " + chatChannelName);
//            channelMetadata.setAddMemberMessage(ChannelMetadata.ADMIN_NAME + " added " + ChannelMetadata.USER_NAME);
//            channelMetadata.setRemoveMemberMessage(ChannelMetadata.ADMIN_NAME + " removed " + ChannelMetadata.USER_NAME);
//            channelMetadata.setGroupNameChangeMessage(ChannelMetadata.USER_NAME + " changed group name " + ChannelMetadata.GROUP_NAME);
//            channelMetadata.setJoinMemberMessage(ChannelMetadata.USER_NAME + " joined");
//            channelMetadata.setGroupLeftMessage(ChannelMetadata.USER_NAME + " left group " + ChannelMetadata.GROUP_NAME);
//            channelMetadata.setGroupIconChangeMessage(ChannelMetadata.USER_NAME + " changed icon");
//            channelMetadata.setDeletedGroupMessage(ChannelMetadata.ADMIN_NAME + " deleted group " + ChannelMetadata.GROUP_NAME);
            List<String> channelMembersList =  new ArrayList<>();
            channelMembersList.add(AppPreferences.init(mContext).getString(FB_ID));
            ChannelInfo channelInfo  = new ChannelInfo(chatChannelName,channelMembersList);
            channelInfo.setType(Channel.GroupType.PUBLIC.getValue().intValue());
            ChannelService service=ChannelService.getInstance(mContext);
            Channel channel = service.createChannel(channelInfo);
            Log.i("Channel","Channel respone is:"+channel);
            chatChannelId= String.valueOf(channel.getKey());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateChatChannelId();
        }



    }
    private void updateChatChannelId(){
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("match_id", matchid);
        mountMap.put("channeltype", "team");
        mountMap.put("chatChannelid", chatChannelId);
        App.getApiHelper().updateChatId(mountMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}


