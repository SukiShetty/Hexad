package test.android.com.hexad.presenter;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import test.android.com.hexad.BasePresenterImpl;
import test.android.com.hexad.R;
import test.android.com.hexad.contract.RatingActivityContract;
import test.android.com.hexad.model.FavoriteItem;
import test.android.com.hexad.model.RatingActivityModel;

public class RatingActivityPresenter extends BasePresenterImpl<RatingActivityContract.View> implements RatingActivityContract.RatingPresenter {

    private static final String TAG = RatingActivityPresenter.class.getSimpleName();

    private RatingActivityModel mModel;

    private Context mContext;

    private RatingHandler mThreadHandler;

    private HandlerThread mRatingWorkerThread;

    private static final int CREATEDATA = 1;

    private static final int UPDATEDATA = 2;

    private boolean mRandomRatingInProgress;

    private int mRandomRatingPosition = 0;



    @Override
    public void start(Context aContext) {
        mContext = aContext;
        mModel = RatingActivityModel.getInstance();

        mRatingWorkerThread = new HandlerThread("RatingWorkerThread");
        mRatingWorkerThread.start();
        mThreadHandler = new RatingHandler(mRatingWorkerThread.getLooper());

        Message msg = mThreadHandler.obtainMessage();
        msg.arg1 = CREATEDATA;
        mThreadHandler.sendMessage(msg);
    }


    @Override
    public void attachView(RatingActivityContract.View view) {
        super.attachView(view);
        mModel.registerPresenter(this);
        mModel.checkDataAndNotify();
    }

    @Override
    public void detachView() {
        mModel.deregisterPresenter(this);
        super.detachView();
    }

    @Override
    public void resetModelAndWorkerThread() {
        stopHandlerThread();
        mModel.resetModel();
    }

    @Override
    public void handleRandomRating() {
        Log.d(TAG, "Random rating is in progress " + mRandomRatingInProgress);
        if (mRandomRatingInProgress == false) {
            mRandomRatingInProgress = true;
            onRatingUpdate(mRandomRatingPosition, mRandomRatingPosition % 6);
        } else {
            mRandomRatingInProgress = false;
        }
    }


    @Override
    public void onRatingUpdate(int position, int rating) {

        if (mRandomRatingInProgress == false)
            onFetchOfDataInProgress();

        Message msg = mThreadHandler.obtainMessage();
        msg.arg1 = UPDATEDATA;
        UpdateRequest lUpdateRequest = new UpdateRequest(position, rating);
        msg.obj = lUpdateRequest;
        mThreadHandler.sendMessage(msg);
    }


    @Override
    public void onFetchOfDataInProgress() {
        getView().onShowProgress();
    }

    @Override
    public void notifySuccessResponse(final ArrayList<FavoriteItem> dataList) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached())
                    getView().onResponseSuccess(dataList);
            }
        });

        if (mRandomRatingInProgress) {
            mRandomRatingPosition++;
            if (mRandomRatingPosition >= mModel.getDataList().size()) {
                mRandomRatingPosition = 0;
            }
            onRatingUpdate(mRandomRatingPosition, mRandomRatingPosition % 6);
        }
    }

    @Override
    public void notifyError() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getView().onResponseError();
            }
        });
    }

    class RatingHandler extends Handler {
        public RatingHandler(Looper aLooper) {
            super(aLooper);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.arg1) {

                case CREATEDATA: {
                    String[] lNamesArray = mContext.getResources().getStringArray(R.array.names);
                    String[] lDescriptionArray = mContext.getResources().getStringArray(R.array.description);
                    mModel.createInitialSetOfData(lNamesArray,lDescriptionArray);
                    mContext = null;
                }
                break;

                case UPDATEDATA: {
                    UpdateRequest lUpdateRequest = (UpdateRequest) msg.obj;
                    mModel.executeUserTask(lUpdateRequest.mListPositionToUpdate, lUpdateRequest.mRatingToUpdate);
                }
                break;

                default:
                    break;
            }
        }
    }


    public void stopHandlerThread() {
        if (mRatingWorkerThread != null) {
            mRatingWorkerThread.quit();
            mRatingWorkerThread = null;
        }
    }

    private class UpdateRequest {

        int mListPositionToUpdate;

        int mRatingToUpdate;

        public UpdateRequest(int aListPositionToUpdate, int aRatingToUpdate) {
            mListPositionToUpdate = aListPositionToUpdate;
            mRatingToUpdate = aRatingToUpdate;
        }
    }
}
