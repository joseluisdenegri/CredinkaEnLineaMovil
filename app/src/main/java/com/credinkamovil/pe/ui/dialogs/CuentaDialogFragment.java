package com.credinkamovil.pe.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.ItemOption;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseDialog;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaFragment;
import com.credinkamovil.pe.ui.dialogs.adapters.CustomDialogAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class CuentaDialogFragment extends BaseDialog implements CuentaDialogMvpView, View.OnClickListener {
    public static final String TAG = CuentaDialogFragment.class.getName();
    private RelativeLayout rlSpace, rlSpace2, lyMainDialog;
    private GridView gvOptionMenu;
    private ArrayList<ItemOption> itemListaOptions;
    private static String sNombreCuenta;

    @Inject
    CuentaDialogMvpPresenter<CuentaDialogMvpView> mPresenterDialogCuenta;
    @Inject
    CustomDialogAdapter mCustomDialogAdapter;

    public CuentaDialogFragment() {
    }

    public static CuentaDialogFragment newInstance(String param1, String param2) {
        CuentaDialogFragment fragment = new CuentaDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        itemListaOptions = new ArrayList<>();
        if (getArguments().containsKey("oListaOpcion")) {
            itemListaOptions = getArguments().getParcelableArrayList("oListaOpcion");
        }
        if (getArguments() != null) {
            sNombreCuenta = getArguments().getString("sNameCta");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_cuenta_dialog, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterDialogCuenta.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    @Override
    public void showFragmentEstadoCuenta(int iOpcion, String sNroCta) {
        dismissDialog(TAG);
        Bundle mBundle = new Bundle();
        mBundle.putString("sNumeroCuenta", sNroCta);
        mBundle.putString("sNombreCuenta", sNombreCuenta);
        if (iOpcion == 1) {
            showFragments(getParentFragmentManager(), new EstadoCuentaFragment(), EstadoCuentaFragment.TAG, mBundle);
            dismissAllowingStateLoss();
        }
    }

    @Override
    protected void setUp(View view) {
        rlSpace = view.findViewById(R.id.rl_space);
        rlSpace2 = view.findViewById(R.id.rl_space2);
        gvOptionMenu = view.findViewById(R.id.gv_options);
        lyMainDialog = view.findViewById(R.id.ly_main_dialog);

        gvOptionMenu.setAdapter(mCustomDialogAdapter);
        initListener();
    }

    private void initListener() {
        try {
            rlSpace.setOnClickListener(this);
            rlSpace2.setOnClickListener(this);
            lyMainDialog.setVisibility(View.VISIBLE);
            gvOptionMenu.setOnItemClickListener(new isOnClicDialogListener());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getDialog()).getWindow().getAttributes().windowAnimations = R.style.DialogAnimationPopup;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCustomDialogAdapter != null) {
            if (itemListaOptions.size() > 0) {
                mCustomDialogAdapter.addListNotifyChanged(itemListaOptions, getContext());
            }
        }
    }

    @Override
    public void onDestroyView() {
        mPresenterDialogCuenta.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_space:
            case R.id.rl_space2:
                dismissDialog();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    class isOnClicDialogListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ItemOption item = (ItemOption) parent.getItemAtPosition(position);
            int iIdOpcion = item.getiIdOption();
            String sNumeroCuenta = item.getsNumeroCuenta();
            mPresenterDialogCuenta.onClickedEECC(iIdOpcion, sNumeroCuenta);
        }
    }
}
