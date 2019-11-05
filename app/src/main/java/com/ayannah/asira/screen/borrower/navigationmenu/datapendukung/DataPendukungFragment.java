package com.ayannah.asira.screen.borrower.navigationmenu.datapendukung;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
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

    @NotEmpty(message = "Masukan Alamat Kerabat", trim = true)
    @BindView(R.id.etRelatedAddress)
    EditText etRelatedAddress;

    @BindView(R.id.etRelatedPhone)
    EditText etRelatedPhone;

    @NotEmpty(message = "Masukan No HP Kerabat", trim = true)
    @BindView(R.id.etRelatedHP)
    EditText etRelatedHP;

    private String[] siblings = {"Saudara", "Teman", "Keluarga Kandung"};
    private AlertDialog dialogAlert;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialogAlert = builder.create();
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
        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
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
        dialogAlert.dismiss();

        Intent intent = new Intent(parentActivity(), DataPendukungActivity.class);
        startActivity(intent);
        parentActivity().finish();

        Toast.makeText(parentActivity(), "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
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
        jsonObject.addProperty("related_address", etRelatedAddress.getText().toString());
        jsonObject.addProperty("related_homenumber", etRelatedPhone.getText().toString());
        jsonObject.addProperty("related_phonenumber", etRelatedHP.getText().toString());

        dialogAlert.show();
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
