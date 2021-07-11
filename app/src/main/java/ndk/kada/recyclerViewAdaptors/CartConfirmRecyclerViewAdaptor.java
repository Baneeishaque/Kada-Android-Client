package ndk.kada.recyclerViewAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ndk.kada.R;
import ndk.kada.objectModels.CartProductModel;
import ndk.utils_android1.DateUtils1;

public class CartConfirmRecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String currentCartProductShop = "";

    LayoutInflater layoutInflater;
    ArrayList<CartProductModel> cartProducts;
    public static SimpleDateFormat orderDateTimeFormat = new SimpleDateFormat("dd MMM. h:mm a", Locale.UK);

    public CartConfirmRecyclerViewAdaptor(Context context, ArrayList<CartProductModel> cartProducts) {

        layoutInflater = LayoutInflater.from(context);
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CartProductViewHolder(layoutInflater.inflate(R.layout.list_item_cart_confirm, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CartProductModel cartProduct = cartProducts.get(position);
        CartProductViewHolder cartProductHolder = (CartProductViewHolder) holder;
        cartProductHolder.textViewCartProductName.setText("Item : " + cartProduct.getCartProductName());
        cartProductHolder.textViewCartProductQuantity.setText("Qty : " + cartProduct.getCartProductQuantity());
        cartProductHolder.textViewCartProductAddedDateTime.setText("Added On " + DateUtils1.dateTimeToCustomDateTimeString(cartProduct.getCartProductAddedOn(), orderDateTimeFormat));

        if (weatherToDisplayShopName(cartProduct)) {

            currentCartProductShop = cartProduct.getShopName();
            cartProductHolder.textViewCartProductShopName.setText(currentCartProductShop);

        } else {

            cartProductHolder.textViewCartProductShopName.setVisibility(View.GONE);
        }
    }

    public boolean weatherToDisplayShopName(CartProductModel cartProduct) {

        if (currentCartProductShop.isEmpty()) {

            return true;

        } else {

            if (currentCartProductShop.equals(cartProduct.getShopName())) {

                return false;

            } else {

                return true;
            }
        }
    }

    @Override
    public int getItemCount() {

        return cartProducts.size();
    }

//    public void updateList(ArrayList<CartProductModel> orders) {
//
//        this.cartProducts = orders;
//        notifyDataSetChanged();
//    }

    static class CartProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCartProductName, textViewCartProductQuantity, textViewCartProductAddedDateTime, textViewCartProductShopName;

        public CartProductViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewCartProductName = itemView.findViewById(R.id.textViewCartProductName);
            textViewCartProductQuantity = itemView.findViewById(R.id.textViewCartProductQuantity);
            textViewCartProductAddedDateTime = itemView.findViewById(R.id.textViewCartProductAddedDateTime);
            textViewCartProductShopName = itemView.findViewById(R.id.textViewCartProductShopName);
        }
    }
}
