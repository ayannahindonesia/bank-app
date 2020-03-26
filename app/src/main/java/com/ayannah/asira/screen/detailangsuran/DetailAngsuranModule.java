package com.ayannah.asira.screen.detailangsuran;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailAngsuranModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailAngsuranFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract DetailAngsuranContract.Presenter reqPresenter(DetailAngsuranPresenter presenter);

    @Provides
    @ActivityScoped
    @Named("paging")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_PAGING);
    }

    @Provides
    @ActivityScoped
    @Named("angsuran")
    static CommonListAdapter adapterAngsuran(){
        return new CommonListAdapter(CommonListAdapter.VIEW_DETIL_ANGSURAN);
    }

    @Provides
    @ActivityScoped
    static ArrayList<InstallmentDetails> installments(DetailAngsuranActivity activity){
        return activity.getIntent().getParcelableArrayListExtra("installments");
    }
}
