package com.example.httplib.db.cahce;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Describe:缓存DTO
 *
 * @author zhoupeng
 * Created on 2019/3/20.
 */
@Table(name = "cache")
public class CacheDto {

    @Column(isId = true, name = "id", autoGen = true)
    private int id;
    @Column(name = "cache_key")
    private String key;
    @Column(name = "cache_value")
    private String value;

    public CacheDto() {
    }

    public CacheDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String content) {
        this.value = content;
    }

    @Override
    public String toString() {
        return "CacheDto{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
