/*
package com.softuvo.ipundit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.activities.LiveBroadCastingActivity;

public class GoLiveBottomSheet extends BottomSheetDialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_bottom_sheet_fragment, container, false);
        v.findViewById(R.id.rl_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString("userComingFrom") != null) {
                    if (getArguments().getString("userComingFrom").equals("matchList")) {
                        Intent intent = new Intent(getActivity(), LiveBroadCastingActivity.class);
                        intent.putExtra("userComingFrom", getArguments().getString("userComingFrom"));
                        intent.putExtra("mBrDatum", getArguments().getSerializable("brDatum"));
                        startActivity(intent);
                    } else if (getArguments().getString("userComingFrom").equals("matchStandingList")) {
                        Intent intent = new Intent(getActivity(), LiveBroadCastingActivity.class);
                        intent.putExtra("userComingFrom", getArguments().getString("userComingFrom"));
                        intent.putExtra("mBrDatum", getArguments().getSerializable("brDatum"));
                        startActivity(intent);
                    }
                }
            }
        });
        return v;
    }

}
*/
