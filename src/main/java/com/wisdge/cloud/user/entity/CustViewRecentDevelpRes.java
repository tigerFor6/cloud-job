package com.wisdge.cloud.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户360视图最近动态响应模型
 *
 * @author tiger
 * @date 2021-11-04 11:07:12
 */
@Data
public class CustViewRecentDevelpRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String develpDate;
    private String content;
    private String type;
    private String relationId;
}