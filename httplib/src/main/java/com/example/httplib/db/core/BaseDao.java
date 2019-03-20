package com.example.httplib.db.core;

import android.content.Context;

import com.example.httplib.http.util.GenericsResolver;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Describe:数据库基类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public abstract class BaseDao<T> implements DbManager.DbUpgradeListener {
    private Class<T> genericsClass;
    private DbManager dao;
    private Context context;

    @SuppressWarnings("unchecked")
    public BaseDao(Context context) {
        genericsClass = (Class<T>) GenericsResolver.getObjectClass(getClass(), BaseDao.class);
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbUpgradeListener(this);
        this.context = context;
        dao = x.getDb(config(context, daoConfig));
    }

    public Class<T> getGenericsClass() {
        return genericsClass;
    }

    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
        try {
            db.dropTable(getGenericsClass());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库管理配置
     *
     * @param context   上下文
     * @param daoConfig 数据库管理配置对象
     * @return 数据库管理配置对象
     */
    protected abstract DbManager.DaoConfig config(Context context, DbManager.DaoConfig daoConfig);

    /**
     * 获取Dao
     *
     * @return Dao对象
     */
    public DbManager getDao() {
        return dao;
    }

    /**
     * 获取上下文
     *
     * @return 上下文对象
     */
    public Context getContext() {
        return context;
    }

    /**
     * 存储单个数据对象
     *
     * @param t 数据对象
     * @return 是否存储成功
     */
    public boolean save(T t) {
        try {
            dao.save(t);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 存储或更新单个数据对象
     *
     * @param t 存储或更新的单个数据对象
     * @return 是否存储或更新成功
     */
    public boolean saveOrUpdate(T t) {
        try {
            dao.saveOrUpdate(t);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单个数据对象
     *
     * @param t 删除的单个数据对象
     * @return 是否删除成功
     */
    public boolean delete(T t) {
        try {
            dao.delete(t);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查找第一条数据对象
     *
     * @return 第一条数据对象
     */
    public T findFirst() {
        try {
            return getDao().findFirst(getGenericsClass());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找所有数据对象
     *
     * @return 所有数据对象列表
     */
    public List<T> findAll() {
        List<T> result;
        try {
            result = getDao().findAll(getGenericsClass());
            return result;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除所有对象
     *
     * @return 是否删除成功
     */
    public boolean deleteAll() {
        try {
            getDao().delete(getGenericsClass());
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
}
