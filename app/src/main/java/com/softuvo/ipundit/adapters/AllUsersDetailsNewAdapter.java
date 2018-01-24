package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
 * Created by Neha Kalia on 1/24/2018.
 */

public class AllUsersDetailsNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<UserDetailsAndMatchDetailsModel.UsersList> userItemList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public AllUsersDetailsNewAdapter(Context context, List<UserDetailsAndMatchDetailsModel.UsersList> userItemList, AllUsersDetailsNewAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.userItemList = userItemList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllUsersDetailsNewAdapter.UserDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_right_now_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserDetailsViewHolder viewholder = (UserDetailsViewHolder) holder;
        if(userItemList.get(position).getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
            viewholder.cvContainerMatch.setVisibility(View.VISIBLE);
            viewholder.cvContainerTeam.setVisibility(View.GONE);
            viewholder.tvPunditsNameMatch.setText(userItemList.get(position).getFirstName());
            viewholder.tvTeam1Name.setText(userItemList.get(position).getChannelInfo().get(0).getMatchInfo().getTeam1Name());
            viewholder.tvTeam2Name.setText(userItemList.get(position).getChannelInfo().get(0).getMatchInfo().getTeam2Name());
            Picasso.with(context).load(ApiConstants.TEAM_ICON_URL + userItemList.get(position).getChannelInfo().get(0).getMatchInfo().getTeam1Icon()).fit().into(viewholder.ivTeam1Icon);
            Picasso.with(context).load(ApiConstants.TEAM_ICON_URL + userItemList.get(position).getChannelInfo().get(0).getMatchInfo().getTeam2Icon()).fit().into(viewholder.ivTeam2Icon);
        }else if(userItemList.get(position).getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")){
            viewholder.cvContainerMatch.setVisibility(View.GONE);
            viewholder.cvContainerTeam.setVisibility(View.VISIBLE);
            viewholder.tvPunditsNameTeam.setText(userItemList.get(position).getFirstName());
            viewholder.tvContestentName.setText(userItemList.get(position).getChannelInfo().get(0).getTeamInfo().getContestantClubName());
            viewholder.ivContestentIcon.setImageResource(R.drawable.app_icon);
            Picasso.with(context).load(ApiConstants.TEAM_ICON_URL + userItemList.get(position).getChannelInfo().get(0).getTeamInfo().getMarkImage()).fit().into(viewholder.ivContestentIcon);
        }
    }

    @Override
    public int getItemCount() {
        return userItemList.size();
    }

    @Override
    public void onClick(View v) {

    }

    private class UserDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvContainerMatch;
        CardView cvContainerTeam;
        TextView tvTeam1Name;
        TextView tvTeam2Name;
        ImageView ivTeam1Icon;
        ImageView ivTeam2Icon;
        TextView tvPunditsNameMatch;
        TextView tvPunditsNameTeam;
        TextView tvContestentName;
        ImageView ivContestentIcon;


        private UserDetailsViewHolder(View view) {
            super(view);
            cvContainerMatch = view.findViewById(R.id.cv_container_match);
            tvPunditsNameMatch = view.findViewById(R.id.tv_broadcaster_name_match);
            tvTeam1Name = view.findViewById(R.id.tv_team1_name);
            tvTeam2Name = view.findViewById(R.id.tv_team2_name);
            ivTeam1Icon = view.findViewById(R.id.iv_team1_icon);
            ivTeam2Icon = view.findViewById(R.id.iv_team2_icon);
            cvContainerTeam = view.findViewById(R.id.cv_container_team);
            tvPunditsNameTeam = view.findViewById(R.id.tv_broadcaster_name_team);
            tvContestentName=view.findViewById(R.id.tv_contestent_name);
            ivContestentIcon=view.findViewById(R.id.iv_contestent_icon);
            cvContainerMatch.setOnClickListener(this);
            cvContainerTeam.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}
