package com.atlas.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.atlas.models.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sagar Shedge on 13/6/16.
 */
public class MapTable {
        protected static final String TAG = "MapTable";

        private final Context mContext;
        private SQLiteDatabase mDb;
        private DataBaseHelper mDbHelper;

        public MapTable(Context context)
        {
            this.mContext = context;
            mDbHelper = new DataBaseHelper(mContext);
        }

        public MapTable createDatabase() throws SQLException
        {
            try
            {
                mDbHelper.createDataBase();
            }
            catch (IOException mIOException)
            {
                Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
                throw new Error("UnableToCreateDatabase");
            }
            return this;
        }

        public MapTable open() throws SQLException
        {
            try
            {
                mDbHelper.openDataBase();
                mDbHelper.close();
                mDb = mDbHelper.getReadableDatabase();
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "open >>"+ mSQLException.toString());
                throw mSQLException;
            }
            return this;
        }

        public void close()
        {
            mDbHelper.close();
        }

        public List<Map>  getAllMap()
        {
            List<Map> mapList = new ArrayList<>();
            try
            {
                String sql ="SELECT * FROM Map";
                Cursor cursor = mDb.rawQuery(sql, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Map map = new Map();
                        map.setmId(cursor.getInt(cursor.getColumnIndex("id")));
                        map.setmCategoryId(cursor.getInt(cursor.getColumnIndex("id_category")));
                        map.setmName(cursor.getString(cursor.getColumnIndex("name")));
                        map.setmTitle(cursor.getString(cursor.getColumnIndex("title")));
                        map.setMbron(cursor.getString(cursor.getColumnIndex("bron")));
                        map.setmImage(cursor.getString(cursor.getColumnIndex("image")));
                        mapList.add(map);
                    } while (cursor.moveToNext());
                }
                return mapList;
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>"+ mSQLException.toString());
                throw mSQLException;
            }
        }
}