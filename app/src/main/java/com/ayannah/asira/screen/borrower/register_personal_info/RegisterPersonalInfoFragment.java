package com.ayannah.asira.screen.borrower.register_personal_info;

import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import javax.inject.Inject;

public class RegisterPersonalInfoFragment extends BaseFragment implements RegisterPersonalInfoContract.View, Validator.ValidationListener {

    private Validator validator;

    @Inject
    RegisterPersonalInfoContract.Presenter mPresenter;

    @Inject
    public RegisterPersonalInfoFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_personal_info;
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
