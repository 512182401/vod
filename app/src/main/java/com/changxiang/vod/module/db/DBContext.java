package com.changxiang.vod.module.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;


import com.changxiang.vod.common.utils.MyFileUtil;

import java.io.File;


class DBContext extends ContextWrapper {

    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DBContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        String dbfile = MyFileUtil.getAppDir() + File.separator + "database" + File.separator + name;
        if (!dbfile.endsWith(".db")) {
            dbfile += ".db";
        }

        File result = new File(dbfile);

        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }

        return result;
    }

    /*
     * this version is called for android devices >= api-11. thank to @damccull
     * for fixing this.
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }

    /* this version is called for android devices < api-11 */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
                getDatabasePath(name), null);

        return result;
    }
}
