package ru.terra.et.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import roboguice.activity.RoboListActivity;
import ru.terra.et.R;
import ru.terra.et.core.tasks.ProductsByCategoryLoadingAsyncTask;

public class CategoryActivity extends RoboListActivity {

    public static final String PARAM_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_category);
        Integer categoryId = getIntent().getIntExtra(PARAM_ID, 0);
        new ProductsByCategoryLoadingAsyncTask(this, getListView()).execute(categoryId);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, ProductActivity.class).putExtra(ProductActivity.PARAM_PRODUCTID, (Integer) v.getTag()));
    }

}
