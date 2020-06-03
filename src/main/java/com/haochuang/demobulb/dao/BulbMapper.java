package com.haochuang.demobulb.dao;

import com.haochuang.demobulb.bean.Bulb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BulbMapper {
  //    读取的数据存入数据库
  void sava(Bulb bulb);
  Bulb find();


  void savaup(Bulb bulb);



}
