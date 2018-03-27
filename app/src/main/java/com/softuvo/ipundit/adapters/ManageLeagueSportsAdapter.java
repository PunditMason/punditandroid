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
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.SportsNameModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ManageLeagueSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SportsNameModel.Sports> sportsItemList;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public ManageLeagueSportsAdapter(Context context, List<SportsNameModel.Sports> sportsItemList) {
        this.context = context;
        this.sportsItemList = sportsItemList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SportsView(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SportsView holderView = (SportsView) holder;
        if (sportsItemList.get(position).getName() != null)
            holderView.tvSportsName.setText(sportsItemList.get(position).getName());
        if(sportsItemList.get(position).getLeague().size()>0) {
            List<SportsNameModel.Sports.League> leagueList = sportsItemList.get(position).getLeague();
            ((SportsView) holder).rvSubList.setLayoutManager(new LinearLayoutManager(context));
            ManageLeagueLeaguesAdapter manageLeagueLeaguesAdapter = new ManageLeagueLeaguesAdapter(context, leagueList);
            ((SportsView) holder).rvSubList.setAdapter(manageLeagueLeaguesAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return sportsItemList.size();
    }


    class SportsView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sports_name)
        TextView tvSportsName;

        @BindView(R.id.rv_sublist)
        RecyclerView rvSubList;

        private SportsView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

       /* @OnClick(R.id.rl_sports_cover_image)
        void onClick() {
            itemClickListener.onClick(getLayoutPosition());
        }*/
    }
}
