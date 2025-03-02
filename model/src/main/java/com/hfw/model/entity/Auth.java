package com.hfw.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author farkle
 * @date 2022-12-16
 */
@Data
public class Auth<T> {
    /**
     * id
     */
    private Long id;
    /**
     * token
     */
    private String token;
    /**
     * 有效token
     */
    private List<String> validToken = new ArrayList<>();
    /**
     * 存储对象
     */
    T storeObj;
}
