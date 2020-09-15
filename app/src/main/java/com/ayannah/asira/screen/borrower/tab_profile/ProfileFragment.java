package com.ayannah.asira.screen.borrower.tab_profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.dialog.BottomInputPassword;
import com.ayannah.asira.screen.bantuan.BantuanActivity;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    private Bitmap imageBitmap;

    @Inject
    ProfileContract.Presenter mPresenter;

    @BindView(R.id.photoUser)
    CircleImageView photoUser;

    @BindView(R.id.userName)
    TextView name;

    @BindView(R.id.phoneUser)
    TextView phoneUser;

    @Inject
    public ProfileFragment() {
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_profile_new;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

    }

    @Override
    protected void initView(Bundle state) {
        mPresenter.takeView(this);

        mPresenter.getUserIdentity();
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void userIdentity(PreferenceRepository user) {

//        ImageUtils.displayImageFromUrlWithErrorDrawable(parentActivity(), photoUser, "", null);

        name.setText(user.getUserName().toUpperCase());

        phoneUser.setText(CommonUtils.formatPhoneString(user.getUserPhone()));

    }

    @OnClick(R.id.btnLogout)
    void onClickLogout() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        mPresenter.doLogout();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.cancel();

                        break;
                }
            }
        };

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Apakah Anda Yakin Keluar?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();
    }

    @OnClick(R.id.photoUser)
    void takePhoto() {
        if (ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            needPermission(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(parentActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String pict = Base64.encodeToString(byteArray, Base64.NO_WRAP);
//            mPresenter.patchAgentPhotoProfile(pict);
            photoUser.setImageBitmap(imageBitmap);
        } else {
            Toast.makeText(parentActivity(), "request code not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void logoutComplete() {

        Intent logout = new Intent(parentActivity(), LoginActivity.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        logout.putExtra("hasTop", "false");
        startActivity(logout);
        parentActivity().finish();

    }

    @Override
    public void successSentRequest(String message) {
        Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
        mPresenter.doLogout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @OnClick(R.id.profile)
    void profileClicked() {
        Toast.makeText(parentActivity(), "profile", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.rewards)
    void rewardsClicked() {
        Toast.makeText(parentActivity(), "rewards", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.help)
    void helpClicked() {
//        Toast.makeText(parentActivity(), "help", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(parentActivity(), BantuanActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tnc)
    void tncClicked() {
//        Toast.makeText(parentActivity(), "tnc", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse("https://ayannah.co.id/#termConditions");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.contactUS)
    void contactUSClicked() {
//        Toast.makeText(parentActivity(), "contactUS", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(parentActivity());
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_contactus, null);
        builder1.setView(customLayout);
//        builder1.setCancelable(true);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @OnClick(R.id.btnDeleteAcc)
    void requestDeleteClick() {
        BottomInputPassword bottomInputPassword = new BottomInputPassword();
        bottomInputPassword.showNow(parentActivity().getSupportFragmentManager(), "BottomDialogShow");
        bottomInputPassword.setOnClickButtonListener(new BottomInputPassword.DialogInputPassListener() {
            @Override
            public void onClickYes(String pass) {
                if (!pass.equals("")) {
                    JsonObject request = new JsonObject();
                    request.addProperty("password", pass);

                    mPresenter.requestDeleteAccount(request);
                } else {
                    Toast.makeText(parentActivity(), "Masukan password Anda terlebih dahulu", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onClickNo() {
                bottomInputPassword.dismiss();
            }
        });
    }

}
