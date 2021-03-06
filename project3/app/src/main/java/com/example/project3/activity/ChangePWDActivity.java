package com.example.project3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project3.R;
import com.example.project3.entity.HttpResult;
import com.example.project3.http.ProgressDialogSubscriber;
import com.example.project3.http.presenter.MemberPresenter;

public class ChangePWDActivity extends BaseActivity implements View.OnClickListener{

    ImageView titleBack;
    EditText passwordInputOldpass;
    EditText passwordInputNewpass;
    EditText passwordInputRepass;
    Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changge_password);
        titleBack = findViewById(R.id.title_back);
        passwordInputOldpass = findViewById(R.id.password_input_oldpass);
        passwordInputNewpass = findViewById(R.id.password_input_newpass);
        passwordInputRepass = findViewById(R.id.password_input_repass);
        changeButton = findViewById(R.id.change_button);

        titleBack.setOnClickListener(this);
        changeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.change_button:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        String old_password = passwordInputOldpass.getText().toString().trim();
        String new_password = passwordInputNewpass.getText().toString().trim();
        String new_rePassword = passwordInputRepass.getText().toString().trim();

        checkPassword(old_password, new_password, new_rePassword);

        String member_id = getSharedPreferences("user", 0).getString("member_id", "");
        if (TextUtils.isEmpty(member_id)) {
            Toast.makeText(this, "??????????????????id???????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        //????????????
        MemberPresenter.changePassword(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
                if (httpResult.getStatus() == 0) {//????????????
                    Toast.makeText(ChangePWDActivity.this, "??????????????????,?????????????????????", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor localEditor = getSharedPreferences("user", 0).edit();
                    localEditor.remove("member_id");
                    localEditor.remove("uname");
                    localEditor.remove("email");
                    localEditor.remove("image");
                    localEditor.commit();
                    //?????????PersonFragment??????????????????
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("changepass", true);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast.makeText(ChangePWDActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }, member_id, old_password, new_password);
    }

    private void checkPassword(String old_password, String new_password, String new_rePassword) {
        if (TextUtils.isEmpty(old_password) || TextUtils.isEmpty(new_password) || TextUtils.isEmpty(new_rePassword)) {
            Toast.makeText(this, "?????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!new_password.equals(new_rePassword)) {
            Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
