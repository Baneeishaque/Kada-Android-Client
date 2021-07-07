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

public class ProductCategoryGridRecyclerViewAdaptor extends RecyclerView.Adapter<ProductCategoryGridRecyclerViewAdaptor.ProductCategoryGridRecyclerViewHolder> {

    ArrayList<String> productCategoryNames;
    Context context;

    public ProductCategoryGridRecyclerViewAdaptor(Context context, ArrayList<String> productCategoryNames) {

        this.context = context;
        this.productCategoryNames = productCategoryNames;
    }

    @NonNull
    @Override
    public ProductCategoryGridRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ProductCategoryGridRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_product_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryGridRecyclerViewHolder holder, int position) {

        holder.textViewProductCategoryName.setText(productCategoryNames.get(position));
    }

    @Override
    public int getItemCount() {

        return productCategoryNames.size();
    }

    public static class ProductCategoryGridRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProductCategoryName;

        public ProductCategoryGridRecyclerViewHolder(View itemView) {

            super(itemView);
            textViewProductCategoryName = itemView.findViewById(R.id.textViewProductCategoryName);
        }
    }
}
