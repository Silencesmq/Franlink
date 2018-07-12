package com.example.silence.franlink;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.util.ActivityCollector;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.HttpUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox remember;
    private EditText accountEdit;
    private EditText passwordEdit;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        if (event.getCode()==Event.EventCode.LoginFail){
            Toast.makeText(LoginActivity.this,(String)event.getData(),Toast.LENGTH_SHORT).show();
        }else if(event.getCode()==Event.EventCode.LoginSucceed){
            Intent i=new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.removeActivity(this);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit=(EditText) findViewById(R.id.editText_account);
        passwordEdit=(EditText) findViewById(R.id.editText_password);
        remember=(CheckBox)findViewById(R.id.checkBox_remember);
        final boolean isRemember=pref.getBoolean("remember",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remember.setChecked(true);
        }
        Button login=(Button)findViewById(R.id.button_login);
        TextView register=(TextView)findViewById(R.id.textView_register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RequestBody requestBody=new FormBody.Builder()
                        .add("account",accountEdit.getText().toString())
                        .add("pwd",passwordEdit.getText().toString())
                        .build();
                HttpUtil.OkHttpRequestPost("http://139.196.139.63/login.php",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().string());
                            int retCode=jsonObject.getInt("success");
                            if(retCode==1){
                                editor=pref.edit();
                                if(remember.isChecked()){
                                    editor.putBoolean("remember",true);
                                    editor.putString("account",accountEdit.getText().toString());
                                    editor.putString("password",passwordEdit.getText().toString());
                                }
                                else {
                                    editor.clear();
                                }
                                Application.owner=accountEdit.getText().toString();
                                editor.apply();
                                EventBusUtil.sendEvent(new Event<String>(Event.EventCode.LoginSucceed,""));
                            }else if(retCode==0){
                                EventBusUtil.sendEvent(new Event<String>(Event.EventCode.LoginFail, "密码错误"));
                            }else if(retCode==-1){
                                EventBusUtil.sendEvent(new Event<String>(Event.EventCode.LoginFail, "账号不存在"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                });
            }
        });


    }
}
