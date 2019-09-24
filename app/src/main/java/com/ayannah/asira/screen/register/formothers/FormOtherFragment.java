package com.ayannah.asira.screen.register.formothers;

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
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.screen.otpphone.VerificationOTPActivity;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class FormOtherFragment extends BaseFragment implements FormOtherContract.View, Validator.ValidationListener {

    @Inject
    FormOtherContract.Presenter mPresenter;

    public static final String BANK_NAME = "BANK_NAME";
    public static final String BANK_ID = "BANK_ID";

    public static final String ACC_NUMBER = "ACC_NUMBER";

    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String PASS = "PASS";
    public static final String CONF_PASS = "CONF_PASS";

    public static final String KTP_NO = "KTP_NO";
    public static final String NPWP_NO = "NPWP_NO";
    public static final String PHOTO_KTP = "PHOTO_KTP";
    public static final String PHOTO_NPWP = "PHOTO_NPWP";

    public static final String REGIST_NAME = "REGIST_NAME";
    public static final String GENDER = "GENDER";
    public static final String REGIST_BIRTHDATE = "REGIST_BIRTHDATE";
    public static final String REGIST_BIRTHPLACE = "REGIST_BIRTHPLACE";
    public static final String REGIST_EDUCATION = "REGIST_EDUCATION";
    public static final String MOTHER_NAME = "MOTHER_NAME";
    public static final String MARITAL_STATUS = "MARITAL_STATUS";
    public static final String SPOUSE_NAME = "SPOUSE_NAME";
    public static final String SPOUSE_BIRTHDATE = "SPOUSE_BIRTHDATE";
    public static final String SPOUSE_EDUCATION = "SPOUSE_EDUCATION";
    public static final String DEPENDANTS = "DEPENDANTS";
    public static final String ADDRESS = "ADDRESS";
    public static final String PROVINCE = "PROVINCE";
    public static final String CITY = "CITY";
    public static final String SUB_DISTRICT = "SUB_DISTRICT";
    public static final String DISTRICT = "DISTRICT";
    public static final String REGIST_RT = "REGIST_RT";
    public static final String REGIST_RW = "REGIST_RW";
    public static final String REGIST_PHONE = "REGIST_PHONE";
    public static final String HOME_STAY_YEAR = "HOME_STAY_YEAR";
    public static final String HOME_STATUS = "HOME_STATUS";
    public static final String NICKNAME = "NICKNAME";

    public static final String OCCUPATION = "OCCUPATION";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String WORK_PERIOD = "WORK_PERIOD";
    public static final String COMPANY_ADDRESS = "COMPANY_ADDRESS";
    public static final String COMPANY_PHONE = "COMPANY_PHONE";
    public static final String SUPERVISOR = "SUPERVISOR";
    public static final String JOB_TITLE = "JOB_TITLE";
    public static final String SALARY = "SALARY";
    public static final String OTHER_INCOME = "OTHER_INCOME";
    public static final String OTHER_INCOME_SOURCE = "OTHER_INCOME_SOURCE";

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;
    private PreferenceRepository preferenceRepository;

    private Validator validator;
    private AlertDialog dialog;

    @BindView(R.id.spHubungan)
    Spinner spHubungan;

    @NotEmpty(message = "Masukan No Handphone Kerabat Anda", trim = true)
    @BindView(R.id.etRelatedHP)
    @Length(min = 10, message = "Minimal 10 digit, Maximal 14 digit", max = 14)
    EditText etRelatedHP;

    @BindView(R.id.etRelatedPhone)
    EditText etRelatedPhone;

    @NotEmpty(message = "Masukan Nama Kerabat Anda", trim = true)
    @BindView(R.id.etRelatedName)
    EditText etRelatedName;

    @NotEmpty(message = "Masukan Alamat Kerabat Anda", trim = true)
    @BindView(R.id.etRelatedAddress)
    EditText etRelatedAddress;

    private String[] siblings = {"Saudara", "Teman", "Keluarga Kandung"};

    @Inject
    public FormOtherFragment(){
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_account_information_lainlain;
    }

    @Override
    protected void initView(Bundle state) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        validator = new Validator(this);
        validator.setValidationListener(this);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_custom_spinner, siblings);
        spHubungan.setAdapter(mAdapter);

    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){
        validator.validate();
    }

    @Override
    public void showErrorMessage(String message) {
        dialog.dismiss();

        AlertDialog.Builder alert = new AlertDialog.Builder(parentActivity());
        alert.setTitle("Error Register");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public  void registerComplete() {

        Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
        assert bundle != null;
        mPresenter.getUserToken(bundle.getString(PHONE), bundle.getString(PASS), "regist");

    }

    @Override
    public void successGetOTP() {
        dialog.dismiss();

        Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
        assert bundle != null;

        Intent verification = new Intent(parentActivity(), VerificationOTPActivity.class);
        verification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        verification.putExtra("purpose", "regist");
        verification.putExtra(PHONE, bundle.getString(PHONE));
        verification.putExtra(PASS, bundle.getString(PASS));
        startActivity(verification);
        parentActivity().finish();
    }

    @Override
    public void successGetUserToken() {
        Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
        assert bundle != null;

        mPresenter.postBorrowerOTPRequest(bundle.getString(PHONE));
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
        assert bundle != null;

        JsonObject userProfleRequest = new JsonObject();

        userProfleRequest.addProperty("birthday", bundle.getString(REGIST_BIRTHDATE));
        userProfleRequest.addProperty("related_phonenumber", etRelatedHP.getText().toString());
        userProfleRequest.addProperty("taxid_number", bundle.getString(NPWP_NO));
        userProfleRequest.addProperty("idcard_number", bundle.getString(KTP_NO));
        userProfleRequest.addProperty("occupation", bundle.getString(JOB_TITLE));
        userProfleRequest.addProperty("gender", bundle.getString(GENDER));
        userProfleRequest.addProperty("city", bundle.getString(CITY));
        userProfleRequest.addProperty("mother_name", bundle.getString(MOTHER_NAME));
        userProfleRequest.addProperty("direct_superiorname", bundle.getString(SUPERVISOR));
        userProfleRequest.addProperty("bank_accountnumber", bundle.getString(ACC_NUMBER));
        userProfleRequest.addProperty("employer_name", bundle.getString(COMPANY_NAME));
        userProfleRequest.addProperty("hamlets", bundle.getString(REGIST_RW)); //RW
        userProfleRequest.addProperty("related_homenumber", etRelatedPhone.getText().toString());
        userProfleRequest.addProperty("neighbour_association", bundle.getString(REGIST_RT));
        userProfleRequest.addProperty("spouse_name", bundle.getString(SPOUSE_NAME));
//        userProfleRequest.setBank(1); //bank name
        userProfleRequest.addProperty("password", bundle.getString(PASS));
        userProfleRequest.addProperty("field_of_work", bundle.getString(OCCUPATION)); //jenis pekerjaan
        userProfleRequest.addProperty("province", bundle.getString(PROVINCE));
        userProfleRequest.addProperty("spouse_birthday", bundle.getString(SPOUSE_BIRTHDATE));
        userProfleRequest.addProperty("department", bundle.getString(OCCUPATION));  //jenis pekerjaan
        userProfleRequest.addProperty("email", bundle.getString(EMAIL));
        if (bundle.getString(HOME_STAY_YEAR) != null && !bundle.getString(HOME_STAY_YEAR).equals("")) {
            userProfleRequest.addProperty("lived_for", Integer.parseInt(Objects.requireNonNull(bundle.getString(HOME_STAY_YEAR))));
        }
        userProfleRequest.addProperty("address", bundle.getString(ADDRESS));
        userProfleRequest.addProperty("spouse_lasteducation", bundle.getString(SPOUSE_EDUCATION));
        if (bundle.getString(OTHER_INCOME) != null && bundle.getString(OTHER_INCOME) != "") {
            userProfleRequest.addProperty("other_income", Integer.parseInt(Objects.requireNonNull(bundle.getString(OTHER_INCOME))));
        }
        userProfleRequest.addProperty("home_phonenumber", bundle.getString(REGIST_PHONE));
        if (bundle.getString(SALARY) != null && !bundle.getString(SALARY).equals("")) {
            userProfleRequest.addProperty("monthly_income", Integer.parseInt(Objects.requireNonNull(bundle.getString(SALARY))));
        }
        userProfleRequest.addProperty("home_ownership", bundle.getString(HOME_STATUS));
        userProfleRequest.addProperty("last_education", bundle.getString(REGIST_EDUCATION));
        userProfleRequest.addProperty("marriage_status", bundle.getString(MARITAL_STATUS));
        userProfleRequest.addProperty("related_personname", etRelatedName.getText().toString());
        userProfleRequest.addProperty("related_relation", spHubungan.getSelectedItem().toString());
        userProfleRequest.addProperty("employer_address", bundle.getString(COMPANY_ADDRESS));
        userProfleRequest.addProperty("birthplace", bundle.getString(REGIST_BIRTHPLACE));
        if (bundle.getString(WORK_PERIOD) != null && !bundle.getString(WORK_PERIOD).equals("")) {
            userProfleRequest.addProperty("been_workingfor", Integer.parseInt(Objects.requireNonNull(bundle.getString(WORK_PERIOD))));
        }
        userProfleRequest.addProperty("phone", bundle.getString(PHONE));
        if (bundle.getString(DEPENDANTS) != null && !bundle.getString(DEPENDANTS).equals("")) {
            userProfleRequest.addProperty("dependants", Integer.parseInt(Objects.requireNonNull(bundle.getString(DEPENDANTS))));
        }
        userProfleRequest.addProperty("subdistrict", bundle.getString(DISTRICT));
        userProfleRequest.addProperty("employee_id", bundle.getString(EMPLOYEE_ID));
        userProfleRequest.addProperty("other_incomesource", bundle.getString(OTHER_INCOME_SOURCE));
        userProfleRequest.addProperty("urban_village", bundle.getString(SUB_DISTRICT));
        userProfleRequest.addProperty("fullname", bundle.getString(REGIST_NAME));
        userProfleRequest.addProperty("employer_number", bundle.getString(COMPANY_PHONE));
        userProfleRequest.addProperty("related_address", etRelatedAddress.getText().toString());
        userProfleRequest.addProperty("idcard_image", bundle.getString(PHOTO_KTP));
        userProfleRequest.addProperty("taxid_image", bundle.getString(PHOTO_NPWP));

        //new
        userProfleRequest.addProperty("bank", bundle.getInt(BANK_ID));
        userProfleRequest.addProperty("nickname", bundle.getString(NICKNAME));
        userProfleRequest.addProperty("nationality", "Indonesia");

        dialog.show();
        mPresenter.postRegisterBorrower(userProfleRequest);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                view.requestFocus();
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
