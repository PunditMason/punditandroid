package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.ExpandableListAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageLeaguesActivity extends BaseActivity {
    private Activity mContext;
    private List<SportsNameModel.Sports> sportsItemList;
    ExpandableListAdapter expandableListAdapter;

    @BindView(R.id.lv_list)
    ExpandableListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_leagues);
        mContext = ManageLeaguesActivity.this;
        ButterKnife.bind(this);
        getSportsAndLeagueData();
    }

    private void getSportsAndLeagueData() {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getSportsAndLeauges(new ApiCallBack<SportsNameModel>() {
                @Override
                public void onSuccess(SportsNameModel sportsNameModel) {
                    if (sportsNameModel.getData() != null)
                        sportsItemList = new ArrayList<>();
                    sportsItemList = sportsNameModel.getData();
                    expandableListAdapter=new ExpandableListAdapter(mContext,sportsItemList,lvList);
                    lvList.setAdapter(expandableListAdapter);
                    /*lvList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            CheckedTextView checkbox = v.findViewById(R.id.ctv_league_name);
                            checkbox.toggle();


                            // find parent view by tag
                           *//* View parentView = sportsItemList.findViewWithTag(sportsItemList.get(groupPosition).getName());
                            if(parentView != null) {
                                TextView sub = parentView.findViewById(R.id.tv_sports_name);

                                if(sub != null) {
                                    Category category = categories.get(groupPosition);
                                    if(checkbox.isChecked()) {
                                        // add child category to parent's selection list
                                        category.selection.add(checkbox.getText().toString());

                                    }
                                    else {
                                        // remove child category from parent's selection list
                                        category.selection.remove(checkbox.getText().toString());
                                    }

                                    // display selection list
                                    sub.setText(category.selection.toString());
                                }*//*
                            }
                            return true;

                        }
                    });*/
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }
}
