package com.ayannah.asira.screen.detailangsuran;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.Angsuran;

import java.util.List;

public interface DetailAngsuranContract {

    interface View extends BaseView<Presenter>{

        void showAllPaging(List<Angsuran> results);

    }

    interface Presenter extends BasePresenter<View>{

        void dataProcessing();

    }
}
