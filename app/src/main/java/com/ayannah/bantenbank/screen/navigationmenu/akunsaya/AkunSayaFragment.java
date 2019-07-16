package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AkunSayaFragment extends BaseFragment implements AkunSayaContract.View, Validator.ValidationListener {

    @Inject
    AkunSayaContract.Presenter mPresenter;

    @BindView(R.id.etName)
    EditText etName;

    @NotEmpty(message = "Masukan Alamat Email Anda", trim = true)
    @Email(message = "Format Email Salah")
    @BindView(R.id.etEmail)
    EditText etEmail;

//    @BindView(R.id.etPassword)
//    EditText etPassword;
//
//    @BindView(R.id.etPasswordRetype)
//    EditText etPasswordRetype;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    private Validator validator;

    @Inject
    public AkunSayaFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        mPresenter.getDataUser();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_akunsaya;
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDataUser(PreferenceRepository preferenceRepository) {
        phoneNumber.setText(preferenceRepository.getUserPhone());
        etName.setText(preferenceRepository.getUserName());
        etEmail.setText(preferenceRepository.getUserEmail());

//        etPassword.setText(preferenceRepository);
//        etPasswordRetype.setText(preferenceRepository.);
    }

    @Override
    public void berhasil() {

        Toast.makeText(parentActivity(), "Akun berhasil di update", Toast.LENGTH_SHORT).show();

        etEmail.clearFocus();

        mPresenter.getDataUser();
    }

    @OnClick(R.id.buttonSubmit)
    void onClickSubmit(){

        validator.validate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onValidationSucceeded() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        mPresenter.updateDataUser(etEmail.getText().toString());
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

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for(ValidationError error: errors){

            String message = error.getCollatedErrorMessage(parentActivity());
            View view = error.getView();

            if(view instanceof EditText){

                ((EditText)view).setError(message);

            }else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
