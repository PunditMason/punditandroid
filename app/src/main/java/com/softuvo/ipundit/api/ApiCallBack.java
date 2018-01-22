package com.softuvo.ipundit.api;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public interface ApiCallBack<T> {
    void onSuccess(T t);
    void onFailure(String message);
}
