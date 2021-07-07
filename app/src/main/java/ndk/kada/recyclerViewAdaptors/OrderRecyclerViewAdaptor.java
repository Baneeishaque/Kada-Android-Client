package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ndk.kada.R;
import ndk.kada.objectModels.OrderModel;
import ndk.kada.utils.KadaApiUtils;
import ndk.utils_android1.DateUtils1;

public class OrderRecyclerViewAdaptor extends RecyclerView.Adapter<OrderRecyclerViewAdaptor.ProductImageViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<OrderModel> orders;
    public static SimpleDateFormat orderDateTimeFormat = new SimpleDateFormat("dd MMM. h:mm a", Locale.UK);

    public OrderRecyclerViewAdaptor(Context context, ArrayList<OrderModel> orders) {

        layoutInflater = LayoutInflater.from(context);
        this.orders = orders;
    }

    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ProductImageViewHolder(layoutInflater.inflate(R.layout.list_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {

        OrderModel order = orders.get(position);
        holder.textViewOrderShopName.setText(order.getShopOrUserName());
        holder.textViewOrderId.setText("Id : " + order.getOrderId());
        holder.textViewOrderDateTime.setText("On " + DateUtils1.dateTimeToCustomDateTimeString(order.getOrderDateTime(), orderDateTimeFormat));
        Picasso.get()
                .load(new KadaApiUtils().getOrderImageUrl(order.getOrderId()))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.roundedImageViewOrderImage);
    }

    @Override
    public int getItemCount() {

        return orders.size();
    }

    public void updateList(ArrayList<OrderModel> orders){

        this.orders=orders;
        notifyDataSetChanged();
    }

    static class ProductImageViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView roundedImageViewOrderImage;
        TextView textViewOrderShopName, textViewOrderId, textViewOrderDateTime;

        public ProductImageViewHolder(@NonNull View itemView) {

            super(itemView);
            roundedImageViewOrderImage = itemView.findViewById(R.id.roundedImageViewOrderImage);
            textViewOrderShopName = itemView.findViewById(R.id.textViewOrderShopName);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDateTime = itemView.findViewById(R.id.textViewOrderDateTime);
        }
    }
}
