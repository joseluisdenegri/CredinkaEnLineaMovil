package com.credinkamovil.pe.utils;

import android.app.Activity;
import android.os.CountDownTimer;

import com.credinkamovil.pe.ui.base.IOperationView;
import com.credinkamovil.pe.ui.main.MainActivity;

public class TimerSessionCount extends CountDownTimer {
    private IOperationView mOperationView;
    private long nTimeDisminuid = 0;
    private Activity mActivity;

    public TimerSessionCount(long millisInFuture, long countDownInterval, Activity activity) {
        super(millisInFuture, countDownInterval);
        this.mActivity = activity;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        nTimeDisminuid = millisUntilFinished / 1000;
    }

    @Override
    public void onFinish() {
        if (mActivity instanceof MainActivity) {
            mOperationView = (MainActivity) mActivity;
            mOperationView.finishDelegateTimer();
        }
    }

    public long getTimeDisminuid() {
        return nTimeDisminuid;
    }
}
