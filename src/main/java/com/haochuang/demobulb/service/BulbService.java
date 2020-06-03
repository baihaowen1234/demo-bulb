package com.haochuang.demobulb.service;

import com.haochuang.demobulb.bean.Bulb;

public interface BulbService {
//    读取的数据存入数据库
    void sava(Bulb bulb);
    void savaup(short[] shorts);
    short[] read();
}
