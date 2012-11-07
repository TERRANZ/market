package ru.terra.et.core.tasks;

import ru.terra.et.core.network.dto.product.ProductDTO;
import ru.terra.et.provider.ProductsProvider;
import android.app.Activity;
import android.os.AsyncTask;

public class ProductLoadingAsyncTask extends AsyncTask<Integer, Void, ProductDTO>
{
	private Activity activity;

	public ProductLoadingAsyncTask(Activity activity)
	{
		super();
		this.activity = activity;
	}

	@Override
	protected ProductDTO doInBackground(Integer... params)
	{
		return new ProductsProvider(activity).getProduct(params[0]);
	}

	@Override
	protected void onPostExecute(ProductDTO result)
	{
		super.onPostExecute(result);
	}

}
