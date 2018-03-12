package com.softuvo.ipundit.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.StandingListeningModel;

import java.util.List;

/*
 * Created by softuvo on 02-08-2017.
 */
public class TeamStandingAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<StandingListeningModel.Datum> teamStandingList;
    ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public TeamStandingAdapter(Context context, List<StandingListeningModel.Datum> teamStandingList, ItemClickListener itemClickListener) {
        this.context = context;
        this.teamStandingList = teamStandingList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standing_screen_items, null);
        return new TeamViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeamViewHolder holder1 = (TeamViewHolder) holder;
        if(teamStandingList.get(position).getChannel().size()>0)
            holder1.cvContainer.setCardBackgroundColor(context.getResources().getColor(R.color.colorBroadcastersListBackground));
        else
            holder1.cvContainer.setOnClickListener(null);
        if(teamStandingList.get(position).getRank()!=null)
        holder1.tvLeaguePos.setText(teamStandingList.get(position).getRank());
        if(teamStandingList.get(position).getContestantClubName()!=null)
        holder1.tv_league_club.setText(teamStandingList.get(position).getContestantClubName());
        if(teamStandingList.get(position).getMatchesPlayed()!=null)
        holder1.tv_league_p.setText(teamStandingList.get(position).getMatchesPlayed());
        if(teamStandingList.get(position).getMatchesWon()!=null)
        holder1.tv_league_w.setText(teamStandingList.get(position).getMatchesWon());
        if(teamStandingList.get(position).getMatchesDrawn()!=null)
        holder1.tv_league_d.setText(teamStandingList.get(position).getMatchesDrawn());
        if(teamStandingList.get(position).getMatchesLost()!=null)
        holder1.tv_league_l.setText(teamStandingList.get(position).getMatchesLost());
        if(teamStandingList.get(position).getGoaldifference()!=null)
        holder1.tv_league_gd.setText(teamStandingList.get(position).getGoaldifference());
        if(teamStandingList.get(position).getPoints()!=null)
        holder1.tv_league_pt.setText(teamStandingList.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return teamStandingList.size();
    }

    private class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLeaguePos, tv_league_club, tv_league_p, tv_league_w, tv_league_d,
                tv_league_l, tv_league_gd, tv_league_pt;
        private CardView cvContainer;

        private TeamViewHolder(View view) {
            super(view);
            cvContainer=view.findViewById(R.id.cv_container);
            tvLeaguePos =  view.findViewById(R.id.tv_league_pos);
            tv_league_club =  view.findViewById(R.id.tv_league_club);
            tv_league_p =  view.findViewById(R.id.tv_league_p);
            tv_league_w =  view.findViewById(R.id.tv_league_w);
            tv_league_d =  view.findViewById(R.id.tv_league_d);
            tv_league_l =  view.findViewById(R.id.tv_league_l);
            tv_league_gd =  view.findViewById(R.id.tv_league_gd);
            tv_league_pt =  view.findViewById(R.id.tv_league_pt);
            cvContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());

        }
    }
}
