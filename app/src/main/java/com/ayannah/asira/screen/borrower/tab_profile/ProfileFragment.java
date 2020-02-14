package com.ayannah.asira.screen.borrower.tab_profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.screen.borrower.profile_menu.akunsaya.AkunSayaActivity;
import com.ayannah.asira.screen.borrower.profile_menu.datapendukung.DataPendukungActivity;
import com.ayannah.asira.screen.borrower.profile_menu.infokeuangan.InformasiKeuanganActivity;
import com.ayannah.asira.screen.borrower.profile_menu.infopribadi.InfoPribadiActivity;
import com.ayannah.asira.screen.chooselogin.ChooseLoginActivity;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.ImageUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    @Inject
    ProfileContract.Presenter mPresenter;

    @BindView(R.id.ivImage)
    ImageView ivImage;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.email)
    TextView email;

    @Inject
    public ProfileFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getUserIdentity();

    }

    @Override
    protected void initView(Bundle state) {

    }

    @Override
    public void showErrorMessage(String message, int errorCode) {

        Toast.makeText(parentActivity(), String.format("%s (%s)", message, errorCode), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void userIdentity(PreferenceRepository user) {

        ImageUtils.displayImageFromUrlWithErrorDrawable(parentActivity(), ivImage, "", null);

        name.setText(user.getUserName().toUpperCase());

        email.setText(user.getUserEmail());

    }

    @OnClick(R.id.akunSaya)
    void onClickAkunSaya(){

        Intent intent = new Intent(parentActivity(), AkunSayaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.informasiPribadi)
    void onClickAInformasiPribadi(){

        Intent intent = new Intent(parentActivity(), InfoPribadiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.pekerjaanKeuangan)
    void onClickPekerjaanKeuangan(){

        Intent intent = new Intent(parentActivity(), InformasiKeuanganActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.dataPendukung)
    void onClickDataPendukung(){

        Intent intent = new Intent(parentActivity(), DataPendukungActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnLogout)
    void onClickLogout(){

        mPresenter.doLogout();
    }



    @Override
    public void logoutComplete() {

        Intent logout = new Intent(parentActivity(), LoginActivity.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);
        parentActivity().finish();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }
}
