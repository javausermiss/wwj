package com.fh.service.system.paycard;

import com.fh.entity.system.Paycard;

import java.util.List;

public interface PaycardManager {

    public List<Paycard> getPayCard() throws Exception;

    public Paycard getGold(String amount) throws Exception;

}
