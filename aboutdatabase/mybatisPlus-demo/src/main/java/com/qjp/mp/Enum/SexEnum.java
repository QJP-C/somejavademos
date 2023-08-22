package com.qjp.mp.Enum;

import com.baomidou.mybatisplus.annotation.EnumValue;



public enum SexEnum {
 
 MAN(1, "男"),
 WOMAN(2, "女");
 
 @EnumValue
 private Integer key;


 SexEnum(int i, String sex) {
 }
}