package com.ayannah.asira.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,  int frame){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frame, fragment);
        fragmentTransaction.commit();
    }

    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frame){
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(frame, fragment);
        ft.commit();
    }

    public static void moveFragment(FragmentManager fragmentManager, Fragment fragment, int frame){

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

}
