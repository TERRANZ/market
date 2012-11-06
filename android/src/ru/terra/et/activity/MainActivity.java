package ru.terra.et.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.activity.RoboActivity;
import ru.terra.et.R;
import ru.terra.et.core.ProjectModule;
import ru.terra.et.core.constants.ActivityConstants;
import ru.terra.et.core.db.entity.TransactionDBEntity;
import ru.terra.et.core.db.entity.TypeDBEntity;
import ru.terra.et.core.helper.DateHelper;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends RoboActivity
{
	private SharedPreferences prefs;
	private ViewPager vp;
	private List<View> pages = new ArrayList<View>();
	private String[] titles = null;
	private ListView lvMainList;
	private ProjectModule pm;
	private DateFormat tf;

	private class MainAcvitiyAdapter extends PagerAdapter
	{
		@Override
		public int getCount()
		{
			return pages.size();
		}

		@Override
		public Object instantiateItem(View collection, int position)
		{
			View v = pages.get(position);
			((ViewPager) collection).addView(v, 0);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == (View) arg1;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return titles[position];
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		pm = new ProjectModule(this);
		if (prefs.getBoolean("first_start", true))
			firstStart();
		titles = new String[] {};
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View main, list;
		main = inflater.inflate(R.layout.f_main, null);
		list = inflater.inflate(R.layout.f_main_list, null);
		lvMainList = (ListView) list.findViewById(R.id.lv_main_list);
		pages.add(main);
		pages.add(list);
		vp = (ViewPager) findViewById(R.id.vp_main_activity);
		MainAcvitiyAdapter adapter = new MainAcvitiyAdapter();
		vp.setAdapter(adapter);
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.tpi_main_activity);
		titleIndicator.setViewPager(vp);
	}

	private void firstStart()
	{
		Editor e = prefs.edit();
		e.putBoolean("first_start", false);
		e.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.m_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.mi_qr:
		{
			return true;
		}
		case R.id.mi_login:
		{
			startActivityForResult(new Intent(this, LoginActivity.class), ActivityConstants.LOGIN);
			return true;
		}
		default:
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (RESULT_OK == resultCode)
		{
			if (ActivityConstants.LOGIN == requestCode)
			{
				Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
