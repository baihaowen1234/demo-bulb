package com.haochuang.demobulb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Modbus {

    private String ip; //ip
    private int port;   // 端口
    private int slaveId;
    private int start;
    private short[] values;
    private int readLenth;



}
