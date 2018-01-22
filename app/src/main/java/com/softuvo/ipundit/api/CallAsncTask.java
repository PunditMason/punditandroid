package com.softuvo.ipundit.api;
import android.content.Context;
import android.os.AsyncTask;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public  class CallAsncTask extends AsyncTask<String, Void, String> {
    private Context mContext;

    public CallAsncTask(Context mContext){
        this.mContext=mContext;

    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(String... arg0) {
        String result = arg0[0] ;
        return "";
    }


    protected  void onPostExecute(String response){


    }

}
