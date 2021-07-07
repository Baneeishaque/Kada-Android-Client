package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ndk.kada.R;
import ndk.kada.objectModels.StoreModel;

public class StoreListRecyclerViewAdaptor extends RecyclerView.Adapter<StoreListRecyclerViewAdaptor.StoreListRecyclerViewHolder> {

    ArrayList<StoreModel> stores;
    Context context;

    public StoreListRecyclerViewAdaptor(Context context, ArrayList<StoreModel> stores) {

        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    public StoreListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new StoreListRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListRecyclerViewHolder holder, int position) {

        StoreModel store = stores.get(position);
        holder.textViewStoreName.setText(store.getStoreName());
        holder.textViewStoreName.setTag(store);
        holder.textViewStoreRating.setText(String.valueOf(store.getStoreRating()));
        holder.textViewStoreAverageDeliveryTime.setText("Fast Delivery Within " + store.getStoreAverageDeliveryTime() + " Hour");
    }

    @Override
    public int getItemCount() {

        return stores.size();
    }

    public interface OnItemClickListener {

        void onItemClick(TextView textViewStoreName);
    }

    private OnItemClickListener onItemClickListener;

    public class StoreListRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStoreName, textViewStoreRating, textViewStoreAverageDeliveryTime;
        ImageView imageViewStoreImage;

        public StoreListRecyclerViewHolder(View itemView) {

            super(itemView);
            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewStoreRating = itemView.findViewById(R.id.textViewStoreRating);
            textViewStoreAverageDeliveryTime = itemView.findViewById(R.id.textViewStoreAverageDeliveryTime);
            imageViewStoreImage = itemView.findViewById(R.id.imageViewStorePhoto);
            imageViewStoreImage.setOnClickListener(v -> onItemClickListener.onItemClick(textViewStoreName));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }
}
