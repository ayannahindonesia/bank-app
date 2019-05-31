package com.ayannah.bantenbank.screen.register;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.EasyPermissions;

public class AddDocumentRegister extends Fragment {

    @BindView(R.id.imgKTP)
    ImageView imgKtp;

    @BindView(R.id.imgNPWP)
    ImageView imgNpwp;

    @BindView(R.id.editKTP)
    ImageView editKtp;

    @BindView(R.id.editNPWP)
    ImageView editNpwp;

    CameraManager mCameraManager;

    File fileKtp, fileNpwp;
    private static final int KTP = 9;
    private static final int NPWP = 10;

    private FormEmailPhone fragment = new FormEmailPhone();

    public AddDocumentRegister(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_doc_regist, container, false);

        needPermission(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        ButterKnife.bind(this, view);
        return view;
    }

    private void needPermission(String[] permissions) {
        if(!EasyPermissions.hasPermissions(getContext(), permissions)){
            EasyPermissions.requestPermissions(this, "Application need some permission to access features",
                    100, permissions);
        }
    }

    @OnClick(R.id.imgKTP)
    void onClickKtp(){

        showDialogPicker(KTP);

    }

    @OnClick(R.id.imgNPWP)
    void onClickNpwp(){

        showDialogPicker(NPWP);

    }

    @OnClick(R.id.editKTP)
    void onClickEdit(){

        showDialogPicker(KTP);
    }

    @OnClick(R.id.editNPWP)
    void onClickEditNpwp(){

        showDialogPicker(NPWP);
    }

    @OnClick(R.id.btnNext)
    void onClickNext(){

        if(fileKtp == null){
            Toast.makeText(getContext(), "Mohon tambahkan foto KTP anda", Toast.LENGTH_SHORT).show();
        }else {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void showDialogPicker(int type){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setItems(new String[]{"Take Camera", "From Gallery", "Cancel"}, ((dialog, which) -> {

                    switch (which){
                        case 0:

                            EasyImage.openCamera(this, type);

                            break;
                        case 1:

                            EasyImage.openGallery(this, type);

                            break;
                        case 2:

                            dialog.dismiss();

                            break;

                    }

                }));

        builder.create().show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                e.printStackTrace();
                Log.e("AddCoumentRegister", "onImageError: "+e.getMessage());
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                if(imageFile != null){

                    switch (type){
                        case KTP:

                            fileKtp = imageFile;
                            Bitmap bitmap = BitmapFactory.decodeFile(fileKtp.getAbsolutePath());
                            imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imgKtp.setImageBitmap(bitmap);
                            editKtp.setVisibility(View.VISIBLE);

                            break;

                        case NPWP:

                            fileNpwp = imageFile;
                            Bitmap bitmapnpwp = BitmapFactory.decodeFile(fileNpwp.getAbsolutePath());
                            imgNpwp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imgNpwp.setImageBitmap(bitmapnpwp);
                            editNpwp.setVisibility(View.VISIBLE);

                            break;

                    }

                }

            }
        });
    }
}
