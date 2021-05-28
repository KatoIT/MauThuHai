package com.example.mauthuhai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASENAME = "note.sqlite";
    private static final int VERSION = 1;
    public VarFinal mVarFinal = new VarFinal();

    public Database(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    // AUTOINCREMENT    : tự động sinh khóa
    // PRIMARY KEY      : Khóa chính
    // NVARCHAR(255)
    // MONEY    INTEGER     BOOLEAN     DATETIME       DATE     : Kiểu dữ liệu

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + mVarFinal.TABLENAME + " ("
                + mVarFinal.COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + mVarFinal.COL_1 + " NVARCHAR(255),"
                + mVarFinal.COL_2 + " NVARCHAR(255), "
                + mVarFinal.COL_3 + " FLOAT, "
                + mVarFinal.COL_4 + " FLOAT, "
                + mVarFinal.COL_5 + " FLOAT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Truy vấn có trả kết quả: CREATE, INSERT, UPDATE, DELETE,...
    public void QueryData(String str) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(str);
    }

    // Truy vấn có trả kết quả: SELECT
    public Cursor GetData(String str) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(str, null);
    }

    public void Delete(HocSinh object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + mVarFinal.TABLENAME + " WHERE " + mVarFinal.COL_0 + " = " + object.getId() + ";");
    }

    public void Insert(HocSinh object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO " + mVarFinal.TABLENAME + " VALUES(NULL,?,?,?,?,?)";
        SQLiteStatement mSqLiteStatement = sqLiteDatabase.compileStatement(sql);
        mSqLiteStatement.clearBindings();
        mSqLiteStatement.bindString(1, object.getSBD());
        mSqLiteStatement.bindString(2, object.getHoTen());
        mSqLiteStatement.bindDouble(3, object.getDToan());
        mSqLiteStatement.bindDouble(4, object.getDLy());
        mSqLiteStatement.bindDouble(5, object.getDHoa());

        mSqLiteStatement.executeInsert();
    }

    public void Update(HocSinh object) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = " UPDATE " + mVarFinal.TABLENAME + " SET "
                + mVarFinal.COL_1 + " = ?, "
                + mVarFinal.COL_2 + " = ?, "
                + mVarFinal.COL_3 + " = ?, "
                + mVarFinal.COL_4 + " = ?, "
                + mVarFinal.COL_5 + " = ?"
                + " WHERE " + mVarFinal.COL_0 + " = " + object.getId() + ";";
        SQLiteStatement mSqLiteStatement = sqLiteDatabase.compileStatement(sql);
        mSqLiteStatement.clearBindings();
        mSqLiteStatement.bindString(1, object.getSBD());
        mSqLiteStatement.bindString(2, object.getHoTen());
        mSqLiteStatement.bindDouble(3, object.getDToan());
        mSqLiteStatement.bindDouble(4, object.getDLy());
        mSqLiteStatement.bindDouble(5, object.getDHoa());

        mSqLiteStatement.executeUpdateDelete();
    }
}
