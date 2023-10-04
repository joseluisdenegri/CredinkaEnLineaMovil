package com.credinkamovil.pe.ui.ofertas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnOfertaComercial;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.ofertas.comercial.OfertaCreditoFragment;

import javax.inject.Inject;

public class OfertasFragment extends BaseFragment implements OfertasMvpView {

    public static final String TAG = OfertasFragment.class.getName();
    private RelativeLayout rlOfertaCreditos;
    @Inject
    OfertasMvpPresenter<OfertasMvpView> mPresenterOfertas;

    public OfertasFragment() {
    }

    public static OfertasFragment newInstance(String param1, String param2) {
        OfertasFragment fragment = new OfertasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_ofertas, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterOfertas.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            rlOfertaCreditos = view.findViewById(R.id.rl_oferta_creditos);

            rlOfertaCreditos.setOnClickListener(new onClicOfertaCreditos());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    @Override
    public void onMostrarOfertaCreditos(EnOfertaComercial enOfertaComercial) {
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("oEnOfertaComercial", enOfertaComercial);
        showFragments(getParentFragmentManager(), new OfertaCreditoFragment(), OfertaCreditoFragment.TAG, mBundle);
    }

    class onClicOfertaCreditos implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mPresenterOfertas.onObtenerOfertaComercialCredito();
        }
    }

    @Override
    public void onDestroyView() {
        mPresenterOfertas.onDetach();
        super.onDestroyView();
    }
}
