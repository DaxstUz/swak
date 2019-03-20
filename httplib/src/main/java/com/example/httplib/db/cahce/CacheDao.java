package com.example.httplib.db.cahce;

import android.content.Context;

import com.example.httplib.db.core.BaseDao;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

/**
 * Describe:缓存数据库
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class CacheDao extends BaseDao<CacheDto> {

    public CacheDao(Context context) {
        super(context);
    }

    @Override
    protected DbManager.DaoConfig config(Context context, DbManager.DaoConfig daoConfig) {
        return daoConfig.setDbDir(context.getCacheDir()).setDbName("cache.db").setDbVersion(4);
    }

    /**
     * 删除 By 缓存key
     *
     * @param key 缓存key
     * @return 是否删除成功
     */
    public boolean deleteByKey(String key) {
        try {
            getDao().delete(getGenericsClass(), WhereBuilder.b("cache_key", "=", key));
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查找 By 缓存key
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public CacheDto findByKey(String key) {
        CacheDto cacheDto = null;
        try {
            cacheDto = getDao().selector(getGenericsClass()).where(WhereBuilder.b("cache_key", "=", key)).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return cacheDto;
    }
}
