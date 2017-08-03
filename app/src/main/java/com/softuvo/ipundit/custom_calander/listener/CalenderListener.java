package com.softuvo.ipundit.custom_calander.listener;

import org.joda.time.LocalDateTime;

public abstract class CalenderListener {

    public abstract void onSelectPicker();

    public abstract void onSelectDate(LocalDateTime mSelectedDate);




}
