package com.example.hailm.sqlitedemo.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hailm.sqlitedemo.model.DBUtils;
import com.example.hailm.sqlitedemo.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDBManager {
    private static StudentDBManager INSTANCE;
    private StudentSQLiteHelper studentSQLiteHelper;

    public StudentDBManager(Context context) {
        studentSQLiteHelper = new StudentSQLiteHelper(context);
    }

    public static StudentDBManager getInstance(Context context) {
        return (INSTANCE == null ? new StudentDBManager(context) : INSTANCE);
    }

    /**
     * insert student
     *
     * @param student
     */
    public void addStudent(Student student) {
        SQLiteDatabase database = studentSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.NAME, student.getName());
        values.put(DBUtils.NUMBER, student.getNumber());
        values.put(DBUtils.EMAIL, student.getEmail());
        values.put(DBUtils.ADDRESS, student.getAddress());

        database.insert(DBUtils.STUDENT_TBL_NAME, null, values);
        database.close();
    }

    /**
     * Select student by id
     *
     * @param id
     * @return
     */
    public Student getStudentById(int id) {
        SQLiteDatabase database = studentSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DBUtils.STUDENT_TBL_NAME, new String[]{
                        DBUtils.ID, DBUtils.NAME, DBUtils.EMAIL, DBUtils.NUMBER},
                DBUtils.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Student student = new Student(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        database.close();
        return student;
    }

    /**
     * Update student
     *
     * @param student
     * @return
     */
    public int updateStudent(Student student) {
        SQLiteDatabase database = studentSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.NAME, student.getName());
        values.put(DBUtils.NUMBER, student.getNumber());
        values.put(DBUtils.EMAIL, student.getEmail());
        values.put(DBUtils.ADDRESS, student.getAddress());
        return database.update(DBUtils.STUDENT_TBL_NAME, values, DBUtils.ID + "=?"
                , new String[]{String.valueOf(student.getId())});
    }

    /**
     * get all student
     *
     * @return
     */
    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM " + DBUtils.STUDENT_TBL_NAME;

        SQLiteDatabase database = studentSQLiteHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setEmail(cursor.getString(2));
                student.setNumber(cursor.getString(3));
                student.setAddress(cursor.getString(4));
                studentList.add(student);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return studentList;
    }

    public List<Student> getAllStudentByName(String name) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM " + DBUtils.STUDENT_TBL_NAME + " WHERE NAME " + " LIKE " + "'%" + name + "%'";
        Log.d("Test", "getAllStudentByName: " + sql);
        SQLiteDatabase database = studentSQLiteHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()) {
            Student student = new Student();
            student.setId(cursor.getInt(0));
            student.setName(cursor.getString(1));
            student.setEmail(cursor.getString(2));
            student.setNumber(cursor.getString(3));
            student.setAddress(cursor.getString(4));

            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }

    /**
     * Delete student by ID
     *
     * @param id
     */
    public int deleteStudent(int id) {
        SQLiteDatabase database = studentSQLiteHelper.getWritableDatabase();
        return database.delete(DBUtils.STUDENT_TBL_NAME, DBUtils.ID + "=?"
                , new String[]{String.valueOf(id)});
    }

    public int getCountStudent() {
        String sql = "SELECT * FROM " + DBUtils.STUDENT_TBL_NAME;
        SQLiteDatabase database = studentSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
