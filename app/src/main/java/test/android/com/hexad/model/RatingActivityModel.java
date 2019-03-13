package test.android.com.hexad.model;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import test.android.com.hexad.R;
import test.android.com.hexad.contract.RatingActivityContract;
import test.android.com.hexad.interfaces.RatingComparator;
import test.android.com.hexad.presenter.RatingActivityPresenter;

public class RatingActivityModel implements RatingActivityContract.RatingModel {

    private static final String TAG = RatingActivityModel.class.getSimpleName();

    private static RatingActivityModel instance;

    private List<RatingActivityContract.RatingPresenter> activityPresenters = new ArrayList<>();

    private ArrayList<FavoriteItem> dataList;

    private int mPosition;

    private int mRating;

    @Override
    public void registerPresenter(RatingActivityContract.RatingPresenter ratingActivityPresenter) {
        if (activityPresenters != null) {
            if (!activityPresenters.contains(ratingActivityPresenter)) {
                activityPresenters.add(ratingActivityPresenter);
            }
        }
    }

    @Override
    public void deregisterPresenter(RatingActivityContract.RatingPresenter mainActivityPresenter) {
        if (activityPresenters != null && !activityPresenters.isEmpty())
            activityPresenters.remove(mainActivityPresenter);
    }

    @Override
    public void createInitialSetOfData(String[] aNamesArray, String[] aDescriptionArray) {
        dataList = new ArrayList<FavoriteItem>();
        for (int i = 0; i < aNamesArray.length; i++) {
            FavoriteItem lFavoriteItem = new FavoriteItem();
            lFavoriteItem.setName(aNamesArray[i]);
            lFavoriteItem.setDescription((aDescriptionArray[i] != null)? aDescriptionArray[i] : "");
            lFavoriteItem.setRating(0);
            dataList.add(lFavoriteItem);
        }

    }

    @Override
    public void checkDataAndNotify() {
        if (dataList.size() == 0) {
            notifyError();
        } else {
            notifySuccess();
        }
    }


    public static RatingActivityModel getInstance() {
        if (instance == null) {
            synchronized (RatingActivityModel.class) {
                if (instance == null) {
                    instance = new RatingActivityModel();
                }
            }
        }

        return instance;
    }

    public void executeUserTask(int position, int rating) {
        mPosition = position;
        mRating = rating;
        updateListDataAndNotifyUIForInvalidate();
    }

    private void updateListDataAndNotifyUIForInvalidate() {
        try {
            FavoriteItem lFavoriteItem = dataList.get(mPosition);
            Log.d(TAG, "Favorite item before rating is " + lFavoriteItem.getRating());
            lFavoriteItem.setRating(mRating);
            Log.d(TAG, "Favorite item name is " + lFavoriteItem.getName());
            Log.d(TAG, "Favorite item after rating is " + lFavoriteItem.getRating());
            dataList.remove(mPosition);
            dataList.add(mPosition, lFavoriteItem);
            Collections.sort(dataList, new RatingComparator());
            notifySuccess();
        } catch (ArrayIndexOutOfBoundsException exception) {
            Log.d(TAG, "Exception when random click on UI ");
        }

    }


    private void notifySuccess() {
        for (int i = 0; i < activityPresenters.size(); ++i) {
            RatingActivityContract.RatingPresenter obs = activityPresenters.get(i);
            if (obs != null) {
                obs.notifySuccessResponse(dataList);
            }
        }
    }

    private void notifyError() {
        for (int i = 0; i < activityPresenters.size(); ++i) {
            RatingActivityContract.RatingPresenter obs = activityPresenters.get(i);
            if (obs != null) {
                obs.notifyError();
            }
        }
    }

    public void resetModel() {
        dataList = null;
        instance = null;
    }

    public ArrayList<FavoriteItem> getDataList() {
        return dataList;
    }
}
