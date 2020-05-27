package com.example.prototype;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

public class Problem_result_activity extends AppCompatActivity {

    TextView result;
    Button save_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_result_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();

        int answer_count = intent.getExtras().getInt("answer_count");
        int wrong_count = intent.getExtras().getInt("wrong_count");

        result = (TextView) findViewById(R.id. scoring_result);
        result.setText("정답 횟수: "+answer_count + "\n틀린횟수: "+wrong_count);
        result.setTextSize(30);
        result.setGravity(Gravity.CENTER);
        result.setTextColor(Color.BLACK);

        save_number = (Button) findViewById(R.id.button5);
    }
}
