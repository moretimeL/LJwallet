package com.liu.ljwallet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liu.ljwallet.entity.MyWallet;

import java.util.List;

public class DbController {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private MyWalletDao myWalletDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"person.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        myWalletDao = mDaoSession.getMyWalletDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"person.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param myWallet
     */
    public void insertOrReplace(MyWallet myWallet){
        myWalletDao.insertOrReplace(myWallet);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param myWallet
     */
    public long insert(MyWallet myWallet){
        return  myWalletDao.insert(myWallet);
    }

    /**
     * 按条件查询数据
     */
    public List<MyWallet> searchByWhere(Long id){
        List<MyWallet>personInfors = (List<MyWallet>) myWalletDao.queryBuilder().where(MyWalletDao.Properties.Id.eq(id)).build().unique();
        return personInfors;
    }
    /**
     * 查询所有数据
     */
    public List<MyWallet> searchAll(){
        List<MyWallet>personInfors=myWalletDao.queryBuilder().list();
        return personInfors;
    }
    /**
     * 删除数据
     */
    public void delete(Long id){
        myWalletDao.queryBuilder().where(MyWalletDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
