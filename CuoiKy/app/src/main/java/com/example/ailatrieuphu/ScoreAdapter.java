package com.example.ailatrieuphu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import CSDL_bean.BangXepHang;

public class ScoreAdapter extends ArrayAdapter<BangXepHang> {
    Context parentContext;
    int resource;
    ArrayList<BangXepHang> data;
    CSDLAilatrieuphu db;

    public void setDb(CSDLAilatrieuphu db) {
        this.db = db;
    }

    public ScoreAdapter(@NonNull Context context, int resource, ArrayList<BangXepHang> data) {
        super(context, resource);
        this.parentContext = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parentContext).inflate(R.layout.score_custom_listview, parent, false);

        // ánh xạ
        TextView diemTv = convertView.findViewById(R.id.diemTv_ScoreListView);
        TextView ngayTv = convertView.findViewById(R.id.ngayTv_ScoreListView);

        // gán data lên view
        BangXepHang bangXepHang = data.get(position);
        diemTv.setText(String.valueOf(bangXepHang.getDiem()));
        ngayTv.setText(bangXepHang.getNgay());
        return convertView;
    }
}
