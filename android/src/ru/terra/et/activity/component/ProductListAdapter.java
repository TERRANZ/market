package ru.terra.et.activity.component;

import lazylist.ImageLoader;
import ru.terra.et.R;
import ru.terra.et.core.constants.ImageType;
import ru.terra.et.core.network.dto.photo.PhotoDTO;
import ru.terra.et.core.network.dto.product.ProductDTO;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends ArrayAdapter<ProductDTO>
{

	private ImageLoader il;

	public ProductListAdapter(Activity context, int resource, ProductDTO[] objects)
	{
		super(context, resource, objects);
		il = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = LayoutInflater.from(getContext()).inflate(R.layout.i_product_list_item, null);
		((TextView) v.findViewById(R.id.tv_name)).setText(getItem(position).name);
		if (getItem(position).photos != null && getItem(position).photos.length > 0)
		{
			PhotoDTO p = getItem(position).photos[0];
			il.DisplayImage(p.path, ((ImageView) v.findViewById(R.id.iv_photo)), ImageType.full);
		}

		return super.getView(position, convertView, parent);
	}
}
