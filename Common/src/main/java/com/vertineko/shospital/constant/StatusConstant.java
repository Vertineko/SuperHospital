package com.vertineko.shospital.constant;

public final class StatusConstant {

    //已取消
    public static final int CANCELED = -1;

    //正常状态，等待医生接诊的状态
    public static final int NORMAL = 0;

    //已经开始创建病历档案
    public static final int SUBMITTED = 1;

    //病历档案填写完毕
    public static final int COMPLETED = 2;

    //已支付
    public static final int PAYED = 3;
}
