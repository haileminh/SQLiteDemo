package com.example.hailm.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hailm.sqlitedemo.manager.StudentDBManager;
import com.example.hailm.sqlitedemo.model.Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStudentActivity extends AppCompatActivity {
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_address)
    EditText edtAddress;

    private static final String TAG = "UpdateStudentActivity";
    private StudentDBManager mStudentDBManager;
    private Student mStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);
        ButterKnife.bind(this);
        mStudentDBManager = StudentDBManager.getInstance(this);

        mStudent = (Student) getIntent().getSerializableExtra("STUDENT");
        edtName.setText(mStudent.getName());
        edtNumber.setText(mStudent.getNumber());
        edtEmail.setText(mStudent.getEmail());
        edtAddress.setText(mStudent.getAddress());
    }

    @OnClick({R.id.btn_update})
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateStudent();
                break;
            default:
                break;
        }
    }

    private void updateStudent() {
        Student student = new Student();
        student.setId(mStudent.getId());
        student.setName(edtName.getText().toString());
        student.setNumber(edtNumber.getText().toString());
        student.setEmail(edtEmail.getText().toString());
        student.setAddress(edtAddress.getText().toString());

        int i = mStudentDBManager.updateStudent(student);
        if (i > 0) {
            startActivity(new Intent(UpdateStudentActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Update failure", Toast.LENGTH_SHORT).show();
        }
    }
}
