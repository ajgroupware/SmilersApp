package co.smilers.model.data.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.Campaign;
import co.smilers.model.CampaignFooter;
import co.smilers.model.CampaignHeader;
import co.smilers.model.GeneralHeader;
import co.smilers.model.GeneralQuestionItem;
import co.smilers.model.Headquarter;
import co.smilers.model.QuestionItem;
import co.smilers.model.Zone;
import co.smilers.model.data.AppDataHelper;

public class CampaignDAO {

    private static final String TAG = CampaignDAO.class.getSimpleName();
    private Context context;

    public CampaignDAO(Context context) {
        this.context = context;
    }

    /**
     * Ingresar o actualizar las canpañas
     *
     * @param values
     */
    public void addCampaign(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addCampaign");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "campaign",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addCampaign");
    }

    /**
     * Ingresar o actualizar las preguntas
     *
     * @param values
     */
    public void addQuestionItem(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addQuestionItem");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "question_item",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addQuestionItem");
    }

    public List<Campaign> getCampaign(String accountCode){
        Log.i(TAG, "-- start: getCampaign");
        List<Campaign> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"title"
                    ,"description"
                    ,"start_date"
                    ,"end_date"
                    ,"is_published"
                    ,"account_code"
            };

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            String currentDateStr = simpleDateFormat.format(currentDate);
            Log.d(TAG, "--currentDateStr: "+currentDateStr);
            // Define 'where' part of query.
            //String selection = "account_code = ?";
            String selection = "account_code = ? and start_date <= '" + currentDateStr + "' and end_date >= '" + currentDateStr + "' and is_published = 1"; //Dejar para próxima versión
            //String selection = "account_code = ? and start_date <= '" + currentDateStr + "' and end_date >= '" + currentDateStr + "'";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = null;
            cursor = db.query(
                    "campaign",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            Campaign object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new Campaign();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setStartDate(cursor.getString(cursor.getColumnIndex("start_date")));
                    object.setEndDate(cursor.getString(cursor.getColumnIndex("end_date")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );

                    if (!"Satisfacción General  Colina".equals(object.getTitle()) && !"Satisfacción General Poblado".equals(object.getTitle())) { //Ajuste temporal para el éxito
                        list.add(object);
                    }

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
        Log.i(TAG, "-- end: getCampaign");
        return  list;
    }

    public Campaign getCampaignByCode(String accountCode, Long code){
        Log.i(TAG, "-- start: getCampaignByCode");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        Campaign object = new Campaign();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"title"
                    ,"description"
                    ,"start_date"
                    ,"end_date"
                    ,"is_published"
                    ,"account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(code)};
            String sortOrder = null;
            cursor = db.query(
                    "campaign",  // The table to query
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
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setStartDate(cursor.getString(cursor.getColumnIndex("start_date")));
                    object.setEndDate(cursor.getString(cursor.getColumnIndex("end_date")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );

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
        Log.i(TAG, "-- end: getCampaignByCode");
        return  object;
    }

    public List<QuestionItem> getQuestionItemByCampaign(String accountCode, Long codeCampaign){
        Log.i(TAG, "-- start: getQuestionItemByCampaign");
        List<QuestionItem> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"title"
                    ,"description"
                    ,"design_order"
                    ,"design_color"
                    ,"campaign_code"
                    ,"is_published"
                    ,"min_score"
                    ,"receive_comment"
                    ,"send_sms_notification"
                    ,"account_code"
                    ,"question_type"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and campaign_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(codeCampaign)};
            String sortOrder = "design_order";
            cursor = db.query(
                    "question_item",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            QuestionItem object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new QuestionItem();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setDesignColor(cursor.getString(cursor.getColumnIndex("design_color")));
                    object.setDesignOrder(cursor.getInt(cursor.getColumnIndex("design_order")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );
                    object.setReceiveComment("true".equals(cursor.getString(cursor.getColumnIndex("receive_comment"))) ? true : false );
                    object.setSendSmsNotification("true".equals(cursor.getString(cursor.getColumnIndex("send_sms_notification"))) ? true : false );
                    object.setMinScore(cursor.getDouble(cursor.getColumnIndex("min_score")));
                    object.setQuestionType(cursor.getString(cursor.getColumnIndex("question_type")));

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
        Log.i(TAG, "-- end: getQuestionItemByCampaign");
        return  list;
    }

    public QuestionItem getQuestionItemByCode(String accountCode, Long codeQuestionItem){
        Log.i(TAG, "-- start: getQuestionItemByCode");
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        QuestionItem object = new QuestionItem();
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"title"
                    ,"description"
                    ,"design_order"
                    ,"design_color"
                    ,"campaign_code"
                    ,"is_published"
                    ,"min_score"
                    ,"receive_comment"
                    ,"send_sms_notification"
                    ,"account_code"
                    ,"question_type"
            };

            // Define 'where' part of query.
            String selection = "account_code = ? and code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode, String.valueOf(codeQuestionItem)};
            String sortOrder = "design_order";
            cursor = db.query(
                    "question_item",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new QuestionItem();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setDesignColor(cursor.getString(cursor.getColumnIndex("design_color")));
                    object.setDesignOrder(cursor.getInt(cursor.getColumnIndex("design_order")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );
                    object.setReceiveComment("true".equals(cursor.getString(cursor.getColumnIndex("receive_comment"))) ? true : false );
                    object.setSendSmsNotification("true".equals(cursor.getString(cursor.getColumnIndex("send_sms_notification"))) ? true : false );
                    object.setMinScore(cursor.getDouble(cursor.getColumnIndex("min_score")));
                    object.setQuestionType(cursor.getString(cursor.getColumnIndex("question_type")));

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
        Log.i(TAG, "-- end: getQuestionItemByCode");
        return  object;
    }

    public void deleteSmsCellPhone(String account) {
        Log.i(TAG, "-- start: deleteSmsCellPhone");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ?";
            String[] arg = {account};
            newRowId = db.delete("sms_cell_phone", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteSmsCellPhone");
    }

    /**
     * Ingresar o actualizar las canpañas
     *
     * @param values
     */
    public void addSmsCellPhone(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addSmsCellPhone");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "sms_cell_phone",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addSmsCellPhone");
    }

    public void deleteTargetZone(String account) {
        Log.i(TAG, "-- start: deleteTargetZone");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ?";
            String[] arg = {account};
            newRowId = db.delete("target_zone", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteTargetZone");
    }

    public void addTargetZone(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addTargetZone");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "target_zone",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addTargetZone");
    }

    public List<GeneralHeader> getGeneralHeader(String accountCode){
        Log.i(TAG, "-- start: getGeneralHeader");
        List<GeneralHeader> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id"
                    ,"title"
                    ,"description"
                    ,"design_order"
                    ,"design_color"
                    ,"is_published"
                    ,"account_code"
            };
            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "design_order";
            cursor = db.query(
                    "general_header",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            GeneralHeader object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new GeneralHeader();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setDesignColor(cursor.getString(cursor.getColumnIndex("design_color")));
                    object.setDesignOrder(cursor.getInt(cursor.getColumnIndex("design_order")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );

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
        Log.i(TAG, "-- end: getGeneralHeader");
        return  list;
    }

    public List<GeneralQuestionItem> getGeneralQuestionItem(String accountCode){
        Log.i(TAG, "-- start: getGeneralQuestionItem");
        List<GeneralQuestionItem> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "code"
                    ,"title"
                    ,"description"
                    ,"design_order"
                    ,"design_color"
                    ,"is_published"
                    ,"min_score"
                    ,"receive_comment"
                    ,"send_sms_notification"
                    ,"account_code"
            };

            // Define 'where' part of query.
            //String selection = "account_code = ?";
            String selection = "account_code = ? and is_published = 1";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "design_order";
            cursor = db.query(
                    "general_question_item",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            GeneralQuestionItem object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new GeneralQuestionItem();
                    object.setCode(cursor.getLong(cursor.getColumnIndex("code")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setDesignColor(cursor.getString(cursor.getColumnIndex("design_color")));
                    object.setDesignOrder(cursor.getInt(cursor.getColumnIndex("design_order")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );
                    object.setReceiveComment("true".equals(cursor.getString(cursor.getColumnIndex("receive_comment"))) ? true : false );
                    object.setSendSmsNotification("true".equals(cursor.getString(cursor.getColumnIndex("send_sms_notification"))) ? true : false );
                    object.setMinScore(cursor.getDouble(cursor.getColumnIndex("min_score")));

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
        Log.i(TAG, "-- end: getGeneralQuestionItem");
        return  list;
    }

    public List<CampaignFooter> getCampaignFooter(String accountCode){
        Log.i(TAG, "-- start: getCampaignFooter");
        List<CampaignFooter> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id"
                    ,"title"
                    ,"description"
                    ,"design_order"
                    ,"design_color"
                    ,"is_published"
                    ,"account_code"
            };
            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "design_order";
            cursor = db.query(
                    "campaign_footer",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            CampaignFooter object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new CampaignFooter();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    object.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    object.setDesignColor(cursor.getString(cursor.getColumnIndex("design_color")));
                    object.setDesignOrder(cursor.getInt(cursor.getColumnIndex("design_order")));
                    object.setIsPublished("true".equals(cursor.getString(cursor.getColumnIndex("is_published"))) ? true : false );

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
        Log.i(TAG, "-- end: getCampaignFooter");
        return  list;
    }

    /**
     * Ingresar o actualizar las respuestas
     *
     * @param values
     */
    public void addAnswerScore(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addAnswerScore");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "answer_score",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addAnswerScore");
    }

    /**
     * Ingresar o actualizar las respuestas generales
     *
     * @param values
     */
    public void addAnswerGeneralScore(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addAnswerGeneralScore");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "answer_general_score",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addAnswerGeneralScore");
    }

    /**
     * Ingresar o actualizar las respuestas Si no
     *
     * @param values
     */
    public void addAnswerBooleanScore(ContentValues values, SQLiteDatabase db) {
        Log.i(TAG, "-- start: addAnswerBooleanScore");

        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            newRowId = db.replaceOrThrow(
                    "answer_boolean_score",
                    null,
                    values);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: addAnswerBooleanScore");
    }

    public Long getNextIdAnswerScore(String accountCode) {
        Log.i(TAG, "-- start: getNextIdAnswerScore");
        Long id = 0L;
        SQLiteDatabase db       = null;
        Cursor cursor = null;
        try {
            AppDataHelper mDbHelper = new AppDataHelper(context);
            db = mDbHelper.getReadableDatabase();
            String query = "select id from answer_score where account_code = '" + accountCode + "'";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    id = cursor.getLong(cursor.getColumnIndex("id"));
                }while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        id = id + 1L;
        Log.i(TAG, "-- end: getNextIdAnswerScore");
        return id;
    }

    public Long getNextIdAnswerGeneralScore(String accountCode) {
        Log.i(TAG, "-- start: getNextIdAnswerGeneralScore");
        Long id = 0L;
        SQLiteDatabase db       = null;
        Cursor cursor = null;
        try {
            AppDataHelper mDbHelper = new AppDataHelper(context);
            db = mDbHelper.getReadableDatabase();
            String query = "select id from answer_general_score where account_code = '" + accountCode + "'";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    id = cursor.getLong(cursor.getColumnIndex("id"));
                }while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        id = id + 1L;
        Log.i(TAG, "-- end: getNextIdAnswerGeneralScore");
        return id;
    }

    public Long getNextIdAnswerBooleanScore(String accountCode) {
        Log.i(TAG, "-- start: getNextIdAnswerBooleanScore");
        Long id = 0L;
        SQLiteDatabase db       = null;
        Cursor cursor = null;
        try {
            AppDataHelper mDbHelper = new AppDataHelper(context);
            db = mDbHelper.getReadableDatabase();
            String query = "select id from answer_boolean_score where account_code = '" + accountCode + "'";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    id = cursor.getLong(cursor.getColumnIndex("id"));
                }while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        id = id + 1L;
        Log.i(TAG, "-- end: getNextIdAnswerBooleanScore");
        return id;
    }

    public List<AnswerScore> getAnswerScore(String accountCode){
        Log.i(TAG, "-- start: getAnswerScore");
        List<AnswerScore> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id" ,
                    "campaign_code" ,
                    "headquarter_code" ,
                    "zone_code" ,
                    "city_code" ,
                    "city_name" ,
                    "registration_date" ,
                    "excellent" ,
                    "good" ,
                    "moderate" ,
                    "bad" ,
                    "poor" ,
                    "score" ,
                    "meter_device_id" ,
                    "question_item_code" ,
                    "comment" ,
                    "user_id" ,
                    "account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "id";
            cursor = db.query(
                    "answer_score",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            AnswerScore object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new AnswerScore();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setCityCode(cursor.getLong(cursor.getColumnIndex("city_code")));
                    Headquarter headquarter = new Headquarter();
                    headquarter.setCode(cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setHeadquarter(headquarter);
                    Zone zone = new Zone();
                    zone.setCode(cursor.getLong(cursor.getColumnIndex("zone_code")));
                    object.setZone(zone);
                    Campaign campaign = new Campaign();
                    campaign.setCode(cursor.getLong(cursor.getColumnIndex("campaign_code")));
                    object.setCampaign(campaign);

                    object.setExcellent(cursor.getInt(cursor.getColumnIndex("excellent")));
                    object.setGood(cursor.getInt(cursor.getColumnIndex("good")));
                    object.setModerate(cursor.getInt(cursor.getColumnIndex("moderate")));
                    object.setBad(cursor.getInt(cursor.getColumnIndex("bad")));
                    object.setPoor(cursor.getInt(cursor.getColumnIndex("poor")));
                    object.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                    QuestionItem questionItem = getQuestionItemByCode(accountCode, cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    //questionItem.setCode(cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    object.setQuestionItem(questionItem);

                    object.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    object.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));

                    String registrationDateStr = cursor.getString(cursor.getColumnIndex("registration_date"));
                    Log.d(TAG, "--registrationDateStr: "+registrationDateStr);
                    object.setRegistrationDate(registrationDateStr);


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
        Log.i(TAG, "-- end: getAnswerScore");
        return  list;
    }

    public List<AnswerGeneralScore> getAnswerGeneralScore(String accountCode){
        Log.i(TAG, "-- start: getAnswerGeneralScore");
        List<AnswerGeneralScore> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id" ,
                    "headquarter_code" ,
                    "zone_code" ,
                    "city_code" ,
                    "city_name" ,
                    "registration_date" ,
                    "excellent" ,
                    "good" ,
                    "moderate" ,
                    "bad" ,
                    "poor" ,
                    "score" ,
                    "meter_device_id" ,
                    "question_item_code" ,
                    "comment" ,
                    "user_id" ,
                    "account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "id";
            cursor = db.query(
                    "answer_general_score",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            AnswerGeneralScore object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new AnswerGeneralScore();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setCityCode(cursor.getLong(cursor.getColumnIndex("city_code")));
                    Headquarter headquarter = new Headquarter();
                    headquarter.setCode(cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setHeadquarter(headquarter);
                    Zone zone = new Zone();
                    zone.setCode(cursor.getLong(cursor.getColumnIndex("zone_code")));
                    object.setZone(zone);

                    object.setExcellent(cursor.getInt(cursor.getColumnIndex("excellent")));
                    object.setGood(cursor.getInt(cursor.getColumnIndex("good")));
                    object.setModerate(cursor.getInt(cursor.getColumnIndex("moderate")));
                    object.setBad(cursor.getInt(cursor.getColumnIndex("bad")));
                    object.setPoor(cursor.getInt(cursor.getColumnIndex("poor")));
                    object.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                    QuestionItem questionItem = getQuestionItemByCode(accountCode, cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    //questionItem.setCode(cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    object.setQuestionItem(questionItem);

                    object.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    object.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));

                    String registrationDateStr = cursor.getString(cursor.getColumnIndex("registration_date"));
                    Log.d(TAG, "--registrationDateStr: "+registrationDateStr);
                    object.setRegistrationDate(registrationDateStr);


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
        Log.i(TAG, "-- end: getAnswerGeneralScore");
        return  list;
    }

    public List<AnswerBooleanScore> getAnswerBooleanScore(String accountCode){
        Log.i(TAG, "-- start: getAnswerBooleanScore");
        List<AnswerBooleanScore> list = new ArrayList<>();
        AppDataHelper mDbHelper = new AppDataHelper(context);
        SQLiteDatabase db       = null;
        Cursor cursor           = null;
        try {
            db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    "id" ,
                    "campaign_code" ,
                    "headquarter_code" ,
                    "zone_code" ,
                    "city_code" ,
                    "city_name" ,
                    "registration_date" ,
                    "yes_answer" ,
                    "no_answer" ,
                    "score" ,
                    "meter_device_id" ,
                    "question_item_code" ,
                    "comment" ,
                    "user_id" ,
                    "account_code"
            };

            // Define 'where' part of query.
            String selection = "account_code = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {accountCode};
            String sortOrder = "id";
            cursor = db.query(
                    "answer_boolean_score",  // The table to query
                    projection,                       // The columns to return
                    selection,                        // The columns for the WHERE clause
                    selectionArgs,                    // The values for the WHERE clause
                    null,                             // don't group the rows
                    null,                             // don't filter by row groups
                    sortOrder                         // The sort order
            );
            AnswerBooleanScore object = null;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    object = new AnswerBooleanScore();
                    object.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    object.setCityCode(cursor.getLong(cursor.getColumnIndex("city_code")));
                    Headquarter headquarter = new Headquarter();
                    headquarter.setCode(cursor.getLong(cursor.getColumnIndex("headquarter_code")));
                    object.setHeadquarter(headquarter);
                    Zone zone = new Zone();
                    zone.setCode(cursor.getLong(cursor.getColumnIndex("zone_code")));
                    object.setZone(zone);
                    Campaign campaign = new Campaign();
                    campaign.setCode(cursor.getLong(cursor.getColumnIndex("campaign_code")));
                    object.setCampaign(campaign);

                    object.setYesAnswer(cursor.getInt(cursor.getColumnIndex("yes_answer")));
                    object.setNoAnswer(cursor.getInt(cursor.getColumnIndex("no_answer")));

                    object.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                    QuestionItem questionItem = getQuestionItemByCode(accountCode, cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    //questionItem.setCode(cursor.getLong(cursor.getColumnIndex("question_item_code")));
                    object.setQuestionItem(questionItem);

                    object.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    object.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));

                    String registrationDateStr = cursor.getString(cursor.getColumnIndex("registration_date"));
                    Log.d(TAG, "--registrationDateStr: "+registrationDateStr);
                    object.setRegistrationDate(registrationDateStr);


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
        Log.i(TAG, "-- end: getAnswerBooleanScore");
        return  list;
    }

    public void deleteAnswerScore(String account, String listId) {
        Log.i(TAG, "-- start: deleteAnswerScore");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ? and id in (" + listId + ")";
            String[] arg = {account};
            newRowId = db.delete("answer_score", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteAnswerScore");
    }

    public void deleteGeneralAnswerScore(String account, String listId) {
        Log.i(TAG, "-- start: deleteGeneralAnswerScore");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ? and id in (" + listId + ")";
            String[] arg = {account};
            newRowId = db.delete("answer_general_score", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteGeneralAnswerScore");
    }

    public void deleteBooleanAnswerScore(String account, String listId) {
        Log.i(TAG, "-- start: deleteBooleanAnswerScore");
        SQLiteDatabase db = null;
        AppDataHelper mDbHelper = new AppDataHelper(context);
        long newRowId = 0;
        try {
            db = mDbHelper.getReadableDatabase();
            String where = "account_code = ? and id in (" + listId + ")";
            String[] arg = {account};
            newRowId = db.delete("answer_boolean_score", where, arg);
            Log.i(TAG, "-- newRowId: " + newRowId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "-- end: deleteBooleanAnswerScore");
    }
}
