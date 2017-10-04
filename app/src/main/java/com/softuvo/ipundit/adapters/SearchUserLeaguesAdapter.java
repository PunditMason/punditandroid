package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.UserSearchLeagueModel;

import java.util.List;

/**
 * Created by softuvo on 03-08-2017.
 */

public class SearchUserLeaguesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<UserSearchLeagueModel.Datum> searchUserItemList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public SearchUserLeaguesAdapter(Context context, List<UserSearchLeagueModel.Datum> searchUserItemList,ItemClickListener itemClickListener) {
        this.context = context;
        this.searchUserItemList = searchUserItemList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchUserDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchUserDetailsViewHolder viewholder = (SearchUserDetailsViewHolder) holder;
        if (searchUserItemList.get(position).getLive().toString().equals("1")) {
            viewholder.cvUserDetailsContainer.setBackgroundResource(R.color.colorBroadcastersListBackground);
        }
        else {
            viewholder.cvUserDetailsContainer.setBackgroundResource(R.color.colorPrivacyPolicyBackground);
        }
        viewholder.tvUsername.setText(searchUserItemList.get(position).getFirstName());
        viewholder.tvUsername.setGravity(Gravity.CENTER);

    }

    @Override
    public int getItemCount() {
        return searchUserItemList.size();
    }

    @Override
    public void onClick(View v) {

    }

    private class SearchUserDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvUserDetailsContainer;
        TextView tvUsername;

        private SearchUserDetailsViewHolder(View view) {
            super(view);
            cvUserDetailsContainer = (CardView) view.findViewById(R.id.cv_user_details_container);
            tvUsername = (TextView) view.findViewById(R.id.tv_username);
            cvUserDetailsContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}


