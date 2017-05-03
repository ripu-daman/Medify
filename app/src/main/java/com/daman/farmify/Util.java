package com.daman.farmify;

import android.net.Uri;

/**
 * Created by Daman on 11-04-2017.
 */

public class Util {
    //Database Info
    public static final int DB_VERSION =1;
    public static final String DB_NAME ="Farmify.db";

    //Table info
    public static final String TAB_NAME = "Users";
    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "NAME";
    public static final String Col_ADDRESS ="ADDRESS";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_STATE = "STATE";
    public static final String CREATE_TAB_QUERY ="create table Users(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "ADDRESS varchar(256)," +
            "PHONE varchar(40)," +
            "EMAIL varchar(40)," +
            "PASSWORD varchar(40)," +
            "STATE varchar(20)" +
            ")";
    public static final Uri User_URI = Uri.parse("content://com.daman.farmify.userprovider/"+TAB_NAME);
    public static final String INSERT_USER_PHP = "http://gogna.esy.es/Farmify/insert.php";
    public static final String LOGIN_USER_PHP = "http://gogna.esy.es/Farmify/login.php";
}
