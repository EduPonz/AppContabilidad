package com.example.eduponz.appcontabilidad.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eduponz on 12/02/2017.
 */

public class UserContract {

    private UserContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.eduponz.appcontabilidad";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";

    public static abstract class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        // The MIME type of the {@link #CONTENT_URI} for a list of users.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        // The MIME type of the {@link #CONTENT_URI} for a single user.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_PASSWORD = "password";

    }
}
