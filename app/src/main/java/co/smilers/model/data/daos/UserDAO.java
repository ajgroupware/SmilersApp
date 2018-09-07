package co.smilers.model.data.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import co.smilers.model.Account;
import co.smilers.model.MeterDevice;
import co.smilers.model.User;
import co.smilers.model.data.AppDataHelper;

public class UserDAO {
    private static final String TAG = UserDAO.class.getSimpleName();
    private Context context;

    public UserDAO(Context context) {
        this.context = context;
    }


    /**
     * Buscar un parámetro general por su llave
     *
     * @return Parameter
     */
    public User getUserLogin(){
        Log.i(TAG, "-- start: getUserLogin");
        User object = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id_usuario"
                    ,"userName"
                    ,"password"
                    ,"name"
                    ,"accountCode"
                    ,"accountName"
                    ,"token"
                    ,"lastSync"
            };

            // Define 'where' part of query.
            String selection =  "isLogin = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {"1"};
            String sortOrder = null;
            cursor = db.query(
                    "User",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            if (cursor != null && cursor.moveToFirst()) {
                object = new User();
                object.setId(cursor.getLong(cursor.getColumnIndex("id_usuario")));
                object.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
                object.setName(cursor.getString(cursor.getColumnIndex("name")));
                object.setToken(cursor.getString(cursor.getColumnIndex("token")));
                object.setLastSync(cursor.getString(cursor.getColumnIndex("lastSync")));

                String accountCode = cursor.getString(cursor.getColumnIndex("accountCode"));
                String accountName = cursor.getString(cursor.getColumnIndex("accountName"));
                Account account = new Account();
                account.setCode(accountCode);
                account.setName(accountName);

                object.setAccount(account);

            }

        } catch (Exception e){
            Log.e(TAG, "--Error: "+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.i(TAG, "-- end: getUserLogin");
        return  object;
    }

    /**
     * Ingresar o actualizar un usuario
     *
     * @param values
     */
    public void addUser(ContentValues values) {
        Log.i(TAG, "-- start: addUser");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        long newRowId = 0;
        try {
            db = mDbHelper.getWritableDatabase();
            db.beginTransaction();

            newRowId = db.replaceOrThrow(
                    "User",
                    null,
                    values);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db = null;
        }

        Log.i(TAG, "-- end: addUser");
    }

    public ArrayList<String> getListUser(){
        Log.i(TAG, "-- start: getListUser");
        ArrayList<String> list = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {"userName"};

            // Define 'where' part of query.
            String selection =  null;
            // Specify arguments in placeholder order.
            String[] selectionArgs = null;
            String sortOrder = null;
            cursor = db.query(
                    "User",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            if (cursor != null && cursor.moveToFirst()) {
                list = new ArrayList<String>();
                do {
                    list.add(cursor.getString(cursor.getColumnIndex("userName")));
                } while (cursor.moveToNext());
            }

        } catch (Exception e){
            Log.e(TAG, "Error: "+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.i(TAG, "-- end: getListUser");
        return  list;
    }

    public void logout() {
        Log.i(TAG, "-- start: logout");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        long newRowId = 0;
        try {
            db = mDbHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues userUpdateValues = new ContentValues();
            userUpdateValues.put("isLogin", 0);

            newRowId = db.update(
                    "User",
                    userUpdateValues,
                    null,
                    null);


            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db = null;
        }

        Log.i(TAG, "-- end: logout");
    }

    /**
     * Ingresar o actualizar un dispositivo
     *
     * @param values
     */
    public void addDevice(ContentValues values) {
        Log.i(TAG, "-- start: addDevice");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        long newRowId = 0;
        try {
            db = mDbHelper.getWritableDatabase();
            db.beginTransaction();

            newRowId = db.replaceOrThrow(
                    "Device",
                    null,
                    values);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db = null;
        }

        Log.i(TAG, "-- end: addDevice");
    }

    /**
     * Buscar dispositivo
     *
     * @return Parameter
     */
    public MeterDevice getDevice(){
        Log.i(TAG, "-- start: getDevice");
        MeterDevice object = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "idPush"
                    ,"osVersionDispositivo"
                    ,"referenciaDispositivo"
                    ,"serialDispositivo"
            };

            // Define 'where' part of query.
            String selection =  null;
            // Specify arguments in placeholder order.
            String[] selectionArgs = null;
            String sortOrder = null;
            cursor = db.query(
                    "Device",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            if (cursor != null && cursor.moveToFirst()) {
                object = new MeterDevice();
                object.setDeviceIdPush(cursor.getString(cursor.getColumnIndex("idPush")));
                object.setDeviceSerial(cursor.getString(cursor.getColumnIndex("serialDispositivo")));
                object.setDeviceVersionOs(cursor.getString(cursor.getColumnIndex("osVersionDispositivo")));
                object.setDescription(cursor.getString(cursor.getColumnIndex("serialDispositivo")));

            }

        } catch (Exception e){
            Log.e(TAG, "--Error: "+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.i(TAG, "-- end: getDevice");
        return  object;
    }

    public void deleteDevice() {
        Log.i(TAG, "-- start: deleteDevice");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = null;
            String[] arg = null;
            newRowId = db.delete("Device", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteDevice");
    }
}
