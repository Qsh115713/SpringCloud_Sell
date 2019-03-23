package com.qsh.order.utils;

import com.qsh.order.enums.ResultEnum;
import com.qsh.order.enums.ResultStatusEnum;
import com.qsh.order.viewobject.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(ResultStatusEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultStatusEnum.SUCCESS.getMsg());
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
