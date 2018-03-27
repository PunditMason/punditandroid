package com.softuvo.ipundit.adapters;

/*
 * Created by softuvo on 14/3/18.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.SportsNameModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageLeagueLeaguesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SportsNameModel.Sports.League> leaguesItemList;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public ManageLeagueLeaguesAdapter(Context context, List<SportsNameModel.Sports.League> leaguesItemList) {
        this.context = context;
        this.leaguesItemList = leaguesItemList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeaguesView(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        LeaguesView holderView = (LeaguesView) holder;
        holderView.cbSubType.setText(leaguesItemList.get(position).getName());
        holderView.cbSubType.setChecked(leaguesItemList.get(position).getSelectedLeague());
        holderView.cbSubType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                leaguesItemList.get(holder.getAdapterPosition()).setSelectedLeague(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leaguesItemList.size();
    }




    class LeaguesView extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_sub_type)
        CheckBox cbSubType;

        private LeaguesView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

       /* @OnClick(R.id.rl_sports_cover_image)
        void onClick() {
            itemClickListener.onClick(getLayoutPosition());
        }*/
    }
}
