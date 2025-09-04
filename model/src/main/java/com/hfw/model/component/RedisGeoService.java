package com.hfw.model.component;

import com.hfw.model.entity.PageResult;
import com.hfw.model.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author farkle
 * @date 2022-11-27
 */
@Component
public class RedisGeoService {
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
    private static final String near_zset_key = "redis_geo:near_zset";
    public static final String near_geo_key = "redis_geo:near_geo";

    @Autowired
    private RedisUtil redisUtil;

    public Set<ZSetOperations.TypedTuple<Object>> near(double lng, double lat, Integer pageNumber, Integer pageSize){
        PageResult<Void> page = new PageResult<>(pageNumber, pageSize);
        Integer requestCount = page.end()+1;
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

        Long size = redisUtil.zCard(near_zset_key);
        if(searchCount>size){
            Integer distance = 10*1000;
            Long cnt = redisUtil.geoSearchStore(near_geo_key, near_zset_key, new Point(lng, lat), new Distance(distance, Metrics.METERS), searchCount);
            //System.out.println(distance);
            while (cnt < requestCount && distance<max_distance){
                distance *= 10;
                cnt = redisUtil.geoSearchStore(near_geo_key, near_zset_key, new Point(lng, lat), new Distance(distance, Metrics.METERS), searchCount);
            }
            //System.out.println(distance+":end");
            redisUtil.expire(near_zset_key, 1, TimeUnit.HOURS);
        }
        return redisUtil.zRangeWithScores(near_zset_key, page.start(),page.end());
    }

    public Long geoAdd(double lng, double lat, Object obj){
        return redisUtil.geoAdd(near_geo_key, new Point(lng,lat), obj);
    }

    public Long geoRemove(Object obj){
        return redisUtil.geoRemove(near_geo_key, obj);
    }

}
