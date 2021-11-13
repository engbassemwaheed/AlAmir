package com.waheed.bassem.alamir.database;

import android.provider.BaseColumns;

class Contract {

    Contract() {

    }

    static final class StoreEntry implements BaseColumns {

        static final String COLUMN_NAME = "store_name";
        static final String COLUMN_CITY = "store_city";
        static final String COLUMN_AREA = "store_area";
        static final String COLUMN_PHONE_NUMBER = "store_phone_number";
        static final String COLUMN_IMAGE = "store_image";
        static final String COLUMN_ID = "store_id";

        static final String TABLE_NAME = "stores_table";

    }
}
