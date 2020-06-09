package cn.xiuminglee.jt809.handle;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.packet.JT809BasePacket;
import cn.xiuminglee.jt809.packet.JT809HeartbeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 14:42
 * @Version 1.0
 * @Describe:
 */
public class JT809HeartbeatHandle extends SimpleChannelInboundHandler<JT809HeartbeatResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JT809HeartbeatResponse msg) throws Exception {
        msg.setMessageHeader();
        ctx.channel().writeAndFlush(msg);
    }
}
