package com.softuvo.ipundit.api;

public interface ApiCallBack<T> {
    void onSuccess(T t);
    void onFailure(String message);
}
