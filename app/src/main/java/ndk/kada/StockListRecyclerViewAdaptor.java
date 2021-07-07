package ndk.kada;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StockListRecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<StockModal> stocks;
    Context context;

    public StockListRecyclerViewAdaptor(Context context, ArrayList<StockModal> stocks) {

        this.context = context;
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public StockListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StockListRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stock, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        StockModal itemStock = stocks.get(position);
        StockListRecyclerViewHolder stockListRecyclerViewHolder = (StockListRecyclerViewHolder) holder;

        stockListRecyclerViewHolder.textViewItemName.setText(itemStock.getItemName());
        stockListRecyclerViewHolder.textViewIndianRupeeSymbolItemMaximumRetailPrice.setPaintFlags(stockListRecyclerViewHolder.textViewIndianRupeeSymbolItemMaximumRetailPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        stockListRecyclerViewHolder.textViewItemMaximumRetailPrice.setText(String.valueOf(itemStock.getItemMaximumRetailPrice()));
        stockListRecyclerViewHolder.textViewItemMaximumRetailPrice.setPaintFlags(stockListRecyclerViewHolder.textViewItemMaximumRetailPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        stockListRecyclerViewHolder.textViewItemSellingPrice.setText(String.valueOf(itemStock.getItemSellingPrice()));

        stockListRecyclerViewHolder.switchItemStockOnOff.setTag(itemStock.itemId);
        if (itemStock.isInStock()) {

            stockListRecyclerViewHolder.textViewItemStockOnOff.setText("In Stock");
            stockListRecyclerViewHolder.switchItemStockOnOff.setChecked(true);

        } else {

            stockListRecyclerViewHolder.textViewItemStockOnOff.setText("Out of Stock");
            stockListRecyclerViewHolder.textViewItemStockOnOff.setTextColor(context.getResources().getColor(R.color.red2));
            stockListRecyclerViewHolder.switchItemStockOnOff.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {

        return stocks.size();
    }

    private OnSwitchItemStockOnOffClickListener onSwitchItemStockOnOffClickListener;

    public interface OnSwitchItemStockOnOffClickListener {

        void onSwitchItemStockOnOffClick(SwitchCompat switchItemStockOnOff, TextView textViewItemOnOff);
    }

    public class StockListRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItemName, textViewItemMaximumRetailPrice, textViewIndianRupeeSymbolItemMaximumRetailPrice, textViewItemSellingPrice, textViewItemStockOnOff;
        SwitchCompat switchItemStockOnOff;

        public StockListRecyclerViewHolder(View itemView) {

            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemMaximumRetailPrice = itemView.findViewById(R.id.textViewItemMaximumRetailPrice);
            textViewIndianRupeeSymbolItemMaximumRetailPrice = itemView.findViewById(R.id.textViewIndianRupeeSymbolItemMaximumRetailPrice);
            textViewItemSellingPrice = itemView.findViewById(R.id.textViewItemSellingPrice);
            textViewItemStockOnOff = itemView.findViewById(R.id.textViewItemStockOnOff);
            switchItemStockOnOff = itemView.findViewById(R.id.switchItemStockOnOff);

            switchItemStockOnOff.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onSwitchItemStockOnOffClickListener.onSwitchItemStockOnOffClick(switchItemStockOnOff, textViewItemStockOnOff);
                }
            });
        }
    }

    public void setOnSwitchItemStockOnOffClickListener(final OnSwitchItemStockOnOffClickListener onSwitchItemStockOnOffClickListener) {

        this.onSwitchItemStockOnOffClickListener = onSwitchItemStockOnOffClickListener;
    }
}
