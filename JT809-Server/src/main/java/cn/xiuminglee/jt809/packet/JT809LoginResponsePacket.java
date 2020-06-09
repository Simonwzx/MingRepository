package cn.xiuminglee.jt809.packet;

import cn.xiuminglee.jt809.common.MsgId;
import cn.xiuminglee.jt809.common.util.CommonUtils;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:37
 * @Version 1.0
 * @Describe: 登录应答包
 */
public class JT809LoginResponsePacket extends JT809BasePacket {

    public JT809LoginResponsePacket() {
    }

    /**
     * 标志 1位
     */
    private byte result;
    /**
     * 校验码 4字节
     */
    private int verifyCode;

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public byte[] getMsgBodyByteArr() {
        byte[] verifyCodeBytes = CommonUtils.int2bytes(this.verifyCode);
        return CommonUtils.append(new byte[]{this.result}, verifyCodeBytes);
    }

    @Override
    public short getDefineMsgId() {
        return MsgId.UP_CONNECT_RSP;
    }

    @Override
    public String toString() {
        return "JT809LoginResponsePacket{" +
                "result=" + result +
                ", verifyCode=" + verifyCode +
                super.toString() +
                '}';
    }
}
