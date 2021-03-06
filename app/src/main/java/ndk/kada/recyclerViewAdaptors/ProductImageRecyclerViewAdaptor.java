package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ndk.kada.objectModels.ProductImageModel;
import ndk.kada.R;

public class ProductImageRecyclerViewAdaptor extends RecyclerView.Adapter<ProductImageRecyclerViewAdaptor.ProductImageViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<ProductImageModel> productImages;

    public ProductImageRecyclerViewAdaptor(Context context, ArrayList<ProductImageModel> productImages) {

        layoutInflater = LayoutInflater.from(context);
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ProductImageViewHolder(layoutInflater.inflate(R.layout.list_item_product_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {

        holder.imageViewProduct.setImageDrawable(productImages.get(position).getImageDrawable());
    }

    @Override
    public int getItemCount() {

        return productImages.size();
    }

    static class ProductImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;

        public ProductImageViewHolder(@NonNull View itemView) {

            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}
