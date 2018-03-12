package com.softuvo.ipundit.adapters;

/*
 * Created by Neha Kalia on 12/11/2017.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.gson.internal.LinkedTreeMap;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PodcastDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PodcastDetailsModel.Match.Channel> podacstList;
    private ItemClickListener itemClickListener;
    private String imageUrl;
    private String text;
    private String newText;


    public interface ItemClickListener {
        void onClick(int position);
    }


    public PodcastDetailsAdapter(Context context, List<PodcastDetailsModel.Match.Channel> podacstList, String imgaeurl, ItemClickListener itemClickListener) {
        this.context = context;
        this.podacstList = podacstList;
        this.itemClickListener = itemClickListener;
        this.imageUrl = imgaeurl;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PodcastDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_podacst_details, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {
        final PodcastDetailsViewHolder viewholder = (PodcastDetailsViewHolder) holder;
        if (podacstList.get(position).getLength() != null) {
            if (podacstList.get(position).getLength().equalsIgnoreCase(""))
                viewholder.tvPodacstTime.setText("00:00:00");
            else
                viewholder.tvPodacstTime.setText(podacstList.get(position).getLength());
        }
        if (imageUrl != null)
            if(!(imageUrl.equalsIgnoreCase("")))
            Picasso.with(context).load(imageUrl).into(viewholder.ivBackImage);
        if (podacstList.get(position).getName() != null)
            viewholder.tvMatchName.setText(podacstList.get(position).getName().trim());
        if (podacstList.get(position).getPodcastName() != null)
            viewholder.edSubtitle.setText(podacstList.get(position).getPodcastName());

        if (podacstList.get(viewholder.getLayoutPosition()).getmCheckBool()) {
            viewholder.ivPlayPause.setImageDrawable(context.getResources().getDrawable(R.drawable.stop_podcast));
        } else {
            viewholder.ivPlayPause.setImageDrawable(context.getResources().getDrawable(R.drawable.play_podcast));
        }

        if (AppPreferences.init(context).getString(AppConstant.USER_ID).equalsIgnoreCase(podacstList.get(viewholder.getAdapterPosition()).getBroadcasterId())) {
            viewholder.edSubtitle.setClickable(true);
            viewholder.edSubtitle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
               /* viewholder.ivcheck.setVisibility(View.VISIBLE);
                viewholder.ivCross.setVisibility(View.VISIBLE);
                viewholder.edSubtitle.setText("");*/
                    newText=podacstList.get(viewholder.getAdapterPosition()).getPodcastName();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Change Poadcast Name");
                    View viewInflated = LayoutInflater.from(context).inflate(R.layout.text_input, null, false);
                    final EditText edInput = viewInflated.findViewById(R.id.ed_input);
                    edInput.setText(newText);
                    builder.setView(viewInflated);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            text = edInput.getText().toString();
                            Map<String, String> mountMap = new HashMap<>();
                            mountMap.put("channel_id", podacstList.get(viewholder.getAdapterPosition()).getId());
                            mountMap.put("podcast_name", text);
                            updatePodacstName(mountMap, viewholder.edSubtitle,viewholder.getAdapterPosition());
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    })
                            .show();
                }

            });

        } else {
            viewholder.edSubtitle.setClickable(false);
        }



    }

    private void updatePodacstName(Map<String, String> mountMap, final CustomTextView edSubtitle, final int position) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().updatePodacstName(mountMap, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {
                    String newText=((LinkedTreeMap) (map.get("channel_info"))).get("podcast_name").toString();
                    edSubtitle.setText(newText);
                    podacstList.get(position).setPodcastName(newText);
                    notifyDataSetChanged();

                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
    }

    public void removeItem(int position) {
        podacstList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return podacstList.size();
    }

    public class PodcastDetailsViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public CustomRelativeLayout viewForeground;
        public RelativeLayout viewBackground;
        CustomTextView tvPodacstTime, tvMatchName;
        public ImageView ivPlayPause;
        CustomTextView edSubtitle;
        ImageView ivBackImage;
        //TextView tvDelete;


        public PodcastDetailsViewHolder(View view) {
            super(view);
            tvPodacstTime = view.findViewById(R.id.tv_podacst_time);
            ivPlayPause = view.findViewById(R.id.iv_play_pause);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            tvMatchName = view.findViewById(R.id.tv_match_name);
            edSubtitle = view.findViewById(R.id.ed_subtitle);
            ivBackImage=view.findViewById(R.id.iv_back_image);
            ivPlayPause.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
            if (podacstList.get(getLayoutPosition()).getmCheckBool()) {
                ivPlayPause.setImageDrawable(context.getResources().getDrawable(R.drawable.stop_podcast));
            } else {
                ivPlayPause.setImageDrawable(context.getResources().getDrawable(R.drawable.play_podcast));
            }

        }
    }
}

