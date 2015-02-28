package ru.terra.et.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import ru.terra.et.R;
import ru.terra.et.core.tasks.ProductLoadingAsyncTask;

public class ProductActivity extends RoboActivity {

    public static final String PARAM_PRODUCTID = "product_id";
    public static final String PARAM_CATEGORYID = "category_id";

    @InjectView(R.id.tv_name)
    private TextView name;
    @InjectView(R.id.gv_photos)
    private GridView photos;
    @InjectView(R.id.rb_rating)
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_product);
        new ProductLoadingAsyncTask(this, name, photos, rating).execute(getIntent().getIntExtra(PARAM_PRODUCTID, 0));
    }

}
