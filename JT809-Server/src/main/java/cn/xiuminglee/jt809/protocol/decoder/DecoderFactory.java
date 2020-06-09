package cn.xiuminglee.jt809.protocol.decoder;


import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.common.MsgId;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 21:13
 * @Version 1.0
 * @Describe: 解码工厂类
 */
public class DecoderFactory {
    private static final Map<Short, Decoder> DECODER_FACTORY = new HashMap<>();

    static {
        DECODER_FACTORY.put(MsgId.UP_CONNECT_REQ, new LoginDecoder());
        DECODER_FACTORY.put(MsgId.UP_LINKTEST_REQ, new HeartbeatDecoder());
        //主链路动态信息交换
        DECODER_FACTORY.put(MsgId.UP_EXG_MSG_REAL_LOCATION, new JT809Packet0x1202Decoder());
    }

    /**
     * @param businessDataType 业务数据类型标志
     * @return 具体的解码器
     */
    public static Decoder getDecoder(short businessDataType) {
        return DECODER_FACTORY.get(businessDataType);
    }
}
