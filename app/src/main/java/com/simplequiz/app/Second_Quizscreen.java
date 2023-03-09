package com.simplequiz.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simplequiz.app.databinding.ActivitySecondQuizscreenBinding;

public class Second_Quizscreen extends AppCompatActivity {

    ActivitySecondQuizscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySecondQuizscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottomNavigationView=findViewById(R.id.quiz_bottom);
        NavController navController=Navigation.findNavController(this,R.id.fragmentContainerView);
//        NavigationUI.setupActionBarWithNavController(this, navController);
//        NavigationUI.setupWithNavController(bottomNavigationView,navController);

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//
//            }
//        });


//
        replaceFragment(new HomeFragment());
        binding.quizBottom.setOnItemSelectedListener(item ->{
            switch(item.getItemId())
            {
                case (R.id.home) :
                    replaceFragment(new HomeFragment());
                    break;
                case (R.id.dashboard) :
                    replaceFragment(new DashboardFragment());
                    break;
                case (R.id.notification) :
                    replaceFragment(new NotificationsFragment());
                    break;
            }
            return false;
        });


    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,fragment);
        transaction.commit();

  }
}