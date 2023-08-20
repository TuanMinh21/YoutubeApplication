package com.example.youtubechannel.ui.main;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.ListHomeAdapter;
import com.example.youtubechannel.adapter.PlaylistHorizontalAdapter;
import com.example.youtubechannel.adapter.VideoPopularAdapter;
import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.HomeObject;
import com.example.youtubechannel.models.Playlist;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.about_us.AboutUsActivity;
import com.example.youtubechannel.ui.all_video.AllVideoActivity;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.ui.my_channel.MyChannelActivity;
import com.example.youtubechannel.ui.playlist.PlaylistActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator3;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseMVPDialogActivity implements MainMVPView {

    @Inject
    MainPresenter presenter;

    private ActionBarDrawerToggle mToggle;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    @BindView(R.id.viewpager_2)
    public ViewPager2 viewPager2;

    @BindView(R.id.indicator_3)
    public CircleIndicator3 circleIndicator3;

    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    @BindView(R.id.rcv_playlist)
    public RecyclerView rcvPlaylist;

    @BindView(R.id.rcvData)
    public RecyclerView rcvData;

    private ArrayList<Video> mListVideoPopular;
    VideoPopularAdapter videoPopularAdapter;
    PlaylistHorizontalAdapter playlistHorizontalAdapter;
    ListHomeAdapter listHomeAdapter;

    private final Handler mHandlerBanner = new Handler();
    private final Runnable mRunnableBanner = new Runnable() {
        @Override
        public void run() {
            if (viewPager2 == null || mListVideoPopular == null || mListVideoPopular.isEmpty()) {
                return;
            }
            if (viewPager2.getCurrentItem() == mListVideoPopular.size() - 1) {
                viewPager2.setCurrentItem(0);
                return;
            }
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        MobileAds.initialize(this, "ca-app-pub-8577216370890753~4422934437");
        showAdmobBanner();

        initNavigationView();
        initUI();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandlerBanner.removeCallbacks(mRunnableBanner);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandlerBanner.postDelayed(mRunnableBanner, 3000);
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        if (videoPopularAdapter != null) {
            videoPopularAdapter.release();
        }
        if (playlistHorizontalAdapter != null) {
            playlistHorizontalAdapter.release();
        }
        if (listHomeAdapter != null) {
            listHomeAdapter.release();
        }
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFunction.showMessageError(this);
    }

    private void initNavigationView() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                GlobalFunction.hideSoftKeyboard(MainActivity.this);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        presenter.getListPlaylist();
    }

    private void initUI() {
        tvTitle.setText(getString(R.string.playlist));
    }

    private void initData() {
        presenter.getListPopularVideo();
        presenter.getListHomeObject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            showDialogExitApp();
        }
    }

    private void showDialogExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive((dialog, which) -> finish())
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }

    @Override
    public void loadListPlaylist(ArrayList<Playlist> playlists) {
        playlistHorizontalAdapter = new PlaylistHorizontalAdapter(this);
        playlistHorizontalAdapter.injectInto(rcvPlaylist);
        playlistHorizontalAdapter.setListData(playlists);
    }

    @Override
    public void loadListPopularVideo(ArrayList<Video> videos) {
        mListVideoPopular = videos;
        videoPopularAdapter = new VideoPopularAdapter(this, mListVideoPopular);
        viewPager2.setAdapter(videoPopularAdapter);
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandlerBanner.removeCallbacks(mRunnableBanner);
                mHandlerBanner.postDelayed(mRunnableBanner, 3000);
            }
        });
    }

    @Override
    public void loadListHomeObject(ArrayList<HomeObject> listHomeObject) {
        listHomeAdapter = new ListHomeAdapter(this);
        listHomeAdapter.injectInto(rcvData);
        listHomeAdapter.setData(listHomeObject);
    }

    @OnClick({R.id.tv_see_all_playlist, R.id.layout_popular_video, R.id.layout_latest_video,
            R.id.layout_playlist, R.id.layout_my_channel, R.id.layout_rate_app, R.id.layout_share_app,
            R.id.layout_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_see_all_playlist:
                GlobalFunction.startActivity(this, PlaylistActivity.class);
                break;

            case R.id.layout_my_channel:
                GlobalFunction.startActivity(this, MyChannelActivity.class);
                break;

            case R.id.layout_playlist:
                GlobalFunction.startActivity(this, PlaylistActivity.class);
                break;

            case R.id.layout_popular_video:
                goToAllVideoActivity(Config.VALUE_ORDER_VIEW_COUNT);
                break;

            case R.id.layout_latest_video:
                goToAllVideoActivity(Config.VALUE_ORDER_DATE);
                break;

            case R.id.layout_rate_app:
                rateApp();
                break;

            case R.id.layout_share_app:
                shareApp();
                break;

            case R.id.layout_about:
                GlobalFunction.startActivity(this, AboutUsActivity.class);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void goToAllVideoActivity(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_TYPE_ORDER, type);
        GlobalFunction.startActivity(this, AllVideoActivity.class, bundle);
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void shareApp() {
        String URL = "http://play.google.com/store/apps/details?id=" + getPackageName();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, URL);
        startActivity(Intent.createChooser(intent, getString(R.string.share_app)));
    }

    private void showAdmobBanner() {
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
