package com.ayannah.bantenbank.screen.register.adddoc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ayannah.bantenbank.R;
import com.ayannah.bantenbank.base.BaseFragment;
import com.ayannah.bantenbank.dialog.BottomSheetInstructionDialog;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.bantenbank.screen.register.formemailphone.FormEmailPhoneActivity;
import com.ayannah.bantenbank.screen.register.formemailphone.FormEmailPhoneFragment;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherFragment;
import com.ayannah.bantenbank.util.CameraTakeBeforeM;
import com.ayannah.bantenbank.util.CameraTakeM;
import com.ayannah.bantenbank.util.ImageUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class AddDocumentFragment extends BaseFragment implements AddDocumentContract.View, Validator.ValidationListener {

    @Inject
    AddDocumentContract.Presenter mPresenter;

    private Bitmap mBitmapKTP;

    @BindView(R.id.imgKTP)
    ImageView imgKtp;

    @BindView(R.id.imgNPWP)
    ImageView imgNpwp;

    @BindView(R.id.editKTP)
    ImageView editKtp;

    @BindView(R.id.editNPWP)
    ImageView editNpwp;

    @Length(min = 16, message = "Masukan 16 Karakter Nomor KTP")
    @BindView(R.id.etKTP)
    EditText etKTP;

    @BindView(R.id.etNPWP)
    EditText etNPWP;

    @NotEmpty(message = "Masukan Alamat Email Anda")
    @Email(message = "Format Email Salah")
    @BindView(R.id.regist_email)
    EditText email;

    @NotEmpty(message = "Masukan Nomor Handphone Anda")
    @BindView(R.id.regist_phone)
    EditText phone;

    @Password(min = 1, message = "Masukan Password")
    @BindView(R.id.regist_pass)
    EditText pass;

    @NotEmpty(message = "Masukan Konfirmasi Password")
    @ConfirmPassword(message = "Password Tidak Cocok")
    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    private Validator validator;

    File fileKtp, fileNpwp;
    private static final int KTP = 9;
    private static final int NPWP = 10;

    private BottomSheetInstructionDialog bottomDialog;

    @Inject
    public AddDocumentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_add_doc_regist;
    }

    @Override
    public void onResume() {
        super.onResume();

        //check permission to access camera and gallery photo
        needPermission(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

    }

    @Override
    protected void initView(Bundle state) {
        validator = new Validator(this);
        validator.setValidationListener(this);

        ///show dialog instruction
//        bottomDialog = new BottomSheetInstructionDialog().show(getActivity().getSupportFragmentManager(),
//                BottomSheetInstructionDialog.KTP_NPWP,
//                "Upload kartu identitas anda",
//                "Silahkan menambahkan foto kartu identitas pribadi anda seperti KTP dan NPWP (opsional) pribadi anda",
//                R.drawable.identity_card);
//        bottomDialog.setOnClickBottomSheetInstruction(new BottomSheetInstructionDialog.BottomSheetInstructionListener() {
//            @Override
//            public void onClickButtonDismiss() {
//
//                bottomDialog.dismiss();
//
//            }
//
//            @Override
//            public void onClickButtonYes() {
//                //Noo
//            }
//        });

    }

    @OnClick(R.id.imgKTP)
    void onClickKtp(){

//        showDialogPicker(KTP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(parentActivity(), CameraTakeM.class);
            startActivityForResult(intent, 2);
        } else {
            Intent intent = new Intent(parentActivity(), CameraTakeBeforeM.class);
            startActivityForResult(intent, 2);
        }

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
//        if(fileKtp == null){
//            Toast.makeText(getContext(), "Mohon tambahkan foto KTP anda", Toast.LENGTH_SHORT).show();
//        }else {
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container, fragment);
//            ft.addToBackStack(null);
//            ft.commit();
//        }

        //sdkjfhsjdhf
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, fragment);
//        ft.addToBackStack(null);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.commit();

        if (editNpwp.getVisibility() ==  View.VISIBLE && etNPWP.getText().toString().equals("")) {
            etNPWP.setError("Masukan Nomor NPWP");
        } else if (editKtp.getVisibility() == View.GONE) {
            Toast.makeText(parentActivity(), "Ambil Foto KTP Terlebih Dahulu", Toast.LENGTH_LONG).show();
        } else {
            validator.validate();
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

        if (requestCode == 2 && resultCode == -1) {

            try {
//                data = getIntent();
//                Bundle extras = data.getExtras();
                FileOutputStream file = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "DecodepicKTP.jpg");
//                    mBitmapKTP = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "picKTP.jpg");
                mBitmapKTP = decodedFile(Environment.getExternalStorageDirectory() + File.separator + "picKTP.jpg");
//                    mBitmapKTP = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + File.separator + "picKTP.jpg");
//                    Bitmap resized = Bitmap.createScaledBitmap(mBitmapKTP, 720, 1080, true);
//                    mBitmapKTP.compress(Bitmap.CompressFormat.JPEG, 30, file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                mBitmapKTP.compress(Bitmap.CompressFormat.JPEG, 30, out);
//                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                out.writeTo(file);

                byte[] bytes = out.toByteArray();
                String pictKTP64 = Base64.encodeToString(bytes, Base64.NO_WRAP); // result for base64

                imgKtp.setImageBitmap(mBitmapKTP);
                editKtp.setVisibility(View.VISIBLE);

//                UploadDocumentToServer(myappsDocument.getSimulationcode(), docTypeCode, decoded);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }

        } else {

            EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {

                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    super.onImagePickerError(e, source, type);
                    e.printStackTrace();
                    Log.e("AddCoumentRegister", "onImageError: " + e.getMessage());
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                    if (imageFile != null) {

                        switch (type) {
                            case KTP:
//                            fileKtp = imageFile;
                                fileKtp = ImageUtils.compressImageFile(parentActivity(), imageFile);
                                Bitmap bitmap = BitmapFactory.decodeFile(fileKtp.getAbsolutePath());
                                imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imgKtp.setImageBitmap(bitmap);
//                            imgKtp.setBackgroundResource(R.drawable.border_selected_image);
                                editKtp.setVisibility(View.VISIBLE);
                                break;

                            case NPWP:
//                            fileNpwp = imageFile;
                                fileNpwp = ImageUtils.compressImageFile(parentActivity(), imageFile);
                                Bitmap bitmapnpwp = BitmapFactory.decodeFile(fileNpwp.getAbsolutePath());
                                imgNpwp.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                            imgNpwp.setBackgroundResource(R.drawable.border_selected_image);
                                imgNpwp.setImageBitmap(bitmapnpwp);
                                editNpwp.setVisibility(View.VISIBLE);
                                break;

                        }

                    }

                }
            });

        }
    }

    @Override
    public void onValidationSucceeded() {
        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){
            bundle = new Bundle();
        }

        bundle.putString(FormOtherFragment.KTP_NO, etKTP.getText().toString());
        bundle.putString(FormOtherFragment.NPWP_NO, etNPWP.getText().toString());
        bundle.putString(FormOtherFragment.EMAIL, email.getText().toString());
        bundle.putString(FormOtherFragment.PHONE, phone.getText().toString());
        bundle.putString(FormOtherFragment.PASS, pass.getText().toString());
        bundle.putString(FormOtherFragment.CONF_PASS, passRetype.getText().toString());

        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
        form.putExtras(bundle);
        startActivity(form);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(parentActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public  Bitmap decodedFile(String path) {//you can provide file path here
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 0;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

//            Log.e("ExifInteface .........", "rotation ="+orientation);

//          exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

//            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
//              m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
//                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
//                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
//                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
//            else if (orientation == ExifInterface.ORIENTATION_NORMAL) {
//                m.postRotate(-90);
////                Log.e("in orientation", "" + orientation);
//                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
//                        bm.getHeight(), m, true);
//                return bitmap;
//            }
//            m.postRotate(-90);
////                Log.e("in orientation", "" + orientation);
//            bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
//                    bm.getHeight(), m, true);
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successCheckMandotryEntity(String message) {

    }
}
