package com.haochuang.demobulb.controller;

import com.haochuang.demobulb.bean.Modbus;
import com.haochuang.demobulb.modbus.ModbusUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BulbController {

    @GetMapping("/ceshi")
    public String cehis() {
        Modbus modbus = new Modbus();

        //读modbusTCP
        final short[] shorts = ModbusUtils.modbusTCP(modbus);
        //写modbusWTCP
        final boolean b = ModbusUtils.modbusWTCP(modbus);

        return "ceshi";
    }

}



