package ru.terra.et.core.tasks;

import ru.terra.et.activity.component.CategoriesAdapter;
import ru.terra.et.provider.CategoriesProvider;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ExpandableListView;

public class CategoriesLoadingAsyncTask extends AsyncTask<Void, Void, Void>
{

	private Activity activity;
	private ExpandableListView expandableListView;

	public CategoriesLoadingAsyncTask(Activity activity, ExpandableListView expandableListView)
	{
		super();
		this.expandableListView = expandableListView;
		this.activity = activity;
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
		expandableListView.setAdapter(new CategoriesAdapter(activity));
	}
}
