package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 11/12/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.PodcastDetailsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.exoplayer.player.PlayerManager;

import com.softuvo.ipundit.exoplayer.ui.SimpleExoPlayerView;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import com.softuvo.ipundit.utils.RecyclerItemTouchHelper;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PodcastDetailsActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private Activity mContext;
    PodcastDetailsAdapter podcastDetailsAdapter;
    List<PodcastDetailsModel.Match.Channel> podacstchannelList;
    String imgaeurl;
    int oldPosition=-1;
    private PlayerManager player;
    private SimpleExoPlayerView playerView;

    @BindView(R.id.rv_podcast_details)
    RecyclerView rvPodcastDetails;

    private long contentPosition,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_details);
        mContext=PodcastDetailsActivity.this;
        ButterKnife.bind(mContext);
        playerView=findViewById(R.id.player_view);
        getPodcastDetails();
    }


    private void getPodcastDetails() {
        rvPodcastDetails.setLayoutManager(new LinearLayoutManager(mContext));
        final PodcastDetailsModel.Match podcastDetailsList=(PodcastDetailsModel.Match)mContext.getIntent().getSerializableExtra("mPodcastChannelDetails");
        podacstchannelList = podcastDetailsList.getChannel();
        if(getIntent().getStringExtra("baseImageUrl")!=null) {
            imgaeurl = getIntent().getStringExtra("baseImageUrl");
        }
        podcastDetailsAdapter = new PodcastDetailsAdapter(mContext, podacstchannelList, imgaeurl,new PodcastDetailsAdapter.ItemClickListener() {
            @Override
            public void onClick(final int position) {
             /*   if (!(podacstchannelList.get(position).getLength().equalsIgnoreCase("00:00:00")||!(podacstchannelList.get(position).getLength()==null))||!(podacstchannelList.get(position).getLength().equalsIgnoreCase(""))) {
                    if (!podacstchannelList.get(position).getmCheckBool()) {
                        if (oldPosition != -1) {
                            podcastDetailsAdapter.notifyItemChanged(oldPosition);
                            podacstchannelList.get(oldPosition).setmCheckBool(false);
                        }
                        podacstchannelList.get(position).setmCheckBool(true);
                        stopMediaPlayer();
                        player = new PlayerManager(mContext, "https://s3.amazonaws.com/red5proautoplay/live/streams/" + podacstchannelList.get(position).getStreamName() + ".mp4");
                        player.init(mContext, playerView);
                        if (podacstchannelList.get(position).getLength() != null || !(podacstchannelList.get(position).getLength().equalsIgnoreCase(""))) {
                            String time = podacstchannelList.get(position).getLength();
                            String[] tokens = time.split(":");
                            long secondsToMs = Integer.parseInt(tokens[2]) * 1000;
                            long minutesToMs = Integer.parseInt(tokens[1]) * 60000;
                            long hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
                            final long result = secondsToMs + minutesToMs + hoursToMs + 5000;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    Log.e("resultPlayer", String.valueOf(result));
                                    podacstchannelList.get(position).setmCheckBool(false);
                                    podcastDetailsAdapter.notifyItemChanged(position);
                                    stopMediaPlayer();
                                }
                            }, result);

                        }
                    } else {
                        stopMediaPlayer();
                        podacstchannelList.get(position).setmCheckBool(false);
                    }
                    podcastDetailsAdapter.notifyItemChanged(position);
                    oldPosition = position;

                }
                else{
                    SnackbarUtil.showWarningShortSnackbar(mContext,"Audio not get uploaded! Please wait for sometime");
                }*/

                Uri uri = Uri.parse("https://s3.amazonaws.com/red5proautoplay/live/streams/" +  podacstchannelList.get(position).getStreamName() + ".mp4");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });
        rvPodcastDetails.setItemAnimator(new DefaultItemAnimator());
        rvPodcastDetails.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvPodcastDetails.setAdapter(podcastDetailsAdapter);
        if(AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase(podacstchannelList.get(0).getBroadcasterId()))
        {
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvPodcastDetails);
        }



    }



  /*  private void startAudio(String streamName) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.getString(R.string.app_name)));
        // Produces Extractor instances for parsing the content media (i.e. not the ad).
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        // Bind the player to the view.
        playerView.setPlayer(player);
        // This is the MediaSource representing the content media (i.e. not the ad).
        MediaSource contentMediaSource = new ExtractorMediaSource(Uri.parse("https://s3.amazonaws.com/red5proautoplay/live/streams/" + streamName + ".mp4"), dataSourceFactory, extractorsFactory, null, null);
        player.prepare(contentMediaSource);
        player.setPlayWhenReady(true);
        contentPosition=player.getContentPosition();
        duration=player.getDuration();
        long newduration = duration / 1000;
        long minutes=duration/(60);
        long second=duration-(minutes*60);
        Log.e("time",minutes+":"+second);
    }*/

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PodcastDetailsAdapter.PodcastDetailsViewHolder) {
            if (podacstchannelList.size() > viewHolder.getAdapterPosition()) {
                Map<String, String> deleteMap = new HashMap<>();
                deleteMap.put("broadcaster_id", AppPreferences.init(mContext).getString(AppConstant.USER_ID));
                deleteMap.put("channel_id", podacstchannelList.get(viewHolder.getAdapterPosition()).getId());
                App.getApiHelper().deletePodcast(deleteMap, new ApiCallBack<Map>() {
                    @Override
                    public void onSuccess(Map map) {
                        try {
                            if (podacstchannelList.size() > viewHolder.getAdapterPosition()) {
                                podcastDetailsAdapter.removeItem(viewHolder.getAdapterPosition());
                               // stopMediaPlayer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        }
    }

    public void stopMediaPlayer(){
        if (player != null) {
//            player.stop();
            player.release();
            player.reset();
            player = null;
        }
    }

    @Override
    public void onBackPressed() {
       // stopMediaPlayer();
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
       // stopMediaPlayer();
        super.onDestroy();
    }
}
