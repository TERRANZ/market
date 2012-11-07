package ru.terra.et.core.tasks;

import ru.terra.et.core.WorkIsDoneListener;
import ru.terra.et.core.constants.ActivityConstants;
import ru.terra.et.provider.CategoriesProvider;
import android.app.Activity;
import android.os.AsyncTask;

public class CategoriesLoadingAsyncTask extends AsyncTask<Void, Void, Void>
{

	private Activity activity;
	private WorkIsDoneListener w;

	public CategoriesLoadingAsyncTask(Activity activity, WorkIsDoneListener w)
	{
		super();
		this.activity = activity;
		this.w = w;
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		new CategoriesProvider(activity).load();
		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		if (w != null)
			w.workIsDone(ActivityConstants.CATEGORIES_LOADED);
	}
}
