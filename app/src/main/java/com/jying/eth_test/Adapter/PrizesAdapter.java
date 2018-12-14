package com.jying.eth_test.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jying.eth_test.Bean.PrizeInfo;
import com.jying.eth_test.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PRIZES = 1;
    private Context context;
    private List<PrizeInfo> list = new ArrayList<>();

    public PrizesAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PrizeInfo> infoList) {
        list.clear();
        list.addAll(infoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == PRIZES) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_prize, viewGroup, false);
            PrizeHolder prizeHolder = new PrizeHolder(view);
            return prizeHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PrizeHolder) {
            PrizeHolder vh_prize = (PrizeHolder) viewHolder;
            vh_prize.image.setImageResource(list.get(i).getImage());
            vh_prize.tv.setText(list.get(i).getInfo());
            vh_prize.tv_winner.setText("中奖人：\n" + list.get(i).getWinner());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return PRIZES;
    }

    public class PrizeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_prize_image)
        ImageView image;
        @BindView(R.id.item_prize_info)
        TextView tv;
        @BindView(R.id.item_prize_winner)
        TextView tv_winner;
        @BindView(R.id.item_prize_cardview)
        CardView cardView;

        public PrizeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
