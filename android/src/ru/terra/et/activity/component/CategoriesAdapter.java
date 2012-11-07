package ru.terra.et.activity.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.terra.et.R;
import ru.terra.et.core.cache.CategoriesCache;
import ru.terra.et.core.network.dto.category.CategoryDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CategoriesAdapter extends BaseExpandableListAdapter
{

	private Context myContext;
	private HashMap<Integer, List<CategoryDTO>> categories = new HashMap<Integer, List<CategoryDTO>>();
	private String[] arrGroupelements;
	private CategoryDTO[][] arrChildelements;

	public class ViewHolder
	{
		public Integer id;
		public String name;
		public Integer parent;

		public ViewHolder(Integer id, String name, Integer parent)
		{
			super();
			this.id = id;
			this.name = name;
			this.parent = parent;
		}
	}

	public CategoriesAdapter(Context context)
	{
		myContext = context;

		for (CategoryDTO stso : CategoriesCache.getInstance().getAll())
		{
			if (categories.containsKey(stso.parent))
			{
				categories.get(stso.parent).add(stso);
			}
			else
			{
				categories.put(stso.parent, new ArrayList<CategoryDTO>());
				categories.get(stso.parent).add(stso);
			}
		}
		arrGroupelements = categories.keySet().toArray(new String[categories.keySet().size()]);
		arrChildelements = new CategoryDTO[arrGroupelements.length][];
		for (int i = 0; i < arrGroupelements.length; i++)
		{
			List<CategoryDTO> stlist = categories.get(arrGroupelements[i]);
			arrChildelements[i] = stlist.toArray(new CategoryDTO[stlist.size()]);
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.i_signal_type_item, null);
		}

		TextView tvPlayerName = (TextView) convertView.findViewById(R.id.tv_signal_type_item_name);
		tvPlayerName.setText(arrChildelements[groupPosition][childPosition].name);
		CategoryDTO stso = arrChildelements[groupPosition][childPosition];
		convertView.setTag(new ViewHolder(stso.id, stso.name, stso.parent));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return arrChildelements[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public int getGroupCount()
	{
		return arrGroupelements.length;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{

		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.i_signal_type_item_header, null);
		}

		TextView tvGroupName = (TextView) convertView.findViewById(R.id.tv_signal_type_item_header_groupname);
		tvGroupName.setText(arrGroupelements[groupPosition]);

		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

}
