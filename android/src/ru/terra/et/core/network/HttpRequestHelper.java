package ru.terra.et.core.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.terra.et.R;
import ru.terra.et.core.constants.Constants;
import ru.terra.et.core.constants.ImageType;
import ru.terra.et.provider.LoginProvider;
import ru.terra.et.util.Logger;
import ru.terra.et.util.SettingsUtil;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Environment;

//Синхронные вызовы rest сервисов, вызывать только внутри асинхронной таски
public class HttpRequestHelper
{
	private static final int IMAGE_MAX_SIZE = 1024;
	private final String TAG = "HttpRequestHelper";
	private HttpClient hc;
	private String baseAddress = "";
	private Activity cntx;

	public HttpRequestHelper(Activity c)
	{
		this.cntx = c;
		if (c != null)
			this.baseAddress = c.getString(R.string.server_address);
		else
			Logger.i(TAG, "activity is null");
		hc = new DefaultHttpClient();
		hc.getParams().setParameter("http.protocol.content-charset", "UTF-8");
	}

	public String runSimpleJsonRequest(String uri)
	{
		HttpGet httpGet = new HttpGet(baseAddress + uri);
		return runRequest(httpGet);
	}

	private String runRequest(HttpUriRequest httpRequest)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			httpRequest.setHeader("Cookie", "JSESSIONID=" + SettingsUtil.getSetting(cntx, Constants.CONFIG_SESSION, ""));
			Logger.i("runRequest", "header: " + httpRequest.getHeaders("Cookie"));
			HttpResponse response = null;
			try
			{
				response = hc.execute(httpRequest);
			} catch (ConnectException e)
			{
				Logger.w("HttpRequestHelper", "Connect exception " + e.getMessage());
				return null;
			} catch (IllegalStateException e)
			{
				Logger.w("HttpRequestHelper", "IllegalStateException " + e.getMessage());
				return null;
			}
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			// Logger.i("HttpRequestHelper", "Received status code " + statusCode);
			if (HttpStatus.SC_OK == statusCode)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				while ((line = reader.readLine()) != null)
				{
					builder.append(line);
				}
			}
			else if (HttpStatus.SC_FORBIDDEN == statusCode)
			{
				Logger.i("HttpRequestHelper", "Received status code FORBIDDEN! invocating relogin and doing runRequest");
				// if (new UserProvider(cntx).doStoredLogin())
				// {
				// return runRequest(httpRequest);
				// }
				// else
				// {
				// Logger.w(TAG, "Unable to login, need to enter valid login and password");
				// throw new UnableToLoginException();
				// }
			}
			else
			{
				Logger.w("HttpRequestHelper", statusLine.toString() + "at" + httpRequest.getURI());
			}
		} catch (ConnectException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			Logger.w("HttpRequestHelper", "runRequest", e);
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}

	public String runJsonRequest(String uri, NameValuePair... params)
	{
		HttpPost request = new HttpPost(baseAddress + uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (int i = 0; i < params.length; ++i)
		{
			nameValuePairs.add(params[i]);
		}

		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		UrlEncodedFormEntity entity;
		try
		{
			entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			entity.setContentType("appplication/x-www-form-urlencoded");
			request.setEntity(entity);

			return runRequest(request);
		} catch (UnsupportedEncodingException e)
		{
			Logger.w("HttpRequestHelper", "Failed to form request content" + e.getMessage());
			return "";
		}
	}

	public String uploadImage(String title, String fileName, String type, String id) throws UnableToLoginException
	{
		MultipartEntity reqEntity = new MultipartEntity();
		// TODO: mb we can do it in another way?
		new LoginProvider(cntx).doStoredLogin();
		HttpPost post = new HttpPost(baseAddress + "/icity/uploadfile");
		try
		{
			Logger.i("uploadImage", "Starting to upload image: " + title + " with type: " + type + " : " + id);
			reqEntity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
			reqEntity.addPart("return", new StringBody("json"));
			reqEntity.addPart(type, new StringBody(id));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		reqEntity.addPart("fileData", new FileBody(new File(resizeImage(fileName))));

		post.setEntity(reqEntity);
		return runRequest(post);
	}

	public Bitmap getImageBitmap(String localUrl, ImageType type)
	{
		if (localUrl == null)
			return getNoPhotoBitmap(type);

		String url = baseAddress + localUrl;
		switch (type)
		{
		case thumbnail:
			url += "?height=50";
			break;
		case preview:
			url += "?height=100";
			break;
		case avatar:
			url += "?height=300";
			break;
		case full:
			break;
		default:
			return null;
		}

		Bitmap b;
		try
		{
			b = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
		} catch (MalformedURLException e)
		{
			Logger.w(TAG, "Failed to download image", e);
			return null;
		} catch (FileNotFoundException e)
		{
			Logger.w("HttpRequestHelper", "getImageBitmap file not found exception" + e.getMessage());
			return getNoPhotoBitmap(type);
		} catch (IOException e)
		{
			Logger.w(TAG, "Failed to download image", e);
			return null;
		} catch (Exception e)
		{
			Logger.w(TAG, "Failed to download image", e);
			return null;
		}

		if (b != null)// request.addHeader("Accept",

			b.setDensity(Bitmap.DENSITY_NONE);
		return b == null ? getNoPhotoBitmap(type) : b;
	}

	private Bitmap getNoPhotoBitmap(ImageType type)
	{
		Logger.i("HttpRequestHelper", "Returning stub bitmap");
		return BitmapFactory.decodeResource(cntx.getResources(), R.drawable.no_photo2);
	}

	private String resizeImage(String path)
	{
		int divScale = 1;
		// TODO: image resizing for better performance
		// if (cntx != null)
		// divScale = Integer
		// .valueOf(PreferenceManager.getDefaultSharedPreferences(cntx).getString(cntx.getString(R.string.upload_image_size), "1"));
		File root = new File(Environment.getExternalStorageDirectory() + File.separator + "icity" + File.separator);
		root.mkdirs();
		if (divScale == 1) // no scaling, just copy file
		{
			try
			{
				File destFile = new File(root, "photo.jpg");
				File sourceFile = new File(path);
				if (!destFile.exists())
				{
					destFile.createNewFile();
				}

				FileChannel source = null;
				FileChannel destination = null;
				try
				{
					source = new FileInputStream(sourceFile).getChannel();
					destination = new FileOutputStream(destFile).getChannel();
					destination.transferFrom(source, 0, source.size());
				} finally
				{
					if (source != null)
					{
						source.close();
					}
					if (destination != null)
					{
						destination.close();
					}
				}
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				int inWidth = 0;
				int inHeight = 0;

				InputStream in = new FileInputStream(path);

				// decode image size (decode metadata only, not the whole image)
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(in, null, options);
				in.close();
				in = null;

				// save width and height
				inWidth = options.outWidth;
				inHeight = options.outHeight;

				int dstWidth = inWidth / divScale;
				int dstHeight = inHeight / divScale;

				// decode full image pre-resized
				in = new FileInputStream(path);
				options = new BitmapFactory.Options();
				// calc rought re-size (this is no exact resize)
				options.inSampleSize = Math.max(inWidth / dstWidth, inHeight / dstHeight);
				// decode full image
				Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

				// calc exact destination size
				Matrix m = new Matrix();
				RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
				RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
				m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
				float[] values = new float[9];
				m.getValues(values);

				// resize bitmap
				Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]),
						(int) (roughBitmap.getHeight() * values[4]), true);

				// save image
				try
				{
					FileOutputStream out = new FileOutputStream(new File(root, "photo.jpg"));
					resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
					resizedBitmap.recycle();
				} catch (Exception e)
				{
					Logger.i("Image", e.getMessage());
					return "";
				}
			} catch (IOException e)
			{
				Logger.i("Image", e.getMessage());
				return "";
			}
		}
		return root.getPath() + "/photo.jpg";
	}

}