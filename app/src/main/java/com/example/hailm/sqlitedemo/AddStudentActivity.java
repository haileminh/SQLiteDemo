package com.example.hailm.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hailm.sqlitedemo.manager.StudentDBManager;
import com.example.hailm.sqlitedemo.model.Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStudentActivity extends AppCompatActivity {
    @BindView(R.id.txt_name)
    EditText edtName;
    @BindView(R.id.txt_number)
    EditText edtNumber;
    @BindView(R.id.txt_email)
    EditText edtEmail;
    @BindView(R.id.txt_address)
    EditText edtAddress;

    private StudentDBManager mStudentDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mStudentDBManager = StudentDBManager.getInstance(this);

    }

    @OnClick({R.id.btn_insert})
    public void onClickItem(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                Toast.makeText(this, "Insert", Toast.LENGTH_SHORT).show();
                Student student = createStudent();
                if (student != null) {
                    mStudentDBManager.addStudent(student);
                    startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private Student createStudent() {
        String name = edtName.getText().toString();
        String number = edtNumber.getText().toString();
        String address = edtAddress.getText().toString();
        String email = edtEmail.getText().toString();

        return new Student(name, number, email, address);
    }
}
