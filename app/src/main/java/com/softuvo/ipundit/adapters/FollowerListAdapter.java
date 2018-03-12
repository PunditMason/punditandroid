package com.softuvo.ipundit.adapters;

/*
 * Created by Neha Kalia on 12/12/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.activities.PodcastActivity;
import com.softuvo.ipundit.activities.PunditsProfileActivity;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<FollowerListModel.Follwer> follwerList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public FollowerListAdapter(Context context, List<FollowerListModel.Follwer> follwerList, ItemClickListener itemClickListener) {
        this.context = context;
        this.follwerList = follwerList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_following_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        final FollowerViewHolder viewholder = (FollowerViewHolder) holder;
        Picasso.with(context).load(ApiConstants.PROFILE_IMAGE_BASE_URL + follwerList.get(position).getAvatar()).into(viewholder.ivUserPic);
        viewholder.tvFollwerFollowingName.setText(follwerList.get(position).getFirstName());
        if(follwerList.get(position).getFollow()==1)
            viewholder.ivFollowUnfollow.setImageResource(R.drawable.unfollow);
        else if(follwerList.get(position).getFollow()==0)
            viewholder.ivFollowUnfollow.setImageResource(R.drawable.follow_me);
        viewholder.ivFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.init(context).getString(AppConstant.USER_ID).equalsIgnoreCase(follwerList.get(viewholder.getAdapterPosition()).getId())) {
                    SnackbarUtil.showWarningShortSnackbar((Activity) context, context.getString(R.string.self_follow_text));
                    viewholder.ivFollowUnfollow.setClickable(false);
                } else {
                    String followUnfoloowPath;
                    if (follwerList.get(viewholder.getAdapterPosition()).getFollow() == 0) {
                        viewholder.ivFollowUnfollow.setImageResource(R.drawable.follow_me);
                        followUnfoloowPath = AppPreferences.init(context).getString(AppConstant.USER_ID) + "/" + follwerList.get(viewholder.getAdapterPosition()).getId();
                        followunfollowUser(followUnfoloowPath,viewholder.ivFollowUnfollow);

                    } else if (follwerList.get(viewholder.getAdapterPosition()).getFollow() == 1) {
                        viewholder.ivFollowUnfollow.setImageResource(R.drawable.unfollow);
                        followUnfoloowPath = AppPreferences.init(context).getString(AppConstant.USER_ID) + "/" + follwerList.get(viewholder.getAdapterPosition()).getId();
                        followunfollowUser(followUnfoloowPath,viewholder.ivFollowUnfollow);
                    }
                }
            }
        });
        viewholder.ivPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PodcastActivity.class);
                intent.putExtra("punditsId", follwerList.get(viewholder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
        viewholder.tvFollwerFollowingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PunditsProfileActivity.class);
                intent.putExtra("userComingFrom", "followerList");
                intent.putExtra("mUserDatum", follwerList.get(viewholder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    private void followunfollowUser(final String stringPath, final ImageView ivFollowUnfollow) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollowlist(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    int followStatus;
                    if (followUnfollowModel != null) {
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
                            ivFollowUnfollow.setImageResource(R.drawable.follow_me);
                        } else if (followStatus == 1) {
                            ivFollowUnfollow.setImageResource(R.drawable.unfollow);
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar((Activity) context, context.getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    public int getItemCount() {
        return follwerList.size();
    }

    private class FollowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivUserPic,ivPodcast,ivFollowUnfollow;
        TextView tvFollwerFollowingName;

        private FollowerViewHolder(View view) {
            super(view);
            ivUserPic=view.findViewById(R.id.iv_user_pic);
            ivPodcast=view.findViewById(R.id.iv_podcast);
            ivFollowUnfollow=view.findViewById(R.id.iv_follow_unfollow);
            tvFollwerFollowingName=view.findViewById(R.id.tv_follwer_following_name);
            ivPodcast.setOnClickListener(this);
            ivFollowUnfollow.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }


    @Override
    public void onClick(View v) {

    }
}
