package com.luobi.study.project.common.model;

import lombok.Getter;

@Getter
public enum CommonResponseCode {

    /**
     * 0 -> success
     */
    SUCCESS(0),

    /**
     * 100-999 -> common error
     */
    RESOURCE_EXISTS(101),
    RESOURCE_NOT_EXISTS(102),

    INSTANCE_NOT_EXISTS(103),
    CONTRACT_NOT_EXISTS(104),

    PARAMETER_IS_NULL(105),
    PARAMETER_IS_INVALID(106),

    INVALID_OPERATION(107),
    OPERATION_FAILED(108),
    DUPLICATE_KEY(109),
    OUT_OF_BOUND(110),

    NO_AUTH(401),

    SYSTEM_ERROR(500),
    REJECT_OPERATION(999),

    /**
     * 1000-9999 -> API ERROR
     * ...
     */
    API_ERROR(1000),
    NOT_SUPPORTED_API(1001);

    private Integer code;

    CommonResponseCode(Integer code) {
        this.code = code;
    }

}
