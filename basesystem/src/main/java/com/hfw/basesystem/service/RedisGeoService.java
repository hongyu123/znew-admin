package com.hfw.basesystem.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * redis实现附近的人
 * @author farkle
 * @date 2022-11-28
 */
public interface RedisGeoService {

    /**
     * 查询附近的点
     * @param lng
     * @param lat
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Set<ZSetOperations.TypedTuple<Object>> near(double lng, double lat, Integer pageNumber, Integer pageSize);

    /**
     * 添加点
     * @param lng
     * @param lat
     * @param obj
     * @return
     */
    Long geoAdd(double lng, double lat, Object obj);

    /**
     * 删除点
     * @param obj
     * @return
     */
    Long geoRemove(Object obj);
}
