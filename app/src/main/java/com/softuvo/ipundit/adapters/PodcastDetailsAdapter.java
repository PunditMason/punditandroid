package com.softuvo.ipundit.adapters;

/*
 * Created by Neha Kalia on 12/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.PodcastDetailsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PodcastDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PodcastDetailsModel.Match.Channel> podacstList;


    public PodcastDetailsAdapter(Context context, List<PodcastDetailsModel.Match.Channel> podacstList) {
        this.context = context;
        this.podacstList = podacstList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PodcastDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.podcast_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final PodcastDetailsViewHolder viewholder = (PodcastDetailsViewHolder) holder;
        if (podacstList.get(position).getTimeStamp() != null)
            viewholder.tvPodcastNameDetails.setText(podacstList.get(position).getTimeStamp());
        viewholder.cvPodcastDetailsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewholder.tvDelete.setVisibility(View.GONE);
                String streamName = podacstList.get(holder.getAdapterPosition()).getStreamName();
                Uri uri = Uri.parse("https://s3.amazonaws.com/red5proautoplay/live/streams/" + streamName + ".mp4");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        viewholder.tvDelete.setVisibility(View.GONE);
        viewholder.cvPodcastDetailsContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (podacstList.size() > holder.getAdapterPosition()) {
                        if (AppPreferences.init(context).getString(AppConstant.USER_ID).equalsIgnoreCase(podacstList.get(holder.getAdapterPosition()).getBroadcasterId())) {
                            viewholder.tvDelete.setVisibility(View.VISIBLE);
                        } else {
                            viewholder.tvDelete.setVisibility(View.GONE);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        });
        viewholder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (podacstList.size() > holder.getAdapterPosition()) {
                    Map<String, String> deleteMap = new HashMap<>();
                    deleteMap.put("broadcaster_id", AppPreferences.init(context).getString(AppConstant.USER_ID));
                    deleteMap.put("channel_id", podacstList.get(holder.getAdapterPosition()).getId());
                    App.getApiHelper().deletePodcast(deleteMap, new ApiCallBack<Map>() {
                        @Override
                        public void onSuccess(Map map) {
                            try {
                                if (podacstList.size() > holder.getAdapterPosition()) {
                                    podacstList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return podacstList.size();
    }

    private class PodcastDetailsViewHolder extends RecyclerView.ViewHolder {
        CardView cvPodcastDetailsContainer;
        TextView tvPodcastNameDetails;
        TextView tvDelete;

        private PodcastDetailsViewHolder(View view) {
            super(view);
            cvPodcastDetailsContainer = view.findViewById(R.id.cv_podcast_details_container);
            tvPodcastNameDetails = view.findViewById(R.id.tv_podcast_name_details);
            tvDelete = view.findViewById(R.id.tv_delete);

        }

    }
}

