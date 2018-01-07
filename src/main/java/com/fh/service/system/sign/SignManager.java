package com.fh.service.system.sign;

import com.fh.entity.system.Sign;
import com.fh.util.PageData;

public interface SignManager {
    /**
     * 新增签到记录
     * @param sign
     * @return
     * @throws Exception
     */
    public int insertSign(Sign sign)throws Exception;

    /**
     * 查询最近的签到记录
     * @param userId
     * @return
     * @throws Exception
     */
    public Sign getSignLastByUserId(String userId)throws Exception;

    public int updateSign(Sign sign) throws Exception;



}
