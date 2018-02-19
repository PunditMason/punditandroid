package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/07/2017.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.SlidingImageAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.config.NotificationConstants;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.DataModelBgImg;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LoginUserModel;
import com.softuvo.ipundit.models.TermsAndServicesModel;
import com.softuvo.ipundit.models.UserProfileResponse;
import com.softuvo.ipundit.models.UserProfileResponseModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.NotificationUtils;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.utils.StringUtil;
import com.softuvo.ipundit.utils.ToastUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;
import static com.softuvo.ipundit.config.AppConstant.DEVICE_TOKENN;
import static com.softuvo.ipundit.config.AppConstant.PUNDITS_PAGE_BACKGROUND;

public class DashboardActivity extends BaseActivity {
    private Activity mContext;
    TextView termsAndServicesHeading, termsAndServicesContent;
    Button accept, decline;
    String userId = "", userEmail, userFirstName = "", userLastName = "", userName, regId;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

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

    @BindView(R.id.wv_broadcast)
    WebView wvBroadcast;

    @BindView(R.id.wv_listen)
    WebView wvListen;

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

    @BindView(R.id.tv_listner_main_count)
    CustomTextView tvListnerMainCount;

    @BindView(R.id.tv_broadcasters_main_count)
    CustomTextView tvBroadcastersMainCount;

    @BindView(R.id.crl_listner_main_count)
    CustomRelativeLayout crlListnerMainCount;

    @BindView(R.id.crl_broadcasters_main_count)
    CustomRelativeLayout crlBroadcastersMainCount;

    @BindView(R.id.rl_background_signin)
    CustomRelativeLayout rlBackgroundSignin;

    @BindView(R.id.ed_email_signin)
    EditText edEmailSignIn;

    @BindView(R.id.ed_password_signin)
    EditText edPasswordSignIn;

    @BindView(R.id.ll_signup_background)
    CustomRelativeLayout llSignupBackground;

    @BindView(R.id.ed_name)
    EditText edName;

    @BindView(R.id.ed_email)
    EditText edEmail;

    @BindView(R.id.ed_password)
    EditText edPassword;

    @BindView(R.id.ed_confirm_password)
    EditText edConfirmPassword;

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
        Date date = new Date();  // to get the date
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // getting date in this format
        String formattedDate = df.format(date.getTime());
        Log.e("cureent date:",formattedDate);
        getNewsFromServer(formattedDate);
        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(NotificationConstants.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        AppPreferences.init(mContext).putString(DEVICE_TOKENN, regId);
        if (!TextUtils.isEmpty(regId))
            Log.e("Firebase reg id: ", regId);
        else
            Log.e("Firebase reg id: ", "Firebase Reg Id is not received yet!");
    }

    private void setUIBackGrounds() {
        if (ConnectivityReceivers.isConnected()) {
            DataModelBgImg dataModelBgImg = (DataModelBgImg) getIntent().getSerializableExtra(AppConstant.MAP_IMG);
            if (dataModelBgImg != null) {
                if (dataModelBgImg.getData() != null) {
                    if (dataModelBgImg.getData().getBackground() != null) {
                        AppPreferences.init(mContext).putString(APP_BACKGROUND, ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBackground());
                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBackground()).into(llMAinBackground);
                    }
                    if (dataModelBgImg.getData().getLivematchBackground() != null) {
                        AppPreferences.init(mContext).putString(PUNDITS_PAGE_BACKGROUND, ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getLivematchBackground());
                    }
                    String broadcastTemplate = "<html><head></head><body><img src=\"file:///android_asset/img/broadcast.svg\"></body></html>";
                    String listenTemplate = "<html><head></head><body><img src=\"file:///android_asset/img/listen.svg\"></body></html>";
                    wvBroadcast.loadDataWithBaseURL(null, broadcastTemplate, "text/html", "utf-8",null);
                    wvBroadcast.getSettings();
                    wvBroadcast.setBackgroundColor(Color.TRANSPARENT);
                    wvListen.loadDataWithBaseURL(null, listenTemplate, "text/html", "utf-8",null);
                    wvListen.getSettings();
                    wvListen.setBackgroundColor(Color.TRANSPARENT);

                    wvBroadcast.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (!isLoggedIn())
                                SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
                            else {
                                AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_BROADCAST);
                                Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
                                startActivity(intent);
                                Log.e("broadcast","broadcastcalled");
                            }
                            return false;
                        }
                    });

                    wvListen.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (!isLoggedIn())
                                SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
                            else {
                                AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_LISTNER);
                                Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
                                startActivity(intent);
                                Log.e("listen","listencalled");
                            }
                            return false;
                        }
                    });

