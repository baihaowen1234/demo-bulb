package com.haochuang.demobulb.service;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.messaging.ByteeUtil;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.util.queue.ByteQueue;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ModBusImpl {
    /**
     * 批量写数据到保持寄存器
     * @param ip 从站IP  ip
     * @param port modbus端口   502
     * @param slaveId 从站ID  id
     * @param start 起始地址偏移量  索引
     * @param values 待写数据  添加值
     */
   public static ModbusFactory modbusFactory = new ModbusFactory();

    // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
    public  static IpParameters params = new IpParameters();
    public static ModbusMaster tcpMaster = null;

//    写
    public static void modbusWTCP( String ip,int port, int slaveId,int start, short[] values) {

        params.setHost(ip);
        // 设置端口，默认502
        if (502 != port) {
            params.setPort(port);
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

            WriteRegistersRequest request  = new WriteRegistersRequest( slaveId, start, values );
            System.out.println(request);
            WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);



            if (response.isException()){
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            }else{
                System.out.println("Success");
            }
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读保持寄存器上的内容
     * @param ip 从站IP
     * @param port modbus端口
     * @param start 起始地址偏移量
     * @param readLenth 待读寄存器个数
     * @return
     */
    static ModbusRequest modbusRequest=null;
    public static ByteQueue modbusTCP(String ip, int port, int slaveId,int start,int readLenth) {

        params.setHost(ip);
        //设置端口，默认502
        if(502!=port){
            params.setPort(port);
        }
        ModbusMaster tcpMaster = null;
        tcpMaster = modbusFactory.createTcpMaster(params, false);

        try {


            //初始化
            tcpMaster.init();
            System.out.println("========初始化成功=======");

        } catch (ModbusInitException e) {
            return null;
        }

        try {
            //功能码03   读取保持寄存器的值
            modbusRequest = new ReadHoldingRegistersRequest(slaveId,start, readLenth);

        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
        ModbusResponse modbusResponse=null;
        try {

            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, readLenth);
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) tcpMaster.send(request);
            System.out.println(Arrays.toString(response.getShortData()));
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }

        ByteQueue byteQueue= new ByteQueue(1024);
        modbusResponse.write(byteQueue);

        System.out.println("功能码:"+modbusRequest.getFunctionCode());
        System.out.println("从站地址:"+modbusRequest.getSlaveId());
        System.out.println("收到的响应信息大小:"+byteQueue.size());
        System.out.println("收到的响应信息值:"+byteQueue);
        ByteeUtil by=new ByteeUtil();
        Short[] ss = by.bytte(byteQueue);
        for(Short  sq:ss){
            System.out.println(sq);
        }

        return byteQueue;
    }

    public static void main(String[] args) {
        final short[] shorts = new short[1];



        modbusWTCP("127.0.0.1",502,1,3,1);
    }

}
