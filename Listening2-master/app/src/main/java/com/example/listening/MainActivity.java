package com.example.listening;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 이미지버튼 리스너  소리 나오는 스피커
        ImageView sound_button = (ImageView) findViewById(R.id.Listening_evaluation_Button);
        sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.sound_toast,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //제출버튼 리스너
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Answer_input = (EditText) findViewById(R.id.Answer_input);
                if (Answer_input.getText().toString() == null){  //답 입력안하면 안 넘어감
                    Toast.makeText(
                            getApplicationContext(),
                            "답을 입력 하세요",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                else{
                    Toast.makeText(
                            getApplicationContext(),
                            Answer_input.getText(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });


        ImageView listening_back = (ImageView) findViewById(R.id.problem_back);    //뒤로가기 버튼 리스너
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        /*
        * 다이얼로그 설정 (취소,확인 있는 알림창)
        * 뒤로가기 눌렀을 때 바로가면 좀 그러니 한번 물어보는 용도
        * */
        builder.setTitle("뒤로가기")
                .setMessage("문제 풀기를 포기하실겁니까? \n포기시 결과는 저장되지 않고 이전페이지로 돌아갑니다.")
                .setCancelable(false)

                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getApplicationContext(),
                                "뒤로가기",
                                Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getApplicationContext(),
                                "문제 계속 푼다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        // 뒤로가기 이미지 누르면 다이얼로그생성
        listening_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


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
