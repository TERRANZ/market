package ru.terra.et.core.tasks;

import ru.terra.et.R;
import ru.terra.et.activity.component.ProductListAdapter;
import ru.terra.et.core.network.dto.product.ProductDTO;
import ru.terra.et.provider.ProductsProvider;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class ProductsByKindLoadingAsyncTask extends AsyncTask<String, Void, ProductDTO[]>
{

	private Activity activity;
	private ListView listView;

	public ProductsByKindLoadingAsyncTask(Activity activity, ListView listView)
	{
		super();
		this.activity = activity;
		this.listView = listView;
	}

	@Override
	protected ProductDTO[] doInBackground(String... params)
	{
		return new ProductsProvider(activity).getProductsByKind(params[0], 3);
	}

	@Override
	protected void onPostExecute(ProductDTO[] result)
	{
		listView.setAdapter(new ProductListAdapter(activity, R.layout.i_product_list_item, result));
	}
}