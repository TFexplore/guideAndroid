package com.example.guide.pages.start.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.guide.pages.master.activity.MainActivity;
import com.example.guide.R;
import com.example.guide.common.Utils;
import com.example.guide.databinding.FragmentLoginBinding;
import com.example.guide.http.api.ApiLogin;
import com.example.guide.http.model.HttpData;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;


public class LoginFragment extends Fragment{
    FragmentLoginBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //点击事件注册
        clickInit();
    }
    void clickInit(){
        //登录请求
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //游客访问跳转
        binding.loginOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
        //跳转注册
        binding.toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }
    void login(){
        Log.d("login", "login: "+binding.pwd.getText().toString());
        ToastUtils.show("登录成功");
        toMainActivity();

        EasyHttp.post(this.getParentFragment())
                .api(new ApiLogin(binding.pwd.getText().toString(),binding.user.getText().toString()))
                .request(new OnHttpListener<HttpData<ApiLogin.Bean>>() {
                    @Override
                    public void onSucceed(HttpData<ApiLogin.Bean> result) {
                        Log.d("http", "onSucceed: ");
                        MMKV mmkv=MMKV.defaultMMKV();
                        if (!mmkv.encode("user", Utils.getGson().toJson(result.getData().getUser()))||
                                !mmkv.encode("token",result.getData().getToken())){

                            ToastUtils.show("用户信息保存失败，请重试");
                        }else ToastUtils.show("登录成功");

                        toMainActivity();

                   }

                    @Override
                    public void onFail(Exception e) {
                        Log.d("http", "onFail: ");
                    }

                });

    }

    void toMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(requireContext(), MainActivity.class);
                requireActivity().startActivity(mainIntent);
                requireActivity().finish();
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
