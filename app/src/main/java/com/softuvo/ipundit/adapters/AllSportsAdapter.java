package com.softuvo.ipundit.adapters;
/*
 * Created by Neha Kalia on 12/07/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<SportsNameModel.Sports> sportsItemList;
    private List<SportsNameModel.Sports> searchsportsItemList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }


    public AllSportsAdapter(Context context, List<SportsNameModel.Sports> sportsItemList, ItemClickListener itemClickListener) {
        searchsportsItemList = new ArrayList<>();
        this.context = context;
        this.sportsItemList = sportsItemList;
        searchsportsItemList.addAll(sportsItemList);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SportsView(LayoutInflater.from(parent.getContext()).inflate(R.layout.maincatagory_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SportsView holderView = (SportsView) holder;
        if (AppPreferences.init(context).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
            if(sportsItemList.get(position).getAvatar()!=null)
                Picasso.with(context).load(ApiConstants.IMAGE_BASE_URL + sportsItemList.get(position).getAvatar()).into(holderView.sportsIcon);
            if(sportsItemList.get(position).getCoverImage()!=null)
                Picasso.with(context).load(ApiConstants.IMAGE_BASE_URL + sportsItemList.get(position).getCoverImage()).into(holderView.sportsCoverImage);
            if(sportsItemList.get(position).getName()!=null)
                holderView.sportsName.setText(sportsItemList.get(position).getName());
        } else if (AppPreferences.init(context).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
            if(sportsItemList.get(position).getAvatar()!=null)
                Picasso.with(context).load(ApiConstants.IMAGE_BASE_URL + sportsItemList.get(position).getAvatar()).into(holderView.sportsIcon);
            if(sportsItemList.get(position).getCoverImage()!=null)
                Picasso.with(context).load(ApiConstants.IMAGE_BASE_URL + sportsItemList.get(position).getCoverImage()).into(holderView.sportsCoverImage);
            if(sportsItemList.get(position).getName()!=null)
                holderView.sportsName.setText(sportsItemList.get(position).getName());
            if (!sportsItemList.get(position).getBroadcasteCount().equalsIgnoreCase("0")) {
                holderView.crlBroadcasterSportsContainer.setVisibility(View.VISIBLE);
                holderView.tvBroadcasterSportsCount.setText(sportsItemList.get(position).getBroadcasteCount());
            }
            else{
                holderView.crlBroadcasterSportsContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sportsItemList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        sportsItemList.clear();
        if (charText.length() == 0) {
            sportsItemList.addAll(searchsportsItemList);
        } else {
            for (int i = 0; i < searchsportsItemList.size(); i++) {
                if (searchsportsItemList.get(i).getName().toLowerCase().contains(charText)) {
                    sportsItemList.add(searchsportsItemList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    class SportsView extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_sports_cover_image)
        CustomRelativeLayout sportsCoverImage;

        @BindView(R.id.iv_sports_icon)
        ImageView sportsIcon;

        @BindView(R.id.tv_sports_name)
        TextView sportsName;

        @BindView(R.id.tv_broadcaster_sports_count)
        TextView tvBroadcasterSportsCount;

        @BindView(R.id.crl_broadcasters_sports_container)
        CustomRelativeLayout crlBroadcasterSportsContainer;

        private SportsView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.rl_sports_cover_image)
        void onClick() {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}
