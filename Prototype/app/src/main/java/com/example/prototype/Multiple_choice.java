package com.example.prototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Multiple_choice extends AppCompatActivity {

    private DBHelper databaseHelper;
    private SQLiteDatabase db;

    private static final float FONT_SIZE = 20;   // 선택지 TextView 때문에
    private LinearLayout parent_option;

    int answer; // 정답 저장되는 변수
    int result[] = new int[4]; // 보기 커서 위치 용

    int random; // 정답 랜덤으로 하기 위한 변수
    int result_arr_length;
    int count =0;

    int number_of_correct_answers = 0;  // 정답 횟수
    int wrong_count = 0; // 틀린횟수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        databaseHelper = new DBHelper(this);

        db = databaseHelper.getWritableDatabase();   // 쓰기 모드


        /**
         * 문제 출력하는 곳
         * 일단 임시로 mean1에 있는 것만 들고 옴  2020-05-26
         * **/
        Intent intent = getIntent();
        final String WordbookId = Long.toString(intent.getLongExtra("wordbookId", -1));
        TextView problem = (TextView) findViewById(R.id.problem);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        random = (int) (Math.random()*cursor.getCount());  // 해당 단어장의 단어DB 테이블의 행의 크기 만큼 랜덤
        cursor.moveToPosition(random);
        problem.setText(cursor.getString(1));

        parent_option = (LinearLayout) findViewById(R.id.parent_option);
        LinearLayout.LayoutParams parent_layout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parent_option.setLayoutParams(parent_layout);



        for(int i=0;i<4;i++){  // 문제 보기 중복 제거
            result[i] = (int)(Math.random()*cursor.getCount());
            for (int j=0;j<i;j++){
                if(result[i] == result[j]){
                    i--;
                }
            }
        }
        for(int i = 0; i<4; i++){
            if (result[i] == random){
                count += 1;
            }
        }                              // 랜덤으로 생성한 보기에 정답이 있으면 아래 if 패스  // 없으면 정답 랜덤으로 지정
        if(count==0){
            result_arr_length = (int) (Math.random()*3);
            result[result_arr_length] = random;
        }
        count =0;

        /**
         * 보기 생성 및 단어 가져오기
         * **/
        Cursor cu1 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu2 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu3 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu4 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        create_text_view(1, result[0], cu1);
        create_text_view(2, result[1], cu2);
        create_text_view(3, result[2], cu3);
        create_text_view(4, result[3], cu4);

        /**
         * 뒤로가기 버튼 생성
         * 객관식 답을 자바로 생성해서 xml에 추가하면 이상하게 나와서
         * 이렇게 생성함
         * **/

        ImageView multiple_back = new ImageView(this);
        multiple_back.setImageResource(R.drawable.arrows);
        LinearLayout.LayoutParams back_image = new LinearLayout.LayoutParams(150,180);
        back_image.gravity = Gravity.BOTTOM;  // 왜 아래로 정렬이 안될까??
        multiple_back.setLayoutParams(back_image);
        parent_option.addView(multiple_back);

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

        multiple_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    /**
     * 영어단어 작을 때도 유동적으로 하기 위해 그때 그때 TextView 생성
     *
     * **/
    public void create_text_view(int a, int i, Cursor cu){
        cu.moveToPosition(i);
        TextView view = new TextView(this);
        view.setText(cu.getString(2));
        view.setTextSize(FONT_SIZE);
        view.setTextColor(Color.BLACK);
        view.setBackgroundResource(R.drawable.border_radius);
        view.setGravity(Gravity.CENTER);
        view.setTag(a);                    // 여기까지 TextView 설정(글자색, 폰트크기 등등)

        view.setOnClickListener(problem_text);          // 각각의 TextView의 클릭 이벤트 설정


        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //p.gravity = Gravity.CENTER;
        p.setMargins(50, 0 ,50, 20);
        view.setLayoutParams(p);

        parent_option.addView(view);
    }
    /**
     * 보기를 누르면 출력값이 변경됨
     * 보기가 새로 만든거라서 다시 실행하면 보기가 계속생김 --> 그래서 삭제하고 다시 생성
     * **/
    public void reset_activity(){
        Intent intent = getIntent();
        final String WordbookId = Long.toString(intent.getLongExtra("wordbookId", -1));

        Cursor cu123 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        int fn = cu123.getCount();
        if(fn-1 == number_of_correct_answers+wrong_count){  // 디비에 저장된 단어수에 따라 문제생성후 끝내기
            Toast.makeText(getApplicationContext(), "정답횟수:"+number_of_correct_answers+"\n틀린횟수:"+wrong_count,Toast.LENGTH_SHORT).show();
            finish();
        }

        Toast.makeText(getApplicationContext(), "정답횟수:"+number_of_correct_answers+"\n틀린횟수:"+wrong_count,Toast.LENGTH_SHORT).show();

        parent_option.removeAllViews(); // 기존 보기 삭제


        TextView problem = (TextView) findViewById(R.id.problem);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        random = (int) (Math.random()*cursor.getCount());  // 해당 단어장의 단어DB 테이블의 행의 크기 만큼 랜덤
        cursor.moveToPosition(random);
        problem.setText(cursor.getString(1));


        for(int i=0;i<4;i++){  // 문제 보기 중복 제거
            result[i] = (int)(Math.random()*cursor.getCount());
            for (int j=0;j<i;j++){
                if(result[i] == result[j]){
                    i--;
                }
            }
        }
        for(int i = 0; i<4; i++){
            if (result[i] == random){
                count += 1;
            }
        }                              // 랜덤으로 생성한 보기에 정답이 있으면 아래 if 패스  // 없으면 정답 랜덤으로 지정
        if(count==0){
            result_arr_length = (int) (Math.random()*3);
            result[result_arr_length] = random;
        }

        count = 0;

        /**
         * 보기 생성 및 단어 가져오기
         * **/
        Cursor cu1 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu2 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu3 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        Cursor cu4 = db.rawQuery("SELECT * FROM " + DbContract.DbEntry2.TABLE_NAME + " WHERE " + DbContract.DbEntry2.WORDBOOK_ID + "=" + WordbookId,null);
        create_text_view(1, result[0], cu1);
        create_text_view(2, result[1], cu2);
        create_text_view(3, result[2], cu3);
        create_text_view(4, result[3], cu4);

        /**
         * 뒤로가기 버튼 생성
         * 객관식 답을 자바로 생성해서 xml에 추가하면 이상하게 나와서
         * 이렇게 생성함
         * **/

        ImageView multiple_back = new ImageView(this);
        multiple_back.setImageResource(R.drawable.arrows);
        LinearLayout.LayoutParams back_image = new LinearLayout.LayoutParams(150,180);
        back_image.gravity = Gravity.BOTTOM;  // 왜 아래로 정렬이 안될까??
        multiple_back.setLayoutParams(back_image);
        parent_option.addView(multiple_back);

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

        multiple_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private View.OnClickListener problem_text = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int view_tag = (Integer)v.getTag();

            switch (view_tag){
                case 1:
                    if (result[0] == random){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                        number_of_correct_answers += 1;
                        reset_activity();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                        wrong_count += 1;
                        reset_activity();
                    }
                    break;

                case 2:
                    if (result[1] == random){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                        number_of_correct_answers += 1;
                        reset_activity();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                        wrong_count += 1;
                        reset_activity();
                    }
                    break;

                case 3:
                    if (result[2] == random){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                        number_of_correct_answers += 1;
                        reset_activity();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                        wrong_count += 1;
                        reset_activity();
                    }
                    break;

                case 4:
                    if (result[3] == random){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                        number_of_correct_answers += 1;
                        reset_activity();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                        wrong_count += 1;
                        reset_activity();
                    }
                    break;

                default:
                    // 아무일도 안 일어남
            }

        }
    };

    @Override
    public void onBackPressed(){
        Toast.makeText(
                this,
                "뒤로가기 버튼을 눌러 \n결과를 저장하지 않고 돌아갑니다.",
                Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}

