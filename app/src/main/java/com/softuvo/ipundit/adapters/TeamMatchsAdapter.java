package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.PodcastDetailsModel;

import java.util.List;

/*
 * Created by Neha Kalia on 12/11/2017.
 */

public class TeamMatchsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<PodcastDetailsModel.Match> podacstList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }
    public TeamMatchsAdapter(Context context, List<PodcastDetailsModel.Match> podacstList, ItemClickListener itemClickListener) {
        this.context = context;
        this.podacstList = podacstList;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PodcastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.podcast_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PodcastViewHolder viewholder = (PodcastViewHolder) holder;
        if(podacstList.size()>0)
        viewholder.tvPodcastNameDetails.setText(podacstList.get(position).getTeam1Name()+"Vs"+podacstList.get(position).getTeam2Name());
        if(podacstList.get(position).getChannel().size()>0){
            viewholder.tvPodcastNameDetails.setBackgroundColor(context.getResources().getColor(R.color.colorBroadcastersListBackground));
        }
        else{
            viewholder.tvPodcastNameDetails.setBackgroundColor(context.getResources().getColor(R.color.colorPodcastPage));
            viewholder.tvPodcastNameDetails.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return podacstList.size();
    }

    @Override
    public void onClick(View v) {

    }
    private class PodcastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout cvPodcastDetailsContainer;
        TextView tvPodcastNameDetails;

        private PodcastViewHolder(View view) {
            super(view);
            cvPodcastDetailsContainer =  view.findViewById(R.id.cv_podcast_details_container);
            tvPodcastNameDetails =  view.findViewById(R.id.tv_podcast_name_details);
            cvPodcastDetailsContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}
