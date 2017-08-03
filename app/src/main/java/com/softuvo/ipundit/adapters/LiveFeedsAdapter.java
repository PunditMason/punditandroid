package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.LiveFeedsModel;

import java.util.List;

public class LiveFeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<LiveFeedsModel.Feed> liveFeedsList;


    public LiveFeedsAdapter(Context context, List<LiveFeedsModel.Feed> liveFeedsList) {
        this.context = context;
        this.liveFeedsList = liveFeedsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LiveFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_feeds_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LiveFeedsViewHolder holder = (LiveFeedsViewHolder) viewHolder;
        if (liveFeedsList.get(position).getType() != null) {
            if (liveFeedsList.get(position).getType().equalsIgnoreCase("YC")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.yellow_card);
            } else if (liveFeedsList.get(position).getType().equalsIgnoreCase("G")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getScorerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.goal);
            } else if (liveFeedsList.get(position).getType().equalsIgnoreCase("OG")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getScorerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.footballball);
            } else if (liveFeedsList.get(position).getType().equalsIgnoreCase("Y2C")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.y_two_c);
            } else if (liveFeedsList.get(position).getType().equalsIgnoreCase("RC")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.red_card);
            }else if (liveFeedsList.get(position).getType().equalsIgnoreCase("PG")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getScorerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.pg);
            }else if (liveFeedsList.get(position).getType().equalsIgnoreCase("PM")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.missed_pen);
            }else if (liveFeedsList.get(position).getType().equalsIgnoreCase("PS")) {
                holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerName() + ":" + liveFeedsList.get(position).getTimeMin());
                holder.liveFeedIcon.setImageResource(R.drawable.missed_pen);
            }
        } else {
            holder.tvPlayerNameAndTimeMin.setText(liveFeedsList.get(position).getPlayerOffName() + ":" + liveFeedsList.get(position).getPlayerOnName() + ": " + liveFeedsList.get(position).getTimeMin());
            holder.liveFeedIcon.setImageResource(R.drawable.substitution_of_player);
        }
    }

    @Override
    public int getItemCount() {
        return liveFeedsList.size();
    }

    private class LiveFeedsViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerNameAndTimeMin;
        ImageView liveFeedIcon;

        public LiveFeedsViewHolder(View itemView) {
            super(itemView);
            tvPlayerNameAndTimeMin = (TextView) itemView.findViewById(R.id.tv_playerName_and_timeMin);
            liveFeedIcon = (ImageView) itemView.findViewById(R.id.live_feed_icon);


        }
    }
}