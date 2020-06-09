package cn.xiuminglee.jt809.packet;

import cn.xiuminglee.jt809.common.Const;
import cn.xiuminglee.jt809.common.util.CommonUtils;
import cn.xiuminglee.jt809.common.util.CrcUtil;
import cn.xiuminglee.jt809.common.util.DateUtil;

import java.util.Arrays;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:01
 * @Version 1.0
 * @Describe: 基础报文实体类
 */
public abstract class JT809BasePacket {
    /**
     * 不算消息体的固定字节长度
     * Head flag + Message Header + CRC Code + End Flag
     * 1 + 30 + 2 + 1
     */
    public static final int FIXED_BYTE_LENGTH = 34;

    /**
     * 头标识
     */
    public static final byte HEAD_FLAG = 0x5b;
    /**
     * 数据长度(包括头标识、数据头、数据体和尾标识) 4字节
     */
    private int msgLength;
    /**
     * 报文序列号 4字节
     */
    private int msgSn;
    /**
     * 业务数据类型 2字节
     */
    private short msgId;
    /**
     * 下级平台接入码，上级平台给下级平台分配唯一标识码。4字节
     */
    private int msgGNSSCenterId;
    /**
     * 协议版本号标识，上下级平台之间采用的标准协议版
     * 编号；长度为 3 个字节来表示，0x01 0x02 0x0F 标识
     * 的版本号是 v1.2.15，以此类推。
     * Hex编码 ,这个是3个字节，需要注意
     */
    private byte[] versionFlag;
    /**
     * 报文加密标识位 b: 0 表示报文不加密，1 表示报文加密。0x00 0x01,这里默认不加密 1字节
     */
    private byte encryptFlag = 0x00;
    /**
     * 数据加密的密匙，长度为 4 个字节
     */
    private int encryptKey;
    /**
     * 发送消息的系统UTC时间(单位：秒)，长度为8个字节
     */
    private long utcTime;

    /**
     * 应答消息需要按顺序拼接好消息体字节
     * @return
     */
    public abstract byte[] getMsgBodyByteArr();

    /**
     * CRC 校验码 2字节
     */
    private int crcCode;
    /**
     * 尾标识
     */
    public static final byte END_FLAG = 0x5d;

    /**
     * 获取需要校验的部分
     */
    private byte[] getNeedCrcBody() {
        return CommonUtils.append(
                CommonUtils.int2bytes(this.msgLength),
                CommonUtils.int2bytes(this.msgSn),
                CommonUtils.short2Bytes(this.msgId),
                CommonUtils.int2bytes(this.msgGNSSCenterId),
                this.versionFlag,
                new byte[]{this.encryptFlag},
                CommonUtils.int2bytes(this.encryptKey),
                CommonUtils.longToBytes(this.utcTime),
                getMsgBodyByteArr()
        );
    }

    /**
     * 没有头标志和尾标志
     *
     * @return
     */
    public byte[] getAllBody() {
        byte[] needCrcBody = getNeedCrcBody();
        short crc16 = CrcUtil.crc16Ccitt(needCrcBody);
        byte[] crcCode = CommonUtils.short2Bytes(crc16);
        return CommonUtils.append(needCrcBody, crcCode);
    }

    /**
     * 在设置完消息体后执行
     */
    public void setMessageHeader() {
        this.setMsgLength(FIXED_BYTE_LENGTH + getMsgBodyByteArr().length);
        this.setMsgSn(Const.getMsgSN());
        this.setMsgId(getDefineMsgId());
        this.setMsgGNSSCenterId(Const.UserInfo.MSG_GNSSCENTERID);
        this.setVersionFlag(new byte[]{1,0,0});
        this.setEncryptFlag(Const.EncryptFlag.NO);
        this.setEncryptKey(0);
        this.setUtcTime(DateUtil.nowUtcTime());
    }

    /**
     * 获取定义的业务数据类型
     * @return 业务数据类型数值，如0x1202
     */
    public abstract short getDefineMsgId();

    public static int getFixedByteLength() {
        return FIXED_BYTE_LENGTH;
    }

    public static byte getHeadFlag() {
        return HEAD_FLAG;
    }

    public static byte getEndFlag() {
        return END_FLAG;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }

    public short getMsgId() {
        return msgId;
    }

    public void setMsgId(short msgId) {
        this.msgId = msgId;
    }

    public int getMsgGNSSCenterId() {
        return msgGNSSCenterId;
    }

    public void setMsgGNSSCenterId(int msgGNSSCenterId) {
        this.msgGNSSCenterId = msgGNSSCenterId;
    }

    public byte[] getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public byte getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(byte encryptFlag) {
        this.encryptFlag = encryptFlag;
    }

    public int getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(int encryptKey) {
        this.encryptKey = encryptKey;
    }


    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public long getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(long utcTime) {
        this.utcTime = utcTime;
    }

    @Override
    public String toString() {
        return "JT809BasePacket{" +
                "msgLength=" + msgLength +
                ", msgSn=" + msgSn +
                ", msgId=" + msgId +
                ", msgGNSSCenterId=" + msgGNSSCenterId +
                ", versionFlag=" + Arrays.toString(versionFlag) +
                ", encryptFlag=" + encryptFlag +
                ", encryptKey=" + encryptKey +
                ", crcCode=" + crcCode +
                ", utcTime=" + utcTime +
                '}';
    }
}
