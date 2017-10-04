package com.softuvo.ipundit.adapters;

import android.app.Activity;
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

public class AllLeagueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Activity context;
    private List<SportsNameModel.Sports.League> leaguesItemList;
    private List<SportsNameModel.Sports.League> searchleaguesItemList;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(int position);
    }

    public AllLeagueAdapter(Activity context, List<SportsNameModel.Sports.League> leaguesItemList, ItemClickListener itemClickListener) {
        searchleaguesItemList = new ArrayList<>();
        this.context = context;
        this.leaguesItemList = leaguesItemList;
        searchleaguesItemList.addAll(leaguesItemList);
        this.itemClickListener = itemClickListener;
        ButterKnife.bind(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (position == 0) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcatagory_header_item, null);
            return new HeaderViewHolder(headerView); // view holder for normal items
        } else {
            View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcatagory_normal_item, null);
            return new NormalViewHolder(normalView); // view holder for header items
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(AppPreferences.init(context).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
            if (position == 0) {
                HeaderViewHolder headerviewHolder = (HeaderViewHolder) holder;
                if(leaguesItemList.get(position).getMarkImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getMarkImage()).into(headerviewHolder.ivTopLeagueIcon);
                if(leaguesItemList.get(position).getCoverImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getCoverImage()).into(headerviewHolder.rlLeagueTopCoverImage);
               if(leaguesItemList.get(position).getName()!=null)
                headerviewHolder.tvTopLeagueName.setText(leaguesItemList.get(position).getName());
            } else {
                NormalViewHolder normalviewHolder = (NormalViewHolder) holder;
                if(leaguesItemList.get(position).getCoverImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getCoverImage()).into(normalviewHolder.leagueCoverImage);
                if(leaguesItemList.get(position).getMarkImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getMarkImage()).into(normalviewHolder.leagueIcon);
                if(leaguesItemList.get(position).getName()!=null)
                    normalviewHolder.leagueName.setText(leaguesItemList.get(position).getName());
            }
        }
        else if(AppPreferences.init(context).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)){
            if (position == 0) {
                HeaderViewHolder headerviewHolder = (HeaderViewHolder) holder;
                if(leaguesItemList.get(position).getMarkImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getMarkImage()).into(headerviewHolder.ivTopLeagueIcon);
                if(leaguesItemList.get(position).getCoverImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getCoverImage()).into(headerviewHolder.rlLeagueTopCoverImage);
                if(leaguesItemList.get(position).getName()!=null)
                    headerviewHolder.tvTopLeagueName.setText(leaguesItemList.get(position).getName());
                if(!leaguesItemList.get(position).getBroadcasterCount().equalsIgnoreCase("0")) {
                    headerviewHolder.crlHeaderBroadcastersLeagueCount.setVisibility(View.VISIBLE);
                    headerviewHolder.tvHeaderBroadcastersLeagueCount.setText(leaguesItemList.get(position).getBroadcasterCount());
                }
                else{
                    headerviewHolder.crlHeaderBroadcastersLeagueCount.setVisibility(View.GONE);
                }
            } else {
                NormalViewHolder normalviewHolder = (NormalViewHolder) holder;
                if(leaguesItemList.get(position).getCoverImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getCoverImage()).into(normalviewHolder.leagueCoverImage);
                if(leaguesItemList.get(position).getMarkImage()!=null)
                    Picasso.with(context).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + leaguesItemList.get(position).getMarkImage()).into(normalviewHolder.leagueIcon);
                if(leaguesItemList.get(position).getName()!=null)
                    normalviewHolder.leagueName.setText(leaguesItemList.get(position).getName());
                if(!leaguesItemList.get(position).getBroadcasterCount().equalsIgnoreCase("0")) {
                    normalviewHolder.crlNormalBroadcastersLeagueCount.setVisibility(View.VISIBLE);
                    normalviewHolder.tvNormalBroadcastersLeagueCount.setText(leaguesItemList.get(position).getBroadcasterCount());
                }
                else{
                    normalviewHolder.crlNormalBroadcastersLeagueCount.setVisibility(View.GONE);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return leaguesItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return position;
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        leaguesItemList.clear();
        if (charText.length() == 0) {


            leaguesItemList.addAll(searchleaguesItemList);
        } else {
            for (int i = 0; i < searchleaguesItemList.size(); i++) {
                if (searchleaguesItemList.get(i).getName().toLowerCase().contains(charText)) {
                    leaguesItemList.add(searchleaguesItemList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_league_top_cover_image)
        CustomRelativeLayout rlLeagueTopCoverImage;

        @BindView(R.id.iv_top_league_icon)
        ImageView ivTopLeagueIcon;

        @BindView(R.id.tv_top_league_name)
        TextView tvTopLeagueName;

        @BindView(R.id.crl_header_broadcasters_league_count)
        CustomRelativeLayout crlHeaderBroadcastersLeagueCount;

        @BindView(R.id.tv_header_broadcasters_league_count)
        TextView tvHeaderBroadcastersLeagueCount;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick(R.id.rl_league_top_cover_image)
        public void onClick() {
            itemClickListener.onClick(getLayoutPosition());
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_league_cover_image)
        CustomRelativeLayout leagueCoverImage;

        @BindView(R.id.iv_league_icon)
        ImageView leagueIcon;

        @BindView(R.id.tv_league_name)
        TextView leagueName;

        @BindView(R.id.crl_normal_broadcasters_league_count)
        CustomRelativeLayout crlNormalBroadcastersLeagueCount;

        @BindView(R.id.tv_normal_broadcasters_league_count)
        TextView tvNormalBroadcastersLeagueCount;

        NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.rl_league_cover_image)
        public void onClick() {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}


