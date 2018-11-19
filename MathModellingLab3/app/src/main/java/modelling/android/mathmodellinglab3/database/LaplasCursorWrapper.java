package modelling.android.mathmodellinglab3.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import modelling.android.mathmodellinglab3.LaplasItem;

public class LaplasCursorWrapper extends CursorWrapper {

    public LaplasCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public LaplasItem getLaplas1Item() {
        double x = getDouble(getColumnIndex(LaplasDbSchema.Laplas1Table.Cols.X));
        double fx = getDouble(getColumnIndex(LaplasDbSchema.Laplas1Table.Cols.FX));

        return new LaplasItem(x, fx);

    }

    public LaplasItem getLaplas2Item() {
        double x = getDouble(getColumnIndex(LaplasDbSchema.Laplas2Table.Cols.X));
        double fx = getDouble(getColumnIndex(LaplasDbSchema.Laplas2Table.Cols.FX));

        return new LaplasItem(x, fx);

    }
}
