package com.haochuang.demobulb.service;

import com.haochuang.demobulb.bean.Bulb;
import com.haochuang.demobulb.bean.Modbus;
import com.haochuang.demobulb.dao.BulbMapper;
import com.haochuang.demobulb.modbus.ModbusUtils;
import com.haochuang.demobulb.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BulbServiceImpl implements BulbService {

  @Autowired
  BulbMapper bulbMapper;

  @Override
  public void sava(Bulb bulb) {
    bulbMapper.sava(bulb);
  }

  @Override
  public void savaup(short[] shorts) {
//    session.setAttribute("shorts", shorts);
    // 读出来的板子值存入数据库  展示给客户
    Bulb bulb = new Bulb();
    bulb.setId(shorts[0]);
    //、开/关、
    bulb.setIsOnline(shorts[1]);
    //设备在线状态
    bulb.setIsOppen(shorts[2]);
    //瓦数
    bulb.setWattage(shorts[3]);
    bulb.setDeviceName("第一个灯泡");
    Date date = new Date();
    bulb.setTimingOpen(DateUtils.dateToString(date));
    bulb.setParticulars("智能灯泡");
    bulbMapper.savaup(bulb);

  }

  @Override
  public short[] read() {
//先查数据库
    Bulb bulb = bulbMapper.find();
    Modbus modbus = new Modbus();
    modbus.setIp("127.0.0.1");
    modbus.setPort(502);
    modbus.setSlaveId(1);
    modbus.setStart(0);
    modbus.setReadLenth(5);
    short[] shorts = new short[]{bulb.getId(), bulb.getIsOppen(), bulb.getIsOnline(), bulb.getWattage()};
    modbus.setValues(shorts);
//    写入板子
    ModbusUtils.modbusWTCP(modbus);
//    查看板子
    final short[] shorts1 = ModbusUtils.modbusTCP(modbus);
    return  shorts1;
  }
  
  @Override
  public Bulb getStatus(int id) {
    return bulbMapper.getStatus(id);
  }
}
