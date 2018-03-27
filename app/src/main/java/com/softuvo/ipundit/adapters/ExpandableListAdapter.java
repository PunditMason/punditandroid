package com.softuvo.ipundit.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    List<SportsNameModel.Sports> mParent;
    private ExpandableListView accordion;
    public int lastExpandedGroupPosition;


    public ExpandableListAdapter(Context context, List<SportsNameModel.Sports> parent, ExpandableListView accordion) {
        mParent = parent;
        inflater = LayoutInflater.from(context);
        this.accordion = accordion;

    }


    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).getLeague().size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getName();
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).getLeague().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.group_row, viewGroup, false);
        }

        TextView sub =  view.findViewById(R.id.tv_sports_name);
        sub.setText(mParent.get(i).getName());

        /*if (mParent.get(i).selection.size() > 0) {
            sub.setText(mParent.get(i).selection.toString());
        } else {
            sub.setText("");
        }*/

        //return the entire view
        return view;
    }


    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.child_row, viewGroup, false);
        }


        CheckBox textView =   view.findViewById(R.id.cb_sub_type);

        //"i" is the position of the parent/group in the list and
        //"i1" is the position of the child
        textView.setText(mParent.get(i).getLeague().get(i1).getName());
// set checked if parent category selection contains child category
        if (mParent.get(i).getLeague().contains(textView.getText().toString())) {
            textView.setChecked(true);
        } else {
            textView.setChecked(false);
        }

        //return the entire view
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    /**
     * automatically collapse last expanded group
     * @see http://stackoverflow.com/questions/4314777/programmatically-collapse-a-group-in-expandablelistview
     */
    public void onGroupExpanded(int groupPosition) {

        if (groupPosition != lastExpandedGroupPosition) {
            accordion.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;
    }
}