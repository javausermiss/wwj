package com.fh.vo.system;

import lombok.Data;

import java.util.List;

/**
 *玩具实体类
 */
@Data
public class DollToyVo {
    private Integer toy_id;
    private String toy_name;
    private Integer toy_num;
    private String buy_price;
    private Integer toy_conversion;
    private Integer doll_gold;
    private String remark;
    private String create_time;
    private String update_time;
    private List<DollVo> dollVos;
    private String toy_type;

}
