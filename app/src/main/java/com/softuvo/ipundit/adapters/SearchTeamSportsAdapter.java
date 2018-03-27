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
import com.softuvo.ipundit.models.TeamSearchSportsModel;

import java.util.List;

/*
 * Created by Neha Kalia on 02-08-2017.
 */

public class SearchTeamSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
private Context context;
private List<TeamSearchSportsModel.Datum> searchTeamItemList;
private ItemClickListener itemClickListener;

public interface ItemClickListener {
    void onClick(int position);
}


    public SearchTeamSportsAdapter(Context context, List<TeamSearchSportsModel.Datum> searchTeamItemList, ItemClickListener itemClickListener) {
        this.context = context;
        this.searchTeamItemList = searchTeamItemList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchTeamDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchTeamDetailsViewHolder viewholder = (SearchTeamDetailsViewHolder) holder;
        viewholder.cvUserDetailsContainer.setBackgroundResource(R.color.colorPrivacyPolicyBackground);
        viewholder.tvUsername.setText(searchTeamItemList.get(position).getName());
        viewholder.tvUsername.setGravity(Gravity.CENTER);

    }

    @Override
    public int getItemCount() {
        return searchTeamItemList.size();
    }

    @Override
    public void onClick(View v) {

    }

    private class SearchTeamDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvUserDetailsContainer;
        TextView tvUsername;

        private SearchTeamDetailsViewHolder(View view) {
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
