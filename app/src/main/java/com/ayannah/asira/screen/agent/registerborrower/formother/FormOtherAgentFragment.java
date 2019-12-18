package com.ayannah.asira.screen.agent.registerborrower.formother;

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

public class FormOtherAgentFragment extends BaseFragment implements FormOtherAgentContract.View, Validator.ValidationListener {

    @Inject
    FormOtherAgentContract.Presenter mPresenter;

    public static final String BANK_NAME = "BANK_NAME";
    public static final String BANK_ID = "BANK_ID";

    public static final String ACC_NUMBER = "ACC_NUMBER";

    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
//    public static final String PASS = "PASS";
//    public static final String CONF_PASS = "CONF_PASS";

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
    public FormOtherAgentFragment(){
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
    public  void registerComplete(String id_borrower, String phone_agent) {

        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), VerificationOTPActivity.class);
        intent.putExtra(VerificationOTPActivity.PURPOSES, "REGISTER_BORROWER");
        intent.putExtra("id_borrower", id_borrower);
        startActivity(intent);

    }


    @Override
    public void onValidationSucceeded() {
        Bundle bundle = Objects.requireNonNull(parentActivity()).getIntent().getExtras();
        assert bundle != null;

        JsonObject userProfleRequestAgent = new JsonObject();

        userProfleRequestAgent.addProperty("birthday", bundle.getString(REGIST_BIRTHDATE));
        userProfleRequestAgent.addProperty("related_phonenumber", etRelatedHP.getText().toString());
        userProfleRequestAgent.addProperty("taxid_number", bundle.getString(NPWP_NO));
        userProfleRequestAgent.addProperty("idcard_number", bundle.getString(KTP_NO));
        userProfleRequestAgent.addProperty("occupation", bundle.getString(OCCUPATION));
        userProfleRequestAgent.addProperty("gender", bundle.getString(GENDER));
        userProfleRequestAgent.addProperty("city", bundle.getString(CITY));
        userProfleRequestAgent.addProperty("mother_name", bundle.getString(MOTHER_NAME));
        userProfleRequestAgent.addProperty("direct_superiorname", bundle.getString(SUPERVISOR));
        userProfleRequestAgent.addProperty("bank_accountnumber", bundle.getString(ACC_NUMBER));
        userProfleRequestAgent.addProperty("employer_name", bundle.getString(COMPANY_NAME));
        userProfleRequestAgent.addProperty("hamlets", bundle.getString(REGIST_RW)); //RW
        userProfleRequestAgent.addProperty("related_homenumber", etRelatedPhone.getText().toString());
        userProfleRequestAgent.addProperty("neighbour_association", bundle.getString(REGIST_RT));
        userProfleRequestAgent.addProperty("spouse_name", bundle.getString(SPOUSE_NAME));
//        userProfleRequestAgent.setBank(1); //bank name
//        userProfleRequestAgent.addProperty("password", bundle.getString(PASS));
        userProfleRequestAgent.addProperty("field_of_work", bundle.getString(OCCUPATION)); //jenis pekerjaan
        userProfleRequestAgent.addProperty("province", bundle.getString(PROVINCE));
        userProfleRequestAgent.addProperty("spouse_birthday", bundle.getString(SPOUSE_BIRTHDATE));
        userProfleRequestAgent.addProperty("department", bundle.getString(JOB_TITLE));  //jenis pekerjaan
        userProfleRequestAgent.addProperty("email", bundle.getString(EMAIL));
        if (bundle.getString(HOME_STAY_YEAR) != null && !bundle.getString(HOME_STAY_YEAR).equals("")) {
            userProfleRequestAgent.addProperty("lived_for", Integer.parseInt(Objects.requireNonNull(bundle.getString(HOME_STAY_YEAR))));
        }
        userProfleRequestAgent.addProperty("address", bundle.getString(ADDRESS));
        userProfleRequestAgent.addProperty("spouse_lasteducation", bundle.getString(SPOUSE_EDUCATION));
        if (bundle.getString(OTHER_INCOME) != null && bundle.getString(OTHER_INCOME) != "") {
            userProfleRequestAgent.addProperty("other_income", Integer.parseInt(Objects.requireNonNull(bundle.getString(OTHER_INCOME))));
        }
        userProfleRequestAgent.addProperty("home_phonenumber", bundle.getString(REGIST_PHONE));
        if (bundle.getString(SALARY) != null && !bundle.getString(SALARY).equals("")) {
            userProfleRequestAgent.addProperty("monthly_income", Integer.parseInt(Objects.requireNonNull(bundle.getString(SALARY))));
        }
        userProfleRequestAgent.addProperty("home_ownership", bundle.getString(HOME_STATUS));
        userProfleRequestAgent.addProperty("last_education", bundle.getString(REGIST_EDUCATION));
        userProfleRequestAgent.addProperty("marriage_status", bundle.getString(MARITAL_STATUS));
        userProfleRequestAgent.addProperty("related_personname", etRelatedName.getText().toString());
        userProfleRequestAgent.addProperty("related_relation", spHubungan.getSelectedItem().toString());
        userProfleRequestAgent.addProperty("employer_address", bundle.getString(COMPANY_ADDRESS));
        userProfleRequestAgent.addProperty("birthplace", bundle.getString(REGIST_BIRTHPLACE));
        if (bundle.getString(WORK_PERIOD) != null && !bundle.getString(WORK_PERIOD).equals("")) {
            userProfleRequestAgent.addProperty("been_workingfor", Integer.parseInt(Objects.requireNonNull(bundle.getString(WORK_PERIOD))));
        }
        userProfleRequestAgent.addProperty("phone", bundle.getString(PHONE));
        if (bundle.getString(DEPENDANTS) != null && !bundle.getString(DEPENDANTS).equals("")) {
            userProfleRequestAgent.addProperty("dependants", Integer.parseInt(Objects.requireNonNull(bundle.getString(DEPENDANTS))));
        }
        userProfleRequestAgent.addProperty("subdistrict", bundle.getString(DISTRICT));
        userProfleRequestAgent.addProperty("employee_id", bundle.getString(EMPLOYEE_ID));
        userProfleRequestAgent.addProperty("other_incomesource", bundle.getString(OTHER_INCOME_SOURCE));
        userProfleRequestAgent.addProperty("urban_village", bundle.getString(SUB_DISTRICT));
        userProfleRequestAgent.addProperty("fullname", bundle.getString(REGIST_NAME));
        userProfleRequestAgent.addProperty("employer_number", bundle.getString(COMPANY_PHONE));
        userProfleRequestAgent.addProperty("related_address", etRelatedAddress.getText().toString());
        userProfleRequestAgent.addProperty("idcard_image", bundle.getString(PHOTO_KTP));
        userProfleRequestAgent.addProperty("taxid_image", bundle.getString(PHOTO_NPWP));

        //new
        userProfleRequestAgent.addProperty("bank", Integer.parseInt(bundle.getString(BANK_ID)));
        userProfleRequestAgent.addProperty("nickname", bundle.getString(NICKNAME));
        userProfleRequestAgent.addProperty("nationality", "Indonesia");

        dialog.show();
        mPresenter.postRegisterBorrower(userProfleRequestAgent);
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
