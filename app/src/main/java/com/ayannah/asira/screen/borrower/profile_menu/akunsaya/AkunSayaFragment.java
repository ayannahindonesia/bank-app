package com.ayannah.asira.screen.borrower.profile_menu.akunsaya;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.util.ImageUtils;
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

    @BindView(R.id.imgUser)
    ImageView imgUser;

    @BindView(R.id.name)
    TextView tvName;

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

    @NotEmpty(message = "Masukan Nama Panggilan Anda", trim = true)
    @BindView(R.id.etNickname)
    EditText nickname;

    @BindView(R.id.etKewarganegaraan)
    EditText kewarganegaraan;

    @BindView(R.id.ubah)
    Button btnUbah;

    @BindView(R.id.selesai)
    Button btnSelesai;

    private Validator validator;

    private AlertDialog dialogAlert;

    @Inject
    public AkunSayaFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialogAlert = builder.create();

        mPresenter.getDataUser();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_akun_saya;
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void showErrorMessage(String message) {

        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDataUser(PreferenceRepository preferenceRepository) {

        ImageUtils.displayImageFromUrlWithErrorDrawable(parentActivity(), imgUser, "", null, R.drawable.user_profile_default);
        tvName.setText(preferenceRepository.getUserName().toUpperCase());

        phoneNumber.setText(formatPhoneString(preferenceRepository.getUserPhone()));
        etName.setText(preferenceRepository.getUserName());
        etEmail.setText(preferenceRepository.getUserEmail());

        nickname.setText(preferenceRepository.getUserNickname());
        kewarganegaraan.setText(preferenceRepository.getUserNationality());

//        etPassword.setText(preferenceRepository);
//        etPasswordRetype.setText(preferenceRepository.);
    }

    private String formatPhoneString(String userPhone) {

        return userPhone.substring(0,2) + " " + userPhone.substring(2,5) + " " + userPhone.substring(5,9)  + " " + userPhone.substring(9);
    }

    @Override
    public void berhasil() {

        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();

        etEmail.clearFocus();

        Intent intent = new Intent(parentActivity(), AkunSayaActivity.class);
        startActivity(intent);
        parentActivity().finish();

//        mPresenter.getDataUser();
    }

    @OnClick(R.id.ubah)
    void onUbahData(){

        btnSelesai.setVisibility(View.VISIBLE);
        btnUbah.setVisibility(View.GONE);

        etEmail.setEnabled(true);
        etEmail.setBackgroundResource(R.color.colorAsiraPrimary);

        nickname.setEnabled(true);
        nickname.setBackgroundResource(R.color.colorAsiraPrimary);

        Toast.makeText(parentActivity(), "Silahkan ubah data yang diberi warna biru", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.selesai)
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

                        btnSelesai.setVisibility(View.GONE);
                        btnUbah.setVisibility(View.VISIBLE);

                        etEmail.setEnabled(false);
                        etEmail.setBackgroundResource(R.color.colorAsiraWhite);

                        nickname.setEnabled(false);
                        nickname.setBackgroundResource(R.color.colorAsiraWhite);

                        dialogAlert.show();
                        mPresenter.updateDataUser(etEmail.getText().toString().trim(), nickname.getText().toString().trim());

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
