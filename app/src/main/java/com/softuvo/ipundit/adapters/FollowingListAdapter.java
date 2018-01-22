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
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<FollowingListModel.Following> follwingList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public FollowingListAdapter(Context context, List<FollowingListModel.Following> follwingList, ItemClickListener itemClickListener) {
        this.context = context;
        this.follwingList = follwingList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_following_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FollowingViewHolder viewholder = (FollowingViewHolder) holder;
        Picasso.with(context).load(ApiConstants.PROFILE_IMAGE_BASE_URL + follwingList.get(position).getAvatar()).into(viewholder.ivUserPic);
        viewholder.tvFollwerFollowingName.setText(follwingList.get(position).getFirstName());
        if(follwingList.get(position).getFollow()==1)
            viewholder.ivFollowUnfollow.setImageResource(R.drawable.unfollow);
        else if(follwingList.get(position).getFollow()==0)
            viewholder.ivFollowUnfollow.setImageResource(R.drawable.follow_me);
        viewholder.ivFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.init(context).getString(AppConstant.USER_ID).equalsIgnoreCase(follwingList.get(position).getId())) {
                    SnackbarUtil.showWarningShortSnackbar((Activity) context, context.getString(R.string.self_follow_text));
                    viewholder.ivFollowUnfollow.setClickable(false);
                } else {
                    String followUnfoloowPath;
                    if (follwingList.get(position).getFollow() == 0) {
                        viewholder.ivFollowUnfollow.setImageResource(R.drawable.follow_me);
                        followUnfoloowPath = AppPreferences.init(context).getString(AppConstant.USER_ID) + "/" + follwingList.get(position).getId();
                        followunfollowUser(followUnfoloowPath,viewholder.ivFollowUnfollow,position);

                    } else if (follwingList.get(position).getFollow() == 1) {
                        viewholder.ivFollowUnfollow.setImageResource(R.drawable.unfollow);
                        followUnfoloowPath = AppPreferences.init(context).getString(AppConstant.USER_ID) + "/" + follwingList.get(position).getId();
                        followunfollowUser(followUnfoloowPath,viewholder.ivFollowUnfollow,position);
                    }
                }
            }
        });
        viewholder.ivPodcast.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PodcastActivity.class);
                intent.putExtra("punditsId", follwingList.get(position).getId());
                context.startActivity(intent);
            }
        });
        viewholder.tvFollwerFollowingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PunditsProfileActivity.class);
                intent.putExtra("userComingFrom", "followingList");
                intent.putExtra("mUserDatum", follwingList.get(position));
                context.startActivity(intent);
            }
        });
    }

    private void followunfollowUser(final String stringPath, final ImageView ivFollowUnfollow, final int position) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollowlist(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    int followStatus;
                    if (followUnfollowModel != null) {
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
//                            ivFollowUnfollow.setImageResource(R.drawable.follow_me);
                            follwingList.remove(position);
                            notifyItemRemoved(position);
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
        return follwingList.size();
    }

    private class FollowingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivUserPic,ivPodcast,ivFollowUnfollow;
        TextView tvFollwerFollowingName;

        private FollowingViewHolder(View view) {
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
