package test.android.com.hexad.contract;


import android.content.Context;


import java.util.ArrayList;

import test.android.com.hexad.interfaces.BasePresenter;
import test.android.com.hexad.model.FavoriteItem;

public interface RatingActivityContract {

    interface View {

        void onResponseError();

        void onResponseSuccess(ArrayList<FavoriteItem> dataList);

        void onShowProgress();

        Context getApplicationContext();
    }

    interface RatingPresenter extends BasePresenter<View> {

        void notifySuccessResponse(ArrayList<FavoriteItem> dataList);

        void notifyError();

        void onFetchOfDataInProgress();

        void onRatingUpdate(int position,int rating);

        void resetModelAndWorkerThread();

        void handleRandomRating();
    }

    interface RatingModel{

        void registerPresenter(RatingPresenter mainActivityPresenter);

        void deregisterPresenter(RatingPresenter mainActivityPresenter);

        void createInitialSetOfData(String[] aNamesArray,String[] aDescriptionArray);

        void checkDataAndNotify();
    }
}
