package com.ayannah.asira.screen.agent.registerborrower.adddoc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.dialog.BottomDialogHandlingError;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.agent.registerborrower.formborrower.FormBorrowerAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.formother.FormOtherAgentFragment;
import com.ayannah.asira.util.CameraTakeBeforeM;
import com.ayannah.asira.util.CameraTakeM;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddDocumentAgentFragment extends BaseFragment implements AddDocumentAgentContract.View, Validator.ValidationListener {

    @Inject
    AddDocumentAgentContract.Presenter mPresenter;

    @BindView(R.id.imgKTP)
    ImageView imgKtp;

    @BindView(R.id.imgNPWP)
    ImageView imgNpwp;

    @BindView(R.id.editKTP)
    ImageView editKtp;

    @BindView(R.id.editNPWP)
    ImageView editNpwp;

    @Length(min = 16, message = "Masukan 16 Karakter Nomor KTP", trim = true)
    @BindView(R.id.etKTP)
    EditText etKTP;

    @BindView(R.id.etNPWP)
    EditText etNPWP;

    @BindView(R.id.regist_email)
    EditText email;

    @BindView(R.id.regist_phone)
    EditText phone;

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;

    private Validator validator;

    private static final int PP = 8;
    private static final int KTP = 9;
    private static final int NPWP = 10;

    private BottomSheetDialogGlobal bottomDialog;
    private String pictKTP64, pictNPWP64, pictPP64;
    private AlertDialog dialog;
    private String currentPhotoPath;
    private Uri photoURI;
    private File photoFile;

    @Inject
    public AddDocumentAgentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_add_doc_regist;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.takeView(this);

        dialog.show();
        mPresenter.checkPublicToken();

        needPermission(new String[]{
                Manifest.permission.CAMERA
        });

    }

    @Override
    protected void initView(Bundle state) {

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        validator = new Validator(this);
        validator.setValidationListener(this);

        ///show dialog instruction
        bottomDialog = new BottomSheetDialogGlobal().show(parentActivity().getSupportFragmentManager(),
                BottomSheetDialogGlobal.KTP_NPWP,
                "Upload kartu identitas nasabah baru",
                "Silahkan menambahkan foto kartu identitas pribadi nasabah baru seperti KTP dan NPWP (opsional) pribadi nasabah baru",
                R.drawable.identity_card);
        bottomDialog.setOnClickBottomSheetInstruction(new BottomSheetDialogGlobal.BottomSheetInstructionListener() {
            @Override
            public void onClickButtonDismiss() {

                bottomDialog.dismiss();

            }

            @Override
            public void onClickButtonYes() {
                //dont do anything in here
            }

            @Override
            public void closeApps() {
                //dont do anything in here
            }
        });

    }

    @OnClick(R.id.imgKTP)
    void onClickKtp(){
        openCamera(KTP);
    }

    @OnClick(R.id.imgNPWP)
    void onClickNpwp(){
        openCamera(NPWP);
    }

    @OnClick(R.id.editKTP)
    void onClickEdit(){
        openCamera(KTP);
    }

    @OnClick(R.id.editNPWP)
    void onClickEditNpwp(){
        openCamera(NPWP);
    }

    @OnClick(R.id.btnNext)
    void onClickNext(){


        if (editNpwp.getVisibility() ==  View.VISIBLE && etNPWP.getText().toString().equals("")) {

            etNPWP.setError("Masukan Nomor NPWP");

        } else if (editKtp.getVisibility() == View.GONE) {

            Toast.makeText(parentActivity(), "Ambil Foto KTP Terlebih Dahulu", Toast.LENGTH_LONG).show();

        } else {

            validator.validate();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            switch (requestCode){

                case KTP:

                    try {
                        pictKTP64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgKtp);
                        imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        editKtp.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        Log.d("Error KTP", e.getMessage());
                    }

//                    try {
//
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                        byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//                        pictKTP64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);
//
//                        imgKtp.setImageBitmap(imageBitmap);
//                        imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        editKtp.setVisibility(View.VISIBLE);
//
//                    } catch (Exception e) {
//                        Log.d("Error", e.getMessage());
//                    }

//                    try {
//                        FileOutputStream file = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "DecodepicKTP.jpg");
//
//                        mBitmapKTP = decodedFile(Environment.getExternalStorageDirectory() + File.separator + "picTemp.jpg");
//
//                        ByteArrayOutputStream out = new ByteArrayOutputStream();
//                        mBitmapKTP.compress(Bitmap.CompressFormat.JPEG, 75, out);
//
//                        out.writeTo(file);
//
//                        byte[] bytes = out.toByteArray();
//
//                        pictKTP64 = Base64.encodeToString(bytes, Base64.NO_WRAP); // result for base64
//
////----------------------------------------------------------------------------------------------------
////                        Matrix matrix = new Matrix();
////                        matrix.setRotate(90, 0, 0);
////                        matrix.postTranslate(mBitmapKTP.getHeight(), 0);
////                        Bitmap newBItmap1 = Bitmap.createBitmap(mBitmapKTP, 0,0,mBitmapKTP.getWidth(), mBitmapKTP.getHeight());
////
////                        Canvas tmpCanvas = new Canvas(newBItmap1);
////                        tmpCanvas.drawBitmap(mBitmapKTP, matrix, null);
////                        tmpCanvas.setBitmap(null);
////----------------------------------------------------------------------------------------------------
//
//                        int imgWidth = mBitmapKTP.getWidth();
//                        int imgHeight = mBitmapKTP.getHeight();
//
//                        int coorX = (imgWidth * 5) / 100;
//                        int coorY = (imgHeight * 33) /100;
//
//                        Bitmap newBItmap = Bitmap.createBitmap(mBitmapKTP, coorX,coorY,mBitmapKTP.getWidth()-coorX, coorY);
//
//                        imgKtp.setImageBitmap(newBItmap);
//                        imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        editKtp.setVisibility(View.VISIBLE);
//
//                    } catch (Exception e) {
//                        Log.d("Error", e.getMessage());
//                    }
                    break;

                case NPWP:

                    try {
                        pictNPWP64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgNpwp);
                        imgNpwp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        editNpwp.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        Log.d("Error KTP", e.getMessage());
                    }
                    break;

                case PP:

                    try {
                        pictPP64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgProfile);
                        imgProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    } catch (Exception e) {
                        Log.d("Error KTP", e.getMessage());
                    }
                    break;
            }

        }

    }

    @Override
    public void onValidationSucceeded() {

//        Bundle bundle = parentActivity().getIntent().getExtras();
//
//        if(bundle == null){
//            bundle = new Bundle();
//        }
//
//        bundle.putString(FormOtherFragment.KTP_NO, etKTP.getText().toString());
//        bundle.putString(FormOtherFragment.NPWP_NO, etNPWP.getText().toString());
//        bundle.putString(FormOtherFragment.EMAIL, email.getText().toString());
//        bundle.putString(FormOtherFragment.PHONE, phone.getText().toString());
//        bundle.putString(FormOtherFragment.PASS, pass.getText().toString());
//        bundle.putString(FormOtherFragment.CONF_PASS, passRetype.getText().toString());
//
//        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
//        form.putExtras(bundle);
//        startActivity(form);

        if (email.getText().toString().equals("") || isValidEmail(email.getText().toString().trim())) {
            String pnumber = "";
            if (!phone.getText().toString().equals("")) {
                if (phone.getText().toString().trim().substring(0, 1).equals("0")) {
                    pnumber = "62" + phone.getText().toString().trim().substring(1);
                } else if (phone.getText().toString().trim().substring(0, 2).equals("62")) {
                    pnumber = phone.getText().toString().trim();
                } else {
                    pnumber = "62" + phone.getText().toString().trim();
                }
            }

            dialog.show();
            mPresenter.checkExistingBorrowerAgent(etKTP.getText().toString(), pnumber, email.getText().toString(), etNPWP.getText().toString());
        } else {
            email.setError("Format email salah");
        }

    }

    @OnTextChanged(value = R.id.regist_email, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void textEmail(Editable editable) {
        if (!editable.toString().equals("")) {
            email.setError(null);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);

                switch (view.getId()){
                    case R.id.etKTP:
                        etKTP.requestFocus();
                        etKTP.setSelection(0);

                        break;
                    case R.id.regist_email:
                        email.requestFocus();
                        email.setSelection(0);

                        break;
                    case R.id.regist_phone:
                        phone.requestFocus();
                        phone.setSelection(0);

                        break;

                }
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void showErrorMessage(String message, int code) {

        dialogDismiss();

        BottomDialogHandlingError error = new BottomDialogHandlingError(message, code);
        error.showNow(parentActivity().getSupportFragmentManager(), "error message");
        error.setOnClickLister(error::dismiss);

    }

    @Override
    public void successCheckMandotryEntity(String pnumber) {

        dialogDismiss();

        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){

            bundle = new Bundle();
        }

        Log.e("pnumber", pnumber);

//        String pnumber = "62"+phone.getText().toString().trim();

        bundle.putString(FormOtherAgentFragment.PHOTO_KTP, pictKTP64);
        bundle.putString(FormOtherAgentFragment.PHOTO_NPWP, pictNPWP64);
        bundle.putString(FormOtherAgentFragment.PHOTO_PP, pictPP64);
        bundle.putString(FormOtherAgentFragment.KTP_NO, etKTP.getText().toString());
        bundle.putString(FormOtherAgentFragment.NPWP_NO, etNPWP.getText().toString());
        bundle.putString(FormOtherAgentFragment.EMAIL, email.getText().toString());
        bundle.putString(FormOtherAgentFragment.PHONE, pnumber);

        Intent form = new Intent(parentActivity(), FormBorrowerAgentActivity.class);
        form.putExtras(bundle);
        startActivity(form);

    }

    @Override
    public void dialogDismiss() {
        dialog.dismiss();
    }

    @OnClick(R.id.rltImageProfile)
    void cameraTake() {

        openCamera(PP);
    }

    @OnTextChanged(value = R.id.regist_phone, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void et_onTextChange_registPhone(Editable editable) {
        if (editable.toString().equals("0")) {
            Toast.makeText(getContext(), "Masukan tanpa diawali angka 0", Toast.LENGTH_LONG).show();
            phone.setText("");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = parentActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = photoFile.getAbsolutePath();
        return photoFile;
    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,60,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
        //Base64.de
        return encImage;

    }

    private void openCamera(int type) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(parentActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("createImage", ex.getMessage());
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(parentActivity(),
                        "com.ayannah.asira.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, type);
            }
        }
    }

}
