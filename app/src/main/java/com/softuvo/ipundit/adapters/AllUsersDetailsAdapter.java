package com.softuvo.ipundit.adapters;

/*
 * Created by Neha Kalia on 12/07/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;

import java.util.List;


public class AllUsersDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<UserDetailsAndMatchDetailsModel.UsersList> userItemList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public AllUsersDetailsAdapter(Context context, List<UserDetailsAndMatchDetailsModel.UsersList> userItemList, ItemClickListener itemClickListener) {
        this.context = context;
        this.userItemList = userItemList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserDetailsViewHolder viewholder = (UserDetailsViewHolder) holder;
        if (userItemList.get(position).getLive().toString().equals("1")) {
            viewholder.cvUserDetailsContainer.setBackgroundResource(R.color.colorBroadcastersListBackground);
        }
        else {
            viewholder.cvUserDetailsContainer.setBackgroundResource(R.color.colorPrivacyPolicyBackground);
        }
        viewholder.tvUsername.setText(userItemList.get(position).getFirstName());

    }

    @Override
    public int getItemCount() {
        return userItemList.size();
    }


    private class UserDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvUserDetailsContainer;
        TextView tvUsername;

        private UserDetailsViewHolder(View view) {
            super(view);
            cvUserDetailsContainer =  view.findViewById(R.id.cv_user_details_container);
            tvUsername =  view.findViewById(R.id.tv_username);
            cvUserDetailsContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}
