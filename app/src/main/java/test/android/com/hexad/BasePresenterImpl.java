package test.android.com.hexad;


import android.content.Context;

import java.lang.ref.WeakReference;

import test.android.com.hexad.interfaces.BasePresenter;


public abstract class BasePresenterImpl<T> implements BasePresenter<T> {

    private WeakReference<T> mView;

    public void attachView(T view){
        this.mView = new WeakReference<>(view);
    }

    public void detachView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView.get();
    }
}
