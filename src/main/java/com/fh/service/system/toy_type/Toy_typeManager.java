package com.fh.service.system.toy_type;

import com.fh.entity.system.Toy_type;

import java.util.List;

public interface Toy_typeManager {

    public int regToy_Type( Toy_type toy_type)throws Exception;

    public Toy_type getToy_Type(Toy_type toy_type)throws Exception;

    public int deleteToy_Type(Toy_type toy_type)throws Exception;

    public int deleteToy_TypeAll(Integer id)throws Exception;

    public List<Toy_type> getToy_TypeList(Integer id)throws Exception;
}
