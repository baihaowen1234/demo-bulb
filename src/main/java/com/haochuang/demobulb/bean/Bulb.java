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
    private Integer id;
    private String  noeopen;
    private String  noeclose;
    private String  open;
    private String  close;
    private Integer power;
    private Integer luminance;
    private String timingopen;
    private String timingclose;


}
