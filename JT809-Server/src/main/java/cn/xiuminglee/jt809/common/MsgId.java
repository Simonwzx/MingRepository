package cn.xiuminglee.jt809.common;

/**
 * @author wangzongxiang
 * @date 2020/6/9 12:01 下午
 */
public class MsgId {

    /** 主链路登录请求消息*/
    public static final short UP_CONNECT_REQ = 0x1001;
    /** 主链路登录应答消息*/
    public static final short UP_CONNECT_RSP = 0x1002;
    /** 主链路连接保持请求消息*/
    public static final short UP_LINKTEST_REQ = 0x1005;
    /** 主链路连接保持应答消息*/
    public static final short UP_LINKTEST_RSP = 0x1006;

    /** 主链路动态信息交换消息*/
    public static final short UP_EXG_MSG = 0x1200;
    /** 实时上传车辆定位信息*/
    public static final short UP_EXG_MSG_REAL_LOCATION = 0x1202;
}
