package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;

import javax.inject.Inject;

import butterknife.BindView;

public class DataPendukungFragment extends BaseFragment implements DataPendukungContract.View {

    @Inject
    DataPendukungContract.Presenter mPresenter;

    @BindView(R.id.spHubungan)
    Spinner spHub;

    @BindView(R.id.etRelatedName)
    EditText etRelatedName;

    @BindView(R.id.etRelatedAddress)
    EditText etRelatedAddress;

    @BindView(R.id.etRelatedPhone)
    EditText etRelatedPhone;

    @BindView(R.id.etRelatedHP)
    EditText etRelatedHP;

    private String[] siblings = {"Saudara Kandung", "Suami/Istri", "Saudara"};

    @Inject
    public DataPendukungFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_data_pendukung;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void initView(Bundle state) {

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, siblings);
        spHub.setAdapter(mAdapter);

        mPresenter.takeView(this);
        mPresenter.getDataPendukung();

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showDataPendukung(PreferenceRepository preferenceRepository) {
        for (int i = 0; i<siblings.length; i++) {
            if (siblings[i].toLowerCase() == preferenceRepository.getUserRelatedRelation().toLowerCase()) {
                spHub.setSelection(i);
            }
        }
        etRelatedName.setText(preferenceRepository.getUserRelatedPersonName());
        etRelatedAddress.setText(preferenceRepository.getUserRelatedRelation());
        etRelatedPhone.setText(preferenceRepository.getUserRelatedHomeNumber());
        etRelatedHP.setText(preferenceRepository.getUserRelatePhoneNumber());
    }
}
