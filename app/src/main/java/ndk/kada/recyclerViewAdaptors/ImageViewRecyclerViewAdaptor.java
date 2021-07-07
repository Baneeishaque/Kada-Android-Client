package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ndk.kada.R;

public class ImageViewRecyclerViewAdaptor extends RecyclerView.Adapter<ImageViewRecyclerViewAdaptor.ProductImageViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<Drawable> images;

    public ImageViewRecyclerViewAdaptor(Context context, ArrayList<Drawable> images) {

        layoutInflater = LayoutInflater.from(context);
        this.images = images;
    }

    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ProductImageViewHolder(layoutInflater.inflate(R.layout.list_item_image_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {

        holder.imageView.setImageDrawable(images.get(position));
    }

    @Override
    public int getItemCount() {

        return images.size();
    }

    static class ProductImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ProductImageViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
