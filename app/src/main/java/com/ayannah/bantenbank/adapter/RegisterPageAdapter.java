package com.ayannah.bantenbank.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class RegisterPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> listPage;

    public RegisterPageAdapter(FragmentManager fm){
        super(fm);
    }

    public void setPage(List<Fragment> results){
        this.listPage = results;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 1:
                return listPage.get(1);
            case 2:
                return listPage.get(2);
            case 3:
                return listPage.get(3);
            case 4:
                return listPage.get(4);
            case 5:
                return listPage.get(5);
            case 6:
                return listPage.get(6);
            default:
                return listPage.get(0);
        }
    }

    @Override
    public int getCount() {
        return 7;
    }
}
