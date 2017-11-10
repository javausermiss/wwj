package com.fh.entity.system;

import java.sql.Date;

/**
 * 网关实体类
 * @author wjy
 * 2017/11/09
 */
public class Gateway {
    private String id;
    private String builname;
    private String communityname;
    private Date create_time;
    private String gateway_ip;
    private String houseid;
    private String node_ip;
    private String node_name;
    private String room_id;
    private String scg_sn;
    private String session_id;
    private String unitname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuilname() {
        return builname;
    }

    public void setBuilname(String builname) {
        this.builname = builname;
    }

    public String getCommunityname() {
        return communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getGateway_ip() {
        return gateway_ip;
    }

    public void setGateway_ip(String gateway_ip) {
        this.gateway_ip = gateway_ip;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getNode_ip() {
        return node_ip;
    }

    public void setNode_ip(String node_ip) {
        this.node_ip = node_ip;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getScg_sn() {
        return scg_sn;
    }

    public void setScg_sn(String scg_sn) {
        this.scg_sn = scg_sn;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
