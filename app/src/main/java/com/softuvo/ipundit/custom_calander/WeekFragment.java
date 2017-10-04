package com.softuvo.ipundit.custom_calander;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.App;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekFragment extends Fragment {

    //Declaring Variables

    LocalDateTime mSelectedDate, startDate, mCurrentDate;

    TextView sundayTV, mondayTv, tuesdayTv, wednesdayTv, thursdayTv, fridayTv, saturdayTv;
    TextView[] textViewArray = new TextView[7];

    int datePosition = 0, selectorDateIndicatorValue = 0, currentDateIndicatorValue = 0;
    ArrayList<LocalDateTime> dateInWeekArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weekcell, container, false);

        sundayTV = (TextView) view.findViewById(R.id.sun_txt);
        mondayTv = (TextView) view.findViewById(R.id.mon_txt);
        tuesdayTv = (TextView) view.findViewById(R.id.tue_txt);
        wednesdayTv = (TextView) view.findViewById(R.id.wen_txt);
        thursdayTv = (TextView) view.findViewById(R.id.thu_txt);
        fridayTv = (TextView) view.findViewById(R.id.fri_txt);
        saturdayTv = (TextView) view.findViewById(R.id.sat_txt);

        /*Adding WeekViews to array for background changing purpose*/
        textViewArray[0] = sundayTV;
        textViewArray[1] = mondayTv;
        textViewArray[2] = tuesdayTv;
        textViewArray[3] = wednesdayTv;
        textViewArray[4] = thursdayTv;
        textViewArray[5] = fridayTv;
        textViewArray[6] = saturdayTv;


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*Setting the date info in the Application class*/

        startDate = App.getAppContext().getDate();
        mCurrentDate = App.getAppContext().getDate();


        /*Setting the Resources values and Customization values to the views*/

        Resources resources = getActivity().getResources();

        selectorDateIndicatorValue = resources.getIdentifier(getArguments().getString(RWeekCalendar.DATE_SELECTOR_BACKGROUND), "drawable",
                RWeekCalendar.PAKAGENAMEVALUE);

        currentDateIndicatorValue = getArguments().getInt(RWeekCalendar.CURRENT_DATE_BACKGROUND);


        datePosition = getArguments().getInt(RWeekCalendar.POSITIONKEY);
        int addDays = datePosition * 7;
        if (startDate != null) {
            startDate = startDate.plusDays(addDays);//Adding the 7days to the previous week

            mSelectedDate = App.getAppContext().getSelected();


            textViewArray[0].setBackgroundResource(selectorDateIndicatorValue);//Setting the first days of the week as selected


      /*Fetching the data's for the week to display*/

            for (int i = 0; i < 7; i++) {

                if (mSelectedDate != null) {

                    if (mSelectedDate.getDayOfMonth() == startDate.getDayOfMonth()) {

                   /*Indicate  if the day is selected*/

                        textViewArray[i].setBackgroundResource(selectorDateIndicatorValue);

                        mDateSelectedBackground(i);//Changing View selector background

                        App.getAppContext().setSelected(null);//null the selected date

                    }
                }

                dateInWeekArray.add(startDate);//Adding the days in the selected week to list


                startDate = startDate.plusDays(1); //Next day
            }

        }
        if (dateInWeekArray != null) {
        /*Setting color in the week views*/
            if (dateInWeekArray.size() > 6) {
                sundayTV.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                mondayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                tuesdayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                wednesdayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                thursdayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                fridayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));
                saturdayTv.setTextColor(getArguments().getInt(RWeekCalendar.PRIMARY_BACKGROUND));

        /*Displaying the days in the week views*/

                sundayTV.setText(Integer.toString(dateInWeekArray.get(0).getDayOfMonth()));
                mondayTv.setText(Integer.toString(dateInWeekArray.get(1).getDayOfMonth()));
                tuesdayTv.setText(Integer.toString(dateInWeekArray.get(2).getDayOfMonth()));
                wednesdayTv.setText(Integer.toString(dateInWeekArray.get(3).getDayOfMonth()));
                thursdayTv.setText(Integer.toString(dateInWeekArray.get(4).getDayOfMonth()));
                fridayTv.setText(Integer.toString(dateInWeekArray.get(5).getDayOfMonth()));
                saturdayTv.setText(Integer.toString(dateInWeekArray.get(6).getDayOfMonth()));

        /*if the selected week is the current week indicates the current day*/
                if (datePosition == RWeekCalendar.CURRENT_WEEK_POSITION) {
                    for (int i = 0; i < 7; i++) {
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == dateInWeekArray.get(i).getDayOfMonth()) {
                            textViewArray[i].setTextColor(currentDateIndicatorValue);
                            textViewArray[i].setBackgroundResource(selectorDateIndicatorValue);
                            mDateSelectedBackground(i);
                        }
                    }

                }
                sundayTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(0);
                        //sundayTV.setBackgroundResource(selectorDateIndicatorValue);
                        sundayTV.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(0);
                    }
                });
                mondayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(1);
                        //mondayTv.setBackgroundResource(selectorDateIndicatorValue);
                        mondayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(1);
                    }
                });

                tuesdayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(2);
