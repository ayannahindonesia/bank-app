package com.ayannah.bantenbank.screen.register;

import android.os.Bundle;

import com.ayannah.bantenbank.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

public class RegisterActivity extends AppCompatActivity {

    ChooseBank fragment = new ChooseBank();
    FormBorrowerIdentity fragment2 = new FormBorrowerIdentity();

    String purpose = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        purpose = getIntent().getStringExtra("purpose");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Pendaftaran");
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

//        if(purpose.equals("continueRegist")){
//            ft.add(R.id.fragment_container, fragment2);
//            ft.commit();
//        }else {
//            ft.add(R.id.fragment_container, fragment);
//            ft.commit();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onSupportNavigateUp() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }else {
            super.onBackPressed();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }else {
            super.onBackPressed();
        }
    }
}
