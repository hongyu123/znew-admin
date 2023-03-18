package com.hfw.basesystem.config;

import javax.annotation.Resource;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 官方命令文档:https://redis.io/commands/
 * @author farkle
 * @create 2020-04-25
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*************************string操作*****************************/
    public void setStr(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }
    public void setStrEx(String key, String value, long expire){
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }
    public String getStr(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取并设置过期时间
     * @param key
     * @param expire
     * @param <T>
     * @return
     */
    public <T> T getEx(String key, long expire){
        return (T)redisTemplate.opsForValue().getAndExpire(key, expire, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值和超时事件
     * @param key
     * @param value
     * @param expire
     */
    public void setEx(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * key不存在时设置
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(String key, Object value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    /**
     * key存在时设置
     * @param key
     * @param value
     * @return
     */
    public Boolean setXx(String key, Object value){
        return redisTemplate.opsForValue().setIfPresent(key,value);
    }
    public Boolean setNxEx(String key, Object value, long timeout){
        return redisTemplate.opsForValue().setIfAbsent(key,value, timeout, TimeUnit.SECONDS);
    }
    /*public Boolean setNxEx(String key, Object value, long expire) {
        return redisTemplate.execute((RedisConnection redisConnection) ->
                redisConnection.set(key.getBytes(), value.getBytes(), Expiration.from(expire, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT)
        );
    }*/
    public <T> T getSet(String key, Object value){
        return (T)redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
    public Boolean exists(String key){
        return redisTemplate.hasKey(key);
    }
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }
    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
    public Long decr(String key){
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     *************************list操作*****************************
     * 有序,可重复
     * 1、作为栈或队列使用
     * 2、可用于各种列表，比如用户列表、商品列表、评论列表等。
     */

    /**
     * 从列表头部插入
     * @param key
     * @param value
     * @return 返回列表的长度
     */
    public Long lPush(String key, Object value){
        return redisTemplate.opsForList().leftPush(key, value);
    }
    public <T> T lPop(String key){
        return (T) redisTemplate.opsForList().leftPop(key);
    }
    public Long rPush(String key, Object value){
        return redisTemplate.opsForList().rightPush(key, value);
    }
    public <T> T rPop(String key){
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 当列表为空时阻塞
     * @param key
     * @param timeout 设置最大阻塞时间
     * @param <T>
     * @return
     */
    public <T> T bLPop(String key, long timeout){
        return (T) redisTemplate.opsForList().leftPop(key, timeout, TimeUnit.SECONDS);
    }
    public <T> T bRPop(String key, long timeout){
        return (T) redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }
    public Long lLen(String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 获取指定索引的元素
     * @param key
     * @param index 索引,从0开始
     * @param <T>
     * @return
     */
    public <T> T lIndex(String key, long index){
        return (T) redisTemplate.opsForList().index(key, index);
    }
    public <T> List<T> lRange(String key, long start, long end){
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 从列表中删除元素
     * @param key
     * @param value 要删除的元素
     * @return
     */
    public Long lRem(String key, Object value){
        /**
         * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
         * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
         * count = 0 : 移除表中所有与 VALUE 相等的值
         */
        return redisTemplate.opsForList().remove(key,0, value);
    }

    /**
     * 设置列表中指定索引的值
     * @param key
     * @param index 索引
     * @param value 值
     */
    public void lSet(String key, long index, Object value){
        redisTemplate.opsForList().set(key,index,value);
    }

    /**
     * 对列表进行修剪，只保留start到end区间
     * @param key
     * @param start
     * @param end
     */
    public void lTrim(String key, long start, long end){
        redisTemplate.opsForList().trim(key,start,end);
    }
    public <T> T rPopLPush(String sourceKey, String destinationKey){
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }
    //会阻塞
    public <T> T bRPopLPush(String sourceKey, String destinationKey, long timeout){
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout,TimeUnit.SECONDS);
    }

    /**
     * 将value插入到列表，且位于值pivot之前
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long lInsert(String key, Object pivot, Object value){
        return redisTemplate.opsForList().leftPush(key,pivot,value);
    }

    /**
     **************************set操作*****************************
     * 无序,不重复
     * 适用于不能重复的且不需要顺序的数据结构, 比如：关注的用户，还可以通过spop进行随机抽奖
     */

    public Long sAdd(String key, Object ... values){
        return redisTemplate.opsForSet().add(key, values);
    }
    public Long sRem(String key, Object ... values){
        return redisTemplate.opsForSet().remove(key, values);
    }
    public <T> Set<T> sMembers(String key){
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机弹出一个元素,删除
     * @param key
     * @param <T>
     * @return
     */
    public <T> T sPop(String key){
        return (T) redisTemplate.opsForSet().pop(key);
    }

    /**
     * 随机获取一个元素,不删除
     * @param key
     * @param <T>
     * @return
     */
    public <T> T sRandMember(String key){
        return (T) redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 获取数量
     * @param key
     * @return
     */
    public Long sCard(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 是否在集合内
     * @param key
     * @param member
     * @return
     */
    public Boolean sIsMember(String key, Object member){
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 求两个集合的交集
     * @param key
     * @param anotherKey
     * @param <T>
     * @return
     */
    public <T> Set<T> sInter(String key, String anotherKey){
        return (Set<T>) redisTemplate.opsForSet().intersect(key, anotherKey);
    }

    /**
     * 求两个集合的差集
     * @param key
     * @param anotherKey
     * @param <T>
     * @return
     */
    public <T> Set<T> sDiff(String key, String anotherKey){
        return (Set<T>) redisTemplate.opsForSet().difference(key, anotherKey);
    }

    /**
     * 求两个集合的并集
     * @param key
     * @param anotherKey
     * @param <T>
     * @return
     */
    public <T> Set<T> sUnion(String key, String anotherKey){
        return (Set<T>) redisTemplate.opsForSet().union(key, anotherKey);
    }

    /**
     **************************zset操作*****************************
     * 有序,不重复,且每一个元素关联一个score
     * 由于可以按照分值排序，所以适用于各种排行榜。比如：点击排行榜、销量排行榜、关注排行榜等
     */

    public Boolean zAdd(String key, Object value, double score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }
    public Long zRem(String key, Object... values){
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 获取集合数量
     * @param key
     * @return
     */
    public Long zCard(String key){
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 返回集合中score值在[min,max]区间
     * 的元素数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zCount(String key, double min, double max){
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 集合的元素分值增加
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public Double zIncrBy(String key, Object value, double delta){
        return redisTemplate.opsForZSet().incrementScore(key,value,delta);
    }

    /**
     * 获取指定元素的分值
     * @param key
     * @param value
     * @return
     */
    public Double zScore(String key, Object value){
        return redisTemplate.opsForZSet().score(key,value);
    }

    /**
     * 获取指定元素的分值排行榜(升序,分值从小到大)
     * @param key
     * @param value
     * @return
     */
    public Long zrank(String key, Object value){
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     *  获取指定元素的分值排行榜(降序,分值从大到小)
     * @param key
     * @param value
     * @return
     */
    public Long zRevRank(String key, Object value){
        return redisTemplate.opsForZSet().reverseRank(key,value);
    }

    /**
     * 按索引获取集合中的数据(升序)
     * @param key
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public <T> Set<T> zRange(String key, long start, long end){
        return (Set<T>) redisTemplate.opsForZSet().range(key, start, end);
    }
    public <T> Set<T> zRevRange(String key, long start, long end){
        return (Set<T>) redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 按索引获取集合中的数据及分数(升序)
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end){
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据分值获取集合中的数据(升序)
     * @param key
     * @param min
     * @param max
     * @param <T>
     * @return
     */
    public <T> Set<T> zRangeByScore(String key, double min, double max){
        return (Set<T>) redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     *************************hash操作*****************************
     * 应用场景: 对象的存储 ，表数据的映射
     */

    /**
     * 设置对象的 一个字段
     * @param key 对象主键
     * @param hashKey 对象字段
     * @param hashValue 对象主键值
     */
    public void hSet(String key, String hashKey, Object hashValue){
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }
    public void hMSet(String key, Map<String,Object> data){
        redisTemplate.opsForHash().putAll(key,data);
    }

    /**
     * hashKey不存在时设置 hashKey的值
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    public Boolean hSetNX(String key, String hashKey, Object hashValue){
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey,hashValue);
    }

    /**
     * 查看对象的 某个字段是否存在
     * @param key 对象主键
     * @param hashKey 对象字段
     * @return
     */
    public Boolean hExists(String key, String hashKey){
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    /**
     * 获取对象的某个字段值
     * @param key 对象主键
     * @param hashKey 对象字段
     * @param <T>
     * @return
     */
    public <T> T hGet(String key, String hashKey){
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }
    public List hMGet(String key, Collection hashKeys){
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 删除对象的某个字段
     * @param key 对象主键
     * @param value 对象字段
     */
    public Long hDel(String key, Object... value){
        return redisTemplate.opsForHash().delete(key, value);
    }

    /**
     * 获取对象的字段个数
     * @param key
     * @return
     */
    public Long hLen(String key){
        return redisTemplate.opsForHash().size(key);
    }

    /**
     **************************bitmap操作*****************************
     * 应用:用户每月签到，用户id为key ， 日期作为偏移量 1表示签到
     */

    public Boolean setBit(String key, long offset, boolean value){
        return redisTemplate.opsForValue().setBit(key,offset,value);
    }
    /*public void setBit(String key, long offset, boolean value) {
        stringRedisTemplate.execute((RedisConnection redisConnection) -> {
            try {
                return redisConnection.setBit((key).getBytes("UTF-8"), offset, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }*/
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key,offset);
    }

    /**
     **************************GEO地理位置操作*****************************
     * 应用:附件的人,计算距离
     */

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

    /**
     * 获取geo成员的坐标
     * @param key
     * @param members
     * @return
     */
    public List<Point> geoPos(String key, Object... members){
        return redisTemplate.boundGeoOps(key).position(members);
    }

    /**
     * 计算两个点的距离
     * @param key
     * @param o1 点1的值
     * @param o2 点2的值
     * @return
     */
    public Distance geoDist(String key,Object o1,Object o2){
        return redisTemplate.boundGeoOps(key).distance(o1,o2);
    }
    /**
     * 中心点搜索
     * @param key
     * @param point 中心点
     * @param distance 半径
     * @param count 数量
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Point point, Distance distance, long count){
        Circle circle = new Circle(point,distance);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending().limit(count);
        return redisTemplate.boundGeoOps(key).radius(circle, args);
    }

    /**
     * 成员做中心点搜索
     * @param key
     * @param member 成员
     * @param distance 半径
     * @param count 数量
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMember(String key, Object member,Distance distance, long count){
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending().limit(count);
        return redisTemplate.boundGeoOps(key).radius(member, distance, args);
    }

    /**
     * 根据值删除坐标点
     * @param key
     * @param value
     * @return
     */
    public Long geoRemove(String key, Object value){
        return redisTemplate.boundGeoOps(key).remove(value);
        //return this.zrem(key,value); //GEO数据是保存在zset中的,所以可以通过zset的命令删除
    }

    /**
     * 中心点搜索并存储成zset
     * @param key
     * @param zsetKey 存储的zset_key
     * @param point 中心点
     * @param distance 半径
     * @param count 数量
     * @return
     */
    public Long geoSearchStore(String key, String zsetKey, Point point, Distance distance, long count){
        RedisGeoCommands.GeoSearchStoreCommandArgs args = RedisGeoCommands.GeoSearchStoreCommandArgs.newGeoSearchStoreArgs().storeDistance().sortAscending().limit(count);
        return redisTemplate.boundGeoOps(key).searchAndStore(zsetKey, GeoReference.fromCoordinate(point), GeoShape.byRadius(distance), args);
    }



    /*************************布隆过滤器操作*****************************/
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
        return redisTemplate.execute(script, Arrays.asList(key, value));
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
        Long res = redisTemplate.execute(script, Arrays.asList(key, value));
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
        String res = redisTemplate.execute(script, Arrays.asList(key, errorRate, cnt));
        return "OK".equals(res);
    }

}
