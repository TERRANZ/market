package ru.terra.et.core.tasks;

import ru.terra.et.R;
import ru.terra.et.activity.component.ProductListAdapter;
import ru.terra.et.core.network.dto.product.ProductDTO;
import ru.terra.et.provider.ProductsProvider;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class ProductsByCategoryLoadingAsyncTask extends AsyncTask<Integer, Void, ProductDTO[]>
{

	private Activity activity;
	private ListView listView;

	public ProductsByCategoryLoadingAsyncTask(Activity activity, ListView listView)
	{
		super();
		this.activity = activity;
		this.listView = listView;
	}

	@Override
	protected ProductDTO[] doInBackground(Integer... params)
	{
		return new ProductsProvider(activity).getProductsByCategory(params[0]);
	}

	@Override
	protected void onPostExecute(ProductDTO[] result)
	{
		if (result != null)
			listView.setAdapter(new ProductListAdapter(activity, R.layout.i_product_list_item, result));
	}
}
