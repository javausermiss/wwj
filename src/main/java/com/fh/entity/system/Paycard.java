package com.fh.entity.system;

import lombok.Data;

/**
 * 充值卡实体类
 */
@Data
public class Paycard {
    private Integer ID;//编号
    private String AMOUNT;
    private String GOLD;
    private String DISCOUNT;
    private String IMAGEURL;
    private String RECHARE;
    private String AWARD;

}
