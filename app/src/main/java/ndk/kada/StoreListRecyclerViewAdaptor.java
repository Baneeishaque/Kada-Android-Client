package ndk.kada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreListRecyclerViewAdaptor extends RecyclerView.Adapter<StoreListRecyclerViewAdaptor.StoreListRecyclerViewHolder> {

    ArrayList<StoreModel> productCategoryNames;
    Context context;

    public StoreListRecyclerViewAdaptor(Context context, ArrayList<StoreModel> productCategoryNames) {

        this.context = context;
        this.productCategoryNames = productCategoryNames;
    }

    @NonNull
    @Override
    public StoreListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new StoreListRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListRecyclerViewHolder holder, int position) {

        holder.textViewStoreName.setText(productCategoryNames.get(position).getStoreName());
        holder.textViewStoreRating.setText(String.valueOf(productCategoryNames.get(position).getStoreRating()));
        holder.textViewStoreAverageDeliveryTime.setText("Fast Delivery Within " + productCategoryNames.get(position).getStoreAverageDeliveryTime() + " Hour");
    }

    @Override
    public int getItemCount() {

        return productCategoryNames.size();
    }

    public static class StoreListRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreRating, textViewStoreAverageDeliveryTime;

        public StoreListRecyclerViewHolder(View itemView) {

            super(itemView);
            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreRating = itemView.findViewById(R.id.textViewStoreRating);
            textViewStoreAverageDeliveryTime = itemView.findViewById(R.id.textViewStoreAverageDeliveryTime);
        }
    }
}
