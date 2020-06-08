package cn.xiuminglee.jt809.protocol;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.PacketDecoderUtils;
import cn.xiuminglee.jt809.common.util.PacketEncoderUtils;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.packet.JT809LoginPacket;
import cn.xiuminglee.jt809.protocol.encode.EncoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 10:13
 * @Version 1.0
 * @Describe: 编码适配器
 */
public class JT809EncodeAdapter extends MessageToByteEncoder<JT809BasePacket> {
    private static Logger log = LoggerFactory.getLogger(JT809EncodeAdapter.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, JT809BasePacket packet, ByteBuf out) throws Exception {
        byte[] bytes = convert(packet);
        log.info("发出的报文为：{}", PacketDecoderUtils.bytes2HexStr(bytes));
        out.writeBytes(bytes);
    }

    private static byte[] convert(JT809BasePacket packet) {
        byte[] allBody = packet.getAllBody();
        // 转义
        byte[] dataBytes = PacketEncoderUtils.encoderEscape(allBody);
        byte[] bytes1 = CommonUtils.append(new byte[]{JT809BasePacket.HEAD_FLAG}, dataBytes);
        return CommonUtils.append(bytes1, new byte[]{JT809BasePacket.END_FLAG});
    }

    public static void main(String[] args) {
        JT809BasePacket basePacket = renderJT809LoginPacket();
        byte[] convert = convert(basePacket);
        log.info("发出的报文为：{}", PacketDecoderUtils.bytes2HexStr(convert));
    }

    private static JT809BasePacket renderJT809LoginPacket() {
        JT809LoginPacket a = new JT809LoginPacket();
        a.setMsgSn(Const.getMsgSN());
        a.setMsgId(Const.BusinessDataType.UP_CONNECT_REQ);
        a.setMsgGNSSCenterId(Const.UserInfo.MSG_GNSSCENTERID);
        a.setVersionFlag(new byte[]{1, 0, 0});
        a.setEncryptFlag(Const.EncryptFlag.NO);
        a.setEncryptKey(0);

        a.setUserId(1);
        a.setPassword("模拟密码");
        a.setDownLinkPort((short) 8080);
        a.setMsgLength(JT809LoginPacket.getFixedByteLength() + a.getMsgBodyByteArr().length);
        return a;
    }
}
