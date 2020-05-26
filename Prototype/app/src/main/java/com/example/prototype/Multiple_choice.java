package com.example.prototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Multiple_choice extends AppCompatActivity {

    private DBHelper databaseHelper;
    private SQLiteDatabase db;

    private static final float FONT_SIZE = 20;   // 선택지 TextView 때문에
    private LinearLayout parent_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        databaseHelper = new DBHelper(this);

        db = databaseHelper.getWritableDatabase();   // 쓰기 모드

       // db.execSQL("create view if not exists word_view as select word.spell, word.mean1, word.wordbook_id from word");
        Intent intent = getIntent();
        final String WordbookId = Long.toString(intent.getLongExtra("wordbookId", -1));
        TextView problem = (TextView) findViewById(R.id.problem);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        cursor.moveToPosition(2);
        Toast.makeText(getApplicationContext(), "wadawd", Toast.LENGTH_SHORT).show();
        problem.setText(cursor.getString(1));

    }
}

