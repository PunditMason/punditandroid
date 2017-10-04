package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.MatchStandingListModel;
import java.util.List;

public class MatchSatadingAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MatchStandingListModel.Datum> matchStandingList;
    MatchSatadingAdapter.ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public MatchSatadingAdapter(Context context, List<MatchStandingListModel.Datum> matchStandingList, MatchSatadingAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.matchStandingList = matchStandingList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standing_screen_items, null);
        return new MatchSatadingAdapter.MatchViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MatchSatadingAdapter.MatchViewHolder holder1 = (MatchSatadingAdapter.MatchViewHolder) holder;
        if (matchStandingList.get(position).getRank() != null)
            holder1.tvLeaguePos.setText(matchStandingList.get(position).getRank());
        if (matchStandingList.get(position).getContestantClubName() != null)
            holder1.tv_league_club.setText(matchStandingList.get(position).getContestantClubName());
        if (matchStandingList.get(position).getMatchesPlayed() != null)
            holder1.tv_league_p.setText(matchStandingList.get(position).getMatchesPlayed());
        if (matchStandingList.get(position).getMatchesWon() != null)
            holder1.tv_league_w.setText(matchStandingList.get(position).getMatchesWon());
        if (matchStandingList.get(position).getMatchesDrawn() != null)
            holder1.tv_league_d.setText(matchStandingList.get(position).getMatchesDrawn());
        if (matchStandingList.get(position).getMatchesLost() != null)
            holder1.tv_league_l.setText(matchStandingList.get(position).getMatchesLost());
        if (matchStandingList.get(position).getGoaldifference() != null)
            holder1.tv_league_gd.setText(matchStandingList.get(position).getGoaldifference());
        if (matchStandingList.get(position).getPoints() != null)
            holder1.tv_league_pt.setText(matchStandingList.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return matchStandingList.size();
    }

    private class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLeaguePos, tv_league_club, tv_league_p, tv_league_w, tv_league_d,
        tv_league_l, tv_league_gd, tv_league_pt;

        private MatchViewHolder(View view) {
            super(view);
            tvLeaguePos = (TextView) view.findViewById(R.id.tv_league_pos);
            tv_league_club = (TextView) view.findViewById(R.id.tv_league_club);
            tv_league_p = (TextView) view.findViewById(R.id.tv_league_p);
            tv_league_w = (TextView) view.findViewById(R.id.tv_league_w);
            tv_league_d = (TextView) view.findViewById(R.id.tv_league_d);
            tv_league_l = (TextView) view.findViewById(R.id.tv_league_l);
            tv_league_gd = (TextView) view.findViewById(R.id.tv_league_gd);
            tv_league_pt = (TextView) view.findViewById(R.id.tv_league_pt);
            tv_league_club.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}