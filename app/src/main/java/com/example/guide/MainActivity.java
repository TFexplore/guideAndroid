package com.example.guide;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.guide.common.Utils;
import com.example.guide.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setWindowStatusBarColor(this,R.color.bar_main);
        navController = Navigation.findNavController(this, R.id.nav_master);
        NavigationUI.setupActionBarWithNavController(this, navController);

        Utils.init(getApplication());

    }




    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                //顶部状态栏
                window.setStatusBarColor(colorResId);

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private long pressdTime=0;
    @Override
    public void onBackPressed() {
        long pressTime = System.currentTimeMillis();
        Log.d("back", "onBackPressed: main:" + pressTime);
        switch (navController.getCurrentDestination().getId()) {
            case R.id.login:
                if (pressTime - pressdTime > 800) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    pressdTime = pressTime;

                } else System.exit(0);
                break;


            case R.id.register:
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
                break;
            default:
                super.onBackPressed();
                break;
        }

    }
}