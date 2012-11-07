package ru.terra.et.core.tasks;

import ru.terra.et.activity.component.GridImagesAdapter;
import ru.terra.et.core.network.dto.product.ProductDTO;
import ru.terra.et.provider.ProductsProvider;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProductLoadingAsyncTask extends AsyncTask<Integer, Void, ProductDTO>
{
	private Activity activity;
	private RatingBar rating;
	private GridView photos;
	private TextView name;

	public ProductLoadingAsyncTask(Activity activity, TextView name, GridView photos, RatingBar rating)
	{
		super();
		this.activity = activity;
		this.name = name;
		this.photos = photos;
		this.rating = rating;
	}

	@Override
	protected ProductDTO doInBackground(Integer... params)
	{
		return new ProductsProvider(activity).getProduct(params[0]);
	}

	@Override
	protected void onPostExecute(ProductDTO result)
	{
		if (result != null)
		{
			name.setText(result.name);
			rating.setRating(result.rating);
			if (result.photos != null && result.photos.length > 0)
				photos.setAdapter(new GridImagesAdapter(activity, result.photos));
		}
	}

}