//                tuesdayTv.setBackgroundResource(selectorDateIndicatorValue);
                        tuesdayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(2);
                    }
                });


                wednesdayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(3);
//                wednesdayTv.setBackgroundResource(selectorDateIndicatorValue);
                        wednesdayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(3);
                    }
                });


                thursdayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(4);
                        //thursdayTv.setBackgroundResource(selectorDateIndicatorValue);
                        thursdayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(4);
                    }
                });


                fridayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(5);
                        //fridayTv.setBackgroundResource(selectorDateIndicatorValue);
                        fridayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(5);
                    }
                });


                saturdayTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectedDateInfo(6);
                        //saturdayTv.setBackgroundResource(selectorDateIndicatorValue);
                        saturdayTv.setBackgroundResource(R.drawable.circle);
                        mDateSelectedBackground(6);
                    }
                });
            }
        }
    }

    public static WeekFragment newInstance(int position, String selectorDateIndicatorValue, int currentDateIndicatorValue, int primaryTextColor) {

        WeekFragment f = new WeekFragment();
        Bundle b = new Bundle();

        b.putInt(RWeekCalendar.POSITIONKEY, position);
        b.putString(RWeekCalendar.DATE_SELECTOR_BACKGROUND, selectorDateIndicatorValue);
        b.putInt(RWeekCalendar.CURRENT_DATE_BACKGROUND, currentDateIndicatorValue);
        b.putInt(RWeekCalendar.PRIMARY_BACKGROUND, primaryTextColor);
        f.setArguments(b);
        return f;
    }

    public void mSelectedDateInfo(int position) {
        RWeekCalendar.getInstance().getSelectedDate(dateInWeekArray.get(position), false);

        mSelectedDate = dateInWeekArray.get(position);

        App.getAppContext().setSelected(mSelectedDate);


    }

    public void mDateSelectedBackground(int position) {
        for (int i = 0; i < textViewArray.length; i++) {
            if (position != i) {

                textViewArray[i].setBackgroundColor(Color.TRANSPARENT);


            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /**
         * Reset date to first day of week when week goes from the view
         */

        if (isVisibleToUser) {
            if (dateInWeekArray.size() > 0)
                RWeekCalendar.getInstance().getSelectedDate(dateInWeekArray.get(0), true);


        } else {
            if (mSelectedDate != null) {
                if (RWeekCalendar.calenderType == RWeekCalendar.NORMAL_CALENDER) {
                    //Resetting the day when current week scrolled or hidden
                    if (datePosition != RWeekCalendar.CURRENT_WEEK_POSITION) {
                        textViewArray[0].setBackgroundResource(selectorDateIndicatorValue);
                        textViewArray[1].setBackgroundColor(Color.TRANSPARENT);
                        textViewArray[2].setBackgroundColor(Color.TRANSPARENT);
                        textViewArray[3].setBackgroundColor(Color.TRANSPARENT);
                        textViewArray[4].setBackgroundColor(Color.TRANSPARENT);
                        textViewArray[5].setBackgroundColor(Color.TRANSPARENT);
                        textViewArray[6].setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        //if the scrolled week is the current week then reset the selection to the current date
                        int position = new LocalDateTime().dayOfWeek().get();
                        if (position > 6)
                            textViewArray[position - 1].setBackgroundResource(selectorDateIndicatorValue);
                        else
                            textViewArray[position].setBackgroundResource(selectorDateIndicatorValue);
                        mDateSelectedBackground(position);
                    }
                } else {
                    //if the scrolled week is the current week then reset the selection to the current date for FDFcalender
                    textViewArray[0].setBackgroundResource(selectorDateIndicatorValue);
                    textViewArray[1].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[2].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[3].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[4].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[5].setBackgroundColor(Color.TRANSPARENT);
                    textViewArray[6].setBackgroundColor(Color.TRANSPARENT);
                }
            }

        }
    }

    public void ChangeSelector(LocalDateTime mSelectedDate) {
        if (startDate != null) {
            LocalDateTime startDate = App.getAppContext().getDate();
            int addDays = datePosition * 7;


            startDate = startDate.plusDays(addDays);

            for (int i = 0; i < 7; i++) {

                if (mSelectedDate.getDayOfMonth() == startDate.getDayOfMonth()) {
                    textViewArray[i].setBackgroundResource(selectorDateIndicatorValue);
                    mDateSelectedBackground(i);


                }
                startDate = startDate.plusDays(1);
            }
        }
    }
}
