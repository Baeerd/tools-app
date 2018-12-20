package com.app.common.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    List<T> find(Map<String, String> params);

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}
