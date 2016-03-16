package com.l24o.testtaskseclab;

import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.math.BigDecimal;
import java.util.List;
import java.util.Stack;

import yahoofinance.Stock;


/**
 * Created by l24o on 16.03.16.
 */
public class YahooRecyclerViewAdapter extends RecyclerView.Adapter<YahooRecyclerViewAdapter.ViewHolder> {


    private List<Stock> data;

    public YahooRecyclerViewAdapter(List<Stock> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stock stock = data.get(position);
        holder.item = stock;
        holder.tvSName.setText(stock.getSymbol());
        holder.tvName.setText(stock.getName());
        holder.tvPrice.setText(stock.getQuote().getPrice().toString() + "$");
        BigDecimal change = stock.getQuote().getChange();
        if (change.intValue() > 0) {
            holder.tvDin.setTextColor(holder.mView.getResources().getColor(R.color.up));
        } else {
            holder.tvDin.setTextColor(holder.mView.getResources().getColor(R.color.down));
        }
        holder.tvDin.setText(change.toString());
        holder.tvClosed.setText(stock.getQuote().getPreviousClose().toString());
        holder.tvRange.setText(String.format("%s - %s",stock.getQuote().getDayLow().toString(), stock.getQuote().getDayHigh().toString()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSName;
        private TextView tvName;
        private TextView tvDin;
        private TextView tvClosed;
        private TextView tvPrice;
        private TextView tvRange;
        private final View mView;
        private Stock item;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvSName = (TextView) itemView.findViewById(R.id.tvShortName);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDin = (TextView) itemView.findViewById(R.id.tvDin);
            tvClosed = (TextView) itemView.findViewById(R.id.tvClosed);
            tvRange = (TextView) itemView.findViewById(R.id.tvRange);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }

    public void setData(List<Stock> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }


}
