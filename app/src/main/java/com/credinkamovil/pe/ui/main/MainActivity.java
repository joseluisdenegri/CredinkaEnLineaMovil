package com.credinkamovil.pe.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.base.BaseDialog;
import com.credinkamovil.pe.ui.base.IOperationView;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogFragment;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogMvpPresenter;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogMvpView;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogPresenter;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.credinkamovil.pe.ui.mas.MasFragment;
import com.credinkamovil.pe.ui.ofertas.OfertasFragment;
import com.credinkamovil.pe.ui.producto.ProductosFragment;
import com.credinkamovil.pe.utils.AppResourceUtils;
import com.credinkamovil.pe.utils.FragmentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView, IOperationView {
    private static final String TAG = MainActivity.class.getName();
    private Toolbar mToolbar;
    private BottomNavigationView mNavigation;
    private ImageView imvLogoHome;

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            getActivityComponent().inject(this);
            mPresenter.onAttach(this);
            setUpView();
            setUpListeners();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void setUpView() {
        mToolbar = findViewById(R.id.t_toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        mNavigation = findViewById(R.id.bnv_navigation);
        imvLogoHome = findViewById(R.id.imv_logo_home);
    }

    @Override
    public void finishDelegateTimer() {
        this.mPresenter.finishApplication();
    }

    @Override
    public void initFragment(FragmentTransaction mFragment) {
        mFragment.disallowAddToBackStack();
        mFragment.add(R.id.fcv_principal, new ProductosFragment(), ProductosFragment.TAG);
        mFragment.commit();
    }

    @Override
    public void showFragmentProducto(FragmentTransaction mFragment) {
        FragmentUtils.clearBackStackFragment(getSupportFragmentManager());
        mFragment.disallowAddToBackStack();
        mFragment.replace(R.id.fcv_principal, new ProductosFragment(), ProductosFragment.TAG);
        mFragment.commit();
    }

    @Override
    public void showFragmentOfertas(FragmentTransaction mFragment) {
        FragmentUtils.clearBackStackFragment(getSupportFragmentManager());
        mFragment.disallowAddToBackStack();
        mFragment.replace(R.id.fcv_principal, new OfertasFragment(), OfertasFragment.TAG);
        mFragment.commit();
    }

    @Override
    public void showFragmentMas(FragmentTransaction mFragment) {
        FragmentUtils.clearBackStackFragment(getSupportFragmentManager());
        mFragment.disallowAddToBackStack();
        mFragment.replace(R.id.fcv_principal, new MasFragment(), MasFragment.TAG);
        mFragment.commit();
    }

    @Override
    public void intentLoginActivity() {
        AppResourceUtils.cancelSessionTimer();
        this.onUserInteractionTimer(false);
        intentNextActivity(MainActivity.this, LoginActivity.class, true);
    }

    @Override
    public void finishApplication() {
        AppResourceUtils.cancelSessionTimer();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppResourceUtils.restartCountDown(this);
        this.onUserInteractionTimer(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        this.onUserInteractionTimer(false);
        int iCountEntry = getSupportFragmentManager().getBackStackEntryCount();
        FragmentUtils.removeFragmentsByName(getSupportFragmentManager(), "CuentaDialogFragment");
        if (iCountEntry == 0) {
            mPresenter.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        super.onFragmentDetached(tag);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    private void setUpListeners() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mPresenter.onInitFragment(fragmentTransaction);
        imvLogoHome.setOnClickListener(new onClicLogoHome());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction mFragment = getSupportFragmentManager().beginTransaction();
            try {
                switch (item.getItemId()) {
                    case R.id.navigation_productos:
                        mPresenter.onClickFragmentProducto(mFragment);
                        break;
                    case R.id.navigation_teofrecemos:
                        mPresenter.onClickFragmentOfrecemos(mFragment);
                        break;
                    case R.id.navigation_mas:
                        mPresenter.onClickFragmentMas(mFragment);
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
    };

    class onClicLogoHome implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mNavigation.setSelectedItemId(R.id.navigation_productos);
            FragmentTransaction mFragment = getSupportFragmentManager().beginTransaction();
            mPresenter.onClickFragmentProducto(mFragment);
        }
    }
}
