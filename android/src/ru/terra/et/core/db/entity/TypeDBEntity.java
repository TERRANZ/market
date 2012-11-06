package ru.terra.et.core.db.entity;

import ru.terra.et.core.constants.Constants;
import android.net.Uri;
import android.provider.BaseColumns;

public interface TypeDBEntity extends BaseColumns
{
	String CONTENT_DIRECTORY = "types";
	Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + CONTENT_DIRECTORY);
	String CONTENT_TYPE = "entity.cursor.dir/types";
	String CONTENT_ITEM_TYPE = "entity.cursor.item/types";

	String NAME = "name";
	String ID = "server_id";
}
