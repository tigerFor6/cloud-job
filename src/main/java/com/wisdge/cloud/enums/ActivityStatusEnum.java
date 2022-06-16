package com.wisdge.cloud.enums;

/**
 * 活动状态枚举
 *
 * @author lsy
 * @date 2022-02-15
 */
public enum ActivityStatusEnum {
    DRAFT(0, "草稿"),
    ON_GING(1, "进行中"),
    COMPLETE(2, "已完成"),
    BE_OVERDUE(3, "逾期"),
    TERMINATION(4, "终止");

    private final Integer code;
    private final String name;

    ActivityStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return name;
    }
}
