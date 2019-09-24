package com.ayannah.asira.screen.register.adddoc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.util.CameraTakeBeforeM;
import com.ayannah.asira.util.CameraTakeM;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDocumentFragment extends BaseFragment implements AddDocumentContract.View, Validator.ValidationListener {

    @Inject
    AddDocumentContract.Presenter mPresenter;

    private Bitmap mBitmapKTP;
    private Bitmap mBitmapNPWP;

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

    @NotEmpty(message = "Masukan Alamat Email Anda", trim = true)
    @Email(message = "Format Email Salah")
    @BindView(R.id.regist_email)
    EditText email;

    @NotEmpty(message = "Masukan Nomor Handphone Anda, min 10 digit", trim = true)
    @Length(min = 10, message = "Minimal 10 digit, Maximal 14 digit", max = 14)
    @BindView(R.id.regist_phone)
    EditText phone;

    @Password(min = 1, message = "Masukan Password")
    @BindView(R.id.regist_pass)
    EditText pass;

    @NotEmpty(message = "Masukan Konfirmasi Password", trim = true)
    @ConfirmPassword(message = "Password Tidak Cocok")
    @BindView(R.id.regist_pass_retype)
    EditText passRetype;

    private Validator validator;

    private static final int KTP = 9;
    private static final int NPWP = 10;

    private BottomSheetDialogGlobal bottomDialog;
    private String pictKTP64, pictNPWP64;
    private AlertDialog dialog;

    @Inject
    public AddDocumentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_add_doc_regist;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.takeView(this);

        dialog.show();
        mPresenter.checkPublicToken();

        //check permission to access camera and gallery photo
        needPermission(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                "Upload kartu identitas anda",
                "Silahkan menambahkan foto kartu identitas pribadi anda seperti KTP dan NPWP (opsional) pribadi anda",
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(parentActivity(), CameraTakeM.class);
            intent.putExtra("state", "KTP");
            startActivityForResult(intent, KTP);
        } else {
            Intent intent = new Intent(parentActivity(), CameraTakeBeforeM.class);
            intent.putExtra("state", "KTP");
            startActivityForResult(intent, KTP);
        }

    }

    @OnClick(R.id.imgNPWP)
    void onClickNpwp(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(parentActivity(), CameraTakeM.class);
            intent.putExtra("state", "NPWP");
            startActivityForResult(intent, NPWP);
        } else {
            Intent intent = new Intent(parentActivity(), CameraTakeBeforeM.class);
            intent.putExtra("state", "NPWP");
            startActivityForResult(intent, NPWP);
        }

    }

    @OnClick(R.id.editKTP)
    void onClickEdit(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(parentActivity(), CameraTakeM.class);
            intent.putExtra("state", "KTP");
            startActivityForResult(intent, KTP);
        } else {
            Intent intent = new Intent(parentActivity(), CameraTakeBeforeM.class);
            intent.putExtra("state", "KTP");
            startActivityForResult(intent, KTP);
        }

    }

    @OnClick(R.id.editNPWP)
    void onClickEditNpwp(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(parentActivity(), CameraTakeM.class);
            intent.putExtra("state", "NPWP");
            startActivityForResult(intent, NPWP);
        } else {
            Intent intent = new Intent(parentActivity(), CameraTakeBeforeM.class);
            intent.putExtra("state", "NPWP");
            startActivityForResult(intent, NPWP);
        }

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
                        FileOutputStream file = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "DecodepicKTP.jpg");

                        mBitmapKTP = decodedFile(Environment.getExternalStorageDirectory() + File.separator + "picTemp.jpg");

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        mBitmapKTP.compress(Bitmap.CompressFormat.JPEG, 70, out);

                        out.writeTo(file);

                        byte[] bytes = out.toByteArray();

                        pictKTP64 = Base64.encodeToString(bytes, Base64.NO_WRAP); // result for base64

//----------------------------------------------------------------------------------------------------
//                        Matrix matrix = new Matrix();
//                        matrix.setRotate(90, 0, 0);
//                        matrix.postTranslate(mBitmapKTP.getHeight(), 0);
//                        Bitmap newBItmap1 = Bitmap.createBitmap(mBitmapKTP, 0,0,mBitmapKTP.getWidth(), mBitmapKTP.getHeight());
//
//                        Canvas tmpCanvas = new Canvas(newBItmap1);
//                        tmpCanvas.drawBitmap(mBitmapKTP, matrix, null);
//                        tmpCanvas.setBitmap(null);
//----------------------------------------------------------------------------------------------------

                        int imgWidth = mBitmapKTP.getWidth();
                        int imgHeight = mBitmapKTP.getHeight();

                        int coorX = (imgWidth * 5) / 100;
                        int coorY = (imgHeight * 33) /100;

                        Bitmap newBItmap = Bitmap.createBitmap(mBitmapKTP, coorX,coorY,mBitmapKTP.getWidth()-coorX, coorY);

                        imgKtp.setImageBitmap(newBItmap);
                        imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        editKtp.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                    break;

                case NPWP:

                    try {

                        FileOutputStream file = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "DecodepicNPWP.jpg");

                        mBitmapNPWP = decodedFile(Environment.getExternalStorageDirectory() + File.separator + "picTemp.jpg");

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        mBitmapNPWP.compress(Bitmap.CompressFormat.JPEG, 70, out);

                        out.writeTo(file);

                        byte[] bytes = out.toByteArray();
                        pictNPWP64 = Base64.encodeToString(bytes, Base64.NO_WRAP); // result for base64

                        int imgWidth = mBitmapKTP.getWidth();
                        int imgHeight = mBitmapKTP.getHeight();

                        int coorX = (imgWidth * 5) / 100;
                        int coorY = (imgHeight * 33) /100;

                        Bitmap newBitmap = Bitmap.createBitmap(mBitmapNPWP, coorX,coorY,mBitmapNPWP.getWidth()-coorX, coorY);

                        imgNpwp.setImageBitmap(newBitmap);
                        imgNpwp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        editNpwp.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
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

        dialog.show();
        mPresenter.checkMandatoryItem(etKTP.getText().toString(), phone.getText().toString(), email.getText().toString(), etNPWP.getText().toString());
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
                    case R.id.regist_pass:
                        pass.requestFocus();
                        pass.setSelection(0);

                        break;

                }
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

        dialogDismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successCheckMandotryEntity(String message) {

        dialogDismiss();

        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){

            bundle = new Bundle();
        }

        bundle.putString(FormOtherFragment.PHOTO_KTP, pictKTP64);
        bundle.putString(FormOtherFragment.PHOTO_NPWP, pictNPWP64);
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
    public void dialogDismiss() {
        dialog.dismiss();
    }

    public static Bitmap setImage(String filePath, Bitmap bitmap){
        File curFile = new File(filePath);
        Bitmap rotatedBitmap = null;

        try {
            ExifInterface exif = new ExifInterface(curFile.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0.0f) {matrix.preRotate(rotationInDegrees);}
            rotatedBitmap = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


        }catch(IOException ex){
            ex.printStackTrace();
        }
        return rotatedBitmap;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 0; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }
}
