package co.smilers.model.data.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.smilers.model.City;
import co.smilers.model.CurrentConfig;
import co.smilers.model.Headquarter;
import co.smilers.model.SmsCellPhone;
import co.smilers.model.Zone;
import co.smilers.model.data.AppDataHelper;

public class ParameterDAO {

    private static final String TAG = ParameterDAO.class.getSimpleName();
    private Context context;

    public ParameterDAO(Context context) {
        this.context = context;
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addHeadquarter(List<ContentValues> values) {
        Log.i(TAG, "-- start: addHeadquarter");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        long newRowId = 0;
        try {
            db = mDbHelper.getWritableDatabase();
            db.beginTransaction();

            for (ContentValues value : values) {
                newRowId = db.replaceOrThrow(
                        "headquarter",
                        null,
                        value);
            }


            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db = null;
        }

        Log.i(TAG, "-- end: addHeadquarter");
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addHeadquarter(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addHeadquarter");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
                newRowId = db.replaceOrThrow(
                        "headquarter",
                        null,
                        values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addHeadquarter");
    }

    /**
     * Ingresar o actualizar las ciudades
     *
     * @param values
     */
    public void addCity(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addCity");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "city",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addCity");
    }

    /**
     * Ingresar o actualizar las zonas
     *
     * @param values
     */
    public void addZone(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addZone");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "zone",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addZone");
    }

    public List<Headquarter> getHeadquarter(String accountCode){
        Log.i(TAG, "-- start: getHeadquarter");
        List<Headquarter> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"name"
                    ,"city_code"
                    ,"account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = null;
            cursor = db.query(
                    "headquarter",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            Headquarter object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new Headquarter();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setName(cursor.getString(cursor.getColumnIndex("name")));
                    City city = getCityByCode(cursor.getLong(cursor.getColumnIndex("city_code")));
                    object.setCity(city);
                    object.setAccount(cursor.getString(cursor.getColumnIndex("account_code")));

                    list.add(object);
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
        Log.i(TAG, "-- end: getHeadquarter");
        return  list;
    }

    public City getCityByCode(Long code){
        Log.i(TAG, "-- start: getCityByCode");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        City object = new City();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id"
                    ,"code"
                    ,"name"
            };

            // Define 'where' part of query.
            String selection = "id = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {String.valueOf(code)};
            String sortOrder = null;
            cursor = db.query(
                    "city",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new City();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setName(cursor.getString(cursor.getColumnIndex("name")));

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
        Log.i(TAG, "-- end: getCityByCode");
        return  object;
    }

    public Headquarter getHeadquarterByCode(String accountCode, Long code){
        Log.i(TAG, "-- start: getHeadquarterByCode");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        Headquarter object = new Headquarter();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"name"
                    ,"city_code"
                    ,"account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(code)};
            String sortOrder = null;
            cursor = db.query(
                    "headquarter",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new Headquarter();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setName(cursor.getString(cursor.getColumnIndex("name")));
                    City city = new City();
                    city.setCode(cursor.getLong(cursor.getColumnIndex("city_code")));
                    object.setCity(city);
                    object.setAccount(cursor.getString(cursor.getColumnIndex("account_code")));

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
        Log.i(TAG, "-- end: getHeadquarterByCode");
        return  object;
    }

    public List<Zone> getZone(String accountCode, Long codeHeadquarter){
        Log.i(TAG, "-- start: getZone");
        List<Zone> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"name"
                    ,"headquarter_code"
                    ,"account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            if (codeHeadquarter != null && codeHeadquarter.intValue() != 0) {
                selection = "account_code = ? and headquarter_code = ?";
                selectionArgs = new String[]{accountCode, String.valueOf(codeHeadquarter)};
            }

            String sortOrder = null;
            cursor = db.query(
                    "zone",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            Zone object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new Zone();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setName(cursor.getString(cursor.getColumnIndex("name")));
                    Headquarter headquarter = getHeadquarterByCode(accountCode, cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setHeadquarter(headquarter);
                    object.setAccount(cursor.getString(cursor.getColumnIndex("account_code")));

                    list.add(object);
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
        Log.i(TAG, "-- end: getZone");
        return  list;
    }

    public Zone getZoneByCode(String accountCode, Long code){
        Log.i(TAG, "-- start: getZoneByCode");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        Zone object = new Zone();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"name"
                    ,"headquarter_code"
                    ,"account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and code = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(code)};

            String sortOrder = null;
            cursor = db.query(
                    "zone",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {

                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setName(cursor.getString(cursor.getColumnIndex("name")));
                    Headquarter headquarter = getHeadquarterByCode(accountCode, cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setHeadquarter(headquarter);
                    object.setAccount(cursor.getString(cursor.getColumnIndex("account_code")));

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
        Log.i(TAG, "-- end: getZoneByCode");
        return  object;
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addGeneralHeader(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addGeneralHeader");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "general_header",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addGeneralHeader");
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addCampaignFooter(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addCampaignFooter");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "campaign_footer",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addCampaignFooter");
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addGeneralQuestion(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addGeneralQuestion");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "general_question_item",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addGeneralQuestion");
    }

    /**
     * Ingresar o actualizar las sedes
     *
     * @param values
     */
    public void addFooterQuestion(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addFooterQuestion");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "footer_question_item",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addFooterQuestion");
    }

    public List<SmsCellPhone> getSmsCellPhone(String accountCode, Long codeHeadquarter){
        Log.i(TAG, "-- start: getSmsCellPhone");
        List<SmsCellPhone> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id"
                    ,"cell_phone_number"
                    ,"email"
                    ,"headquarter_code"
                    ,"zone_code"
                    ,"campaign_code"
                    ,"account_code"
                    ,"is_active"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and headquarter_code = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(codeHeadquarter)};

            String sortOrder = null;
            cursor = db.query(
                    "sms_cell_phone",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            SmsCellPhone object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new SmsCellPhone();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setCellPhoneNumber(cursor.getString(cursor.getColumnIndex("cell_phone_number")));
                    object.setHeadquarterCode(cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setZoneCode(cursor.getLong(cursor.getColumnIndex("zone_code")));
                    object.setCampaignCode(cursor.getLong(cursor.getColumnIndex("campaign_code")));
                    object.setIsActive("true".equals(cursor.getString(cursor.getColumnIndex("is_active"))));
                    object.setEmail(cursor.getString(cursor.getColumnIndex("email")));

                    list.add(object);
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
        Log.i(TAG, "-- end: getSmsCellPhone");
        return  list;
    }

    /**
     * Ingresar o actualizar las sedes
     *
     */
    public void addCurrentConfig(String accountCode, Long headquarterCode, Long zoneCode) {
        Log.i(TAG, "-- start: addCurrentConfig");

        ContentValues values = new ContentValues();
        values.put("headquarter_code", headquarterCode);
        values.put("zone_code", zoneCode);
        values.put("account_code", accountCode);
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            newRowId = db.replaceOrThrow(
                    "current_config",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        Log.i(TAG, "-- end: addCurrentConfig");
    }

    public CurrentConfig getCurrentConfig(String accountCode){
        Log.i(TAG, "-- start: getCurrentConfig");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        CurrentConfig object = new CurrentConfig();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "zone_code"
                    ,"headquarter_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = null;
            cursor = db.query(
                    "current_config",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new CurrentConfig();
                    object.setHeadquarterCode(cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setZoneCode(cursor.getLong(cursor.getColumnIndex("zone_code")));


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
        Log.i(TAG, "-- end: getCurrentConfig");
        return  object;
    }

    public void deleteCurrentConfig(String account) {
        Log.i(TAG, "-- start: deleteCurrentConfig");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ?";
            String[] arg = {account};
            newRowId = db.delete("current_config", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteCurrentConfig");
    }

    public void setGeneralLogo(String accountCode, byte[] imageData) {
        Log.i(TAG, "-- start: setGeneralLogo");
        SQLiteDatabase db       = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            ContentValues values = new ContentValues();
            // Column and value of column
            values.put("account_code", accountCode);
            values.put("image", imageData);
            db = mDbHelper.getWritableDatabase();
            newRowId = db.replaceOrThrow("general_image", null, values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: setGeneralLogo");
    }

    public byte[] getGeneralLogo(String accountCode) {
        Log.i(TAG, "-- start: getGeneralLogo");
        byte[] imageData = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        CurrentConfig object = new CurrentConfig();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "account_code"
                    ,"image"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = null;
            cursor = db.query(
                    "general_image",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    imageData = cursor.getBlob(cursor.getColumnIndex("image"));
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
        Log.i(TAG, "-- end: getGeneralLogo");

        return imageData;
    }

    /**
     * Ingresar o actualizar las canpañas
     *
     * @param values
     */
    public void addGeneralSettingParameter(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addGeneralSettingParameter");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "general_setting_parameter",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addGeneralSettingParameter");
    }

    public String getGeneralSettingParameterValue(String accountCode, String parameterKey){
        Log.i(TAG, "-- start: getGeneralSettingParameterValue");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        String parameterValue = "";
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "parameter_key"
                    ,"parameter_value"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and parameter_key = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, parameterKey};
            String sortOrder = null;
            cursor = db.query(
                    "general_setting_parameter",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    parameterValue = cursor.getString(cursor.getColumnIndex("parameter_value"));
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
        Log.i(TAG, "-- end: getGeneralSettingParameterValue");
        return  parameterValue;
    }
}
