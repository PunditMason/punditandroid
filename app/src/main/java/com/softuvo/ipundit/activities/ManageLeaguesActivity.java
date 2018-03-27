package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.ExpandableListAdapter;
import com.softuvo.ipundit.adapters.ManageLeagueSportsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.USER_ID;

public class ManageLeaguesActivity extends BaseActivity {
    private Activity mContext;
    private List<SportsNameModel.Sports> sportsItemList;
    ManageLeagueSportsAdapter manageLeagueSportsAdapter;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_leagues);
        mContext = ManageLeaguesActivity.this;
        ButterKnife.bind(this);
        getSportsAndLeagueData(AppPreferences.init(mContext).getString(AppConstant.USER_ID));
    }

    private void getSportsAndLeagueData(String userid) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getUserSelcetedLeagues(userid,new ApiCallBack<SportsNameModel>() {
                @Override
                public void onSuccess(SportsNameModel sportsNameModel) {
                    if (sportsNameModel.getData() != null)
                        sportsItemList = new ArrayList<>();
                    rvList.setLayoutManager(new LinearLayoutManager(mContext));
                    sportsItemList = sportsNameModel.getData();
                    manageLeagueSportsAdapter=new ManageLeagueSportsAdapter(mContext, sportsItemList);
                    rvList.setAdapter(manageLeagueSportsAdapter);

                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    public void onBackPressed() {
        String selectedLeagueIds="";
        if(sportsItemList.size()>0) {
            for (int i = 0; i < sportsItemList.size(); i++) {
                if(sportsItemList.get(i).getLeague().size()>0) {
                    for (int j = 0; j < sportsItemList.get(i).getLeague().size(); j++) {
                        if (sportsItemList.get(i).getLeague().get(j).getSelectedLeague()) {
                            selectedLeagueIds = selectedLeagueIds + sportsItemList.get(i).getLeague().get(j).getId().trim() + ",";
                        }

                    }
                }
            }
            Log.e("selectedIds",selectedLeagueIds+"==="+ selectedLeagueIds.substring(0,(selectedLeagueIds.length()-1)));
        }
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("user_id",AppPreferences.init(mContext).getString(USER_ID).trim());
        mountMap.put("leagues_ids",selectedLeagueIds.substring(0,(selectedLeagueIds.length()-1)));
        App.getApiHelper().updateLeagues(mountMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {
                Log.e("Success","leagues  upadted");
            }

            @Override
            public void onFailure(String message) {

            }
        });
        super.onBackPressed();
    }
}
