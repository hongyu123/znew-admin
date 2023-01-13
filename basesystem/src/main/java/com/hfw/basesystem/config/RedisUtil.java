package com.hfw.basesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.GeoShape;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author farkle
 * @create 2020-04-25
 */
@Component
public class RedisUtil {

    /**
     * bitmap实现消息的已读未读状态,数据少时使用,不存在误判
     */
    private final static String MESSAGE_BITMAP = "MESSAGE_BITMAP_";

    /**
     * Bloom Filter实现消息的已读未读状态,数据多时使用,存在误判
     */
    private final static String BLOOM_FILTER = "BLOOM_FILTER_";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    public <T> T get(String key) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    public <T> T getAndExpire(String key, long expire){
        return (T)redisTemplate.opsForValue().getAndExpire(key, expire, TimeUnit.SECONDS);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Boolean setNxEx(String key, String value, long expire) {
        return redisTemplate.execute((RedisConnection redisConnection) ->
                redisConnection.set(key.getBytes(), value.getBytes(), Expiration.from(expire, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT)
        );
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取消息的状态
     *
     * @param messageId 消息id
     * @param userId    用户id
     * @return false未读 true已读
     */
    public Boolean msgStatus(Long messageId, Long userId) {
        return stringRedisTemplate.execute((RedisConnection redisConnection) -> {
            try {
                return redisConnection.getBit((MESSAGE_BITMAP + messageId).getBytes("UTF-8"), userId);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return Boolean.FALSE;
        });
    }

    /**
     * 用户读取消息(把消息置为已读)
     *
     * @param messageId 消息id
     * @param userId    用户id
     * @return
     */
    public void readMsg(Long messageId, Long userId) {
        stringRedisTemplate.execute((RedisConnection redisConnection) -> {
            try {
                return redisConnection.setBit((MESSAGE_BITMAP + messageId).getBytes("UTF-8"), userId, true);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * 布隆过滤器-添加
     * 可用于把消息置为已读
     *
     * @param key
     * @param value
     * @return
     */
    public Long bfAdd(String key, String value) {
        DefaultRedisScript<Long> script = new DefaultRedisScript();
        script.setResultType(Long.class);
        script.setScriptSource(new StaticScriptSource("return redis.call('bf.add', KEYS[1], KEYS[2])"));
        return stringRedisTemplate.execute(script, Arrays.asList(key, value));
    }

    /**
     * 布隆过滤器-判断
     * 可用于获取消息的状态
     * @param key
     * @param value
     * @return
     */
    public Boolean bfExists(String key, String value) {
        DefaultRedisScript<Long> script = new DefaultRedisScript();
        script.setResultType(Long.class);
        script.setScriptSource(new StaticScriptSource("return redis.call('bf.exists', KEYS[1], KEYS[2])"));
        Long res = stringRedisTemplate.execute(script, Arrays.asList(key, value));
        return 1 == res;
    }

    /**
     * 布隆过滤器-设置存储空间和错误率
     * error_rate， initial_size，错误率越低，需要的空间越大，error_rate表示预计错误率，initial_size参数表示预计放入的元素数量，当实际数量超过这个值时，误判率会上升，所以需要提前设置一个较大的数值来避免超出
     * @param key
     * @param count 空间大小
     * @return
     */
    public Boolean bfReserve(String key, Long count) {
        String cnt = count > 100 ? count + "" : "100";
        String errorRate = new BigDecimal(cnt).divide(new BigDecimal(10000)).toString();
        DefaultRedisScript<String> script = new DefaultRedisScript();
        script.setResultType(String.class);
        script.setScriptSource(new StaticScriptSource("return redis.call('bf.reserve', KEYS[1], KEYS[2], KEYS[3])"));
        String res = stringRedisTemplate.execute(script, Arrays.asList(key, errorRate, cnt));
        return "OK".equals(res);
    }


    /**
     * 添加坐标点
     * @param key
     * @param point
     * @param value
     * @return
     */
    public Long geoAdd(String key, Point point, Object value){
       return redisTemplate.boundGeoOps(key).add(point, value);
    }
    public Long geoRemove(String key, Object value){
        return redisTemplate.boundGeoOps(key).remove(value);
    }

    /**
     * 搜索并存储
     * @param key
     * @param zsetKey
     * @param point
     */
    public Long geoSearchAndStore(String key, String zsetKey, Point point, Distance distance, long count){
        RedisGeoCommands.GeoSearchStoreCommandArgs args = RedisGeoCommands.GeoSearchStoreCommandArgs.newGeoSearchStoreArgs().storeDistance().sortAscending().limit(count);
        return redisTemplate.boundGeoOps(key).searchAndStore(zsetKey, GeoReference.fromCoordinate(point), GeoShape.byRadius(distance), args);
    }
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Point point, Distance distance, long count){
        Circle circle = new Circle(point,distance);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending().limit(count);
        return redisTemplate.boundGeoOps(key).radius(circle, args);
    }
    public Distance geoDistance(String key,Object o1,Object o2){
        return redisTemplate.boundGeoOps(key).distance(o1,o2);
    }

    public <T> Set<T> zsetRange(String key, long start, long end){
        return (Set<T>) redisTemplate.opsForZSet().range(key, start, end);
    }
    public Set<ZSetOperations.TypedTuple<Object>> zsetRangeWithScores(String key, long start, long end){
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }
    public Long zsetSize(String key){
        return redisTemplate.opsForZSet().size(key);
    }


}
