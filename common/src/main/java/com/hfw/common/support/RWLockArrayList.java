package com.hfw.common.support;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于读写锁的线程安全ArrayList类
 * @param <E>
 * @author farkle
 * @date 2022-04-06
 *
 * 其他3中线程安全的List类
 * Vector: 性能是最差，所有的方法都是加了synchronized来同步，从而保证线程安全。
 * Collections.synchronizedList(List< T> list): 相当于包装了原始集合,在调用原始集合的所有方法事都加了synchronized来同步
 * CopyOnWriteArrayList:
 *  原理:
 *      添加元素时复制原数组,在数组末添加元素,然后丢弃原数组,指向复制后的数组
 *      读时不加锁,写时加锁同步,可能读到脏数据,能保证数据的最终一致性
 *  使用于读多写少并且数据量少的情况
 *
 * 线程安全的Set:
 *  Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
 *  CopyOnWriteArraySet:基于CopyOnWriteArrayList实现
 */
public class RWLockArrayList<E> implements List<E> {
    private ArrayList<E> list = new ArrayList<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public int size() {
        try {
            lock.readLock().lock();
            return list.size();
        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        try {
            lock.readLock().lock();
            return list.iterator();
        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        try {
            lock.writeLock().lock();
            return list.add(e);
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        try {
            lock.readLock().lock();
            return list.get(index);
        }finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
