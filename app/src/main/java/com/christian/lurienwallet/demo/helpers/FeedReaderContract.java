package com.christian.lurienwallet.demo.helpers;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    private FeedReaderContract(){}

    public static class  FeedEntry implements BaseColumns{
        public static final String TABLE_NAME="user_credentials";
        public static final String COLUMN_NAME_PUBK="public_key";
        public static final String COLUMN_NAME_PK="private_key";
    }
}
