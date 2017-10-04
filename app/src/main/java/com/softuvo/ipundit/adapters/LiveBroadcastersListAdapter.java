package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.LiveBroacastersListModel;

import java.util.List;

/**
 * Created by softuvo on 29-06-2017.
 */

public class LiveBroadcastersListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener{
    Context context;
    List<LiveBroacastersListModel.Channel> livebroadcasterList;
    private LiveBroadcastersListAdapter.ItemClickListener itemClickListener;

    @Override
    public void onClick(View v) {

    }

    public interface ItemClickListener {
        void onClick(int position);
    }


    public LiveBroadcastersListAdapter(Context context, List<LiveBroacastersListModel.Channel> livebroadcasterList, LiveBroadcastersListAdapter.ItemClickListener itemClickListener ) {
        this.context = context;
        this.livebroadcasterList = livebroadcasterList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LivebroadcastersListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_broadcasters_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LivebroadcastersListViewHolder holderView = (LivebroadcastersListViewHolder) viewHolder;
        holderView.tvBroadcastersName.setText(livebroadcasterList.get(position).getBroadcasterName());
        holderView.tvBroadcastingTime.setText(livebroadcasterList.get(position).getStartTime());

    }

    @Override
    public int getItemCount() {
        return livebroadcasterList.size();
    }

    private class LivebroadcastersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvBroadcastersName;
        TextView tvBroadcastingTime;
        LinearLayout llLiveBroadcasterListBg;

        private LivebroadcastersListViewHolder(View itemView) {
            super(itemView);
            tvBroadcastersName=(TextView)itemView.findViewById(R.id.tv_broadcasters_name);
            tvBroadcastingTime=(TextView) itemView.findViewById(R.id.tv_broadcasting_time);
            llLiveBroadcasterListBg=(LinearLayout)itemView.findViewById(R.id.ll_live_broadcasterlist_bg);
            llLiveBroadcasterListBg.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }

}
