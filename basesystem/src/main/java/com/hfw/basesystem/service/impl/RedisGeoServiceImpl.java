package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.service.RedisGeoService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author farkle
 * @date 2022-11-27
 */
@Service
public class RedisGeoServiceImpl implements RedisGeoService {
    /**
     * 附近的点最多获取多少个
     */
    private static final Integer max_count = 10000;
    private static final Integer min_count = 100;
    /**
     * 附近的点最大半径距离,单位米
     */
    private static final Integer max_distance = 1000*1000;
    /**
     * 附近的点实现zset key
     */
    private static final String near_zset_key = "near_zset";
    public static final String near_geo_key = "near_geo";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> near(double lng, double lat, Integer pageNumber, Integer pageSize){
        PageResult page = new PageResult(pageNumber, pageSize);
        Integer requestCount = page.getEnd()+1;
        if(pageNumber>1 && requestCount>max_count){
            return null;
        }
        Integer searchCount = min_count;
        int i = 0;
        while (searchCount<max_count && searchCount<requestCount){
            if(NumberUtil.isEven(i)){
                searchCount *= 5;
            }else{
                searchCount *= 2;
            }
            //System.out.println(searchCount);
            i++;
        }
        if(searchCount>max_count){
            searchCount = min_count;
        }
        //System.out.println(searchCount+":end");

        Long size = redisUtil.zsetSize(near_zset_key);
        if(searchCount>size){
            Integer distance = 10*1000;
            Long cnt = redisUtil.geoSearchAndStore(near_geo_key, near_zset_key, new Point(lng, lat), new Distance(distance, Metrics.METERS), searchCount);
            //System.out.println(distance);
            while (cnt < requestCount && distance<max_distance){
                distance *= 10;
                cnt = redisUtil.geoSearchAndStore(near_geo_key, near_zset_key, new Point(lng, lat), new Distance(distance, Metrics.METERS), searchCount);
            }
            //System.out.println(distance+":end");
            redisUtil.expire(near_zset_key, 60*60);
        }
        return redisUtil.zsetRangeWithScores(near_zset_key, page.getStart(),page.getEnd());
    }

    public Long geoAdd(double lng, double lat, Object obj){
        return redisUtil.geoAdd(near_geo_key, new Point(lng,lat), obj);
    }

    public Long geoRemove(Object obj){
        return redisUtil.geoRemove(near_geo_key, obj);
    }

}
