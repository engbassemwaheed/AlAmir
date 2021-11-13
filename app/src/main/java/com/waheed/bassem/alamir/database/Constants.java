package com.waheed.bassem.alamir.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

final class Constants {

    static boolean isTableNotExist(SQLiteDatabase db, String table) {
        boolean result = false;
        String sql = "SELECT name FROM sqlite_master WHERE type=\'table\' AND name=\'" + table + "\'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            result = false;
        } else {
            result = true;
        }
        mCursor.close();
        return result;
    }

    static final String STORE_DATABASE_NAME = "stores.db";

    static final int STORE_DATABASE_VERSION = 1;

    static final class SQLiteConstants {
        static final String SPACE = " ";
        static final String COMMA = ", ";
        static final String OPENED_BRACKET = " (";
        static final String CLOSED_BRACKET = " );";
        static final String EQUALS_QUERY_MARK = "=?";
        static final String LIKE_QUERY_MARK = " LIKE ?";


        static final String CREATE_TABLE = "CREATE TABLE ";
        static final String INTEGER = "INTEGER";
        static final String PRIMARY_KEY = "PRIMARY KEY";
        static final String AUTO_INCREMENT = "AUTO INCREMENT";
        static final String TEXT = "TEXT";
        static final String NOT_NULL = "NOT NULL";
        static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";
        static final String AND = " AND ";
        static final String OR = " OR ";
        static final String PERCENTAGE_SIGN = "%";

        static final String DESC = "DESC";
    }

}
