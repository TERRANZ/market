package ru.terra.et.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.activity.RoboActivity;
import ru.terra.et.R;
import ru.terra.et.activity.component.CategoriesAdapter.ViewHolder;
import ru.terra.et.core.constants.ActivityConstants;
import ru.terra.et.core.tasks.CategoriesLoadingAsyncTask;
import ru.terra.et.core.tasks.ProductsByKindLoadingAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RoboActivity {
    private SharedPreferences prefs;
    private ViewPager vp;
    private List<View> pages = new ArrayList<View>();
    private String[] titles = null;
    private ListView lvRecent, lvRecommended, lvSearch;
    private ExpandableListView catsLV;

    private class MainAcvitiyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Object instantiateItem(View collection, int position) {
            View v = pages.get(position);
            ((ViewPager) collection).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("first_start", true))
            firstStart();
        titles = new String[]{"Новости", "Категории", "Поиск"};
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View main, categories, search;
        main = inflater.inflate(R.layout.f_main, null);
        categories = inflater.inflate(R.layout.f_main_categories, null);
        search = inflater.inflate(R.layout.f_search, null);

        lvRecent = (ListView) main.findViewById(R.id.lv_recent);
        lvRecommended = (ListView) main.findViewById(R.id.lv_recommend);
        lvSearch = (ListView) search.findViewById(R.id.lv_search_result);
        catsLV = (ExpandableListView) categories.findViewById(R.id.elv_categories);

        pages.add(main);
        pages.add(categories);
        pages.add(search);
        vp = (ViewPager) findViewById(R.id.vp_main_activity);
        MainAcvitiyAdapter adapter = new MainAcvitiyAdapter();
        vp.setAdapter(adapter);
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.tpi_main_activity);
        titleIndicator.setViewPager(vp);

        new ProductsByKindLoadingAsyncTask(this, lvRecent).execute("recent");
        new ProductsByKindLoadingAsyncTask(this, lvRecommended).execute("recommeded");
        OnItemClickListener l = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                startActivity(new Intent(MainActivity.this, ProductActivity.class).putExtra(ProductActivity.PARAM_PRODUCTID, (Integer) v.getTag()));
            }
        };
        lvRecent.setOnItemClickListener(l);
        lvRecommended.setOnItemClickListener(l);
        new CategoriesLoadingAsyncTask(this, catsLV).execute();
        catsLV.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ViewHolder vh = (ViewHolder) v.getTag();
                startActivity(new Intent(MainActivity.this, CategoryActivity.class).putExtra(CategoryActivity.PARAM_ID, vh.id));
                return false;
            }
        });
    }

    private void firstStart() {
        Editor e = prefs.edit();
        e.putBoolean("first_start", false);
        e.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_qr: {
                return true;
            }
            case R.id.mi_login: {
                startActivityForResult(new Intent(this, LoginActivity.class), ActivityConstants.LOGIN);
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            if (ActivityConstants.LOGIN == requestCode) {
                Toast.makeText(this, "Вход успешен", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void search(View v) {
    }

}
