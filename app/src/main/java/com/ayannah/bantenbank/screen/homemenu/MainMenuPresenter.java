package com.ayannah.bantenbank.screen.homemenu;

import android.app.Application;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.BeritaPromo;
import com.ayannah.bantenbank.data.model.MenuProduct;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainMenuPresenter implements MainMenuContract.Presenter {

    private Application application;
    private MainMenuContract.View mView;
    private PreferenceRepository prefRepo;


    @Inject
    MainMenuPresenter(Application application, PreferenceRepository prefRepo){
        this.application = application;
        this.prefRepo = prefRepo;
    }

    @Override
    public void takeView(MainMenuContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getMainMenu() {

        List<MenuProduct> menus = new ArrayList<>();

        MenuProduct pinjamanPns = new MenuProduct();
        pinjamanPns.setName("Pinjaman PNS");
        pinjamanPns.setLogoProduct(R.drawable.ic_menu_pns);

        MenuProduct pinjamanPersonal = new MenuProduct();
        pinjamanPersonal.setName("Pinjaman\nPersonal");
        pinjamanPersonal.setLogoProduct(R.drawable.ic_menu_personal);

        MenuProduct pinjamanPensiunan = new MenuProduct();
        pinjamanPensiunan.setName("Pinjaman\nPensiunan");
        pinjamanPensiunan.setLogoProduct(R.drawable.ic_menu_pensiunan);

        MenuProduct pinjamanUmkm = new MenuProduct();
        pinjamanUmkm.setName("Pinjaman\nUMKM");
        pinjamanUmkm.setLogoProduct(R.drawable.ic_menu_umkm);

        MenuProduct pinjamanMicro = new MenuProduct();
        pinjamanMicro.setName("Pinjaman\nMikro");
        pinjamanMicro.setLogoProduct(R.drawable.ic_menu_micro);

        MenuProduct lainlain = new MenuProduct();
        lainlain.setName("Lain-lain");
        lainlain.setLogoProduct(R.drawable.loan);

        menus.add(pinjamanPns);
        menus.add(pinjamanPersonal);
        menus.add(pinjamanPensiunan);
        menus.add(pinjamanUmkm);
        menus.add(pinjamanMicro);
        menus.add(lainlain);

        mView.showMainMenu(menus);

    }

    @Override
    public void loadPromoAndNews() {

        List<BeritaPromo> listBeritaPromo = new ArrayList<>();

        BeritaPromo data = new BeritaPromo();
        data.setImg(R.drawable.breaking_news);
        data.setTitle("Berita 1");
        data.setDescription(application.getResources().getString(R.string.desc_example));
        listBeritaPromo.add(data);

        BeritaPromo data2 = new BeritaPromo();
        data2.setImg(R.drawable.promo_banner);
        data2.setDescription(application.getResources().getString(R.string.desc_example));
        data2.setTitle("Promo 2");
        listBeritaPromo.add(data2);

        mView.showPromoAndNews(listBeritaPromo);
    }

    @Override
    public void getCurrentUserIdentity() {

        mView.displayUserIdentity(prefRepo.getUserName(), prefRepo.getUserEmail());
    }

    @Override
    public void logout() {

        prefRepo.clearAll();

        mView.showLogoutComplete();

    }
}
