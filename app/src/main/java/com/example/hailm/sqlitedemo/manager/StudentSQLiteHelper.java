package com.example.hailm.sqlitedemo.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.hailm.sqlitedemo.model.DBUtils;

public class StudentSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Student_list";
    private static final int DB_VERSON = 1;
    private static final String TAG = "StudentSQLiteHelper";
    private Context context;

    private static final String CREATE_STUDENT_TBL =
            "CREATE TABLE " + DBUtils.STUDENT_TBL_NAME + " (" +
                    DBUtils.ID + " integer primary key, " +
                    DBUtils.NAME + " TEXT, " +
                    DBUtils.EMAIL + " TEXT, " +
                    DBUtils.NUMBER + " TEXT, " +
                    DBUtils.ADDRESS + " TEXT)";

    public StudentSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSON);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TBL);
        Log.d(TAG, "onCreate: ");
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBUtils.STUDENT_TBL_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onUpgrade: ");
    }
}
