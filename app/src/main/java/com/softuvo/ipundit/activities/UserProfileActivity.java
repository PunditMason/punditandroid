package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.UserProfileResponseModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.CommanUtil;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomBlurImage;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends BaseActivity {
    private Activity mContext;
    String userId, userChoosenTask, base64Img, userEmail;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bm;

    @BindView(R.id.ll_user_profile_background)
    CustomLinearLayout llUserPprofileBackground;

    @BindView(R.id.iv_profile_img)
    ImageView ivProfilePic;

    @BindView(R.id.rl_blur_profile_pic)
    CustomRelativeLayout rlProfilePicBlur;

    @BindView(R.id.ed_userName)
    EditText etUserName;

    @BindView(R.id.iv_save_profile)
    ImageView ivSaveProfile;

    @BindView(R.id.iv_delete_profileImage)
    ImageView ivDeleteProfileImage;

    @BindView(R.id.iv_edit_profileImage)
    ImageView ivEditProfileImage;

    @BindView(R.id.tv_follower_count)
    CustomTextView tvFollowerCount;

    @BindView(R.id.tv_following_count)
    CustomTextView tvFollowingCount;

    @BindView(R.id.et_userBio)
    EditText etUserBio;

    @BindView(R.id.ed_facebook)
    EditText etFacebook;

    @BindView(R.id.ed_twitter)
    EditText etTwitter;

    @BindView(R.id.et_youtube)
    EditText etYouTube;

    @BindView(R.id.progress_bar_user_profile)
    ProgressBar progressBarUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = UserProfileActivity.this;
        ButterKnife.bind(this);
        checkConnection();
    }

    @OnClick(R.id.iv_save_profile)
    public void updateUserProfile() {
        if (ConnectivityReceivers.isConnected()) {
            updateProfile();
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @OnClick(R.id.iv_my_podcasts)
    public void OnClickMyPodcast() {
        Intent intent = new Intent(mContext, PodcastActivity.class);
        intent.putExtra("punditsId", userId);
        startActivity(intent);
    }

    @OnClick(R.id.ll_followers)
    public void OnClickFollowers() {
        Intent intent = new Intent(mContext, FollowersListActivity.class);
        intent.putExtra("usercomingfrom", "userProfile");
        startActivity(intent);
    }

    @OnClick(R.id.ll_followings)
    public void OnClickFollowings() {
        Intent intent = new Intent(mContext, FollowingListActivity.class);
        intent.putExtra("usercomingfrom", "userProfile");
        startActivity(intent);
    }

    @OnClick(R.id.iv_delete_profileImage)
    public void deleteProfilePic() {
        new AlertDialog.Builder(UserProfileActivity.this)
                .setTitle(getString(R.string.delete_diaog_title))
                .setMessage(getString(R.string.delete_diaog_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ivProfilePic.setImageBitmap(null);
                        ivProfilePic.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.profile_dummy_image));
                        Drawable myDrawable = ContextCompat.getDrawable(mContext, R.drawable.profile_dummy_image);
                        Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();
                        base64Img = convertImageToStringForServer(anImage);
                        Bitmap resultedBitmap = CustomBlurImage.blur(mContext, anImage);
                        rlProfilePicBlur.setBackground(new BitmapDrawable(mContext.getResources(), resultedBitmap));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @OnClick(R.id.iv_edit_profileImage)
    public void editProfilePic() {
        selectImage();
    }


    private void checkConnection() {
        if (ConnectivityReceivers.isConnected()) {
            setData();
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            SnackbarUtil.showSuccessLongSnackbar(mContext, getResources().getString(R.string.internet_connected_text));
            setData();
        } else {
            SnackbarUtil.showSuccessLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));

        }
    }

    private void updateProfile() {
        progressBarUserProfile.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (bm != null) {
            base64Img = convertImageToStringForServer(bm);
        }
        Map<String, String> updateProfileMap = new HashMap<>();
        updateProfileMap.put(ApiConstants.USER_PROFILE_ID, userId);
        updateProfileMap.put(ApiConstants.USER_BIO, etUserBio.getText().toString().trim());
        updateProfileMap.put(ApiConstants.USER_FACEBOOK_LINK, etFacebook.getText().toString().trim());
        updateProfileMap.put(ApiConstants.USER_TWITTER_LINK, etTwitter.getText().toString().trim());
        updateProfileMap.put(ApiConstants.USER_YOU_TUBE_LINK, etYouTube.getText().toString().trim());
        updateProfileMap.put(ApiConstants.USER_UPDATED_PROFILE_PIC, base64Img);
        updateProfileMap.put(ApiConstants.USER_EMAIL, userEmail);
        updateProfileMap.put(ApiConstants.USER_NAME, etUserName.getText().toString().trim());
        updateProfileMap.put(ApiConstants.USER_COUNTRY_ID, "ind");

        App.getApiHelper().updateUserProfile(updateProfileMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {
                progressBarUserProfile.setVisibility(View.GONE);
                enableUserIntraction();
                if (map != null) {
                    if ((map.get("result")) != null) {
                        AppPreferences.init(mContext).putString(AppConstant.USER_PROFILE_PIC, ((LinkedTreeMap) (map.get("result"))).get("avatar").toString());
                        AppPreferences.init(mContext).putString(AppConstant.USER_NAME, ((LinkedTreeMap) (map.get("result"))).get("first_name").toString().trim());

                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.update_success_message));
                    }
                } else
                    SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
            }

            @Override
            public void onFailure(String message) {
                progressBarUserProfile.setVisibility(View.GONE);
                enableUserIntraction();
                SnackbarUtil.showErrorLongSnackbar(mContext, message);
            }
        });
    }

    // to set data on load activity
    private void setData() {
        if (AppPreferences.init(mContext).getString(AppConstant.APP_BACKGROUND) != null)
            Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.APP_BACKGROUND)).into(llUserPprofileBackground);
        if (AppPreferences.init(mContext).getString(AppConstant.USER_ID) != null)
            userId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
        progressBarUserProfile.setVisibility(View.VISIBLE);
        disableUserIntraction();
        Map<String, String> profileMap = new HashMap<>();
        profileMap.put(ApiConstants.USER_ID, userId);
        App.getApiHelper().getUserProfile(profileMap, new ApiCallBack<UserProfileResponseModel>() {
            @SuppressLint("StaticFieldLeak")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(final UserProfileResponseModel userProfileResponseModel) {
                if (userProfileResponseModel != null) {
                    if (userProfileResponseModel.getResponsestatus()) {
                        if (userProfileResponseModel.getMessage().getAvatar() != null) {
                            Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + userProfileResponseModel.getMessage().getAvatar()).into(ivProfilePic);
                            AppPreferences.init(mContext).putString(AppConstant.USER_PROFILE_PIC, userProfileResponseModel.getMessage().getAvatar());
                        }
                        if ((userProfileResponseModel.getMessage().getFirstName() != null) && userProfileResponseModel.getMessage().getLastName() != null)
                            AppPreferences.init(mContext).putString(AppConstant.USER_NAME, userProfileResponseModel.getMessage().getFirstName().trim() + " " + userProfileResponseModel.getMessage().getLastName().trim());
                        if (userProfileResponseModel.getMessage().getEmail() != null)
                            userEmail = userProfileResponseModel.getMessage().getEmail();
                        new AsyncTask<Void, Void, Bitmap>() {
                            @Override
                            protected void onPostExecute(Bitmap image) {
                                super.onPostExecute(image);
                                if (image != null) {
                                    progressBarUserProfile.setVisibility(View.GONE);
                                    enableUserIntraction();
                                    base64Img = convertImageToStringForServer(image);
                                    Bitmap resultBmp = CustomBlurImage.blur(mContext, image);
                                    rlProfilePicBlur.setBackground(new BitmapDrawable(mContext.getResources(), resultBmp));
                                }
                                progressBarUserProfile.setVisibility(View.GONE);
                                enableUserIntraction();
                            }

                            @Override
                            protected Bitmap doInBackground(Void... voids) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + userProfileResponseModel.getMessage().getAvatar()).resize(250, 250).get();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return bitmap;
                            }
                        }.execute();
                        if (userProfileResponseModel.getMessage().getFirstName() != null)
                            etUserName.setText(userProfileResponseModel.getMessage().getFirstName());
                        if (userProfileResponseModel.getMessage().getFollower() != null)
                            tvFollowerCount.setText(String.valueOf(userProfileResponseModel.getMessage().getFollowing()));
                        if (userProfileResponseModel.getMessage().getFollowing() != null)
                            tvFollowingCount.setText(String.valueOf(userProfileResponseModel.getMessage().getFollower()));
                        if (userProfileResponseModel.getMessage().getUserBio() != null)
                            etUserBio.setText(userProfileResponseModel.getMessage().getUserBio());
                        if (userProfileResponseModel.getMessage().getFacebook() != null)
                            etFacebook.setText(userProfileResponseModel.getMessage().getFacebook());
                        if (userProfileResponseModel.getMessage().getTwitter() != null)
                            etTwitter.setText(userProfileResponseModel.getMessage().getTwitter());
                        if (userProfileResponseModel.getMessage().getYoutube() != null)
                            etYouTube.setText(userProfileResponseModel.getMessage().getYoutube());
                    }
                } else {
                    progressBarUserProfile.setVisibility(View.GONE);
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
                }
            }

            @Override
            public void onFailure(String message) {
                progressBarUserProfile.setVisibility(View.GONE);
                enableUserIntraction();
                SnackbarUtil.showErrorLongSnackbar(mContext, message);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstant.REQUEST_CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    CommanUtil.checkAndRequestPermission(mContext, Manifest.permission.CAMERA, AppConstant.REQUEST_CAMERA_PERMISSION_CODE);
                }

                break;

            case AppConstant.REQUEST_READ_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    CommanUtil.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.REQUEST_READ_STORAGE_PERMISSION_CODE);
                }

                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (CommanUtil.checkAndRequestPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, AppConstant.REQUEST_STORAGE_PERMISSION_CODE)) {
                            if (CommanUtil.checkAndRequestPermission(mContext, Manifest.permission.CAMERA, AppConstant.REQUEST_CAMERA_PERMISSION_CODE)) {
                                cameraIntent();
                            }
                        }
                    } else {
                        cameraIntent();
                    }

                } else if (items[item].equals("Choose Photo")) {
                    userChoosenTask = "Choose Photo";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (CommanUtil.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.REQUEST_READ_STORAGE_PERMISSION_CODE)) {
                            galleryIntent();
                        }
                    } else {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    userChoosenTask = "Cancel";
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
//            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivProfilePic.setImageBitmap(thumbnail);
        base64Img = convertImageToStringForServer(thumbnail);
        Bitmap resultedBitmap = CustomBlurImage.blur(mContext, thumbnail);
        rlProfilePicBlur.setBackground(new BitmapDrawable(mContext.getResources(), resultedBitmap));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {

            // Get the url from data
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                ivProfilePic.setImageURI(selectedImageUri);
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                    bm = BitmapFactory.decodeStream(imageStream);
                    Bitmap bm1 = CustomBlurImage.blur(mContext, bm);
                    rlProfilePicBlur.setBackground(new BitmapDrawable(mContext.getResources(), bm1));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String convertImageToStringForServer(Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (imageBitmap != null) {
            if (sizeOf(imageBitmap) > 3000000) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byteArray = stream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else if (sizeOf(imageBitmap) > 1000000) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] byteArray = stream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else if (sizeOf(imageBitmap) < 1000000) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byteArray = stream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);

            } else {
                int bytes = imageBitmap.getByteCount();
                ByteBuffer bbf = ByteBuffer.allocate(bytes);
                imageBitmap.copyPixelsToBuffer(bbf);
                byte[] bitmarr = bbf.array();
                return Base64.encodeToString(bitmarr, Base64.DEFAULT);
            }
        } else {
            return "";
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static int sizeOf(Bitmap data) {
        return data.getByteCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataFollowUnFollow();
    }

    // to set data on load activity
    private void setDataFollowUnFollow() {
        if (AppPreferences.init(mContext).getString(AppConstant.USER_ID) != null)
            userId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
        Map<String, String> profileMap = new HashMap<>();
        profileMap.put(ApiConstants.USER_ID, userId);
        App.getApiHelper().getUserProfile(profileMap, new ApiCallBack<UserProfileResponseModel>() {
            @Override
            public void onSuccess(final UserProfileResponseModel userProfileResponseModel) {
                if (userProfileResponseModel != null) {
                    if (userProfileResponseModel.getResponsestatus()) {
                        if (userProfileResponseModel.getMessage().getFollower() != null)
                            tvFollowerCount.setText(String.valueOf(userProfileResponseModel.getMessage().getFollowing()));
                        if (userProfileResponseModel.getMessage().getFollowing() != null)
                            tvFollowingCount.setText(String.valueOf(userProfileResponseModel.getMessage().getFollower()));
                    }
                } else {
                    SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
                }
            }

            @Override
            public void onFailure(String message) {
                SnackbarUtil.showErrorLongSnackbar(mContext, message);
            }
        });

    }

}
