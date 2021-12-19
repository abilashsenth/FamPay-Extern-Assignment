package com.thenextbiggeek.fampayextern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.thenextbiggeek.fampayextern.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        loadFragment(new FragmentMain(), savedInstanceState);


    }

    private void loadFragment(Fragment fragment, Bundle savedInstanceState) {
        if (fragment != null && savedInstanceState ==null) {
            getSupportFragmentManager().beginTransaction().
                    setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FragmentMain.class, null)
                    .commit();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}