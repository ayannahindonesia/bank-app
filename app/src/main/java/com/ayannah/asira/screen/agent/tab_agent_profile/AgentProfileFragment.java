package com.ayannah.asira.screen.agent.tab_agent_profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.adapter.StringListAdapter;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks.AgentProfileBankListActivity;
import com.ayannah.asira.screen.borrower.register_mandatory.RegisterMandatoryActivity;
import com.ayannah.asira.screen.chooselogin.ChooseLoginActivity;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.ImageUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AgentProfileFragment extends BaseFragment implements AgentProfileContract.View {

    @Inject
    AgentProfileContract.Presenter mPresenter;

//    private Validator validator;

    private AlertDialog dialogAlert;

    @BindView(R.id.txtAgentName) TextView txtAgentName;
    @BindView(R.id.tvAgentHp) TextView tvAgentHp;

    @BindView(R.id.imgProfile) CircleImageView imgProfile;
    @BindView(R.id.recyclerViewAkun) RecyclerView recyclerViewAkun;
    @BindView(R.id.recyclerViewTentang) RecyclerView recyclerViewTentang;

//    @BindView(R.id.txtAgentCat)
//    TextView txtAgentCat;

//    @BindView(R.id.txtAgentProvider)
//    TextView txtAgentProvider;

//    @BindView(R.id.etAgentBanks)
//    EditText etAgentBanks;

//    @BindView(R.id.txtStatus)
//    TextView txtStatus;

//    @BindView(R.id.rltBankService)
//    LinearLayout rltBankService;

//    @BindView(R.id.txtBankServiceTitle)
//    TextView txtBankServiceTitle;

//    private Bitmap imageBitmap;
//    private ArrayList<Integer> banksSelectedID = new ArrayList<>();
//    private ArrayList<Integer> banksSelectedIDServer = new ArrayList<>();
//    public static boolean bankSelectFromList;

    @Inject
    public AgentProfileFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        mPresenter.getAgentProfile();
        mPresenter.getMenus();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_akun_saya_agent;
    }

    @Override
    protected void initView(Bundle state) {

        recyclerViewAkun.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewAkun.setHasFixedSize(true);
        recyclerViewAkun.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));

        recyclerViewTentang.setLayoutManager(new LinearLayoutManager(parentActivity()));
        recyclerViewTentang.setHasFixedSize(true);
        recyclerViewTentang.addItemDecoration(new DividerItemDecoration(parentActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showErrorMessage(String message) {

        dialogAlert.dismiss();
        Toast.makeText(parentActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadAgentProfile(PreferenceRepository preferenceRepository) {

        ImageUtils.displayImageFromUrlWithErrorDrawable(parentActivity(), imgProfile, preferenceRepository.getAgentProfileImage(), null);

//        if (preferenceRepository.getAgentCategory().toLowerCase().equals("account_executive")) {
//            txtAgentProvider.setText(preferenceRepository.getAgentBanksName());
//            rltBankService.setVisibility(View.GONE);
//            txtBankServiceTitle.setVisibility(View.GONE);
//        } else {
//            mPresenter.getProviderName(preferenceRepository.getAgentProvider());
//            etAgentBanks.setText(preferenceRepository.getAgentBanksName());
//        }

        txtAgentName.setText(preferenceRepository.getAgentName());
        tvAgentHp.setText(CommonUtils.formatPhoneNumberGlobal(preferenceRepository.getAgentPhone()));
//        txtAgentCat.setText(translateAgentCat(preferenceRepository.getAgentCategory()));

//        txtStatus.setText(translateAgentStatus(preferenceRepository.getAgentStatus()));
//        bankSelectFromList = false;

//        List<String> arrayList = new ArrayList<String>    (Arrays.asList(preferenceRepository.getAgentBanks().split(",")));
//        banksSelectedIDServer.clear();
//        for(String fav:arrayList){
//            banksSelectedIDServer.add(Integer.parseInt(fav.trim()));
//        }

//        banksSelectedID.clear();
//        banksSelectedID.addAll(banksSelectedIDServer);
    }

    @Override
    public void loadMenus(List<String> akuns, List<String> tentangs) {

        StringListAdapter adapterAkun = new StringListAdapter(akuns);
        StringListAdapter adapterTentang = new StringListAdapter(tentangs);

        recyclerViewAkun.setAdapter(adapterAkun);
        recyclerViewTentang.setAdapter(adapterTentang);

        adapterAkun.setOnClickStringListener(new StringListAdapter.StringListListener() {
            @Override
            public void onClickItem(String item) {
                Toast.makeText(parentActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

        adapterTentang.setOnClickStringListener(new StringListAdapter.StringListListener() {
            @Override
            public void onClickItem(String item) {
                Toast.makeText(parentActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private String translateAgentCat(String agentCategory) {
//        if (agentCategory.toLowerCase().equals("agent")) {
//            return "Agen";
//        } else {
//            return "Account Executive";
//        }
//    }
//
//    private String translateAgentStatus(String agentStatus) {
//        if (agentStatus.toLowerCase().equals("active")) {
//            return "Aktif";
//        } else {
//            return "Tidak Aktif";
//        }
//
//    }

//    @Override
//    public void successUpdateProfileAgent() {
//        Toast.makeText(parentActivity(), "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(parentActivity(), LPAgentActivity.class);
//        intent.putExtra("isFrom", "agentProfile");
//        startActivity(intent);
//        parentActivity().finish();
//    }
//
//    @Override
//    public void successUpdatePhotoAgent() {
//        imgProfile.setImageBitmap(imageBitmap);
//        Toast.makeText(parentActivity(), "Foto Berhasil disimpan", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void setAgentProviderName(String name) {
//        txtAgentProvider.setText(name);
//    }

    @Override
    public void logoutComplete() {
        Intent logout = new Intent(parentActivity(), RegisterMandatoryActivity.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);
        parentActivity().finish();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mPresenter.dropView();
//    }


//    @OnClick(R.id.rltImageProfile)
//    void cameraTake() {
//
//        if (ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(parentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            needPermission(new String[]{
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            });
//        } else {
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which) {
//                        case DialogInterface.BUTTON_POSITIVE:
//
//                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            if (takePictureIntent.resolveActivity(parentActivity().getPackageManager()) != null) {
//                                startActivityForResult(takePictureIntent, 1);
//                            }
//
//                            break;
//
//                        case DialogInterface.BUTTON_NEGATIVE:
//
//                            dialog.cancel();
//
//                            break;
//                    }
//                }
//            };
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
//            builder.setMessage("Apakah Anda Yakin Akan Merubah Foto Profil?")
//                    .setPositiveButton("Ya", dialogClickListener)
//                    .setNegativeButton("Tidak", dialogClickListener)
//                    .show();
//
//        }
//    }

//    @OnLongClick(R.id.rltImageProfile)
//    void previewPhoto() {
//        Toast.makeText(parentActivity(), "preview", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//            String pict = Base64.encodeToString(byteArray, Base64.NO_WRAP);
//            mPresenter.patchAgentPhotoProfile(pict);
//        } else if (requestCode == 2 && resultCode == RESULT_OK) {
//            banksSelectedID.clear();
//            List<BankDetail> banksSelected = (List<BankDetail>) data.getSerializableExtra("intent");
////            Toast.makeText(parentActivity(), String.valueOf(banksSelected.size()), Toast.LENGTH_SHORT).show();
//            StringBuilder s = new StringBuilder();
//            for (int i = 0; i< banksSelected.size(); i++) {
//                if (i == banksSelected.size()-1) {
//                    s.append(banksSelected.get(i).getName());
//                } else {
//                    s.append(banksSelected.get(i).getName()).append(", ");
//                }
//                banksSelectedID.add(banksSelected.get(i).getId());
//            }
//
//            etAgentBanks.setText(s);
//            bankSelectFromList = true;
//        }
//    }

//    @OnClick(R.id.imgAddBanks)
//    void addBanks() {
//        Intent intent = new Intent(parentActivity(), AgentProfileBankListActivity.class);
//        intent.putExtra("currentSelectedBanks", banksSelectedID);
//        intent.putExtra("currentSelectedBanksServer", banksSelectedIDServer);
//        startActivityForResult(intent, 2);
//    }


    @OnClick(R.id.btnSignout)
    void signOut() {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity());
        builder.setMessage("Apakah Anda Yakin Keluar?")
                .setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener)
                .show();

    }
}
