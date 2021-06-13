package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.BarDataSet;

import java.util.ArrayList;

import CSDL_bean.CauHoi;
import DAO.CauHoiDAO;
import DAO.UserDAO;

public class AudienceLayout extends AppCompatActivity {
    BarChart troGiupBar;
    int idCauhoi;
    int cotDung;
    private static final String[] dapAn = { "A", "B", "C", "D"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_layout);
        getSupportActionBar().hide();

        idCauhoi = Integer.parseInt(getIntent().getStringExtra("idCauHoi"));
        setControl();
        cotDung = getCauHoi();
//        cotDung = 2; // 1 = A, ... ;4 = D
        veDoThi(cotDung);
    }

    private void veDoThi(int cotDung) {
        BarData data= taoDataChart();
        setupChartAppear();
        chuanBiChar(data);
    }

    private void chuanBiChar(BarData data) {
        data.setValueTextSize(16f);
        troGiupBar.setData(data);
//        troGiupBar.invalidate();
    }

    private void setupChartAppear() {
        troGiupBar.getDescription().setEnabled(false);
        troGiupBar.setDrawValueAboveBar(false);
        XAxis xAxis = troGiupBar.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                System.out.println(value);
                return dapAn[(int)value] ;
            }
        });
        xAxis.setTextColor(Color.WHITE);
        YAxis axisLeft = troGiupBar.getAxisLeft();
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = troGiupBar.getAxisRight();
        axisRight.setAxisMinimum(0);
        troGiupBar.getXAxis().setAxisMaximum(4f);
    }

    private BarData taoDataChart() {
        ArrayList values = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            float x = (float)i;
            float y=1f;
            if((i+1) == cotDung){
                y = 7f;
            }
            values.add(new BarEntry(x, y));
        }

        BarDataSet set1 = new BarDataSet(values,"");
        BarData data = new BarData(set1);
        return data;
    }

    private int getCauHoi() {
        CauHoi cauHoi = CauHoiDAO.timCauHoiTuID(idCauhoi, new CSDLAilatrieuphu(this));
        String da1,da2,da3,da4,dad;
        da1 = cauHoi.getDapAn()[0];
        da2 = cauHoi.getDapAn()[1];
        da3 = cauHoi.getDapAn()[2];
        da4 = cauHoi.getDapAn()[3];
        dad = cauHoi.getDapAnDung();
        if(da1.equals(dad)) return 1;
        if(da2.equals(dad)) return 2;
        if(da3.equals(dad)) return 3;
        if(da4.equals(dad)) return 4;
        return 0;
    }

    private void setControl() {
        troGiupBar = findViewById(R.id.troGiupKhanGiaChart_AL);
    }

    public void onClickDong(View view) {
        this.finish();
    }
}