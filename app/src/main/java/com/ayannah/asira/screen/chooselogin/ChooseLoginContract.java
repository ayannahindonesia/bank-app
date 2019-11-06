package com.ayannah.asira.screen.chooselogin;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.screen.createnewpassword.CreateNewPassContract;

public interface ChooseLoginContract {

    interface View extends BaseView<CreateNewPassContract.Presenter> {

    }

    interface Presenter extends BasePresenter<ChooseLoginContract.View> {

    }
}
