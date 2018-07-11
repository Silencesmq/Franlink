package com.example.silence.franlink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.silence.franlink.jsong.AnimationUtil;
import com.example.silence.franlink.jsong.FloatingDraftButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloatballActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButton)
    FloatingDraftButton floatingActionButton;
    @BindView(R.id.floatingActionButton_1)
    FloatingActionButton floatingActionButton1;
    @BindView(R.id.floatingActionButton_2)
    FloatingActionButton floatingActionButton2;
    @BindView(R.id.floatingActionButton_3)
    FloatingActionButton floatingActionButton3;
    @BindView(R.id.floatingActionButton_4)
    FloatingActionButton floatingActionButton4;
    @BindView(R.id.floatingActionButton_5)
    FloatingActionButton floatingActionButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatball);
        ButterKnife.bind(this);
        floatingActionButton.registerButton(floatingActionButton1);
        floatingActionButton.registerButton(floatingActionButton2);
        floatingActionButton.registerButton(floatingActionButton3);
        floatingActionButton.registerButton(floatingActionButton4);
        floatingActionButton.registerButton(floatingActionButton5);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton);
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "1111111", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "2222222", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "3333333", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "4444444", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(FloatballActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "5555555", Toast.LENGTH_SHORT).show(); }});
    }
}
