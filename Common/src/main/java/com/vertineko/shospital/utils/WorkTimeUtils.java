package com.vertineko.shospital.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于将工作时间解析成便于储存的模式
 * 也用于在将数据传递给前端时转换成可读性好的模式
 */
public final class WorkTimeUtils {

    public static List<Integer> parseWorkTimeStr(String workTime){

        List<Integer> res = new ArrayList<>(7);
        for (char c : workTime.toCharArray()) {
            if (c == '1'){
                res.add(1);
            }else {
                res.add(0);
            }
        }
        return res;
    }
}
