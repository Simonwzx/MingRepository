package cn.xiuminglee.jt809.protocol.decoder;

import cn.xiuminglee.jt809.packet.JT809BasePacket;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 21:12
 * @Version 1.0
 * @Describe:
 */
public interface Decoder {
    /**
     * 将转义后的完整消息 按照协议消息格式 解码成实体
     * @param bytes 转义后的完整消息
     * @return
     */
    JT809BasePacket decoder(byte[] bytes) throws Exception;
}
