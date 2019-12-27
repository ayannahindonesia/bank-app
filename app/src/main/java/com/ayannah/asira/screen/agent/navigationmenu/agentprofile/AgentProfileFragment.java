package com.ayannah.asira.screen.agent.navigationmenu.agentprofile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks.AgentProfileBankListActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgentProfileFragment extends BaseFragment implements AgentProfileContract.View, Validator.ValidationListener {

    @Inject
    AgentProfileContract.Presenter mPresenter;

    private Validator validator;

    private AlertDialog dialogAlert;

    @BindView(R.id.txtIdAgent)
    TextView txtIdAgent;

    @BindView(R.id.txtAgentName)
    TextView txtAgentName;

    @BindView(R.id.txtAgentUserName)
    TextView txtAgentUserName;

    @NotEmpty(message = "Alamat email tidak boleh kosong", trim = true)
    @BindView(R.id.etAgentEmail)
    EditText etAgentEmail;

    @NotEmpty(message = "Nomor Ponsel tidak boleh kosong", trim = true)
    @BindView(R.id.etAgentHp)
    EditText etAgentHp;

    @BindView(R.id.txtAgentCat)
    TextView txtAgentCat;

    @BindView(R.id.txtAgentProvider)
    TextView txtAgentProvider;

    @NotEmpty(message = "Bank tidak boleh kosong", trim = true)
    @BindView(R.id.etAgentBanks)
    EditText etAgentBanks;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;

    @BindView(R.id.rltBankService)
    RelativeLayout rltBankService;

    @BindView(R.id.txtBankServiceTitle)
    TextView txtBankServiceTitle;

    private Bitmap imageBitmap;
    private ArrayList<Integer> banksSelectedID = new ArrayList<>();
    private boolean bankSelectFromList = false;

    @Inject
    public AgentProfileFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_bar);
        dialogAlert = builder.create();

        if (!bankSelectFromList) {
            mPresenter.setAgentProfile();
        }

        etAgentBanks.setKeyListener(null);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_agent_profile;
    }

    @Override
    protected void initView(Bundle state) {

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void showErrorMessage(String message) {

        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadAgentProfile(PreferenceRepository preferenceRepository) {
        if (preferenceRepository.getAgentCategory().toLowerCase().equals("account_executive")) {
            txtAgentProvider.setText(preferenceRepository.getAgentBanksName());
            rltBankService.setVisibility(View.GONE);
            txtBankServiceTitle.setVisibility(View.GONE);
        } else {
            mPresenter.getProviderName(preferenceRepository.getAgentProvider());
            etAgentBanks.setText(preferenceRepository.getAgentBanksName());
        }

        txtIdAgent.setText(preferenceRepository.getAgentId());
        txtAgentName.setText(preferenceRepository.getAgentName());
        txtAgentUserName.setText(preferenceRepository.getAgentUserName());
        etAgentEmail.setText(preferenceRepository.getAgentEmail());
        etAgentHp.setText(preferenceRepository.getAgentPhone());
        txtAgentCat.setText(translateAgentCat(preferenceRepository.getAgentCategory()));

        txtStatus.setText(translateAgentStatus(preferenceRepository.getAgentStatus()));
        bankSelectFromList = false;
    }

    private String translateAgentCat(String agentCategory) {
        if (agentCategory.toLowerCase().equals("agent")) {
            return "Agen";
        } else {
            return "Account Executive";
        }
    }

    private String translateAgentStatus(String agentStatus) {
        if (agentStatus.toLowerCase().equals("active")) {
            return "Aktif";
        } else {
            return "Tidak Aktif";
        }

    }

    @Override
    public void successUpdateProfileAgent() {
        Toast.makeText(parentActivity(), "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(parentActivity(), AgentProfileActivity.class);
        startActivity(intent);
        parentActivity().finish();
    }

    @Override
    public void successUpdatePhotoAgent() {
        imgProfile.setImageBitmap(imageBitmap);
        Toast.makeText(parentActivity(), "Foto Berhasil disimpan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAgentProviderName(String name) {
        txtAgentProvider.setText(name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dropView();
    }

    @Override
    public void onValidationSucceeded() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        JsonArray arr = new JsonArray();
                        for (int i=0; i<banksSelectedID.size(); i++) {
                            arr.add(banksSelectedID.get(i));
                        }

                        JsonObject jsonPatchAgentProfile = new JsonObject();
                        jsonPatchAgentProfile.addProperty("email", etAgentEmail.getText().toString());
                        jsonPatchAgentProfile.addProperty("phone", etAgentHp.getText().toString());
                        jsonPatchAgentProfile.add("banks", arr);
                        mPresenter.patchDataAgent(jsonPatchAgentProfile);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.cancel();

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Apakah Anda Yakin Akan Merubah Data?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error: errors){

            String message = error.getCollatedErrorMessage(parentActivity());
            View view = error.getView();

            Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
//            if(view instanceof EditText){
//
//                ((EditText)view).setError(message);
//
//            }else {
//                Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();
//            }

        }
    }

    @OnClick(R.id.rltImageProfile)
    void cameraTake() {

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
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(parentActivity().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 1);
                            }

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            dialog.cancel();

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
            builder.setMessage("Apakah Anda Yakin Akan Merubah Foto Profil?")
                    .setPositiveButton("Ya", dialogClickListener)
                    .setNegativeButton("Tidak", dialogClickListener)
                    .show();

        }
    }

    @OnLongClick(R.id.rltImageProfile)
    void previewPhoto() {
        Toast.makeText(parentActivity(), "preview", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            String pict = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            mPresenter.patchAgentPhotoProfile(pict);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            banksSelectedID.clear();
            List<BankDetail> banksSelected = (List<BankDetail>) data.getSerializableExtra("intent");
//            Toast.makeText(parentActivity(), String.valueOf(banksSelected.size()), Toast.LENGTH_SHORT).show();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i< banksSelected.size(); i++) {
                if (i == banksSelected.size()-1) {
                    s.append(banksSelected.get(i).getName());
                } else {
                    s.append(banksSelected.get(i).getName()).append(", ");
                }
                banksSelectedID.add(banksSelected.get(i).getId());
            }
            etAgentBanks.setText(s);
            bankSelectFromList = true;
        }
    }

    @OnClick(R.id.imgAddBanks)
    void addBanks() {
        Intent intent = new Intent(parentActivity(), AgentProfileBankListActivity.class);
        startActivityForResult(intent, 2);
    }

    @OnClick(R.id.btnSaveAgentProfile)
    void saveProfile() {
        validator.validate();
    }
}
