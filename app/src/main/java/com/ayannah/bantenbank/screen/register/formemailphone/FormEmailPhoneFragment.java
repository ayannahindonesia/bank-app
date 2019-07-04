package com.ayannah.bantenbank.screen.register.formemailphone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FormEmailPhoneFragment extends BaseFragment {

    @BindView(R.id.regist_email)
    EditText email;

    @BindView(R.id.regist_phone)
    EditText phone;

    @BindView(R.id.regist_pass)
    EditText pass;

    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    @Inject
    public FormEmailPhoneFragment(){}


    @Override
    protected int getLayoutView() {
        return R.layout.fragment_email_phone;
    }

    @Override
    protected void initView(Bundle state) {

    }

    @OnClick(R.id.buttonNext)
    void onClickNext(){
        Bundle bundle = parentActivity().getIntent().getExtras();
        assert bundle != null;
        bundle.putString(FormOtherFragment.EMAIL, email.getText().toString());
        bundle.putString(FormOtherFragment.PHONE, phone.getText().toString());
        bundle.putString(FormOtherFragment.PASS, pass.getText().toString());
        bundle.putString(FormOtherFragment.CONF_PASS, passRetype.getText().toString());

        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
        form.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        form.putExtras(bundle);
        startActivity(form);
    }
}
