package com.example.multiple_choice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
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

    private MySQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;

    int result[] = new int[4]; // 보기 커서 위치 용
    int answer; // 정답커서 위치

    int random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new MySQLiteOpenHelper(this);
        try {
            db = databaseHelper.getReadableDatabase();   //읽기 모드
        } catch (SQLException e) {
            db = databaseHelper.getWritableDatabase();   // 쓰기 모드
    }

        /**
        db.execSQL("insert into izone1 values(null,'" + "김채원" + "'," + "'" + "6" + "');");
        db.execSQL("insert into izone1 values(null,'" + "김민주" + "'," + "'" + "7" + "');");
        db.execSQL("insert into izone1 values(null,'" + "혼다히토미" + "'," + "'" + "8" + "');");
        db.execSQL("insert into izone1 values(null,'" + "야부키나코" + "'," + "'" + "9" + "');");
         db.execSQL("insert into izone1 values(null,'" + "조유리" + "'," + "'" + "10" + "');");
         db.execSQL("insert into izone1 values(null,'" + "안유진" + "'," + "'" + "11" + "');");
         db.execSQL("insert into izone1 values(null,'" + "장원영" + "'," + "'" + "12" + "');");
        Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
         **/

        // 영단어(문제) 출력해주는 곳
        Cursor cursor = db.rawQuery("select * from izone1",null);
        cursor.moveToFirst();
        cursor.moveToNext();
        cursor.moveToNext();
        cursor.moveToNext();
        TextView problem = (TextView) findViewById(R.id.problem);
        problem.setText(cursor.getString(1));
        answer = cursor.getPosition();



        parent_option = (LinearLayout) findViewById(R.id.parent_option);
        LinearLayout.LayoutParams parent_layout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parent_option.setLayoutParams(parent_layout);

        /**
        Cursor cu = db.rawQuery("select * from izone1",null);
        cu.moveToFirst();
        cu.moveToNext();

         **/
        Cursor cu1 = db.rawQuery("select * from izone1",null);
        Cursor cu2 = db.rawQuery("select * from izone1",null);
        Cursor cu3 = db.rawQuery("select * from izone1",null);
        Cursor cu4 = db.rawQuery("select * from izone1",null);
        create_text_view("아이즈원"+ (1), 1, cu1);
        create_text_view("아이즈원"+ (2), 2, cu2);
        create_text_view("아이즈원"+ (3), 3, cu3);
        create_text_view("아이즈원"+ (4), 4, cu4);



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
    public void create_text_view(String a, int i, Cursor cu){
        cu.moveToPosition(i);
        TextView view = new TextView(this);
        view.setText(cu.getString(1));
        view.setTextSize(FONT_SIZE);
        view.setTextColor(Color.BLACK);
        view.setBackgroundResource(R.drawable.border_radius);
        view.setGravity(Gravity.CENTER);
        view.setTag(i);                    // 여기까지 TextView 설정(글자색, 폰트크기 등등)

        view.setOnClickListener(problem_text);          // 각각의 TextView의 클릭 이벤트 설정

        result[i-1] = cu.getPosition();

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
                    case 1:
                        if (result[0] == answer){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if (result[1] == answer){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 3:
                    if (result[2] == answer){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 4:
                    if (result[3] == answer){
                        Toast.makeText(getApplicationContext(),"정답",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"틀림",Toast.LENGTH_SHORT).show();
                    }
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

class MySQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "word.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE_NAME = "word";

    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table izone1(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, " +
                "age TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists izone");
        onCreate(db);

    }
}


