package com.example.hailm.sqlitedemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hailm.sqlitedemo.adapter.StudentAdapter;
import com.example.hailm.sqlitedemo.manager.StudentDBManager;
import com.example.hailm.sqlitedemo.model.Student;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IOnLongClick, View.OnClickListener {
    private StudentDBManager mStudentDBManager;
    private RecyclerView rcvStudents;
    private FloatingActionButton btnAdd;
    private TextView txtTotal;
    private EditText edtSearch;
    private ImageView btnSearch;

    private List<Student> studentList;
    private StudentAdapter mStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        studentList = new ArrayList<>();
        rcvStudents = findViewById(R.id.rcv_student);
        btnAdd = findViewById(R.id.btn_add);
        txtTotal = findViewById(R.id.txt_total_student);
        edtSearch = findViewById(R.id.edt_search);
        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        mStudentDBManager = StudentDBManager.getInstance(this);
        studentList = mStudentDBManager.getAllStudent();

        setAdapter(studentList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddStudentActivity.class));
            }
        });
    }

    private void setAdapter(List<Student> studentList) {
        int countStudent = mStudentDBManager.getCountStudent();
        String totalStudent = "Tổng số sinh viên: " + countStudent;
        txtTotal.setText(totalStudent);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcvStudents.setLayoutManager(llm);
        mStudentAdapter = new StudentAdapter(studentList, this, this);
        rcvStudents.setAdapter(mStudentAdapter);

        mStudentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongItemClick(Student student) {
        int i = mStudentDBManager.deleteStudent(student.getId());
        if (i > 0) {
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            setAdapter(studentList);
        } else {
            Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                List<Student> list = mStudentDBManager.getAllStudentByName(edtSearch.getText().toString());
                setAdapter(list);
                break;
            default:
                break;
        }
    }
}
