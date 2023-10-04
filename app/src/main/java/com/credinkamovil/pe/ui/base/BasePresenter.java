package com.credinkamovil.pe.ui.base;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter <V extends MvpView> implements MvpPresenter<V>{
    private static final String TAG = "BasePresenter";

    private SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;
    private final DataManager mDataManager;

    private V mMvpView;

    //@Inject
    public BasePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
        mCompositeDisposable.dispose();
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Por favor llame a Presenter.onAttach(MvpView) antes" +
                    " de solicitar datos al presentador");
        }
    }
}
