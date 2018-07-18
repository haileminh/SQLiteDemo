package com.example.hailm.sqlitedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hailm.sqlitedemo.IOnLongClick;
import com.example.hailm.sqlitedemo.R;
import com.example.hailm.sqlitedemo.UpdateStudentActivity;
import com.example.hailm.sqlitedemo.manager.StudentDBManager;
import com.example.hailm.sqlitedemo.model.Student;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> studentList;
    private Context context;
    private LayoutInflater inflater;
    private StudentDBManager mStudentDBManager;
    private IOnLongClick callback;

    public StudentAdapter(List<Student> studentList, Context context, IOnLongClick callback) {
        this.studentList = studentList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        mStudentDBManager = StudentDBManager.getInstance(context);
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Student student = studentList.get(position);
        holder.txtName.setText(student.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "onClick", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateStudentActivity.class);
                intent.putExtra("STUDENT", student);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.onLongItemClick(student);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
