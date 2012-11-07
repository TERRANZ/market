package lazylist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.terra.et.R;
import ru.terra.et.core.constants.ImageType;
import ru.terra.et.core.network.HttpRequestHelper;
import ru.terra.et.util.Logger;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageLoader
{

	ImagesMemoryCache memoryCache = new ImagesMemoryCache();
	ImagesFileCache fileCache;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	HttpRequestHelper httpReqHelper;

	public ImageLoader(Activity context)
	{
		fileCache = new ImagesFileCache(context);
		executorService = Executors.newFixedThreadPool(5);
		httpReqHelper = new HttpRequestHelper(context);
	}

	final int stub_id = R.drawable.no_photo2;

	public void DisplayImage(String url, ImageView imageView, ImageType it)
	{
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else
		{
			queuePhoto(url, imageView, it);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView, ImageType it)
	{
		PhotoToLoad p = new PhotoToLoad(url, imageView, it);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url, ImageType it)
	{
		File f = fileCache.getFile(url, it);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try
		{
			Bitmap bitmap = httpReqHelper.getImageBitmap(url, it);
			OutputStream os = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.flush();
			os.close();
			return decodeFile(f);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f)
	{
		try
		{
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true)
			{
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e)
		{
			Logger.w("ImageLoader", "Error while decoding image " + e.getMessage());
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad
	{
		public String url;
		public ImageView imageView;
		public ImageType imageType;

		public PhotoToLoad(String u, ImageView i, ImageType it)
		{
			url = u;
			imageView = i;
			imageType = it;
		}
	}

	class PhotosLoader implements Runnable
	{
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad)
		{
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run()
		{
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.imageType);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad)
	{
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable
	{
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p)
		{
			bitmap = b;
			photoToLoad = p;
		}

		public void run()
		{
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache()
	{
		memoryCache.clear();
		fileCache.clear();
	}

}
