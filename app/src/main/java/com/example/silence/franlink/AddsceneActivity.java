package com.example.silence.franlink;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.Item.Scene;

import java.util.Date;

public class AddsceneActivity extends BaseActivity implements View.OnClickListener{

    private String choose;
    Boolean choose0;
    Boolean choose1;
    Boolean choose2;
    private Toolbar toolbar;
    private ImageView Confirmadd;
    private EditText editText;
    private static final String[] strs = new String[] {
            "开门", "开灯", "打开热水器"
    };//定义一个String数组用来显示ListView的内容
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addscene);
        InitView();
    }

    private void InitView() {
        choose0=false;
        choose1=false;
        choose2=false;
        editText = (EditText) findViewById(R.id.Myscenename);
        toolbar = (Toolbar) findViewById(R.id.toolbar_addscene);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        lv = (ListView) findViewById(R.id.lv_addscene);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        Confirmadd = (ImageView) findViewById(R.id.confirmaddscene);
        Confirmadd.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this,"sb",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmaddscene:{
                SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(AddsceneActivity.this, "请输入名称", Toast.LENGTH_SHORT).show();
                }else if(checkedItems.size()==0){
                    Toast.makeText(AddsceneActivity.this, "请选择操作", Toast.LENGTH_SHORT).show();
                }else{
                    for (int x = 0; x < checkedItems.size(); x++){
                        int key = checkedItems.keyAt(x);
                        //key指的是该item在listview中的position
                        //Toast.makeText(AddsceneActivity.this,"你选择了第"+key+"行",Toast.LENGTH_SHORT).show();
                    }
                    Intent intent=new Intent();
                    intent.putExtra("name",editText.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            }
        }
    }
}
