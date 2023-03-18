package com.hfw.admin.easyexcel;

import lombok.*;

/**
 * 导入返回结果
 * @author farkle
 * @date 2023-03-17
 */
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class ImportResult {

    private ImportEnum importType;
    /**
     * 成功数量
     */
    private int successCnt;
    /**
     * 失败数量
     */
    private int failCnt;

    /**
     * 失败报告id
     */
    private String errorId;

}
