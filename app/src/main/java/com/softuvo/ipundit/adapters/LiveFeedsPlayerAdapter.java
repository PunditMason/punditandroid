package com.softuvo.ipundit.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.PlayerDataModel;
import java.util.List;

/*
 * Created by softuvo on 29-06-2017.
 */

public class LiveFeedsPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<PlayerDataModel> liveFeedsList;


    public LiveFeedsPlayerAdapter(Context context, List<PlayerDataModel> liveFeedsList) {
        this.context = context;
        this.liveFeedsList = liveFeedsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LiveFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LiveFeedsViewHolder holder = (LiveFeedsViewHolder) viewHolder;
        if(liveFeedsList.get(position).getLeftName()!=null){
            holder.ivBlacKShirt.setVisibility(View.VISIBLE);
            holder.tvBlackPlayerName.setVisibility(View.VISIBLE);
            holder.tvBlackShirtNumber.setVisibility(View.VISIBLE);
            holder.tvBlackPlayerName.setText(liveFeedsList.get(position).getLeftName());
            holder.tvBlackShirtNumber.setText(liveFeedsList.get(position).getLeftShirtNumber());
            if(liveFeedsList.get(position).getLeftReplacedBy()!=null){
                holder.tvSubstituteBlackSide.setVisibility(View.VISIBLE);
                holder.tvSubstituteBlackSide.setText(liveFeedsList.get(position).getLeftReplacedBy()+" "+liveFeedsList.get(position).getLeftMinute());
            }
            else
            {
                holder.tvSubstituteBlackSide.setVisibility(View.GONE);
            }
        }else{
            holder.ivBlacKShirt.setVisibility(View.GONE);
            holder.tvBlackPlayerName.setVisibility(View.GONE);
            holder.tvBlackShirtNumber.setVisibility(View.GONE);
            holder.tvSubstituteBlackSide.setVisibility(View.GONE);
        }
        if(liveFeedsList.get(position).getRightName()!=null) {
            holder.ivWhiteShirt.setVisibility(View.VISIBLE);
            holder.tvWhitePlayerName.setVisibility(View.VISIBLE);
            holder.tvWhiteShirtNumber.setVisibility(View.VISIBLE);
            holder.tvWhitePlayerName.setText(liveFeedsList.get(position).getRightName());
            holder.tvWhiteShirtNumber.setText(liveFeedsList.get(position).getRightShirtNumber());
            if(liveFeedsList.get(position).getRightReplacedBy()!=null){
                holder.tvSubstituteWhiteSide.setVisibility(View.VISIBLE);
                holder.tvSubstituteWhiteSide.setText(liveFeedsList.get(position).getRightReplacedBy()+" "+liveFeedsList.get(position).getRightMinute());
            }
            else
            {
                holder.tvSubstituteWhiteSide.setVisibility(View.GONE);
            }
        }else{
            holder.ivWhiteShirt.setVisibility(View.GONE);
            holder.tvWhitePlayerName.setVisibility(View.GONE);
            holder.tvWhiteShirtNumber.setVisibility(View.GONE);
            holder.tvSubstituteWhiteSide.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return liveFeedsList.size();
    }

    private class LiveFeedsViewHolder extends RecyclerView.ViewHolder {
        TextView tvWhiteShirtNumber,tvWhitePlayerName,tvSubstituteWhiteSide,tvBlackShirtNumber,tvBlackPlayerName,tvSubstituteBlackSide;
        ImageView ivBlacKShirt,ivWhiteShirt;


        LiveFeedsViewHolder(View itemView) {
            super(itemView);
            tvBlackShirtNumber =  itemView.findViewById(R.id.tv_black_shirt_number);
            tvBlackPlayerName = itemView.findViewById(R.id.tv_black_player_name);
            tvSubstituteBlackSide =  itemView.findViewById(R.id.tv_substitute_black_side);
            tvWhiteShirtNumber =  itemView.findViewById(R.id.tv_white_shirt_number);
            tvWhitePlayerName =  itemView.findViewById(R.id.tv_white_player_name);
            tvSubstituteWhiteSide =  itemView.findViewById(R.id.tv_substitute_white_side);
            ivBlacKShirt= itemView.findViewById(R.id.iv_black_shirt);
            ivWhiteShirt=itemView.findViewById(R.id.iv_white_shirt);
        }
    }
}
