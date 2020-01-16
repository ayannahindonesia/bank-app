package com.ayannah.asira.screen.agent.tab_beranda;

import androidx.annotation.Nullable;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.MenuAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BerandaPresenter implements BerandaContract.Presenter {

    @Nullable
    private BerandaContract.View mView;

    @Inject
    BerandaPresenter(){

    }

    @Override
    public void takeView(BerandaContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void fetchMenus() {

        if(mView == null){
            return;
        }

        List<MenuAgent> menus = new ArrayList<>();

        MenuAgent first = new MenuAgent();
        first.setId(1);
        first.setName("Pendaftaran Nasabah Baru");
        first.setImg(R.drawable.group_244);
        menus.add(first);

        MenuAgent second = new MenuAgent();
        second.setId(2);
        second.setName(("Nasabah Terdaftar"));
        second.setImg(R.drawable.group_243);
        menus.add(second);

        mView.showMenus(menus);
    }
}
