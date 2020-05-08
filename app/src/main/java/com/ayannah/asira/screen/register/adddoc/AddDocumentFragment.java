package com.ayannah.asira.screen.register.adddoc;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherFragment;
import com.ayannah.asira.util.CommonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDocumentFragment extends BaseFragment implements AddDocumentContract.View, Validator.ValidationListener {

    private final static String TAG = AddDocumentFragment.class.getSimpleName();

    @Inject
    AddDocumentContract.Presenter mPresenter;

    private ArrayAdapter<String> mAdapterProvince;
    private String currentPhotoPath;
    private Uri photoURI;
    private File photoFile;

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

    @BindView(R.id.imgSelfie)
    ImageView imgSelfie;

    @BindView(R.id.txtTitleSelfie)
    TextView txtTitleSelfie;

    private String[] pekerjaan = {"Pilih...", "Pegawai Swasta", "PNS", "Wiraswasta", "Pensiunan", "Mahasiswa", "Lainnya"};

    private Validator validator;

    private static final int KTP = 9;
    private static final int NPWP = 10;
    private static final int SELFIE = 11;

    private String pictKTP64, pictNPWP64, pictSELFIE64;
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
                Manifest.permission.CAMERA
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    @OnClick(R.id.imgSelfie)
    void onClickSelfie() {
        openCamera(SELFIE);
    }

    @OnClick(R.id.imgKTP)
    void onClickKtp(){
        openCamera(KTP);
    }

    @OnClick(R.id.imgNPWP)
    void onClickNpwp(){
        openCamera(NPWP);
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

                case SELFIE:

                    try {
                        pictSELFIE64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgSelfie);
                        imgSelfie.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        txtTitleSelfie.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.d("Error KTP", e.getMessage());
                    }
                    break;

                case KTP:

                    try {

                        pictKTP64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgKtp);
                        imgKtp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        txtTitleKTP.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.d("Error KTP", e.getMessage());
                    }

                    break;

                case NPWP:

                    try {

                        pictNPWP64 = encodeImage(currentPhotoPath);
                        Glide.with(parentActivity()).load(currentPhotoPath).into(imgNpwp);
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

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        doRegist();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.cancel();

                        break;
                }
            }
        };

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Mitra yang Anda pilih akan melakukan validasi terhadap data Anda dan akan melakukan pembuatan akun rekening untuk Anda jika diperlukan. Apakah Anda bersedia?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();

    }

    private void doRegist() {
        dialog.show();
//        mPresenter.checkMandatoryItem(etKTP.getText().toString(), "", "", etNPWP.getText().toString());
        Bundle bundle = parentActivity().getIntent().getExtras();

        if(bundle == null){

            bundle = new Bundle();
        }

//        Bank bank = new Bank();
//        bank.setInt64(Integer.parseInt(bundle.get("BANK_ID").toString()));
//        bank.setValid(true);
//
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.toJsonTree(bank);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("bank", Integer.parseInt(bundle.get("BANK_ID").toString()));
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
        jsonObject.addProperty("image_profile", pictSELFIE64);

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
        bundle.putString(FormOtherFragment.PHOTO_KTP, pictKTP64.replaceAll("\\n","").replaceAll("\\s",""));
        bundle.putString(FormOtherFragment.PHOTO_NPWP, pictNPWP64.replaceAll("\\n","").replaceAll("\\s",""));
        bundle.putString(FormOtherFragment.KTP_NO, etKTP.getText().toString());
        bundle.putString(FormOtherFragment.NPWP_NO, etNPWP.getText().toString());
        bundle.putString(FormOtherFragment.PHONE, pnumber);
        bundle.putString(FormOtherFragment.PHOTO_SELFIE, pictSELFIE64.replaceAll("\\n","").replaceAll("\\s",""));

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
