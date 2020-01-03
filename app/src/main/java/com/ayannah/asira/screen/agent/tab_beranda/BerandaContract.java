package com.ayannah.asira.screen.agent.tab_beranda;

import com.ayannah.asira.base.BasePresenter;
import com.ayannah.asira.base.BaseView;
import com.ayannah.asira.data.model.MenuAgent;

import java.util.List;

public interface BerandaContract {

    interface View extends BaseView<Presenter>{

        void showMenus(List<MenuAgent> results);

    }

    interface Presenter extends BasePresenter<View>{

        void fetchMenus();

    }
}
