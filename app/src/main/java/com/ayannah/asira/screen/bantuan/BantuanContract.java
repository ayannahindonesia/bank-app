package com.ayannah.asira.screen.bantuan;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Question;

import java.util.List;

public interface BantuanContract {

    interface View extends BaseView<Presenter>{

        void showErrorMessage(String message, int code);

        void showAllResult(List<Question.Data> results);

    }

    interface Presenter extends BasePresenter<View>{

        void retrieveFaq(String query);

    }

}
