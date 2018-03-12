package com.softuvo.ipundit.api;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.models.AboutUsModel;
import com.softuvo.ipundit.models.AddsModel;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.BroadacstersDetailsModel;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;
import com.softuvo.ipundit.models.DataModelBgImg;
import com.softuvo.ipundit.models.FollowCheckModel;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.models.LiveFeedsNewModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.LiveBroadcstingModel;
import com.softuvo.ipundit.models.LiveFeedsModel;
import com.softuvo.ipundit.models.LoginUserModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.MatchStandingListModel;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import com.softuvo.ipundit.models.ReconnectModel;
import com.softuvo.ipundit.models.RedFiveProGroupIdModel;
import com.softuvo.ipundit.models.ServerAddressModel;
import com.softuvo.ipundit.models.ServerListenerAddressModel;
import com.softuvo.ipundit.models.SportsNameModel;
import com.softuvo.ipundit.models.StandingListeningModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.models.TermsAndServicesModel;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.models.UserProfileResponse;
import com.softuvo.ipundit.models.UserProfileResponseModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public class ApiHelper {
    private static ApiHelper apiHelper;
    private ApiService apiservice, apiservices, apiservices1,apiServices2;

    private ApiHelper() {

    }

    public static ApiHelper init() {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
            apiHelper.initApiService();
            apiHelper.inItApiService();
            apiHelper.inItApiServices();
            apiHelper.inItApiServices1();
        }
        return apiHelper;
    }

    private void initApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiservice = retrofit.create(ApiService.class);
    }

    private void inItApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.STAGING_APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiservices = retrofit.create(ApiService.class);
    }

    private void inItApiServices() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.RED_FIVE_PRO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiservices1 = retrofit.create(ApiService.class);
    }

    private void inItApiServices1() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.LIVE_FEEDS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiServices2 = retrofit.create(ApiService.class);
    }

    public void getBackgroundImages(final ApiCallBack<DataModelBgImg> apiCallback) {
        apiservice.getBackgroundImages().enqueue(new Callback<DataModelBgImg>() {
            @Override
            public void onResponse(Call<DataModelBgImg> call, Response<DataModelBgImg> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DataModelBgImg> call, Throwable t) {
                apiCallback.onFailure(t.getMessage());
            }
        });
    }

    public void getBreakingNews(final ApiCallBack<BreakingNewsParentModel> apiCallback) {
        apiservice.getBreakingNews().enqueue(new Callback<BreakingNewsParentModel>() {
            @Override
            public void onResponse(Call<BreakingNewsParentModel> call, Response<BreakingNewsParentModel> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BreakingNewsParentModel> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void registerUser(Map map, final ApiCallBack<UserProfileResponse> apiCallback) {
        apiservice.registerUser(map).enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void loginUser(Map map, final ApiCallBack<LoginUserModel> apiCallback) {
        apiservice.loginUser(map).enqueue(new Callback<LoginUserModel>() {
            @Override
            public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LoginUserModel> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void forgotPassword(Map map, final ApiCallBack<Map> apiCallback) {
        apiservice.forgotPassword(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void registerNewUser(Map map, final ApiCallBack<UserProfileResponse> apiCallback) {
        apiservice.registerNewUser(map).enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void followNotification(Map map, final ApiCallBack<Map> apiCallback) {
        apiservice.followNotification(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getAboutUs(final ApiCallBack<AboutUsModel> apiCallBack) {
        apiservice.getAboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getUserDetailsAndMatchDetails(final ApiCallBack<UserDetailsAndMatchDetailsModel> apiCallBack) {
        apiservice.getUserDetailsAndMatchDetails().enqueue(new Callback<UserDetailsAndMatchDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsAndMatchDetailsModel> call, Response<UserDetailsAndMatchDetailsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailsAndMatchDetailsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void getUserProfile(Map map, final ApiCallBack<UserProfileResponseModel> apiCallBack) {
        apiservice.getUserProfile(map).enqueue(new Callback<UserProfileResponseModel>() {
            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }



    public void getTermsAndServices(final ApiCallBack<TermsAndServicesModel> apiCallBack) {
        apiservice.getTermsAndServices().enqueue(new Callback<TermsAndServicesModel>() {
            @Override
            public void onResponse(Call<TermsAndServicesModel> call, Response<TermsAndServicesModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TermsAndServicesModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void updateUserProfile(Map map, final ApiCallBack<Map> apiCallBack) {
        apiservice.updateUserProfile(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void updateScore(Map map, final ApiCallBack<Map> apiCallBack) {
        apiservice.updateScore(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void getSportsAndLeauges(final ApiCallBack<SportsNameModel> apiCallBack) {
        apiservice.getSportsAndLeauges().enqueue(new Callback<SportsNameModel>() {
            @Override
            public void onResponse(Call<SportsNameModel> call, Response<SportsNameModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SportsNameModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void getBrodcastMatchList(String stringPath, final ApiCallBack<BroadcastMatchlistModel> apiCallBack) {
        apiservice.getBrodcastMatchList(stringPath).enqueue(new Callback<BroadcastMatchlistModel>() {
            @Override
            public void onResponse(Call<BroadcastMatchlistModel> call, Response<BroadcastMatchlistModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BroadcastMatchlistModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void updateChatId(Map map, final ApiCallBack<Map> apiCallBack) {
        apiservice.updateChatId(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getBreakingNewsList(String stringPath, final ApiCallBack<BreakingNewsParentModel> apiCallBack) {
        apiservice.getBreakingNewsList(stringPath).enqueue(new Callback<BreakingNewsParentModel>() {
            @Override
            public void onResponse(Call<BreakingNewsParentModel> call, Response<BreakingNewsParentModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BreakingNewsParentModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getListnerMatchList(String stringPath, final ApiCallBack<MatchListListnerModel> apiCallBack) {
        apiservice.getListnerMatchList(stringPath).enqueue(new Callback<MatchListListnerModel>() {
            @Override
            public void onResponse(Call<MatchListListnerModel> call, Response<MatchListListnerModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MatchListListnerModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void getMatchStandingsList(String stringPath, final ApiCallBack<MatchStandingListModel> apiCallBack) {
        apiservice.getMatchStandingsList(stringPath).enqueue(new Callback<MatchStandingListModel>() {
            @Override
            public void onResponse(Call<MatchStandingListModel> call, Response<MatchStandingListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MatchStandingListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getUserDetailsAndBroadacstDetails(String stringPath, final ApiCallBack<UserDetailsAndMatchDetailsModel> apiCallBack) {
        apiservice.getUserDetailsAndBroadacstDetails(stringPath).enqueue(new Callback<UserDetailsAndMatchDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsAndMatchDetailsModel> call, Response<UserDetailsAndMatchDetailsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailsAndMatchDetailsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void mountOnServer(Map map, final ApiCallBack<LiveBroadcstingModel> apiCallBack) {
        apiservice.mountOnServer(map).enqueue(new Callback<LiveBroadcstingModel>() {
            @Override
            public void onResponse(Call<LiveBroadcstingModel> call, Response<LiveBroadcstingModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LiveBroadcstingModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void searchSportsUser(Map map, final ApiCallBack<UserSearchSportsModel> apiCallBack) {
        apiservices.searchSportsUser(map).enqueue(new Callback<UserSearchSportsModel>() {
            @Override
            public void onResponse(Call<UserSearchSportsModel> call, Response<UserSearchSportsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserSearchSportsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });

    }

    public void searchSportsTeam(Map map, final ApiCallBack<TeamSearchSportsModel> apiCallBack) {
        apiservices.searchSportsTeam(map).enqueue(new Callback<TeamSearchSportsModel>() {
            @Override
            public void onResponse(Call<TeamSearchSportsModel> call, Response<TeamSearchSportsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TeamSearchSportsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));

            }
        });
    }


    public void searchLeaguesUser(Map map, final ApiCallBack<UserSearchLeagueModel> apiCallBack) {
        apiservices.searchLeaguesUser(map).enqueue(new Callback<UserSearchLeagueModel>() {
            @Override
            public void onResponse(Call<UserSearchLeagueModel> call, Response<UserSearchLeagueModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserSearchLeagueModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void updatePodacstName(Map map, final ApiCallBack<Map> apiCallBack) {
        apiservice.updatePodacstName(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void logoutUSer(Map map, final ApiCallBack<Map> apiCallBack) {
        apiservice.logoutUSer(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void searchLeaguesTeam(Map map, final ApiCallBack<TeamSearchSportsModel> apiCallBack) {
        apiservices.searchLeaguesTeam(map).enqueue(new Callback<TeamSearchSportsModel>() {
            @Override
            public void onResponse(Call<TeamSearchSportsModel> call, Response<TeamSearchSportsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TeamSearchSportsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void unmountOnServer(String stringPath, final ApiCallBack<Map> apiCallBack) {
        apiservice.unmountOnServer(stringPath).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getLiveFeeds(String stringPath, final ApiCallBack<LiveFeedsModel> apiCallBack) {
        apiservice.getLiveFeeds(stringPath).enqueue(new Callback<LiveFeedsModel>() {
            @Override
            public void onResponse(Call<LiveFeedsModel> call, Response<LiveFeedsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LiveFeedsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getLiveFeedsData(String stringPath, final ApiCallBack<LiveFeedsNewModel> apiCallBack) {
        apiServices2.getLiveFeedsData(stringPath).enqueue(new Callback<LiveFeedsNewModel>() {
            @Override
            public void onResponse(Call<LiveFeedsNewModel> call, Response<LiveFeedsNewModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LiveFeedsNewModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getLiveBroadcastersList(String stringPath, final ApiCallBack<LiveBroacastersListModel> apiCallBack) {
        apiservice.getLiveBroadcastersList(stringPath).enqueue(new Callback<LiveBroacastersListModel>() {
            @Override
            public void onResponse(Call<LiveBroacastersListModel> call, Response<LiveBroacastersListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LiveBroacastersListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getTeamStandingsList(String stringPath, final ApiCallBack<StandingListeningModel> apiCallBack) {
        apiservice.getTeamStandingsList(stringPath).enqueue(new Callback<StandingListeningModel>() {
            @Override
            public void onResponse(Call<StandingListeningModel> call, Response<StandingListeningModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StandingListeningModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }


    public void followunfollow(String stringPath, final ApiCallBack<FollowUnfollowModel> apiCallBack) {
        apiservice.followunfollow(stringPath).enqueue(new Callback<FollowUnfollowModel>() {
            @Override
            public void onResponse(Call<FollowUnfollowModel> call, Response<FollowUnfollowModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowUnfollowModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void followunfollowlist(String stringPath, final ApiCallBack<FollowUnfollowModel> apiCallBack) {
        apiservice.followunfollowlist(stringPath).enqueue(new Callback<FollowUnfollowModel>() {
            @Override
            public void onResponse(Call<FollowUnfollowModel> call, Response<FollowUnfollowModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowUnfollowModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getFollowCount(String stringPath, final ApiCallBack<FollowCheckModel> apiCallBack) {
        apiservice.getFollowCount(stringPath).enqueue(new Callback<FollowCheckModel>() {
            @Override
            public void onResponse(Call<FollowCheckModel> call, Response<FollowCheckModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowCheckModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getBroadcastersDetails(String stringPath, final ApiCallBack<BroadacstersDetailsModel> apiCallBack) {
        apiservice.getBroadcastersDetails(stringPath).enqueue(new Callback<BroadacstersDetailsModel>() {
            @Override
            public void onResponse(Call<BroadacstersDetailsModel> call, Response<BroadacstersDetailsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BroadacstersDetailsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getListnerBroadcastersMainCount(final ApiCallBack<ListnerCountModel> apiCallBack) {
        apiservice.getListnerBroadcastersMainCount().enqueue(new Callback<ListnerCountModel>() {
            @Override
            public void onResponse(Call<ListnerCountModel> call, Response<ListnerCountModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ListnerCountModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getPlaylistUrl(final ApiCallBack<Map> apiCallBack) {
        apiservice.getPlaylistUrl().enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getListnerCount(String stringPath, final ApiCallBack<ListnerCountModel> apiCallBack) {
        apiservice.getListnerCount(stringPath).enqueue(new Callback<ListnerCountModel>() {
            @Override
            public void onResponse(Call<ListnerCountModel> call, Response<ListnerCountModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ListnerCountModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void unmountListenerOnServer(String stringPath, final ApiCallBack<Map> apiCallBack) {
        apiservice.unmountListenerOnServer(stringPath).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getServerAddress(String stringPath, final ApiCallBack<ServerAddressModel> apiCallBack) {
        apiservices1.getServerAddress(stringPath).enqueue(new Callback<ServerAddressModel>() {
            @Override
            public void onResponse(Call<ServerAddressModel> call, Response<ServerAddressModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ServerAddressModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getListeningServerAddress(String stringPath,final ApiCallBack<List<ServerListenerAddressModel>> apiCallback) {
        apiservices1.getListeningServerAddress(stringPath).enqueue(new Callback<List<ServerListenerAddressModel>>() {
            @Override
            public void onResponse(Call<List<ServerListenerAddressModel>> call, Response<List<ServerListenerAddressModel>> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<ServerListenerAddressModel>> call, Throwable t) {
                apiCallback.onFailure(t.getMessage());
            }
        });
    }

    public void getRedFiveProGroupId(final ApiCallBack<List<RedFiveProGroupIdModel>> apiCallback) {
        apiservices1.getRedFiveProGroupId().enqueue(new Callback<List<RedFiveProGroupIdModel>>() {
            @Override
            public void onResponse(Call<List<RedFiveProGroupIdModel>> call, Response<List<RedFiveProGroupIdModel>> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<RedFiveProGroupIdModel>> call, Throwable t) {
                apiCallback.onFailure(t.getMessage());
            }
        });
    }

    public void getPodcastDetalis(String stringPath, final ApiCallBack<PodcastDetailsModel> apiCallBack) {
        apiservice.getPodcastDetalis(stringPath).enqueue(new Callback<PodcastDetailsModel>() {
            @Override
            public void onResponse(Call<PodcastDetailsModel> call, Response<PodcastDetailsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PodcastDetailsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void deletePodcast(Map map, final ApiCallBack<Map> apiCallback) {
        apiservice.deletePodcast(map).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getFollowersList(String stringPath, final ApiCallBack<FollowerListModel> apiCallBack) {
        apiservice.getFollowersList(stringPath).enqueue(new Callback<FollowerListModel>() {
            @Override
            public void onResponse(Call<FollowerListModel> call, Response<FollowerListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowerListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void pauseStream(String stringPath, final ApiCallBack<Map> apiCallBack) {
        apiservice.pauseStream(stringPath).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getFollowingList(String stringPath, final ApiCallBack<FollowingListModel> apiCallBack) {
        apiservice.getFollowingList(stringPath).enqueue(new Callback<FollowingListModel>() {
            @Override
            public void onResponse(Call<FollowingListModel> call, Response<FollowingListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowingListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getConnectFollowerList(String stringPath, final ApiCallBack<FollowerListModel> apiCallBack) {
        apiservice.getConnectFollowerList(stringPath).enqueue(new Callback<FollowerListModel>() {
            @Override
            public void onResponse(Call<FollowerListModel> call, Response<FollowerListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowerListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getConnectFollowingList(String stringPath, final ApiCallBack<FollowingListModel> apiCallBack) {
        apiservice.getConnectFollowingList(stringPath).enqueue(new Callback<FollowingListModel>() {
            @Override
            public void onResponse(Call<FollowingListModel> call, Response<FollowingListModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowingListModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void getAdsList(String stringPath, final ApiCallBack<AddsModel> apiCallBack) {
        apiservice.getAdsList(stringPath).enqueue(new Callback<AddsModel>() {
            @Override
            public void onResponse(Call<AddsModel> call, Response<AddsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AddsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void refreshListening(String stringPath, final ApiCallBack<ReconnectModel> apiCallBack) {
        apiservice.refreshListening(stringPath).enqueue(new Callback<ReconnectModel>() {
            @Override
            public void onResponse(Call<ReconnectModel> call, Response<ReconnectModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ReconnectModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }

    public void deepLinking(String stringPath, final ApiCallBack<UserDetailsAndMatchDetailsModel> apiCallBack) {
        apiservice.deepLinking(stringPath).enqueue(new Callback<UserDetailsAndMatchDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsAndMatchDetailsModel> call, Response<UserDetailsAndMatchDetailsModel> response) {
                apiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailsAndMatchDetailsModel> call, Throwable t) {
                apiCallBack.onFailure(App.getAppContext().getResources().getString(R.string.server_error));
            }
        });
    }
}

