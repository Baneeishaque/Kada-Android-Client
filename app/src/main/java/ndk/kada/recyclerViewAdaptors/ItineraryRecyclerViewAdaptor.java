package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ndk.kada.R;

public class ItineraryRecyclerViewAdaptor extends RecyclerView.Adapter<ItineraryRecyclerViewAdaptor.ItineraryRecyclerViewHolder> {

    ArrayList<String> selectedItems;
    Context context;

    public ItineraryRecyclerViewAdaptor(Context context, ArrayList<String> selectedItems) {

        this.context = context;
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public ItineraryRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItineraryRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryRecyclerViewHolder holder, int position) {

        holder.textViewItemName.setText(selectedItems.get(position));
    }

    @Override
    public int getItemCount() {

        return selectedItems.size();
    }

    public static class ItineraryRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItemName;

        public ItineraryRecyclerViewHolder(View itemView) {

            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
        }
    }
}
