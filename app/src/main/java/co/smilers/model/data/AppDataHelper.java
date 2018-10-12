package co.smilers.model.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDataHelper extends SQLiteOpenHelper {

    private final static String TAG = AppDataHelper.class.getSimpleName();
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Smilers.db";

    public AppDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE User (id_usuario integer primary key, userName varchar, password varchar, name varchar, accountCode varchar, accountName varchar, token GUID, isLogin boolean default 1, lastSync datetime);");
        db.execSQL("CREATE TABLE Device (idPush GUID primary key, osVersionDispositivo varchar, referenciaDispositivo varchar, serialDispositivo varchar);");

        db.execSQL("CREATE TABLE headquarter " +
                "(" +
                " code integer NOT NULL" +
                "  , name varchar" +
                "  , city_code integer" +
                "  , account_code varchar NOT NULL" +
                "  , PRIMARY KEY (code, account_code)" +
                ");");

        db.execSQL("CREATE TABLE zone" +
                "(" +
                " code integer NOT NULL," +
                " name varchar," +
                " headquarter_code integer," +
                " account_code varchar NOT NULL," +
                "  PRIMARY KEY (code, account_code)" +
                ");");

        db.execSQL("CREATE TABLE question_item" +
                "(" +
                "    code integer NOT NULL" +
                ", title varchar" +
                ", description varchar" +
                ", design_order integer" +
                ", design_color varchar" +
                ", campaign_code integer" +
                ", is_published boolean" +
                ", min_score numeric(4,2)" +
                ", receive_comment boolean" +
                ", send_sms_notification boolean" +
                ", account_code varchar NOT NULL " +
                ", question_type varchar" +
                ",PRIMARY KEY (code, account_code)" +
                ");");

        db.execSQL("CREATE TABLE general_header" +
                "(" +
                "    id integer NOT NULL," +
                "    title varchar," +
                "    description varchar," +
                "    design_order integer," +
                "    design_color varchar," +
                "    is_published boolean," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE campaign_footer" +
                "(" +
                "    id integer NOT NULL," +
                "    title varchar," +
                "    description varchar," +
                "    design_order integer," +
                "    design_color varchar," +
                "    is_published boolean," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE general_question_item" +
                "(" +
                "    code integer NOT NULL," +
                "    title varchar," +
                "    description varchar," +
                "    design_order integer," +
                "    design_color varchar," +
                "    is_published boolean ," +
                "    min_score numeric(4,2)," +
                "    receive_comment boolean," +
                "    send_sms_notification boolean," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (code, account_code)" +
                ");");

        db.execSQL("CREATE TABLE campaign" +
                "(" +
                "    code integer NOT NULL," +
                "    title varchar," +
                "    description varchar," +
                "    start_date datetime," +
                "    end_date datetime," +
                "    is_published boolean," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (code, account_code)" +
                ");");

        db.execSQL("CREATE TABLE answer_score" +
                "(" +
                "    id integer NOT NULL," +
                "    campaign_code integer," +
                "    headquarter_code integer," +
                "    zone_code integer," +
                "    city_code integer," +
                "    city_name varchar," +
                "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                "    excellent integer DEFAULT 0," +
                "    good integer DEFAULT 0," +
                "    moderate integer DEFAULT 0," +
                "    bad integer DEFAULT 0," +
                "    poor integer DEFAULT 0," +
                "    score integer," +
                "    meter_device_id integer," +
                "    question_item_code integer," +
                "    comment varchar," +
                "    user_id varchar," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE answer_general_score" +
                "(" +
                "    id integer NOT NULL," +
                "    headquarter_code integer," +
                "    zone_code integer," +
                "    city_code integer," +
                "    city_name varchar," +
                "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                "    excellent integer DEFAULT 0," +
                "    good integer DEFAULT 0," +
                "    moderate integer DEFAULT 0," +
                "    bad integer DEFAULT 0," +
                "    poor integer DEFAULT 0," +
                "    score integer," +
                "    meter_device_id integer," +
                "    question_item_code integer," +
                "    comment varchar," +
                "    user_id varchar," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE answer_boolean_score" +
                "(" +
                "    id integer NOT NULL," +
                "    campaign_code integer," +
                "    headquarter_code integer," +
                "    zone_code integer," +
                "    city_code integer," +
                "    city_name varchar," +
                "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                "    yes_answer integer DEFAULT 0," +
                "    no_answer integer DEFAULT 0," +
                "    score integer," +
                "    meter_device_id integer," +
                "    question_item_code integer," +
                "    comment varchar," +
                "    user_id varchar," +
                " account_code varchar NOT NULL," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE target_zone" +
                "(" +
                "    zone_code integer NOT NULL," +
                "    campaign_code integer NOT NULL," +
                "    account_code varchar NOT NULL," +
                "    PRIMARY KEY (zone_code, campaign_code, account_code)" +
                ")");

        db.execSQL("CREATE TABLE sms_cell_phone" +
                "(" +
                "    id integer NOT NULL," +
                "    cell_phone_number varchar," +
                "    email varchar," +
                "    headquarter_code integer," +
                "    zone_code integer," +
                "    campaign_code integer," +
                "    account_code varchar NOT NULL," +
                "    is_active boolean," +
                "    PRIMARY KEY (id, account_code)" +
                ");");

        db.execSQL("CREATE TABLE general_image" +
                "(" +
                "    account_code varchar NOT NULL," +
                "    image BLOB," +
                "    PRIMARY KEY (account_code)" +
                ");");

        db.execSQL("CREATE TABLE country" +
                "(" +
                "    id integer NOT NULL," +
                "    code varchar ," +
                "    name varchar," +
                "    PRIMARY KEY (id)" +
                ");");

        db.execSQL("CREATE TABLE state" +
                "(" +
                "    id integer NOT NULL," +
                "    code varchar," +
                "    name varchar," +
                "    country_id integer," +
                "    PRIMARY KEY (id)" +
                ");");

        db.execSQL("CREATE TABLE city" +
                "(" +
                "    id integer NOT NULL," +
                "    code varchar," +
                "    name varchar," +
                "    state_id integer," +
                "    country_id integer," +
                "    PRIMARY KEY (id)" +
                ");");

        db.execSQL("CREATE TABLE general_setting_parameter " +
                "(" +
                " parameter_key varchar NOT NULL" +
                "  , parameter_value varchar" +
                "  , account_code varchar NOT NULL" +
                "  , PRIMARY KEY (parameter_key, account_code)" +
                ");");

        db.execSQL("CREATE TABLE current_config" +
                "(" +
                "    id integer NOT NULL," +
                "    zone_code integer NOT NULL," +
                "    headquarter_code integer NOT NULL," +
                "    account_code varchar NOT NULL," +
                "    PRIMARY KEY (id)" +
                ")");

        db.execSQL("INSERT INTO country(id, code, name) VALUES (169, 'Colombia', 'COL');");
        db.execSQL("INSERT INTO state(id, code, name, country_id) VALUES (11, 'BOGDC', 'Bogotá DC', 169);");
        db.execSQL("INSERT INTO state(id, code, name, country_id) VALUES (5, 'ANT', 'Antioquia', 169);");
        db.execSQL("INSERT INTO city(id, code, name, state_id, country_id) VALUES (11001, 'BOG', 'Bogotá D.C', 11, 169);");
        db.execSQL("INSERT INTO city(id, code, name, state_id, country_id) VALUES (5001, 'MED', 'Medellín', 5, 169);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "--onUpgrade");
        Log.w(TAG, "--Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        switch (oldVersion) {
            case 1:
                //Se agrega nueva estructura o datos
                if (newVersion == 2) {
                    Log.i(TAG, "--Upgrade structure DB to version 2");

                    db.execSQL("DROP TABLE general_image;");
                    db.execSQL("CREATE TABLE general_image" +
                            "(" +
                            "    account_code varchar NOT NULL," +
                            "    image BLOB," +
                            "    PRIMARY KEY (account_code)" +
                            ");");

                    //Se agrega tabla de parámetros generales
                    db.execSQL("CREATE TABLE general_setting_parameter " +
                            "(" +
                            " parameter_key varchar NOT NULL" +
                            "  , parameter_value varchar" +
                            "  , account_code varchar NOT NULL" +
                            "  , PRIMARY KEY (parameter_key, account_code)" +
                            ");");

                    db.execSQL("ALTER TABLE sms_cell_phone ADD COLUMN email varchar;");

                } else if (newVersion == 3) {
                    Log.i(TAG, "--Upgrade structure DB to version 3");

                    db.execSQL("CREATE TABLE answer_boolean_score" +
                            "(" +
                            "    id integer NOT NULL," +
                            "    campaign_code integer," +
                            "    headquarter_code integer," +
                            "    zone_code integer," +
                            "    city_code integer," +
                            "    city_name varchar," +
                            "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                            "    yes_answer integer DEFAULT 0," +
                            "    no_answer integer DEFAULT 0," +
                            "    score integer," +
                            "    meter_device_id integer," +
                            "    question_item_code integer," +
                            "    comment varchar," +
                            "    user_id varchar," +
                            " account_code varchar NOT NULL," +
                            "    PRIMARY KEY (id, account_code)" +
                            ");");

                    db.execSQL("CREATE TABLE answer_general_score" +
                            "(" +
                            "    id integer NOT NULL," +
                            "    headquarter_code integer," +
                            "    zone_code integer," +
                            "    city_code integer," +
                            "    city_name varchar," +
                            "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                            "    excellent integer DEFAULT 0," +
                            "    good integer DEFAULT 0," +
                            "    moderate integer DEFAULT 0," +
                            "    bad integer DEFAULT 0," +
                            "    poor integer DEFAULT 0," +
                            "    score integer," +
                            "    meter_device_id integer," +
                            "    question_item_code integer," +
                            "    comment varchar," +
                            "    user_id varchar," +
                            " account_code varchar NOT NULL," +
                            "    PRIMARY KEY (id, account_code)" +
                            ");");

                    db.execSQL("ALTER TABLE question_item ADD COLUMN question_type varchar;");

                }

                break;
            case 2:
                //Se agrega nueva estructura o datos
                if (newVersion == 3) {
                    Log.i(TAG, "--Upgrade structure DB to version 3");

                    db.execSQL("CREATE TABLE answer_boolean_score" +
                            "(" +
                            "    id integer NOT NULL," +
                            "    campaign_code integer," +
                            "    headquarter_code integer," +
                            "    zone_code integer," +
                            "    city_code integer," +
                            "    city_name varchar," +
                            "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                            "    yes_answer integer DEFAULT 0," +
                            "    no_answer integer DEFAULT 0," +
                            "    score integer," +
                            "    meter_device_id integer," +
                            "    question_item_code integer," +
                            "    comment varchar," +
                            "    user_id varchar," +
                            " account_code varchar NOT NULL," +
                            "    PRIMARY KEY (id, account_code)" +
                            ");");

                    db.execSQL("CREATE TABLE answer_general_score" +
                            "(" +
                            "    id integer NOT NULL," +
                            "    headquarter_code integer," +
                            "    zone_code integer," +
                            "    city_code integer," +
                            "    city_name varchar," +
                            "    registration_date datetime DEFAULT CURRENT_TIMESTAMP," +
                            "    excellent integer DEFAULT 0," +
                            "    good integer DEFAULT 0," +
                            "    moderate integer DEFAULT 0," +
                            "    bad integer DEFAULT 0," +
                            "    poor integer DEFAULT 0," +
                            "    score integer," +
                            "    meter_device_id integer," +
                            "    question_item_code integer," +
                            "    comment varchar," +
                            "    user_id varchar," +
                            " account_code varchar NOT NULL," +
                            "    PRIMARY KEY (id, account_code)" +
                            ");");

                    db.execSQL("ALTER TABLE question_item ADD COLUMN question_type varchar;");

                }

                break;
        }

    }
}
