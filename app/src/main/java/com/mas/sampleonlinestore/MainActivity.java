package com.mas.sampleonlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.loopj.android.image.SmartImageView;
import com.mas.sampleonlinestore.adapter.ItemGridAdapter;
import com.mas.sampleonlinestore.adapter.ProductAdapter;
import com.mas.sampleonlinestore.adapter.SliderImageAdapter;
import com.mas.sampleonlinestore.model.CategoryModel;
import com.mas.sampleonlinestore.model.ProductModel;
import com.mas.sampleonlinestore.util.Constant;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SliderView sliderView;
    TextView greatText;
    private LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmartImageView myImage = (SmartImageView) this.findViewById(R.id.sivItemProduct);

        toolbar = findViewById(R.id.toolbar);
        sliderView = findViewById(R.id.svImageSlider);
        greatText = findViewById(R.id.tvGreetingText);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        llRoot = findViewById(R.id.llContentMain);

        loadjson(llRoot, "Terlaris", 0, 25);
        for (String cardtitle : Constant.cards.keySet()) {
            View card = getLayoutInflater().inflate(R.layout.item_card, null);
            RecyclerView rv = card.findViewById(R.id.rvCardListView);
            rv.setNestedScrollingEnabled(false);
            TextView tv = card.findViewById(R.id.tvCardTextView);
            tv.setText(cardtitle);
            Map<Integer, String> cats = Constant.cards.get(cardtitle);
            List<CategoryModel> datacat = new ArrayList<CategoryModel>();
            for (int ic : cats.keySet()) {
                datacat.add(new CategoryModel(ic, ic, cats.get(ic), false));
            }
            rv.addItemDecoration(new SimpleDividerItemDecoration(this));
            rv.setAdapter(new ItemGridAdapter(datacat));
            rv.setLayoutManager(new GridLayoutManager(this, 2));

            llRoot.addView(card);
        }
        loadjson(llRoot, "Produk Terbaru", 26, 0);

        final SliderImageAdapter sliderImageAdapter = new SliderImageAdapter(this);
        sliderImageAdapter.setCount(4);
        sliderView.setSliderAdapter(sliderImageAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        greeting();
    }

    private void greeting(){
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12){
            greatText.setText(getString(R.string.salam_pagi));
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greatText.setText(getString(R.string.salam_siang));
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greatText.setText(getString(R.string.salam_sore));
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            greatText.setText(getString(R.string.salam_malam));
        }
    }

    private void loadjson(LinearLayout llRoot, String title, int startPos, int endPos){
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.bldata);

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            View view = getLayoutInflater().inflate(R.layout.product_horizontal, null);
            RecyclerView recyclerView = view.findViewById(R.id.rvProductHorizontal);
            TextView textView = view.findViewById(R.id.tvProductHorizontal);
            textView.setText(title);

            List<ProductModel> productModelList = new ArrayList<ProductModel>();
            JSONArray jsonArray = new JSONObject(new String(b)).getJSONArray("products");
            for (int i = startPos; i < (endPos == 0? jsonArray.length() : endPos); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name =  jsonObject.getString("name");
                long price = jsonObject.getLong("price");
                long oprice = price - 1000;
                String store= jsonObject.getString("seller_name");
                String img = jsonObject.getJSONArray("images").getString(0);
                float rating = Float.parseFloat(jsonObject.getJSONObject("rating").getString("average_rate") + "f");
                productModelList.add(new ProductModel(name, store, img, price, oprice, 10, rating));
            }

            recyclerView.setAdapter(new ProductAdapter(productModelList, this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            llRoot.addView(view);
        } catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context){
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++){
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public static Drawable setTint(Drawable d, int color){
        Drawable wrappedDrawble = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawble, color);
        return wrappedDrawble;
    }
}