package com.pmx.zelda_java.config;

import lombok.Getter;
import lombok.Setter;

public class AjaxResult {

    // 成功 失败 异常

    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private String msg;
    @Setter
    @Getter
    private Object data;

    public AjaxResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    public static AjaxResult success(Object data) {
        AjaxResult ajaxResult = new AjaxResult(200, "success", data);
        return ajaxResult;
    }

    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult(200, "success", null);
        return ajaxResult;
    }

    public static AjaxResult success(String msg) {
        AjaxResult ajaxResult = new AjaxResult(200, msg, null);
        return ajaxResult;
    }



    public static AjaxResult fail(String msg) {
        AjaxResult ajaxResult = new AjaxResult(500, msg, null);
        return ajaxResult;
    }

    public static AjaxResult exception(String msg) {
        AjaxResult ajaxResult = new AjaxResult(705, msg, null);
        return ajaxResult;
    }

}