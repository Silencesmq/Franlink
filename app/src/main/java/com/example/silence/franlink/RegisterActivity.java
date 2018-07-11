package com.example.silence.franlink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.util.ActivityCollector;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.HttpUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity  {

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveStickyEvent(Event event) {
        if (event.getCode()==1){
            Toast.makeText(RegisterActivity.this,(String)event.getData(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText raccount=(EditText) findViewById(R.id.editText_raccount);
        final EditText name=(EditText) findViewById(R.id.editText_name);
        final EditText rpassword=(EditText) findViewById(R.id.editText_rpassword);
        final EditText rrpassword=(EditText) findViewById(R.id.editText_rrpassword);
        final EditText email=(EditText) findViewById(R.id.editText_email);
        Button rconfirm=(Button)findViewById(R.id.button_rconfirm);
        rconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(raccount.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                }else if(name.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if(rpassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(rrpassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                }else if(email.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else if(!rpassword.getText().toString().equals(rrpassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"确认密码与密码不符",Toast.LENGTH_SHORT).show();
                }else{
                    RequestBody requestBody=new FormBody.Builder()
                            .add("account",raccount.getText().toString())
                            .add("pwd",rpassword.getText().toString())
                            .add("name",name.getText().toString())
                            .add("email",email.getText().toString())
                            .build();
                    HttpUtil.OkHttpRequestPost("http://139.196.139.63/register.php",requestBody,new okhttp3.Callback(){
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject=new JSONObject(response.body().string());
                                int retCode=jsonObject.getInt("success");
                                if(retCode==1){

                                }else if(retCode==0){
                                    EventBusUtil.sendStickyEvent(new Event<String>(1, "该账号已存在"));
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


            }
        });
    }
}
