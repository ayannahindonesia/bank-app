package com.ayannah.asira.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseFragment extends DaggerFragment implements EasyPermissions.PermissionCallbacks {

    protected static final int PERMISSION_REQUEST_CODE = 2000;

    private AppCompatActivity mActivity;

    private Unbinder mUnbinder;

    protected abstract int getLayoutView();

    protected abstract void initView(Bundle state);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = ((AppCompatActivity) context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutView(), container, false);
        mUnbinder = ButterKnife.bind(this, root);
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(mActivity, perms)) {

            new AppSettingsDialog.Builder(mActivity).build().show();

        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @AfterPermissionGranted(PERMISSION_REQUEST_CODE)
    public void needPermission(String[] permissions){

        if (!EasyPermissions.hasPermissions(mActivity, permissions)) {
            EasyPermissions.requestPermissions(mActivity, "Application need some perimission to access features", PERMISSION_REQUEST_CODE, permissions);
        }

    }

    public AppCompatActivity parentActivity(){
        return mActivity;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
