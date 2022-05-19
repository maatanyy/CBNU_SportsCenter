package com.example.cbnu_sportscenter;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Set;


public class MypageFragment extends Fragment {

    TextView userprogram;
    Button btn_update;
    UpdateFragment updatefragment;
    String studentid,program;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mypage, container, false);
        view.findViewById(R.id.mypage);

        userprogram=view.findViewById(R.id.userProgram);
        btn_update=view.findViewById(R.id.btn_update);
        updatefragment=new UpdateFragment();



        bundle=getArguments();
        Set<String> keys = bundle.keySet();


        if(keys.size()>0){  //정보가 정상적으로 전달되었을때
            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getActivity().getApplicationContext());
            studentid=bundle.getString("studentid");
            program=dbHelper.getProgram(studentid);
            userprogram.setText("프로그램: "+program);
        }


        else{       //정보가 정상적으로 전달되지 않았을때
            Toast.makeText(container.getContext(), "bundle count null", Toast.LENGTH_SHORT).show();
        }




        BarChart barChart = view.findViewById(R.id.barchart);

        //샘플 데이터
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2014, 420));
        visitors.add(new BarEntry(2015, 450));
        visitors.add(new BarEntry(2016, 520));
        visitors.add(new BarEntry(2017, 620));
        visitors.add(new BarEntry(2018, 540));
        visitors.add(new BarEntry(2019, 720));
        visitors.add(new BarEntry(2020, 920));

        BarDataSet barDataSet = new BarDataSet(visitors, "hello");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("studentid", studentid);
                updatefragment.setArguments(bundle);
                ((MainPageActivity)getActivity()).getSupportFragmentManager().
                        beginTransaction().replace(R.id.frameLayout, updatefragment).commit();;
            }
        });


        return view;
    }
}