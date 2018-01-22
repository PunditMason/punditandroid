package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import java.util.List;

/*
 * Created by softuvo on 02-08-2017.
 */

public class TagsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<String> tagsList;
    LinearLayout llRvContainer;

    public TagsRecyclerAdapter(Context context, List<String> tagsList, LinearLayout llRvContainer ) {
        this.context = context;
        this.tagsList = tagsList;
        this.llRvContainer = llRvContainer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TagsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        TagsViewHolder holder= (TagsViewHolder) viewHolder;
        if(tagsList.get(position)!=null)
        holder.tvTag.setText(tagsList.get(position));

        holder.deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsList.remove(position);
                notifyDataSetChanged();
                if (tagsList.size()== 0){
                    llRvContainer.setVisibility(v.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    private class TagsViewHolder extends RecyclerView.ViewHolder{
        TextView tvTag;
        Button deleteTag;

        public TagsViewHolder(View itemView) {
            super(itemView);
            tvTag= (TextView) itemView.findViewById(R.id.idForTagsName);
            deleteTag= (Button) itemView.findViewById(R.id.removeTagButton);
        }
    }
}
