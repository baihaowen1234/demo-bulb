package com.haochuang.demobulb.controller;

import com.haochuang.demobulb.bean.Bulb;
import com.haochuang.demobulb.bean.Modbus;
import com.haochuang.demobulb.modbus.ModbusUtils;
import com.haochuang.demobulb.service.BulbService;
import com.haochuang.demobulb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@RestController
public class BulbController {

  @Autowired
  private BulbService bulbService;

  //监控操作  通过读和写  写入数据库
  @GetMapping("/")
  public String cehis(HttpSession session) {

    final short[] read = bulbService.read();
    bulbService.savaup(read);

    session.setAttribute("shorts",read);
    return "index";
  }

  //写入操作 客户端写和读都存入数据库
  @PostMapping("/charu")
  public String charu(Modbus modbus) {
    modbus.setIp("127.0.0.1");
    modbus.setPort(502);
    modbus.setSlaveId(1);
    modbus.setStart(1);
    final short[] values = modbus.getValues();
    modbus.setValues(values);
    ModbusUtils.modbusWTCP(modbus);

     bulbService.savaup(values);




    return "index";
  }
  
  @GetMapping("/getstatus")
  public Bulb getStatus(int id){
    return bulbService.getStatus(id);
  }

}



