package com.example.multiple_choice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final float FONT_SIZE = 20;   // 선택지 TextView 때문에
    private LinearLayout parent_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 영단어(문제) 출력해주는 곳
        TextView problem = (TextView) findViewById(R.id.problem);
        problem.setText(R.string.problem);


        parent_option = (LinearLayout) findViewById(R.id.parent_option);
        for(int i = 0;i<4;i++){ // 여기서 생성되는 갯수 조정 밎 단어 조정
            create_textview("아이즈원");
        }

    }

    /**
     * 영어단어 작을 때도 유동적으로 하기 위해 그때 그때 TextView 생성
     *
     * **/
    public void create_textview(String a){
        TextView view = new TextView(this);
        view.setText(a);
        view.setTextSize(FONT_SIZE);
        view.setTextColor(Color.BLACK);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER;
        view.setLayoutParams(p);

        parent_option.addView(view);
    }
}
