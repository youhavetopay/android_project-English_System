package com.example.multiple_choice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            create_text_view("아이즈원"+ (i+1), i);
        }

        ImageView multiple_back = new ImageView(this);
        multiple_back.setImageResource(R.drawable.ic_menu_revert);

    }

    /**
     * 영어단어 작을 때도 유동적으로 하기 위해 그때 그때 TextView 생성
     *
     * **/
    public void create_text_view(String a, int i){
        TextView view = new TextView(this);
        view.setText(a);
        view.setTextSize(FONT_SIZE);
        view.setTextColor(Color.BLACK);
        view.setBackgroundResource(R.drawable.border_radius);
        view.setGravity(Gravity.CENTER);
        view.setTag(i);                    // 여기까지 TextView 설정(글자색, 폰트크기 등등)
        view.setOnClickListener(problem_text);    // 각각의 TextView의 클릭 이벤트 설정

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //p.gravity = Gravity.CENTER;
        p.setMargins(50, 0 ,50, 20);
        view.setLayoutParams(p);

        parent_option.addView(view);
    }

    /**
     *   각각의 객관식 답의 클릭 이벤트 설정  0(1번) ~ 3(4번)
     */
    private View.OnClickListener problem_text = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int view_tag = (Integer)v.getTag();
            switch (view_tag){
                case 0:
                    Toast.makeText(getApplicationContext(), "1번 답 고름", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(), "2번 답 고름", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "3번 답 고름", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    Toast.makeText(getApplicationContext(), "4번 답 고름", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    // 아무일도 안 일어남
            }
        }
    };
    /**
     * 툴바에 있는 뒤로가기 버튼 눌렀을 때
     * **/

    @Override
    public void onBackPressed(){
        Toast.makeText(
                this,
                "뒤로가기 버튼을 눌러 \n결과를 저장하지 않고 돌아갑니다.",
                Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
