package test.android.com.hexad.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import test.android.com.hexad.R;
import test.android.com.hexad.adapter.ListFavoriteItemsAdapter;
import test.android.com.hexad.contract.RatingActivityContract;
import test.android.com.hexad.interfaces.RatingChangeListener;
import test.android.com.hexad.model.FavoriteItem;
import test.android.com.hexad.presenter.RatingActivityPresenter;

public class RatingActivity extends AppCompatActivity implements RatingActivityContract.View, View.OnClickListener, RatingChangeListener, View.OnScrollChangeListener {

    private static final String TAG = RatingActivity.class.getSimpleName();

    private RecyclerView mListView;

    private ProgressDialog mProgressDialog;

    private ListFavoriteItemsAdapter mAdapter;

    private RatingActivityContract.RatingPresenter mPresenter;

    private Button mRandomRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        mListView = findViewById(R.id.listView);
        mListView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mRandomRating = findViewById(R.id.random_rating);
        mRandomRating.setOnClickListener(this);
        mPresenter = new RatingActivityPresenter();
        mPresenter.start(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mPresenter.resetModelAndWorkerThread();
    }

    @Override
    public void onResponseError() {
        Toast.makeText(getApplicationContext(), getString(R.string.error_string), Toast.LENGTH_SHORT).show();
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onResponseSuccess(ArrayList<FavoriteItem> dataList) {

        if(mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        if (mAdapter == null) {
            mAdapter = new ListFavoriteItemsAdapter(dataList, this);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setDataItems(dataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onShowProgress() {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.random_rating:
                mPresenter.handleRandomRating();
                break;

        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.d(TAG, "onScrollChange "+scrollX+" "+scrollY+" "+oldScrollX+" "+oldScrollY);

    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.d(TAG, "IndexOutOfBoundsException");
            }
        }
    }

    @Override
    public void onRatingChanged(int position, int rating) {
        mPresenter.onRatingUpdate(position, rating);
    }
}
