package com.example.cbnu_sportscenter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainPageActivity extends AppCompatActivity {
    Toolbar mainToolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button cert,usage,intro,notice;
    Intent intent;  //MainPageActivity 인텐트
    String studentid,name,major,program;     //로그인할때 넘겨받은 유저아이디

    //프래그먼트 정의
    UserCertificate userCertificate;
    MypageFragment mypageFragment;
    IntroductionActivity introductionActivity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 시 넘겨받은 정보 받기
        intent=getIntent();
        studentid=intent.getStringExtra("studentid");
        name=intent.getStringExtra("name");
        major=intent.getStringExtra("major");
        program=intent.getStringExtra("program");


        //툴바
        mainToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        //네비뷰
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //왼쪽버튼 사용
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu); //왼쪽버튼 아이콘
        getSupportActionBar().setTitle("Sports Center");  //해당 액티비티의 툴바에 있는 타이틀을 공백으로 처리
        
        //네비게이션뷰 선택하는 코드
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.menu1){
                    replaceFragment(userCertificate);
                    getSupportActionBar().setTitle("이용증");
                }
                else if(id == R.id.menu2){
                    Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu3){
                    replaceFragment(introductionActivity);
                    getSupportActionBar().setTitle("소개");
                }
                else if(id == R.id.menu4){
                    Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu5){
                    replaceFragment(mypageFragment);
                    }
                return true;
            }
        });

        userCertificate=new UserCertificate();
        mypageFragment=new MypageFragment();
        introductionActivity=new IntroductionActivity();

        Bundle bundle=new Bundle();
        bundle.putString("studentid", studentid);
        bundle.putString("name", name);
        bundle.putString("major", major);
        bundle.putString("program", program);
        bundle.putString("studentid", studentid);
        userCertificate.setArguments(bundle);

        //프레그먼트 이동 버튼
        cert=(Button)findViewById(R.id.Cert);
        usage=(Button)findViewById(R.id.Usag);
        intro=(Button)findViewById(R.id.Intr);
        notice=(Button)findViewById(R.id.Noti);


        replaceFragment(userCertificate);

        cert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(userCertificate);
            }
        });
        usage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentTransaction.replace(R.id.frameLayout,userCertificate).commit();
                Toast.makeText(getApplicationContext(), "이용조회", Toast.LENGTH_SHORT).show();
            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentTransaction.replace(R.id.frameLayout,userCertificate).commit();
                replaceFragment(introductionActivity);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentTransaction.replace(R.id.frameLayout,userCertificate).commit();
                Toast.makeText(getApplicationContext(), "공지", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //프레그먼트 교체
    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //첫화면 프래그먼트 지정
        fragmentTransaction.replace(R.id.frameLayout,fragment).commit();
        //fragmentTransaction.replace(R.id.frameLayout,mypageFragment).commit();
    }
    @Override //메뉴설정
    public boolean onCreateOptionsMenu(Menu menu) { 
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar,menu);
        return true;
    }
    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home: //툴바 왼쪽위 버튼(메뉴)
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.mypage:
                // User chose the "Settings" item, show the app settings UI...
                replaceFragment(mypageFragment);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }
    @Override public void onBackPressed() { //뒤로가기 했을 때//
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


}