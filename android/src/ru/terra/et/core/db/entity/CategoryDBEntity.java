package ru.terra.et.core.db.entity;

import ru.terra.et.core.constants.Constants;
import android.net.Uri;

public interface CategoryDBEntity
{
	String CONTENT_DIRECTORY = "category";
	Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + CONTENT_DIRECTORY);
	String CONTENT_TYPE = "entity.cursor.dir/tr";
	String CONTENT_ITEM_TYPE = "entity.cursor.item/tr";

	String ID = "internalid";
	String NAME = "name";
	String PARENT = "parent";
}
