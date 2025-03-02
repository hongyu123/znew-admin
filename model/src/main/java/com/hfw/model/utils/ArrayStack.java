package com.hfw.model.utils;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * 数组栈
 * @param <E>
 * @author farkle
 * @date 2022-04-06
 */
public class ArrayStack<E> extends ArrayList<E> {

    private static final long serialVersionUID = 2130079159931574599L;

    public ArrayStack() {
        super();
    }

    public ArrayStack(int initialSize) {
        super(initialSize);
    }

    public boolean empty() {
        return isEmpty();
    }

    /**
     * 获取栈顶元素,不删除
     * @return
     * @throws EmptyStackException
     */
    public E getLast() throws EmptyStackException {
        int n = size();
        if (n <= 0) {
            throw new EmptyStackException();
        } else {
            return get(n - 1);
        }
    }
    /**
     * 获取栈顶元素,不删除
     * @return
     */
    public E peekLast() {
        int n = size();
        if (n <= 0) {
            return null;
        } else {
            return get(n - 1);
        }
    }


    /**
     * 出栈
     * @return
     * @throws EmptyStackException
     */
    public E removeLast() throws EmptyStackException{
        int n = size();
        if (n <= 0) {
            throw new EmptyStackException();
        } else {
            return remove(n - 1);
        }
    }
    /**
     * 出栈
     * @return
     */
    public E pollLast() {
        int n = size();
        if (n <= 0) {
            return null;
        } else {
            return remove(n - 1);
        }
    }

    /**
     * 入栈
     * @param item
     */
    public void addLast(E item){
        add(item);
    }

    /**
     * 入栈
     * @param item
     * @return
     */
    public boolean offerLast(E item){
        return add(item);
    }

}
