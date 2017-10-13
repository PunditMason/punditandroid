package com.softuvo.ipundit.api;

import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.models.AboutUsModel;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.BroadacstersDetailsModel;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;
import com.softuvo.ipundit.models.DataModelBgImg;
import com.softuvo.ipundit.models.FollowCheckModel;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.LiveBroadcstingModel;
import com.softuvo.ipundit.models.LiveFeedsModel;
import com.softuvo.ipundit.models.LoginUserModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.MatchStandingListModel;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface ApiService {

    @POST(ApiConstants.DASHBOARD_IMAGES_URL)
    Call<DataModelBgImg> getBackgroundImages();

    @POST(ApiConstants.BREAKING_NEWS_URL)
    Call<BreakingNewsParentModel> getBreakingNews();

    @POST(ApiConstants.REGISTER_URL)
    Call<UserProfileResponse> registerUser(@Body Map map);

    @POST(ApiConstants.LOGIN_URL)
    Call<LoginUserModel> loginUser(@Body Map map);

    @POST(ApiConstants.FORGET_PASSWORD)
    Call<Map> forgotPassword(@Body Map map);

    @POST(ApiConstants.USER_REGISTER_NEW_URL)
    Call<UserProfileResponse> registerNewUser(@Body Map map);

    @POST(ApiConstants.ABOUT_US_URL)
    Call<AboutUsModel> getAboutUs();

    @POST(ApiConstants.USER_DETAILS_MATCH_DETAILS_URL)
    Call<UserDetailsAndMatchDetailsModel> getUserDetailsAndMatchDetails();

    @POST(ApiConstants.GET_USER_PROFILE_URL)
    Call<UserProfileResponseModel> getUserProfile(@Body Map map);

    @POST(ApiConstants.TERMS_AND_SERVICE_URL)
    Call<TermsAndServicesModel> getTermsAndServices();

    @POST(ApiConstants.UPDATE_USER_PROFILE_URL)
    Call<Map> updateUserProfile(@Body Map map);

    @POST(ApiConstants.UPDATE_SCORE_URL)
    Call<Map> updateScore(@Body Map map);

    @POST(ApiConstants.GET_SPORTS_AND_LEAGUES_URL)
    Call<SportsNameModel> getSportsAndLeauges();

    @POST("Game/get_match_list_filter/{path}")
    Call<BroadcastMatchlistModel> getBrodcastMatchList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/leaguestat/{path}")
    Call<MatchStandingListModel> getMatchStandingsList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/getProfiles/{path}")
    Call<UserDetailsAndMatchDetailsModel> getUserDetailsAndBroadacstDetails(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/getmatch/{path}")
    Call<MatchListListnerModel> getListnerMatchList(@Path(value = "path", encoded = true) String stringPath);

    @POST(ApiConstants.MOUNT_CHANNEL_URL)
    Call<LiveBroadcstingModel> mountOnServer(@Body Map map);

    @POST(ApiConstants.SEARCH_SPORTS)
    Call<UserSearchSportsModel> searchSportsUser(@Body Map map);

    @POST(ApiConstants.SEARCH_SPORTS)
    Call<TeamSearchSportsModel> searchSportsTeam(@Body Map map);

    @POST(ApiConstants.SEARCH_LEAGUES)
    Call<UserSearchLeagueModel> searchLeaguesUser(@Body Map map);

    @POST(ApiConstants.SEARCH_LEAGUES)
    Call<TeamSearchSportsModel> searchLeaguesTeam(@Body Map map);

    @POST("Broadcast/unmount/{path}")
    Call<Map> unmountOnServer(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/getMatchLiveFeedsdata/{path}")
    Call<LiveFeedsModel> getLiveFeeds(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/broadcaster_detail/{path}")
    Call<LiveBroacastersListModel> getLiveBroadcastersList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/leaguestatest/{path}")
    Call<StandingListeningModel> getTeamStandingsList(@Path(value = "path", encoded = true) String stringPath);

    @POST("game/follow/{path}")
    Call<FollowUnfollowModel> followunfollow(@Path(value = "path", encoded = true) String stringPath);

    @POST("game/followlist/{path}")
    Call<FollowUnfollowModel> followunfollowlist(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/follow_check/{path}")
    Call<FollowCheckModel> getFollowCount(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/broadcaster_detail/{path}")
    Call<BroadacstersDetailsModel> getBroadcastersDetails(@Path(value = "path", encoded = true) String stringPath);

    @POST(ApiConstants.LISTERNER_MAIN_COUNT)
    Call<ListnerCountModel> getListnerBroadcastersMainCount();

    @POST("Game/channellistener_count/{path}")
    Call<ListnerCountModel> getListnerCount(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/listeners_unmount/{path}")
    Call<Map> unmountListenerOnServer(@Path(value = "path", encoded = true) String stringPath);

    @POST(ApiConstants.FOLLOW_NOTIFICATION)
    Call<Map> followNotification(@Body Map map);

    @GET("event/live/{path}/?action=broadcast")
    Call<ServerAddressModel> getServerAddress(@Path("path") String stringPath);

    @GET(ApiConstants.LISTENING_URL)
    Call<List<ServerListenerAddressModel>> getListeningServerAddress();

}
