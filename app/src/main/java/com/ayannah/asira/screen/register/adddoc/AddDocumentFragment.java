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
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Bank;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.dialog.BottomSheetDialogGlobal;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.util.CameraTakeBeforeM;
import com.ayannah.asira.util.CameraTakeM;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddDocumentFragment extends BaseFragment implements AddDocumentContract.View, Validator.ValidationListener {

    @Inject
    AddDocumentContract.Presenter mPresenter;

    private Bitmap mBitmapKTP;
    private Bitmap mBitmapNPWP;
    private ArrayAdapter<String> mAdapterProvince;

    @BindView(R.id.imgKTP)
    ImageView imgKtp;

    @BindView(R.id.imgNPWP)
    ImageView imgNpwp;

    @BindView(R.id.txtTitleKTP)
    TextView txtTitleKTP;

    @BindView(R.id.textTitleNPWP)
    TextView textTitleNPWP;

    @Length(min = 16, message = "Masukan 16 Karakter Nomor KTP", trim = true)
    @BindView(R.id.etKTP)
    EditText etKTP;

    @BindView(R.id.etNPWP)
    EditText etNPWP;

    @NotEmpty(message = "Alamat tidak boleh kosong", trim = true)
    @BindView(R.id.etAddress)
    EditText etAddress;

    @NotEmpty(message = "RT tidak boleh kosong", trim = true)
    @BindView(R.id.etValRT)
    EditText etValRT;

    @NotEmpty(message = "RW tidak boleh kosong", trim = true)
    @BindView(R.id.etValRW)
    EditText etValRW;

    @NotEmpty(message = "Nama Ibu Kandung tidak boleh kosong", trim = true)
    @BindView(R.id.etMotherName)
    EditText etMotherName;

    @Select(message = "Wajib pilih Provinsi")
    @BindView(R.id.spProvince)
    Spinner spProvince;

    @BindView(R.id.txtCity)
    TextView txtCity;

    @Select(message = "Wajib pilih Kabupaten/Kota")
    @BindView(R.id.spCity)
    Spinner spCity;

    @BindView(R.id.txtKecamatan)
    TextView txtKecamatan;

    @Select(message = "Wajib pilih Kecamatan")
    @BindView(R.id.spKecamatan)
    Spinner spKecamatan;

    @BindView(R.id.txtKelurahan)
    TextView txtKelurahan;

    @Select(message = "Wajib pilih Kelurahan")
    @BindView(R.id.spKelurahan)
    Spinner spKelurahan;

    @BindView(R.id.lyRayon)
    LinearLayout lyRayon;

    @Select(message = "Wajib pilih Pekerjaan")
    @BindView(R.id.spJobs)
    Spinner spJobs;

    @NotEmpty(message = "Mohon isi nama lengkap", trim = true)
    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etWork)
    EditText etWork;

    @BindView(R.id.etPhone)
    EditText etPhone;

    private String[] pekerjaan = {"Pilih...", "Pegawai Swasta", "PNS", "Wiraswasta", "Pensiunan", "Mahasiswa", "Lainnya"};

    private Validator validator;

    private static final int KTP = 9;
    private static final int NPWP = 10;

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

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_spinner, pekerjaan);
        spJobs.setAdapter(mAdapter);

        mPresenter.getCurrentProfile();

        if (mAdapterProvince == null ) {
            dialog.show();
            mPresenter.getProvince();
        }

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

    @OnClick(R.id.btnSave)
    void onClickNext(){

        if (textTitleNPWP.getVisibility() ==  View.GONE && etNPWP.getText().toString().equals("")) {

            etNPWP.setError("Masukan Nomor NPWP");
            etNPWP.requestFocus();

        } else if (txtTitleKTP.getVisibility() == View.VISIBLE) {

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
                        txtTitleKTP.setVisibility(View.GONE);

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
                        textTitleNPWP.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }

                    break;
            }

        }

    }

    @Override
    public void onValidationSucceeded() {

        dialog.show();
//        mPresenter.checkMandatoryItem(etKTP.getText().toString(), "", "", etNPWP.getText().toString());
        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){

            bundle = new Bundle();
        }

        Bank bank = new Bank();
        bank.setInt64(Integer.parseInt(bundle.get("BANK_ID").toString()));
        bank.setValid(true);

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(bank);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("bank", jsonElement);
        if (bundle.get("ACC_NUMBER") != null) {
            jsonObject.addProperty("bank_accountnumber", bundle.get("ACC_NUMBER").toString());
        }
        jsonObject.addProperty("fullname", etName.getText().toString());
        jsonObject.addProperty("idcard_image", pictKTP64);
        jsonObject.addProperty("idcard_number", etKTP.getText().toString());
        jsonObject.addProperty("taxid_image", pictNPWP64);
        jsonObject.addProperty("taxid_number", etNPWP.getText().toString());
        jsonObject.addProperty("address", etAddress.getText().toString());
        jsonObject.addProperty("province", spProvince.getSelectedItem().toString());
        jsonObject.addProperty("city", spCity.getSelectedItem().toString());
        jsonObject.addProperty("subdistrict", spKelurahan.getSelectedItem().toString()); //kelurahan
        jsonObject.addProperty("urban_village", spKecamatan.getSelectedItem().toString()); //kecamatan
        jsonObject.addProperty("neighbour_association", etValRT.getText().toString()); //rt
        jsonObject.addProperty("hamlets", etValRW.getText().toString()); //rw
        jsonObject.addProperty("mother_name", etMotherName.getText().toString());
        jsonObject.addProperty("occupation", etWork.getText().toString()); //pekerjaan
        jsonObject.addProperty("field_of_work", spJobs.getSelectedItem().toString()); //jenis pekerjaan

        mPresenter.patchUserProfile(jsonObject);
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

                }
            } else {
                Toast.makeText(parentActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private   Bitmap decodedFile(String path) {//you can provide file path here
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
    public void successCheckMandotryEntity(String message, String pnumber) {

        dialogDismiss();

        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){

            bundle = new Bundle();
        }

        bundle.putString(FormOtherFragment.REGIST_NAME, etName.getText().toString());
        bundle.putString(FormOtherFragment.PHOTO_KTP, pictKTP64);
        bundle.putString(FormOtherFragment.PHOTO_NPWP, pictNPWP64);
        bundle.putString(FormOtherFragment.KTP_NO, etKTP.getText().toString());
        bundle.putString(FormOtherFragment.NPWP_NO, etNPWP.getText().toString());
        bundle.putString(FormOtherFragment.PHONE, pnumber);

        Intent form = new Intent(parentActivity(), FormBorrowerActivity.class);
        form.putExtras(bundle);
        startActivity(form);

    }

    @Override
    public void dialogDismiss() {
        dialog.dismiss();
    }

    @Override
    public void showProvices(List<Provinsi.Data> provinces) {
        List<String> names = new ArrayList<>();
        List<String> idProvinces = new ArrayList<>();

        names.add("Pilih Provinsi...");
        idProvinces.add("0");
        for(Provinsi.Data data: provinces){
            names.add(data.getNama());
            idProvinces.add(data.getId());
        }

        mAdapterProvince =  new ArrayAdapter<>(parentActivity(), R.layout.item_spinner, names);
        spProvince.setAdapter(mAdapterProvince);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    Toast.makeText(parentActivity(), names.get(position), Toast.LENGTH_SHORT).show();
                    mPresenter.getCity(idProvinces.get(position));
                } else {
                    txtCity.setVisibility(View.GONE);
                    spCity.setVisibility(View.GONE);
                    txtKecamatan.setVisibility(View.GONE);
                    spKecamatan.setVisibility(View.GONE);
                    txtKelurahan.setVisibility(View.GONE);
                    spKelurahan.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.dismiss();
    }

    @Override
    public void showCity(List<Kabupaten.KabupatenItem> daftarKabupaten) {
        txtCity.setVisibility(View.VISIBLE);
        spCity.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> idKab = new ArrayList<>();

        names.add("Pilih Kebupaten/Kota...");
        idKab.add("0");
        for(Kabupaten.KabupatenItem data: daftarKabupaten){
            names.add(data.getNama());
            idKab.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_spinner, names);
        spCity.setAdapter(mAdapter);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    mPresenter.getKecamatan(idKab.get(position));
                } else {
                    txtKecamatan.setVisibility(View.GONE);
                    spKecamatan.setVisibility(View.GONE);
                    txtKelurahan.setVisibility(View.GONE);
                    spKelurahan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showKecamatan(List<Kecamatan.KecatamanItem> daftarKecamatan) {
        txtKecamatan.setVisibility(View.VISIBLE);
        spKecamatan.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kecamatan...");
        ids.add("0");
        for(Kecamatan.KecatamanItem data: daftarKecamatan){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_spinner, names);
        spKecamatan.setAdapter(mAdapter);
        spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    mPresenter.getKelurahan(ids.get(position));
                } else {
                    txtKelurahan.setVisibility(View.GONE);
                    spKelurahan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showKelurahan(List<Kelurahan.KelurahanItem> daftarDesa) {
        txtKelurahan.setVisibility(View.VISIBLE);
        spKelurahan.setVisibility(View.VISIBLE);
        lyRayon.setVisibility(View.VISIBLE);

        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        names.add("Pilih Kelurahan...");
        ids.add("0");
        for(Kelurahan.KelurahanItem data: daftarDesa){
            names.add(data.getNama());
            ids.add(data.getId());
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(parentActivity(), R.layout.item_spinner, names);
        spKelurahan.setAdapter(mAdapter);
    }

    @Override
    public void showCurrentProfile(PreferenceRepository preferenceRepository) {
        etName.setText(preferenceRepository.getUserName());
        etPhone.setText(CommonUtils.formatPhoneString(preferenceRepository.getUserPhone()));
    }

    @Override
    public void successUpdateProfile() {
        dialog.dismiss();

        Intent intent = new Intent(parentActivity(), BorrowerLandingPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
