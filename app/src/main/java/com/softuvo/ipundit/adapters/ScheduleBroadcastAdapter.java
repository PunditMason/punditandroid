package com.softuvo.ipundit.adapters;

/*
 * Created by softuvo on 14/3/18.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.ScheduleModel;
import com.softuvo.ipundit.models.SportsNameModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ScheduleBroadcastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ScheduleModel.Schedule> scheduleList;
    private ItemClickListener itemClickListener;



    public interface ItemClickListener {
        void onClick(int position);
    }


    public ScheduleBroadcastAdapter(Context context, List<ScheduleModel.Schedule> scheduleList,ItemClickListener itemClickListener) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.itemClickListener=itemClickListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleView(LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduledlist, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScheduleView holderView = (ScheduleView) holder;
        if (scheduleList.get(position).getTitle() != null)
            holderView.tvScheduleTitle.setText(scheduleList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    class ScheduleView extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_schedule_title)
        TextView tvScheduleTitle;

        @BindView(R.id.iv_edit)
        ImageView ivEdit;


        private ScheduleView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tv_schedule_title)
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }


    }
}
