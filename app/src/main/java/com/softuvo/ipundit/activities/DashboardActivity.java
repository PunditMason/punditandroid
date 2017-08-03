package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.DataModelBgImg;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.TermsAndServicesModel;
import com.softuvo.ipundit.models.UserProfileResponse;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.utils.ToastUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class DashboardActivity extends BaseActivity {
    private Activity mContext;
    TextView termsAndServicesHeading, termsAndServicesContent;
    Button accept, decline;
    String userId = "", userEmail, userFirstName = "", userLastName = "", userName;

    @BindView(R.id.iv_pundits_icon)
    ImageView ivPunditsIcon;

    @BindView(R.id.progress_bar_dashboard)
    ProgressBar progressBar;

    @BindView(R.id.iv_about_pundit_icon)
    ImageView ivAboutPunditIcon;

    @BindView(R.id.iv_profile_icon)
    ImageView ivProfileIcon;

    @BindView(R.id.iv_fb_icon)
    ImageView ivFbIcon;

    @BindView(R.id.tv_breaking_news)
    CustomTextView tvBreakingNews;

    @BindView(R.id.tv_login_logout)
    CustomTextView tvLoginLogout;

    @BindView(R.id.ll_main_background)
    CustomLinearLayout llMAinBackground;

    @BindView(R.id.rl_broadcast_background)
    CustomRelativeLayout rlBroadcastBackground;

    @BindView(R.id.rl_listen_background)
    CustomRelativeLayout rlListenBackground;

    @BindView(R.id.rl_pundits_tile)
    CustomRelativeLayout rlPunditsTile;

    @BindView(R.id.rl_about_pundit_tile)
    CustomRelativeLayout rlAboutPunditTile;

    @BindView(R.id.rl_profile_tile)
    CustomRelativeLayout rlProfileTile;

    @BindView(R.id.rl_fb_tile)
    CustomRelativeLayout rlFbTile;

    @BindView(R.id.tv_listner_main_count)
    CustomTextView tvListnerMainCount;

    @BindView(R.id.tv_broadcasters_main_count)
    CustomTextView tvBroadcastersMainCount;

    @BindView(R.id.crl_listner_main_count)
    CustomRelativeLayout crlListnerMainCount;

    @BindView(R.id.crl_broadcasters_main_count)
    CustomRelativeLayout crlBroadcastersMainCount;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = DashboardActivity.this;
        ButterKnife.bind(this);
        checkForPermission();
        callbackManager = CallbackManager.Factory.create();
        setUIBackGrounds();
        getNewsFromServer();
    }

    private void setUIBackGrounds() {
        if (ConnectivityReceivers.isConnected()) {
            DataModelBgImg dataModelBgImg = (DataModelBgImg) getIntent().getSerializableExtra(AppConstant.MAP_IMG);
            if (dataModelBgImg.getData() != null) {
                if (dataModelBgImg.getData().getBackground() != null) {
                    AppPreferences.init(mContext).putString(APP_BACKGROUND, ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBackground());
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBackground()).into(llMAinBackground);
                }
                if (dataModelBgImg.getData().getBroadcaster() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBroadcaster()).into(rlBroadcastBackground);
                if (dataModelBgImg.getData().getListeners() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getListeners()).into(rlListenBackground);
                if (dataModelBgImg.getData().getSetting() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getSetting()).into(rlPunditsTile);
                if (dataModelBgImg.getData().getProfile() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getProfile()).into(rlProfileTile);
                if (dataModelBgImg.getData().getAbout() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getAbout()).into(rlAboutPunditTile);
                if (dataModelBgImg.getData().getLogin() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getLogin()).into(rlFbTile);

                if (dataModelBgImg.getData().getSettingicon() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getSettingicon()).into(ivPunditsIcon);
                if (dataModelBgImg.getData().getAbouticon() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getAbouticon()).into(ivAboutPunditIcon);
                if (dataModelBgImg.getData().getProfileicon() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getProfileicon()).into(ivProfileIcon);
                if (dataModelBgImg.getData().getLoginicon() != null)
                    Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getLoginicon()).into(ivFbIcon);

                if (isLoggedIn())
                    tvLoginLogout.setText(R.string.fb_logout_text);
                else
                    tvLoginLogout.setText(R.string.fb_login_text);
                getListnerCountData();
            } else {
                SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
            }
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @OnClick(R.id.rl_fb_tile)
    public void onClickLogIn() {
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getBoolean(AppConstant.IS_FIRST_LOGIN)) {
                if (!isLoggedIn()) {
                    LoginManager.getInstance().logInWithReadPermissions(DashboardActivity.this, Arrays.asList("public_profile", "email", "user_posts", "user_birthday"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            getDataFromFacebook(loginResult);
                        }

                        @Override
                        public void onCancel() {
                            ToastUtil.showLongToast(App.getAppContext(), "LoginCanceled");
                        }

                        @Override
                        public void onError(FacebookException error) {
                            ToastUtil.showLongToast(App.getAppContext(), "error");
                        }
                    });
                } else {
                    LoginManager.getInstance().logOut();
                    tvLoginLogout.setText(R.string.fb_login_text);
                    SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.fb_logout_message));
                }
            } else {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_alertdialogbox);
                termsAndServicesHeading = (TextView) dialog.findViewById(R.id.tv_terms_and_condition_heading);
                termsAndServicesContent = (TextView) dialog.findViewById(R.id.tv_terms_and_condition_content);
                accept = (Button) dialog.findViewById(R.id.btn_accept);
                decline = (Button) dialog.findViewById(R.id.btn_decline);
                getTermsAndConditionContent();
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppPreferences.init(mContext).putBoolean(AppConstant.IS_FIRST_LOGIN, true);
                        dialog.dismiss();
                        LoginManager.getInstance().logInWithReadPermissions(DashboardActivity.this, Arrays.asList("public_profile", "email", "user_posts", "user_birthday"));
                        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                getDataFromFacebook(loginResult);
                            }

                            @Override
                            public void onCancel() {
                                ToastUtil.showLongToast(App.getAppContext(), "onCancel");
                            }

                            @Override
                            public void onError(FacebookException error) {
                                ToastUtil.showLongToast(App.getAppContext(), "error");
                            }
                        });
                    }
                });

                decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @OnClick(R.id.rl_about_pundit_tile)
    public void gotoAboutUs() {
        Intent intent = new Intent(mContext, AboutUsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_profile_tile)
    public void goProfileScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            Intent intent = new Intent(mContext, UserProfileActivity.class);
            intent.putExtra(AppConstant.USER_ID, AppPreferences.init(mContext).getString(AppConstant.USER_ID));
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_broadcast_background)
    public void goBroadcastScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_BROADCAST);
            Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_listen_background)
    public void goListnerScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_LISTNER);
            Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_pundits_tile)
    public void goPunditsScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            Intent intent=new Intent(mContext,PunditsScreen.class);
            startActivity(intent);
        }
    }

    private void getDataFromFacebook(final LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        if (object.has("id"))
                            userId = object.getString("id");
                        if (object.has("first_name"))
                            userFirstName = (object.getString("first_name"));
                        if (object.has("last_name"))
                            userLastName = (object.getString("last_name"));
                        if (object.has("email"))
                            userEmail = (object.getString("email"));
                        else
                            userEmail = ("");

                        userName = userFirstName + " " + userLastName;
                        new CallAsncTask().execute("https://graph.facebook.com/" + userId + "/picture?type=large");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, name,first_name, last_name, email, birthday, gender");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    // Register User On server
    private void registerUserProfile(Map map) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().registerUser(map, new ApiCallBack<UserProfileResponse>() {
                @Override
                public void onSuccess(UserProfileResponse map) {
                    if (map != null) {
                        progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        if(map.getUser().get(0).getId()!=null)
                        AppPreferences.init(mContext).putString(AppConstant.USER_ID, map.getUser().get(0).getId());
                        if(map.getUser().get(0).getAvatar()!=null)
                        AppPreferences.init(mContext).putString(AppConstant.USER_PROFILE_PIC, map.getUser().get(0).getAvatar());
                        if( (map.getUser().get(0).getFirstName()!=null)&&(map.getUser().get(0).getLastName()!=null))
                        AppPreferences.init(mContext).putString(AppConstant.USER_NAME, map.getUser().get(0).getFirstName() + " " + map.getUser().get(0).getLastName());
                        tvLoginLogout.setText(R.string.fb_logout_text);
                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.register_successfully_message));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBar.setVisibility(View.GONE);
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    // Getting News From Servr Every 20 sec.
    private void getNewsFromServer() {
        int apiHitTimeInterval = 20000;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        App.getApiHelper().getBreakingNews(new ApiCallBack<BreakingNewsParentModel>() {
                            @Override
                            public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                if (breakingNewsParentModel != null) {
                                    ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                    List<String> breakingNews = new ArrayList<>();
                                    for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                        if(breakingNewsResponse.get(i).getTitle()!=null)
                                        breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                    }
                                    String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                    tvBreakingNews.setText(SubTitle);
                                    tvBreakingNews.setSelected(true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                SnackbarUtil.showErrorLongSnackbar(mContext, message);
                            }
                        });
                    }
                });
            }
        }, 0, apiHitTimeInterval);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void getTermsAndConditionContent() {
        App.getApiHelper().getTermsAndServices(new ApiCallBack<TermsAndServicesModel>() {
            @Override
            public void onSuccess(TermsAndServicesModel termsAndServicesModel) {
                if(termsAndServicesModel!=null) {
                    if(termsAndServicesModel.getData().getTitle()!=null)
                    termsAndServicesHeading.setText(termsAndServicesModel.getData().getTitle());
                    if(termsAndServicesModel.getData().getContent()!=null)
                    termsAndServicesContent.setText(termsAndServicesModel.getData().getContent());
                }
            }

            @Override
            public void onFailure(String message) {
                SnackbarUtil.showErrorLongSnackbar(mContext, message);
            }
        });

    }

    private class CallAsncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            disableUserIntraction();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(mContext).load(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedImg = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Map<String, String> registerMap = new HashMap<>();
            registerMap.put(ApiConstants.FACEBOOK_ID, userId);
            registerMap.put(ApiConstants.USER_EMAIL, userEmail);
            registerMap.put(ApiConstants.USER_NAME, userName);
            registerMap.put(ApiConstants.USER_PROFILE_PIC, encodedImg);
            registerUserProfile(registerMap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void getListnerCountData() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        App.getApiHelper().getListnerBroadcastersMainCount(new ApiCallBack<ListnerCountModel>() {
                            @Override
                            public void onSuccess(ListnerCountModel map) {
                                if (map != null) {
                                    if(map.getListnerCount()>0){
                                        crlListnerMainCount.setVisibility(View.VISIBLE);
                                        tvListnerMainCount.setText("" + map.getListnerCount());
                                    }
                                    else{
                                        crlListnerMainCount.setVisibility(View.GONE);
                                    }
                                    if (map.getBroadcasterCount() > 0) {
                                        crlBroadcastersMainCount.setVisibility(View.VISIBLE);
                                        tvBroadcastersMainCount.setText("" + map.getBroadcasterCount());
                                    }
                                    else{
                                        crlBroadcastersMainCount.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(String message) {

                            }
                        });
                    }
                });
            }
        }, 0, 10000);
    }

    @Override
    public void onResume(){
        super.onResume();
        getListnerCountData();
    }


}
