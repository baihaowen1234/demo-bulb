package com.haochuang.demobulb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*单开、
单关
、开/关、
调功率、
调节最大亮度上限值（0%~100%）、
定时开/关。
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bulb {
    //设备ID
    private Short id;
    //设备名称
    private String deviceName;
    //、开/关、
    private Short  isOppen;
    //设备在线状态
    private Short isOnline;
    //瓦数
    private Short  wattage ;
    //设备开启时间
    private String timingOpen;
    //设备详情
    private String Particulars;



}
