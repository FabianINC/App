package com.archivo.MainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.archivo.MainMenu.menu_fragments.ViewPageAdapter;
import com.archivo.MainMenu.menu_fragments.profile.Profile;
import com.archivo.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ViewPageAdapter viewPageAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // INVISIBILIZA LA STATUS BAR
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        String userEmail = getIntent().getStringExtra("userEmail"); // OBTENGO EL CORREO ENVIADO
        String userName = getIntent().getStringExtra("userName"); // OBTENGO EL CORREO ENVIADO
        String userPhone = getIntent().getStringExtra("userPhone"); // OBTENGO EL CORREO ENVIADO

        //COMO PASO EL CORREO AL FRAGMENT DE PROFILE ?
        Toast.makeText(this, "COREEO: " + userEmail, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "NOMBRE: " + userName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "TELEFONO " + userPhone, Toast.LENGTH_SHORT).show();

        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager2 = findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){

                    case  R.id.home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.favorite:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.profile:
                        viewPager2.setCurrentItem(2);
                        break;

                }
                return false;
            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0 :
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                    break;
                    case 1 :
                        bottomNavigationView.getMenu().findItem(R.id.favorite).setChecked(true);
                    break;
                    case 2 :
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                    break;



                }
                super.onPageSelected(position);
            }
        });


        //El codigo de abajo desactiva el viewPager2
        //viewPager2.setUserInputEnabled(false);

    }

    public ViewPager2 getViewPager(){

        return viewPager2;

    }


}