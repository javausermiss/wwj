package com.fh.entity.system;

import lombok.Data;

/**
 * 运费实体类
 */
@Data
public class SendCost {
    private String SENDCOST_ID;//ID
    private String PROVINCE_NUM;//编号
    private String PROVINCE;//省份
    private Integer COST;//金币数量
}
