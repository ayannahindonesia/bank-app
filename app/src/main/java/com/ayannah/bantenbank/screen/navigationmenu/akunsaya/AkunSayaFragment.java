package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import android.os.Bundle;
import android.widget.EditText;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

import butterknife.BindView;

public class AkunSayaFragment extends BaseFragment implements AkunSayaContract.View {

    @Inject
    AkunSayaContract.Presenter mPresenter;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordRetype)
    EditText etPasswordRetype;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @Inject
    public AkunSayaFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_akunsaya;
    }

    @Override
    protected void initView(Bundle state) {

        mPresenter.takeView(this);
        mPresenter.getDataUser();
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showDataUser(PreferenceRepository preferenceRepository) {
        etName.setText(preferenceRepository.getUserName());
        etEmail.setText(preferenceRepository.getUserEmail());
        phoneNumber.setText(preferenceRepository.getUserPhone());
//        etPassword.setText(preferenceRepository);
//        etPasswordRetype.setText(preferenceRepository.);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
