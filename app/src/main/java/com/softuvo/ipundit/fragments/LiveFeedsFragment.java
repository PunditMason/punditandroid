package com.softuvo.ipundit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.LiveFeedsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.models.LiveFeedsNewModel;
import com.softuvo.ipundit.models.PlayerDataModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Neha Kalia on 15-11-2017.
 */

public class LiveFeedsFragment extends Fragment {
    static String userComming, matchId, team1Score, team2Score;
    View rootView;
    Timer t;
    TextView tvMatchTimeTop, tvNo_DataBr, txtMatchTimeTop, tvTeam1Score, tvTeam2Score, tvNoData;

    @BindView(R.id.rv_live_feeds)
    RecyclerView rvLiveFeeds;


    private LiveFeedsAdapter liveFeedsAdapter;


    public LiveFeedsFragment() {
    }

    public static LiveFeedsFragment newInstance(String strUserComming, String strMatchId) {
        LiveFeedsFragment fragment = new LiveFeedsFragment();
        userComming = strUserComming;
        matchId = strMatchId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_live_feeds, container, false);
        ButterKnife.bind(this, rootView);
        setUpUI();
        return rootView;
    }

    private void setUpUI() {
        if (userComming.equalsIgnoreCase("Broadcasting")) {
            tvMatchTimeTop = getActivity().findViewById(R.id.tv_match_time_top);
            tvTeam1Score =  getActivity().findViewById(R.id.tv_team1_score);
            tvTeam2Score = getActivity().findViewById(R.id.tv_team2_score);
            tvNo_DataBr = getActivity().findViewById(R.id.tv_no_deta_br);
        } else if (userComming.equalsIgnoreCase("Listening")) {
            txtMatchTimeTop = getActivity().findViewById(R.id.txt_match_time_top);
            tvTeam1Score =  getActivity().findViewById(R.id.tv_team1_score);
            tvTeam2Score = getActivity().findViewById(R.id.tv_team2_score);
            tvNoData =getActivity().findViewById(R.id.tv_no_deta);


        }
        getLiveFeedsFromServer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (t != null) {
                t.cancel();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void getLiveFeedsFromServer() {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ConnectivityReceivers.isConnected()) {
                                App.getApiHelper().getLiveFeedsData(matchId, new ApiCallBack<LiveFeedsNewModel>() {
                                    @Override
                                    public void onSuccess(LiveFeedsNewModel liveFeedsNewModel) {
                                        if (liveFeedsNewModel != null) {
                                            if (liveFeedsNewModel.getMatch() != null) {
                                           /* if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Fixture")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchStatus());
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Played")) {
                                                tvMatchTimeTop.setText(getString(R.string.ft_string));
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Playing")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchLengthMin() + ":" + liveFeedsModel.getMatchinfo().getMatchLengthSec());
                                            }*/
                                           /* if (liveFeedsNewModel.getMatchinfo().getTeam1Score() == null || liveFeedsModel.getMatchinfo().getTeam1Score().equalsIgnoreCase(""))
                                                team1Score = "N/A";
                                            else
                                                team1Score = liveFeedsModel.getMatchinfo().getTeam1Score();
                                            tvTeam1Score.setText(team1Score);
                                            if (liveFeedsModel.getMatchinfo().getTeam2Score() == null || liveFeedsModel.getMatchinfo().getTeam2Score().equalsIgnoreCase(""))
                                                team2Score = "N/A";
                                            else
                                                team2Score = liveFeedsModel.getMatchinfo().getTeam2Score();
                                            tvTeam2Score.setText(team2Score);
                                            tvTeam1scoreVsTeam2score.setText(team1Score + ":" + team2Score);*/
                                                try {
                                                    if (userComming.equalsIgnoreCase("Broadcasting")) {
                                                        if (liveFeedsNewModel.getMatch().getStatus().contains("Kick off")) {
                                                            tvMatchTimeTop.setText(getString(R.string.fixture_string));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Full Time")) {
                                                            tvMatchTimeTop.setText(getString(R.string.ft_string));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().contains("First Half") || liveFeedsNewModel.getMatch().getStatus().contains("Second Half")) {
                                                            tvMatchTimeTop.setText(getString(R.string.playing_String));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Match Postponed")) {
                                                            tvMatchTimeTop.setText(getString(R.string.postpone_string));
                                                        } else {
                                                            tvMatchTimeTop.setText("-");
                                                        }
                                                        if (liveFeedsNewModel.getMatch().getTeams().get(0).getScore() == null)
                                                            team1Score = "N/A";
                                                        else
                                                            team1Score = liveFeedsNewModel.getMatch().getTeams().get(0).getScore();
                                                        tvTeam1Score.setText(team1Score);
                                                        if (liveFeedsNewModel.getMatch().getTeams().get(1).getScore() == null)
                                                            team2Score = "N/A";
                                                        else
                                                            team2Score = liveFeedsNewModel.getMatch().getTeams().get(1).getScore();
                                                        tvTeam2Score.setText(team2Score);
                                                        List<LiveFeedsNewModel.Match.Team> liveFeedsList = liveFeedsNewModel.getMatch().getTeams();
                                                        List<PlayerDataModel> playerDataModelsList = new ArrayList<>();
                                                        for (int i = 0; i < liveFeedsList.size(); i++) {
                                                            if (liveFeedsList.get(i).getGoals() != null) {
                                                                if (liveFeedsList.get(i).getGoals().size() > 0) {
                                                                    for (int j = 0; j < liveFeedsList.get(i).getGoals().size(); j++) {
                                                                        PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                                        mPlyerListData.setGoal(liveFeedsList.get(i).getGoals().get(j).getGoal());
                                                                        mPlyerListData.setType("Goal");
                                                                        playerDataModelsList.add(mPlyerListData);
                                                                    }
                                                                }
                                                            }
                                                            if (liveFeedsList.get(i).getPlayers() != null) {
                                                                if (liveFeedsList.get(i).getPlayers().size() > 0) {
                                                                    for (int j = 0; j < liveFeedsList.get(i).getPlayers().size(); j++) {
                                                                        if (liveFeedsList.get(i).getPlayers().get(j).getSubstitution() != null) {
                                                                            PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                                            mPlyerListData.setName(liveFeedsList.get(i).getPlayers().get(j).getName());
                                                                            mPlyerListData.setType("Substitute");
                                                                            mPlyerListData.setReplacedby(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getReplacedBy());
                                                                            mPlyerListData.setMinute(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getMinute());
                                                                            playerDataModelsList.add(mPlyerListData);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (playerDataModelsList.size() > 0) {
                                                            tvNo_DataBr.setVisibility(View.GONE);
                                                            liveFeedsAdapter = new LiveFeedsAdapter(getContext(), playerDataModelsList);
                                                            rvLiveFeeds.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                                            rvLiveFeeds.setAdapter(liveFeedsAdapter);
                                                        } else {
                                                            tvNo_DataBr.setVisibility(View.VISIBLE);
                                                            tvNo_DataBr.setText(R.string.no_live_feeds);
                                                        }
                                                    } else if (userComming.equalsIgnoreCase("Listening")) {
                                                        if (liveFeedsNewModel.getMatch().getStatus().contains("Kick off")) {
                                                            txtMatchTimeTop.setText(getString(R.string.fixture_string));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Full Time")) {
                                                            txtMatchTimeTop.setText(getString(R.string.ft_string));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().contains("First Half") || liveFeedsNewModel.getMatch().getStatus().contains("Second Half")) {
                                                            txtMatchTimeTop.setText(getString(R.string.playing_String));
                                                        } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Match Postponed")) {
                                                            txtMatchTimeTop.setText(getString(R.string.postpone_string));
                                                        } else {
                                                            txtMatchTimeTop.setText("-");
                                                        }
                                                        if (liveFeedsNewModel.getMatch().getTeams().get(0).getScore() == null)
                                                            team1Score = "N/A";
                                                        else
                                                            team1Score = liveFeedsNewModel.getMatch().getTeams().get(0).getScore();
                                                        tvTeam1Score.setText(team1Score);
                                                        if (liveFeedsNewModel.getMatch().getTeams().get(1).getScore() == null)
                                                            team2Score = "N/A";
                                                        else
                                                            team2Score = liveFeedsNewModel.getMatch().getTeams().get(1).getScore();
                                                        tvTeam2Score.setText(team2Score);
                                                        List<LiveFeedsNewModel.Match.Team> liveFeedsList = liveFeedsNewModel.getMatch().getTeams();
                                                        List<PlayerDataModel> playerDataModelsList = new ArrayList<>();
                                                        for (int i = 0; i < liveFeedsList.size(); i++) {
                                                            if (liveFeedsList.get(i).getGoals() != null) {
                                                                if (liveFeedsList.get(i).getGoals().size() > 0) {
                                                                    for (int j = 0; j < liveFeedsList.get(i).getGoals().size(); j++) {
                                                                        PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                                        mPlyerListData.setGoal(liveFeedsList.get(i).getGoals().get(j).getGoal());
                                                                        mPlyerListData.setType("Goal");
                                                                        playerDataModelsList.add(mPlyerListData);
                                                                    }
                                                                }
                                                            }
                                                            if (liveFeedsList.get(i).getPlayers() != null) {
                                                                if (liveFeedsList.get(i).getPlayers().size() > 0) {
                                                                    for (int j = 0; j < liveFeedsList.get(i).getPlayers().size(); j++) {
                                                                        if (liveFeedsList.get(i).getPlayers().get(j).getSubstitution() != null) {
                                                                            PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                                            mPlyerListData.setName(liveFeedsList.get(i).getPlayers().get(j).getName());
                                                                            mPlyerListData.setType("Substitute");
                                                                            mPlyerListData.setReplacedby(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getReplacedBy());
                                                                            mPlyerListData.setMinute(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getMinute());
                                                                            playerDataModelsList.add(mPlyerListData);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (playerDataModelsList.size() > 0) {
                                                            tvNoData.setVisibility(View.GONE);
                                                            liveFeedsAdapter = new LiveFeedsAdapter(getContext(), playerDataModelsList);
                                                            rvLiveFeeds.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                                            rvLiveFeeds.setAdapter(liveFeedsAdapter);
                                                        } else {
                                                            tvNoData.setVisibility(View.VISIBLE);
                                                            tvNoData.setText(R.string.no_live_feeds);
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(String message) {

                                    }
                                });
                            } else {
                                SnackbarUtil.showWarningLongSnackbar(getActivity(), getResources().getString(R.string.internet_not_connected_text));
                            }
                        }
                    });
                }
            }
        }, 0, 20000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (t != null) {
                t.cancel();
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
