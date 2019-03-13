package test.android.com.hexad.presenter;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;

import test.android.com.hexad.contract.RatingActivityContract;

import static org.junit.Assert.assertEquals;

/**
 * Created by shetts7 on 3/10/2019.
 */
@PrepareForTest(Context.class)
public class RatingActivityPresenterTest {

    @Mock
    private Context mContext;

    @Mock
    private RatingActivityContract.View mView;

    private RatingActivityPresenter mRatingActivityPresenter;

    @Before
    public void setup() throws Exception {
        mRatingActivityPresenter = new RatingActivityPresenter();
        mRatingActivityPresenter.start(mContext);
    }

    @Test
    public void testAttachView() {
        Assert.assertEquals(false, mRatingActivityPresenter.isViewAttached());

        mRatingActivityPresenter.attachView(mView);
        Assert.assertEquals(true, mRatingActivityPresenter.isViewAttached());

        mRatingActivityPresenter.detachView();
        Assert.assertEquals(false, mRatingActivityPresenter.isViewAttached());
    }

}