//                    if (dataModelBgImg.getData().getBroadcaster() != null)
//                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getBroadcaster()).into(rlBroadcastBackground);
//                    if (dataModelBgImg.getData().getListeners() != null)
//                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getListeners()).into(rlListenBackground);
                    if (dataModelBgImg.getData().getSettingicon() != null)
                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getSettingicon()).into(ivPunditsIcon);
                    if (dataModelBgImg.getData().getAbouticon() != null)
                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getAbouticon()).into(ivAboutPunditIcon);
                    if (dataModelBgImg.getData().getProfileicon() != null)
                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getProfileicon()).into(ivProfileIcon);
                    if (dataModelBgImg.getData().getLoginicon() != null)
                        Picasso.with(mContext).load(ApiConstants.IMAGE_BASE_URL + dataModelBgImg.getData().getLoginicon()).into(ivFbIcon);
                    if (isLoggedIn()) {
                        tvLoginLogout.setText(R.string.fb_logout_text);
                        if (!(MobiComUserPreference.getInstance(this).isLoggedIn())) {
                            Map<String, String> profileMap = new HashMap<>();
                            profileMap.put(ApiConstants.USER_ID, AppPreferences.init(mContext).getString(AppConstant.USER_ID));
                            App.getApiHelper().getUserProfile(profileMap, new ApiCallBack<UserProfileResponseModel>() {

                                @Override
                                public void onSuccess(UserProfileResponseModel userProfileResponseModel) {
                                    if (userProfileResponseModel != null) {
                                        if (userProfileResponseModel.getResponsestatus()) {
                                            AppPreferences.init(mContext).putString(AppConstant.FB_ID, userProfileResponseModel.getMessage().getFbId());
                                            AppPreferences.init(mContext).putString(AppConstant.USER_NAME, userProfileResponseModel.getMessage().getFirstName().trim());
                                            loginChat(userProfileResponseModel.getMessage().getFbId(), userProfileResponseModel.getMessage().getFirstName(), userProfileResponseModel.getMessage().getAvatar());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }

                    } else
                        tvLoginLogout.setText(R.string.fb_login_text);
                    getListnerCountData();
                } else {
                    SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
                }
            }

            mPager = findViewById(R.id.pager);
            List<String> bannerimages = null;
            if (dataModelBgImg != null) {
                bannerimages = dataModelBgImg.getBannerImages();
                if (bannerimages.size() > 0) {
                    mPager.setAdapter(new SlidingImageAdapter(mContext, bannerimages));
                    NUM_PAGES = bannerimages.size();
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }
                            mPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 4000, 4000);
                }
            }

        }else{
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

    }

    public boolean isLoggedIn() {
        return !AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase("");
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        return accessToken != null;
    }

    @OnClick(R.id.tv_create_account)
    public void onClickCreateAccount() {
        llSignupBackground.setVisibility(View.VISIBLE);
        llSignupBackground.animate()
                .translationYBy(llSignupBackground.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        llSignupBackground.animate().setListener(null);
                    }
                });

        rlBackgroundSignin.setVisibility(View.GONE);
    }


    private boolean validateSignIn() {
        if (StringUtil.isEmpty(edEmailSignIn.getText().toString().trim())) {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        } else if (StringUtil.isEmpty(edPasswordSignIn.getText().toString().trim())) {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_sign_in)
    public void onClickSignIn() {
        if (validateSignIn()) {
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put(ApiConstants.USER_EMAIL, edEmailSignIn.getText().toString());
            loginMap.put(ApiConstants.PASSWORD, edPasswordSignIn.getText().toString());
            loginNewUser(loginMap);
        }
    }

    @OnClick(R.id.iv_cross_signin)
    public void onClickCrossSignIn() {
        rlBackgroundSignin.setVisibility(View.GONE);
        rlBackgroundSignin.animate()
                .translationYBy(rlBackgroundSignin.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlBackgroundSignin.animate().setListener(null);
                    }
                });
        edEmailSignIn.setText("");
        edPasswordSignIn.setText("");
        hideKeyboard();

    }

    @OnClick(R.id.iv_cross_sign_up)
    public void onClickCrossSignUp() {
        llSignupBackground.setVisibility(View.GONE);
        edEmail.setText("");
        edName.setText("");
        edPassword.setText("");
        edConfirmPassword.setText("");
        hideKeyboard();

    }

    private void loginNewUser(Map map) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().loginUser(map, new ApiCallBack<LoginUserModel>() {
                @Override
                public void onSuccess(LoginUserModel loginUserModel) {
                    if (loginUserModel != null) {
                        if (loginUserModel.getResponsestatus().booleanValue()) {
                            if (loginUserModel.getUser().get(0).getId() != null)
                                AppPreferences.init(mContext).putString(AppConstant.USER_ID, loginUserModel.getUser().get(0).getId());
                            if (loginUserModel.getUser().get(0).getAvatar() != null)
                                AppPreferences.init(mContext).putString(AppConstant.USER_PROFILE_PIC, loginUserModel.getUser().get(0).getAvatar());
                            if ((loginUserModel.getUser().get(0).getFirstName() != null) && (loginUserModel.getUser().get(0).getLastName() != null))
                                AppPreferences.init(mContext).putString(AppConstant.USER_NAME, loginUserModel.getUser().get(0).getFirstName().trim() + " " + loginUserModel.getUser().get(0).getLastName().trim());
                            if (loginUserModel.getUser().get(0).getFbId()!=null)
                                AppPreferences.init(mContext).putString(AppConstant.FB_ID, loginUserModel.getUser().get(0).getFbId());
                            SnackbarUtil.showSuccessLongSnackbar(mContext, loginUserModel.getMessage());
                            loginChat(loginUserModel.getUser().get(0).getFbId(),loginUserModel.getUser().get(0).getFirstName(),loginUserModel.getUser().get(0).getAvatar());
                            tvLoginLogout.setText(R.string.fb_logout_text);
                            SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.register_successfully_message));
                            rlBackgroundSignin.setVisibility(View.GONE);
                            rlBackgroundSignin.animate()
                                    .translationYBy(rlBackgroundSignin.getHeight())
                                    .translationY(0)
                                    .setDuration(20000)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            rlBackgroundSignin.animate().setListener(null);
                                        }
                                    });
                            edEmailSignIn.setText("");
                            edPasswordSignIn.setText("");
                            hideKeyboard();

                        } else if (!(loginUserModel.getResponsestatus().booleanValue())) {
                            SnackbarUtil.showErrorShortSnackbar(mContext, loginUserModel.getMessage());
                        }

                    } else {
                     /*   progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));*/
                    }
                }

                @Override
                public void onFailure(String message) {
//
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

    }

    @OnClick(R.id.tv_forgot_password)
    public void onClickForgotPassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(DashboardActivity.this);
        View forgotPasswordView = layoutInflater.inflate(R.layout.forgot_password_alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
        alertDialogBuilder.setView(forgotPasswordView);
        final EditText emailForgotPassword = forgotPasswordView.findViewById(R.id.ed_email_forgot_pssword);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        if (emailForgotPassword.getText().toString().equalsIgnoreCase("")) {
                            SnackbarUtil.showWarningShortSnackbar(mContext, getResources().getString(R.string.fields_error_message));
                        } else {
                            Map<String, String> forgotpasswordMap = new HashMap<>();
                            forgotpasswordMap.put(ApiConstants.USER_EMAIL, emailForgotPassword.getText().toString());
                            if (ConnectivityReceivers.isConnected()) {
                                App.getApiHelper().forgotPassword(forgotpasswordMap, new ApiCallBack<Map>() {
                                    @Override
                                    public void onSuccess(Map map) {
                                        if (map != null) {
                                            SnackbarUtil.showWarningShortSnackbar(mContext, getResources().getString(R.string.please_check_mail));
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        dialog.dismiss();
                                    }


                                });
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrivacyPolicyBackground));
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrivacyPolicyBackground));
    }

    private boolean validateSignUp() {
        if (StringUtil.isEmpty(edName.getText().toString().trim())) {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        } else if (StringUtil.isEmpty(edEmail.getText().toString().trim())) {
            SnackbarUtil.showErrorLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        } else if (StringUtil.isEmpty(edPassword.getText().toString().trim())) {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        } else if (StringUtil.isEmpty(edConfirmPassword.getText().toString().trim())) {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.fields_error_message));
            return false;
        } else if (!edPassword.getText().toString().trim().equals(edConfirmPassword.getText().toString().trim())) {
            SnackbarUtil.showErrorLongSnackbar(mContext, getResources().getString(R.string.password_match_error_message));
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_sign_up)
    public void userSignUp() {
        if (validateSignUp()) {
            Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.profile_dummy_image);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            String encodedImg = Base64.encodeToString(ba, Base64.DEFAULT);
            Map<String, String> registerMap = new HashMap<>();
            registerMap.put(ApiConstants.USER_EMAIL, edEmail.getText().toString());
            registerMap.put(ApiConstants.USER_NAME, edName.getText().toString());
            registerMap.put(ApiConstants.USER_PROFILE_PIC, encodedImg);
            if( AppPreferences.init(mContext).getString(DEVICE_TOKENN).equalsIgnoreCase(""))
                registerMap.put(ApiConstants.DEVICE_TOKEN, "1234");
            else
                registerMap.put(ApiConstants.DEVICE_TOKEN, AppPreferences.init(mContext).getString(DEVICE_TOKENN));
            registerMap.put(ApiConstants.DEVICE_TYPE, "ANDROID");
            registerMap.put(ApiConstants.PASSWORD, edPassword.getText().toString());
            registerUser(registerMap);

        }
    }

    private void registerUser(Map map) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().registerUser(map, new ApiCallBack<UserProfileResponse>() {
                @Override
                public void onSuccess(UserProfileResponse map) {
                    if (map != null) {
                        SnackbarUtil.showSuccessShortSnackbar(mContext, map.getMessage().toString());
                        llSignupBackground.setVisibility(View.GONE);
                        edEmail.setText("");
                        edName.setText("");
                        edPassword.setText("");
                        edConfirmPassword.setText("");
                        hideKeyboard();
                    }
                }

                @Override
                public void onFailure(String message) {
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

    }


    @OnClick(R.id.tv_facebook_login)
    public void onClickLogIn() {
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getBoolean(AppConstant.IS_FIRST_LOGIN)) {
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
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_alertdialogbox);
                termsAndServicesHeading =  dialog.findViewById(R.id.tv_terms_and_condition_heading);
                termsAndServicesContent =  dialog.findViewById(R.id.tv_terms_and_condition_content);
                accept =  dialog.findViewById(R.id.btn_accept);
                decline =  dialog.findViewById(R.id.btn_decline);
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

    public void loginChat(String userID,String userFullName,String imagePath){
        UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                //After successful registration with Applozic server the callback will come here
                if(MobiComUserPreference.getInstance(context).isRegistered()) {

                    PushNotificationTask pushNotificationTask ;
                    PushNotificationTask.TaskListener listener = new PushNotificationTask.TaskListener() {
                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse) {

                        }
                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                        }

                    };

                    pushNotificationTask = new PushNotificationTask(regId, listener, mContext);
                    pushNotificationTask.execute((Void) null);
                }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                //If any failure in registration the callback  will come here
            }};

        User user = new User();
        user.setUserId(userID); //userId it can be any unique user identifier
        user.setDisplayName(userFullName); //displayName is the name of the user which will be shown in chat messages
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(getResources().getString(R.string.applozic_id)); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink(imagePath);//optional,pass your image link
        new UserLoginTask(user, listener, this).execute((Void) null);
    }

    @OnClick(R.id.rl_fb_tile)
    public void onClickFbTile() {
        if (!isLoggedIn()) {
            rlBackgroundSignin.setVisibility(View.VISIBLE);
            rlBackgroundSignin.animate()
                    .translationYBy(rlBackgroundSignin.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlBackgroundSignin.animate().setListener(null);
                        }
                    });
        } else {
            AppPreferences.init(mContext).putString(AppConstant.USER_ID, "");
            LoginManager.getInstance().logOut();
            tvLoginLogout.setText(R.string.fb_login_text);
            SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.fb_logout_message));
        }
    }

    @OnClick(R.id.rl_about_pundit_tile)
    public void gotoAboutUs() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            Intent intent = new Intent(mContext, AboutUsActivity.class);
            startActivity(intent);
        }
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

   /* @OnClick(R.id.rl_broadcast_background)
    public void goBroadcastScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_BROADCAST);
            Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
            startActivity(intent);
        }
    }*/

   /* @OnClick(R.id.rl_listen_background)
    public void goListnerScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_LISTNER);
            Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
            startActivity(intent);
        }
    }*/

    @OnClick(R.id.rl_pundits_tile)
    public void goPunditsScreen() {
        if (!isLoggedIn())
            SnackbarUtil.showWarningLongSnackbar(mContext, getString(R.string.ask_user_for_login));
        else {
            Intent intent = new Intent(mContext, LiveRightNowActivity.class);
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
                        if(object!=null) {
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
                        }
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
            App.getApiHelper().registerNewUser(map, new ApiCallBack<UserProfileResponse>() {
                @Override
                public void onSuccess(UserProfileResponse map) {
                    if (map != null) {
                        progressBar.setVisibility(View.GONE);
                        enableUserIntraction();
                        if (map.getUser().get(0).getId() != null)
                            AppPreferences.init(mContext).putString(AppConstant.USER_ID, map.getUser().get(0).getId());
                        if (map.getUser().get(0).getAvatar() != null)
                            AppPreferences.init(mContext).putString(AppConstant.USER_PROFILE_PIC, map.getUser().get(0).getAvatar());
                        if ((map.getUser().get(0).getFirstName() != null) && (map.getUser().get(0).getLastName() != null))
                            AppPreferences.init(mContext).putString(AppConstant.USER_NAME, map.getUser().get(0).getFirstName().trim() + " " + map.getUser().get(0).getLastName().trim());
                        if (map.getUser().get(0).getFbId()!=null)
                            AppPreferences.init(mContext).putString(AppConstant.FB_ID, map.getUser().get(0).getFbId());
                        tvLoginLogout.setText(R.string.fb_logout_text);
                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.register_successfully_message));
                        loginChat(map.getUser().get(0).getFbId(),map.getUser().get(0).getFirstName(),map.getUser().get(0).getAvatar());
                        rlBackgroundSignin.setVisibility(View.GONE);
                        rlBackgroundSignin.animate()
                                .translationYBy(rlBackgroundSignin.getHeight())
                                .translationY(0)
                                .setDuration(10000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        rlBackgroundSignin.animate().setListener(null);
                                    }
                                });
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
    private void getNewsFromServer(final String date) {
        if (ConnectivityReceivers.isConnected()) {
            int apiHitTimeInterval = 40000;
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           /* App.getApiHelper().getBreakingNews(new ApiCallBack<BreakingNewsParentModel>() {
                                @Override
                                public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                    if (breakingNewsParentModel != null) {
                                        ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                        List<String> breakingNews = new ArrayList<>();
                                        for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                            if (breakingNewsResponse.get(i).getTitle() != null)
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
                            });*/
                            App.getApiHelper().getBreakingNewsList("null/"+date, new ApiCallBack<BreakingNewsParentModel>() {
                                @Override
                                public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                    if (breakingNewsParentModel != null) {
                                        ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                        List<String> breakingNews = new ArrayList<>();
                                        for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                            if (breakingNewsResponse.get(i).getTitle() != null)
                                                breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                        }
                                        String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                        tvBreakingNews.setText(SubTitle);
                                        tvBreakingNews.setSelected(true);
                                    }
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }
                    });
                }
            }, 0, apiHitTimeInterval);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void getTermsAndConditionContent() {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getTermsAndServices(new ApiCallBack<TermsAndServicesModel>() {
                @Override
                public void onSuccess(TermsAndServicesModel termsAndServicesModel) {
                    if (termsAndServicesModel != null) {
                        if (termsAndServicesModel.getData().getTitle() != null)
                            termsAndServicesHeading.setText(termsAndServicesModel.getData().getTitle());
                        if (termsAndServicesModel.getData().getContent() != null)
                            termsAndServicesContent.setText(termsAndServicesModel.getData().getContent());
                    }
                }

                @Override
                public void onFailure(String message) {
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

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
                bitmap = Picasso.with(mContext).load(params[0]).resize(250, 250).get();
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
            registerMap.put(ApiConstants.DEVICE_TOKEN, regId);
            registerMap.put(ApiConstants.DEVICE_TYPE, "ANDROID");
            registerUserProfile(registerMap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void getListnerCountData() {
        if (ConnectivityReceivers.isConnected()) {
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
                                        if (map.getListnerCount() > 0) {
                                            crlListnerMainCount.setVisibility(View.VISIBLE);
                                            tvListnerMainCount.setText("" + map.getListnerCount());
                                        } else {
                                            crlListnerMainCount.setVisibility(View.GONE);
                                        }
                                        if (map.getBroadcasterCount() > 0) {
                                            crlBroadcastersMainCount.setVisibility(View.VISIBLE);
                                            tvBroadcastersMainCount.setText("" + map.getBroadcasterCount());
                                        } else {
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
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListnerCountData();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConstants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }


}
