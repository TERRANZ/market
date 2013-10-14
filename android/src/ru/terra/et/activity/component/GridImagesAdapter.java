package ru.terra.et.activity.component;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import lazylist.ImageLoader;
import ru.terra.et.core.constants.ImageType;
import ru.terra.et.core.network.dto.photo.PhotoDTO;

public class GridImagesAdapter extends BaseAdapter {
    private int galleryItemBackground;
    private Activity context;
    private PhotoDTO[] photos;
    private ImageLoader ip;

    public GridImagesAdapter(Activity c, PhotoDTO[] photos) {
        context = c;
        this.photos = photos;
        ip = new ImageLoader(c);
    }

    public int getCount() {
        return photos.length;
    }

    public Object getItem(int position) {
        return photos[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        ip.DisplayImage(photos[position].path, imageView, ImageType.full);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundResource(galleryItemBackground);
        imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
        imageView.setAdjustViewBounds(false);
        // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        return imageView;
    }
}