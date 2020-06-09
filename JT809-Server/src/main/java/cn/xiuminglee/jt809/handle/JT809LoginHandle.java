package cn.xiuminglee.jt809.handle;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.packet.JT809LoginPacket;
import cn.xiuminglee.jt809.packet.JT809LoginResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 20:06
 * @Version 1.0
 * @Describe:
 */
public class JT809LoginHandle extends SimpleChannelInboundHandler<JT809LoginPacket> {
    private static Logger log = LoggerFactory.getLogger(JT809LoginHandle.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JT809LoginPacket msg) {
        System.out.println("JT809LoginHandle.channelRead0");
        byte loginResponseCode = valid(msg);

        JT809LoginResponsePacket res = new JT809LoginResponsePacket();
        res.setResult(loginResponseCode);
        res.setVerifyCode(0);
        res.setMessageHeader();
        // 登录响应
        ctx.channel().writeAndFlush(res);
    }

    /**
     * 用户名密码校验
     */
    private byte valid(JT809LoginPacket msg) {
        int userId = msg.getUserId();
        String password = msg.getPassword();
        log.info("接收到了登录的请求->用户名：{};密码：{};", userId, password);
        if (Const.UserInfo.USER_ID == userId && Const.UserInfo.PASSWORD.equals(password)) {
            log.info("登录验证成功");
            return Const.LoginResponseCode.SUCCESS;
        } else if (Const.UserInfo.USER_ID != userId) {
            log.info("USER_ID不正确");
            return Const.LoginResponseCode.USERNAME_ERROR;
        } else {
            log.info("PASSWORD_ERROR");
            return Const.LoginResponseCode.PASSWORD_ERROR;
        }
    }

}
