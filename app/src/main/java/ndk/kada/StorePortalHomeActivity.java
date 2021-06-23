package ndk.kada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StorePortalHomeActivity extends KadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_portal_home);

        RecyclerView recyclerViewProductCategoryGrid = findViewById(R.id.recyclerViewProductCategories);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(currentApplicationContext, 3);
        recyclerViewProductCategoryGrid.setLayoutManager(gridLayoutManager);

        ArrayList<String> productCategoryNames=new ArrayList<>();
        productCategoryNames.add("Grocery");
        productCategoryNames.add("Vegetables");
        productCategoryNames.add("Fruits");
        productCategoryNames.add("Meats");
        productCategoryNames.add("Fish");
        productCategoryNames.add("Home Made");

        recyclerViewProductCategoryGrid.setAdapter(new ProductCategoryGridRecyclerViewAdaptor(currentActivityContext,productCategoryNames));
    }
}