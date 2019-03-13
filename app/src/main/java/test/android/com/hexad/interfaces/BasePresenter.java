package test.android.com.hexad.interfaces;


import android.content.Context;

public interface BasePresenter<T> {
    void start(Context aContext);

    void attachView(T view);

    void detachView();

    boolean isViewAttached();
}
