package com.example.httplib.db.cahce;

import android.content.Context;

/**
 * Describe:缓存管理类
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
public class CacheManager {
    private static CacheManager manager;
    private CacheDao mCacheDao;

    public CacheManager(Context context) {
        mCacheDao = new CacheDao(context);
    }

    public static CacheManager getInstance(Context context) {
        if (null == manager) {
            manager = new CacheManager(context);
        }
        return manager;
    }

    /**
     * 存储 By 缓存key & Tag
     *
     * @param key   缓存key
     * @param value 缓存数据
     * @return 是否存储成功
     */
    public boolean saveCache(String value, String key) {
        // 有Tag标签的Key
//        if (null != tag) {
//            StringBuilder builder = new StringBuilder();
//            for (Object o : tag) {
//                builder.append("_").append(o);
//            }
//            key = key + builder.toString();
//        }
        // 删除之前的缓存数据
        mCacheDao.deleteByKey(key);
        // 存储数据
        return mCacheDao.saveOrUpdate(new CacheDto(key, value));
    }

    /**
     * 获取缓存 By 缓存key & Tag
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public CacheDto getCacheByKey(String key) {
        // 有Tag标签的Key
//        if (null != tag && tag.length > 0) {
//            StringBuilder builder = new StringBuilder();
//            for (Object o : tag) {
//                builder.append("_").append(o);
//            }
//            key = key + builder.toString();
//        }
        return mCacheDao.findByKey(key);
    }

    public boolean clear() {
        return mCacheDao.deleteAll();
    }
}
