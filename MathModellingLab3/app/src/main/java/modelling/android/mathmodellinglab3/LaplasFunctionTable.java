package modelling.android.mathmodellinglab3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import modelling.android.mathmodellinglab3.database.LaplasBaseHelper;
import modelling.android.mathmodellinglab3.database.LaplasCursorWrapper;
import modelling.android.mathmodellinglab3.database.LaplasDbSchema;

public class LaplasFunctionTable {
    private static LaplasFunctionTable sLaplasFunctionTable;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LaplasFunctionTable get(Context context) {
        if(sLaplasFunctionTable == null) {
            sLaplasFunctionTable = new LaplasFunctionTable(context);
        }
        return sLaplasFunctionTable;
    }

    private LaplasFunctionTable(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new LaplasBaseHelper(mContext)
                .getReadableDatabase();
    }

    private LaplasCursorWrapper queryLaplas1Items(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                LaplasDbSchema.Laplas1Table.NAME,
                null,                       //null - выбираются все столбцы
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new LaplasCursorWrapper(cursor);
    }

    private LaplasCursorWrapper queryLaplas2Items(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                LaplasDbSchema.Laplas2Table.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new LaplasCursorWrapper(cursor);
    }

    public List<LaplasItem> getLaplas1Items() {
        List<LaplasItem> laplasItems = new ArrayList<>();

        LaplasCursorWrapper cursor = queryLaplas1Items(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                laplasItems.add(cursor.getLaplas1Item());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return laplasItems;
    }

    public List<LaplasItem> getLaplas2Items() {
        List<LaplasItem> laplasItems = new ArrayList<>();

        LaplasCursorWrapper cursor = queryLaplas2Items(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                laplasItems.add(cursor.getLaplas2Item());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return laplasItems;
    }
}
