package com.haochuang.demobulb.modbus;

import com.haochuang.demobulb.bean.Modbus;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.messaging.ByteeUtil;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.util.queue.ByteQueue;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ModbusUtils {
    /**
     * 批量写数据到保持寄存器
     *
     * @param ip 从站IP  ip
     * @param port modbus端口   502
     * @param slaveId 从站ID  id
     * @param start 起始地址偏移量  索引
     * @param values 待写数据  添加值
     */
    public static ModbusFactory modbusFactory = new ModbusFactory();

    // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
    public static IpParameters params = new IpParameters();
    public static ModbusMaster tcpMaster = null;

    //    写
    public static boolean modbusWTCP(Modbus modbus) {

        params.setHost(modbus.getIp());
        // 设置端口，默认502
        if (502 != modbus.getPort()) {
            params.setPort(modbus.getPort());
        }

        // 参数1：IP和端口信息 参数2：保持连接激活
        tcpMaster = modbusFactory.createTcpMaster(params, false);
        try {
            tcpMaster.init();

            System.out.println("=======初始化成功========");

        } catch (ModbusInitException e) {
            System.out.println("初始化异常");
        }
        try {

            WriteRegistersRequest request = new WriteRegistersRequest(modbus.getSlaveId(), modbus.getStart(), modbus.getValues());
            System.out.println(request);
            WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);

            if (response.isException()) {
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            } else {

                System.out.println("Success");
                return true;
            }
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读保持寄存器上的内容
     *
     * @param ip 从站IP
     * @param port modbus端口
     * @param start 起始地址偏移量
     * @param readLenth 待读寄存器个数
     * @return
     */
    static ModbusRequest modbusRequest = null;

    public static short[] modbusTCP(Modbus modbus) {

        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502

        params.setHost(modbus.getIp());
        //设置端口，默认502
        if (502 != modbus.getPort()) {
            params.setPort(modbus.getPort());
        }
        ModbusMaster tcpMaster = null;
        tcpMaster = modbusFactory.createTcpMaster(params, false);
        try {
            tcpMaster.init();
            System.out.println("========初始化成功=======");
        } catch (ModbusInitException e) {
            return null;
        }
        ModbusRequest modbusRequest = null;
        try {
            //功能码03   读取保持寄存器的值
            modbusRequest = new ReadHoldingRegistersRequest(modbus.getSlaveId(), modbus.getStart(), modbus.getReadLenth());

        } catch (ModbusTransportException e) {
//            e.printSTrace();
        }

        short[] data = null;
        try {

            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(modbus.getSlaveId(), modbus.getStart(), modbus.getReadLenth());
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) tcpMaster.send(request);
            data = response.getShortData();

            return data;

        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }

        com.serotonin.modbus4j.msg.ModbusResponse modbusResponse = null;
        try {
            modbusResponse = tcpMaster.send(modbusRequest);
        } catch (ModbusTransportException e) {
//            e.printStackTrace();
        }
        ByteQueue byteQueue = new ByteQueue(1024);
        modbusResponse.write(byteQueue);

//        System.out.println("功能码:"+modbusRequest.getFunctionCode());
//        System.out.println("从站地址:"+modbusRequest.getSlaveId());
//        System.out.println("收到的响应信息大小:"+byteQueue.size());
//        System.out.println("收到的响应信息值:"+byteQueue);
        return null;
    }

    public static void main(String[] args) {
        short[] shorts = {41, 42, 43, 44};
        Modbus modbus = new Modbus();
        modbus.setIp("127.0.0.1");
        modbus.setPort(502);
        modbus.setSlaveId(1);
        modbus.setStart(3);
//        modbus.setReadLenth(4);
        modbus.setValues(shorts);
        modbusWTCP(modbus);



    }

}
