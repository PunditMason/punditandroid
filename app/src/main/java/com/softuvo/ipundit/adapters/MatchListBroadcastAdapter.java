package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;

import java.util.List;

public class MatchListBroadcastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<BroadcastMatchlistModel.Datum> gameItemList;
    ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public MatchListBroadcastAdapter(Context context, List<BroadcastMatchlistModel.Datum> gameItemList, ItemClickListener itemClickListener) {
        this.context = context;
        this.gameItemList = gameItemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcast_matchlist_items, null);
        return new MatchViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MatchViewHolder holder1 = (MatchViewHolder) holder;
        if(gameItemList.get(position).getMatchStartTime()!=null) {
            String[] matchStartTime = gameItemList.get(position).getMatchStartTime().split(":");
            holder1.matchTime.setText(matchStartTime[0] + ":" + matchStartTime[1]);
        }
        if(gameItemList.get(position).getTeam1Name()!=null)
            holder1.team1Name.setText(gameItemList.get(position).getTeam1Name());
        else
            holder1.team1Name.setText("-");

        if (gameItemList.get(position).getTeam1Score() == null || gameItemList.get(position).getTeam1Score().equalsIgnoreCase(""))
            holder1.team1Score.setText("-");
        else
            holder1.team1Score.setText(gameItemList.get(position).getTeam1Score());
        if(gameItemList.get(position).getTeam2Name()!=null)
        holder1.team2Name.setText(gameItemList.get(position).getTeam2Name());
        else
            holder1.team2Name.setText("-");

        if (gameItemList.get(position).getTeam2Score() == null || gameItemList.get(position).getTeam2Score().equalsIgnoreCase(""))
            holder1.team2Score.setText("-");
        else
            holder1.team2Score.setText(gameItemList.get(position).getTeam2Score());
        if (gameItemList.get(position).getMatchStatus()!= null) {
            if (gameItemList.get(position).getMatchStatus().contains("Kick off"))
                holder1.timeInterval.setText(context.getString(R.string.fixture_string));
            else if (gameItemList.get(position).getMatchStatus().equalsIgnoreCase("Full Time")) {
                holder1.timeInterval.setText("FT");
            } else if (gameItemList.get(position).getMatchStatus().contains("First Half")||gameItemList.get(position).getMatchStatus().contains("Second Half")) {
                holder1.timeInterval.setText(context.getString(R.string.playing_String));
            }
            else if (gameItemList.get(position).getMatchStatus().equalsIgnoreCase("Match Postponed")) {
                holder1.timeInterval.setText(context.getString(R.string.postpone_string));
            }
            else {
                holder1.timeInterval.setText("-");
            }
        }

    }

    @Override
    public int getItemCount() {
        return gameItemList.size();
    }

    private class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView matchTime, team1Name, team2Name, team1Score, team2Score, timeInterval;
        private CardView cardViewMatches;

        private MatchViewHolder(View view) {
            super(view);
            matchTime = (TextView) view.findViewById(R.id.tv_match_time);
            team1Name = (TextView) view.findViewById(R.id.tv_first_team);
            team2Name = (TextView) view.findViewById(R.id.tv_second_team);
            team1Score = (TextView) view.findViewById(R.id.tv_first_team_score);
            team2Score = (TextView) view.findViewById(R.id.tv_second_team_score);
            timeInterval = (TextView) view.findViewById(R.id.tv_time_interval);
            cardViewMatches = (CardView) view.findViewById(R.id.cv_container);
            cardViewMatches.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}

