package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.MatchListListnerModel;

import java.util.List;

public class MatchListListnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MatchListListnerModel.ListenMatchList> gameItemList;
    MatchListListnerAdapter.ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public MatchListListnerAdapter(Context context, List<MatchListListnerModel.ListenMatchList> gameItemList, MatchListListnerAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.gameItemList = gameItemList;

        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listening_matchlist_items, null);
        return new MatchListListnerAdapter.MatchViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MatchListListnerAdapter.MatchViewHolder holder1 = (MatchListListnerAdapter.MatchViewHolder) holder;
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
        if(gameItemList.get(position).getMatchStatus()!=null) {
            if (gameItemList.get(position).getMatchStatus().equalsIgnoreCase("Fixture"))
                holder1.timeInterval.setText(gameItemList.get(position).getMatchStatus());
            else if (gameItemList.get(position).getMatchStatus().equalsIgnoreCase("Played")) {
                holder1.timeInterval.setText("FT");
            } else if (gameItemList.get(position).getMatchStatus().equalsIgnoreCase("Playing")) {
                holder1.timeInterval.setText(gameItemList.get(position).getMatchLengthMin() + ":" + gameItemList.get(position).getMatchLengthSec());
            }
        }

    }

    @Override
    public int getItemCount() {
        return gameItemList.size();
    }

    private class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView team1Name, team2Name, team1Score, team2Score, timeInterval;
        private CardView cardViewMatches;

        private MatchViewHolder(View view) {
            super(view);
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

