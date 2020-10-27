package com.liu.ljwallet.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.liu.ljwallet.entity.MyWallet;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_WALLET".
*/
public class MyWalletDao extends AbstractDao<MyWallet, Long> {

    public static final String TABLENAME = "MY_WALLET";

    /**
     * Properties of entity MyWallet.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SeedCode = new Property(1, String.class, "seedCode", false, "SEED_CODE");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property KeystorePath = new Property(4, String.class, "keystorePath", false, "KEYSTORE_PATH");
        public final static Property Address = new Property(5, String.class, "address", false, "ADDRESS");
        public final static Property IsCurrent = new Property(6, boolean.class, "isCurrent", false, "IS_CURRENT");
        public final static Property IsBackup = new Property(7, boolean.class, "isBackup", false, "IS_BACKUP");
    }


    public MyWalletDao(DaoConfig config) {
        super(config);
    }
    
    public MyWalletDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_WALLET\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SEED_CODE\" TEXT," + // 1: seedCode
                "\"USER_NAME\" TEXT," + // 2: userName
                "\"PASSWORD\" TEXT," + // 3: password
                "\"KEYSTORE_PATH\" TEXT," + // 4: keystorePath
                "\"ADDRESS\" TEXT," + // 5: address
                "\"IS_CURRENT\" INTEGER NOT NULL ," + // 6: isCurrent
                "\"IS_BACKUP\" INTEGER NOT NULL );"); // 7: isBackup
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_WALLET\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyWallet entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String seedCode = entity.getSeedCode();
        if (seedCode != null) {
            stmt.bindString(2, seedCode);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String keystorePath = entity.getKeystorePath();
        if (keystorePath != null) {
            stmt.bindString(5, keystorePath);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
        stmt.bindLong(7, entity.getIsCurrent() ? 1L: 0L);
        stmt.bindLong(8, entity.getIsBackup() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyWallet entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String seedCode = entity.getSeedCode();
        if (seedCode != null) {
            stmt.bindString(2, seedCode);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String keystorePath = entity.getKeystorePath();
        if (keystorePath != null) {
            stmt.bindString(5, keystorePath);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
        stmt.bindLong(7, entity.getIsCurrent() ? 1L: 0L);
        stmt.bindLong(8, entity.getIsBackup() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyWallet readEntity(Cursor cursor, int offset) {
        MyWallet entity = new MyWallet( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // seedCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // keystorePath
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // address
            cursor.getShort(offset + 6) != 0, // isCurrent
            cursor.getShort(offset + 7) != 0 // isBackup
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyWallet entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSeedCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setKeystorePath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAddress(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsCurrent(cursor.getShort(offset + 6) != 0);
        entity.setIsBackup(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyWallet entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyWallet entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyWallet entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
