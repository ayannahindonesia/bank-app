package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DataPendukungFragment extends BaseFragment implements DataPendukungContract.View, Validator.ValidationListener {

    @Inject
    DataPendukungContract.Presenter mPresenter;

    @BindView(R.id.spHubungan)
    Spinner spHub;

    @NotEmpty(message = "Masukan Nama Kerabat", trim = true)
    @BindView(R.id.etRelatedName)
    EditText etRelatedName;

    @NotEmpty(message = "Masukan Alamat Kerabat")
    @BindView(R.id.etRelatedAddress)
    EditText etRelatedAddress;

    @BindView(R.id.etRelatedPhone)
    EditText etRelatedPhone;

    @NotEmpty(message = "Masukan No HP Kerabat", trim = true)
    @BindView(R.id.etRelatedHP)
    EditText etRelatedHP;

    private String[] siblings = {"Saudara", "Teman", "Keluarga Kandung"};

    private Validator validator;

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

        validator = new Validator(this);
        validator.setValidationListener(this);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, siblings);
        spHub.setAdapter(mAdapter);

        mPresenter.takeView(this);
        mPresenter.getDataPendukung();

    }

    @OnClick(R.id.buttonConfirm)
    void confirmUpdate() {
        validator.validate();
    }


    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showDataPendukung(PreferenceRepository preferenceRepository) {
        for (int i = 0; i<siblings.length; i++) {
            if (siblings[i].toLowerCase().equals(preferenceRepository.getUserRelatedRelation().toLowerCase())) {
                spHub.setSelection(i);
            }
        }
        etRelatedName.setText(preferenceRepository.getUserRelatedPersonName());
        etRelatedAddress.setText(preferenceRepository.getUserRelatedAddress());
        etRelatedPhone.setText(preferenceRepository.getUserRelatedHomeNumber());
        etRelatedHP.setText(preferenceRepository.getUserRelatePhoneNumber());
    }

    @Override
    public void successUpdateOtherData() {
        Intent intent = new Intent(parentActivity(), DataPendukungActivity.class);
        startActivity(intent);
        parentActivity().finish();

        Toast.makeText(parentActivity(), "Data Berhasil Dirubahh", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationSucceeded() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        setRequestUpdateDataProfile();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Apakah Anda Yakin Akan Merubah Data?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();

    }

    private void setRequestUpdateDataProfile() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("related_personname", etRelatedName.getText().toString());
        jsonObject.addProperty("related_relation", spHub.getSelectedItem().toString());
        jsonObject.addProperty("related address", etRelatedAddress.getText().toString());
        jsonObject.addProperty("related_homenumber", etRelatedPhone.getText().toString());
        jsonObject.addProperty("related_phonenumber", etRelatedHP.getText().toString());

        mPresenter.patchOtherData(jsonObject);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
