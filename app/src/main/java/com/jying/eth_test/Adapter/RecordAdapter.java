package com.jying.eth_test.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jying.eth_test.Bean.RecordBean;
import com.jying.eth_test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RECORD = 1;

    private Context context;
    private List<RecordBean> list = new ArrayList<>();

    public RecordAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<RecordBean> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == RECORD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_record, viewGroup, false);
            RecordHolder holder = new RecordHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof RecordHolder) {
            RecordHolder vh = (RecordHolder) viewHolder;
            if (list.get(i).getFlag().intValue() == 0) {
                vh.tv_flag.setText("消耗");
                vh.tv_ak.setText("-" + list.get(i).getAk().toString() + "ak");
            } else if (list.get(i).getFlag().intValue() == 1) {
                vh.tv_flag.setText("收益");
                vh.tv_ak.setText("+" + list.get(i).getAk().toString() + "ak");
            }
            long timestamp = list.get(i).getTime().intValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date(timestamp * 1000));
            vh.tv_time.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return RECORD;
    }

    public class RecordHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_record_flag)
        TextView tv_flag;
        @BindView(R.id.item_record_ak)
        TextView tv_ak;
        @BindView(R.id.item_record_time)
        TextView tv_time;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
