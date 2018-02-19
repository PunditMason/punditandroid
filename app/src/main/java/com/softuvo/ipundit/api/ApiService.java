package com.softuvo.ipundit.api;

import com.softuvo.ipundit.config.ApiConstants;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

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

    @POST("Game/getLeaguenewsList/{path}")
    Call<BreakingNewsParentModel> getBreakingNewsList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/get_match_list_filter/{path}")
    Call<BroadcastMatchlistModel> getBrodcastMatchList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/leaguestat/{path}")
    Call<MatchStandingListModel> getMatchStandingsList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/getProfiles/{path}")
    Call<UserDetailsAndMatchDetailsModel> getUserDetailsAndBroadacstDetails(@Path(value = "path", encoded = true) String stringPath);

   /* @POST("Game/getmatch/{path}")
    Call<MatchListListnerModel> getListnerMatchList(@Path(value = "path", encoded = true) String stringPath);
*/
    @POST("Game/get_match_channel_list/{path}")
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

    @POST("match.json")
    Call<LiveFeedsNewModel> getLiveFeedsData(@Query(value = "match", encoded = true) String stringPath);

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

    @POST(ApiConstants.GET_PLAYLIST_URL)
    Call<Map> getPlaylistUrl();

    @POST("Game/ChannelListener_count/{path}")
    Call<ListnerCountModel> getListnerCount(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/listeners_unmount/{path}")
    Call<Map> unmountListenerOnServer(@Path(value = "path", encoded = true) String stringPath);

    @POST(ApiConstants.FOLLOW_NOTIFICATION)
    Call<Map> followNotification(@Body Map map);

    @GET("event/live/{path}/?action=broadcast")
    Call<ServerAddressModel> getServerAddress(@Path("path") String stringPath);

    @GET("admin/nodegroup/{path}/node/edge?accessToken=pest8Mmyriad")
    Call<List<ServerListenerAddressModel>> getListeningServerAddress(@Path("path") String stringPath);

    @GET(ApiConstants.GROUP_ID_URL)
    Call<List<RedFiveProGroupIdModel>> getRedFiveProGroupId();

    @POST("game/getChannelCount/{path}")
    Call<PodcastDetailsModel> getPodcastDetalis(@Path(value = "path", encoded = true) String stringPath);

    @POST(ApiConstants.DELETE_PODCAST)
    Call<Map> deletePodcast(@Body Map map);

    @POST("Game/getFollowerList/{path}")
    Call<FollowerListModel> getFollowersList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/pauseStream/{path}")
    Call<Map> pauseStream(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/getFollowingList/{path}")
    Call<FollowingListModel> getFollowingList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/connectFollowers/{path}")
    Call<FollowerListModel> getConnectFollowerList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/connectFollowings/{path}")
    Call<FollowingListModel> getConnectFollowingList(@Path(value = "path", encoded = true) String stringPath);

    //Applogic Chat integartion
    @POST(ApiConstants.UPDATE_CHAT_ID)
    Call<Map> updateChatId(@Body Map map);


    @POST("game/ListenStreamAds/{path}")
    Call<AddsModel> getAdsList(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/refreshListening/{path}")
    Call<ReconnectModel> refreshListening(@Path(value = "path", encoded = true) String stringPath);

    @POST("Game/deepLinking/{path}")
    Call<UserDetailsAndMatchDetailsModel> deepLinking(@Path(value = "path", encoded = true) String stringPath);



}
