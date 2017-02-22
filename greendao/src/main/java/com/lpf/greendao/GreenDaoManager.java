package com.lpf.greendao;

import com.lpf.greendao.gen.DaoMaster;
import com.lpf.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by liupengfei on 17/2/4.
 * use this class or write on App
 */

public class GreenDaoManager {

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.getContext(), ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster(){
        return mDaoMaster;
    }

    public DaoSession getSession(){
        return mDaoSession;
    }

    public DaoSession getNewSession(){
        mDaoSession  = mDaoMaster.newSession();
        return mDaoSession;
    }

}
