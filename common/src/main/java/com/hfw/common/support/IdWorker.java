package com.hfw.common.support;

import java.util.UUID;

/**
 * 雪花算法
 * 雪花算法可以保证：
 *  所有生成的id按时间趋势递增
 *  整个分布式系统内不会产生重复id（因为有datacenterId和workerId来做区分）
 * 优点:
 *  高性能高可用：生成时不依赖于数据库，完全在内存中生成。
 *  容量大：每秒中能生成数百万的自增ID。
 *  ID自增：存入数据库中，索引效率高。
 * 缺点:
 *  依赖与系统时间的一致性，如果系统时间被回调，或者改变，可能会造成id冲突或者重复。
 * 参考博客:
 *  https://segmentfault.com/a/1190000011282426
 *  https://blog.csdn.net/lq18050010830/article/details/89845790
 *
 * @author farkle
 * @date 2022-04-07
 */
public class IdWorker{
    //10位工作机器id,由5位datacenterId和5位workerId组成
    private long workerId;//工作id
    private long datacenterId;//数据id
    //最后12位序列号
    private long sequence;

    public IdWorker(long workerId, long datacenterId, long sequence){
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",maxDatacenterId));
        }
        //System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d\n",
        //        timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }
    public IdWorker(){
        this(1,1,0);
    }

    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public long getWorkerId(){
        return workerId;
    }

    public long getDatacenterId(){
        return datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            //序列化递增
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }

    private static IdWorker idWorker = new IdWorker();
    public static long id(){
        return idWorker.nextId();
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}