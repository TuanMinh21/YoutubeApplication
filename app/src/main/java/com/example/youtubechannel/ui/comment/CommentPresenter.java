package com.example.youtubechannel.ui.comment;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.models.response.CommentResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommentPresenter extends BasePresenter<CommentMVPView> {

    private final ArrayList<Comment> mListComment = new ArrayList<>();

    @Inject
    public CommentPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(CommentMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListComment(String videoId, String nextPageToken) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getListComment(Config.VALUE_PART, Constant.VALUE_KEY, videoId, 10, nextPageToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CommentResponse>() {
                        @Override
                        public void onCompleted() {
                            getMvpView().showProgressDialog(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(CommentResponse commentResponse) {
                            if (commentResponse != null) {
                                mListComment.addAll(commentResponse.getListComment());
                                getMvpView().loadListComment(mListComment, commentResponse.getNextPageToken());
                            }
                        }
                    });
        }
    }
}
