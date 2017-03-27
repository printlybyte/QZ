package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.util.List;

/**
 * Created by du on 2017/3/4.
 */

public class ShenpiDetail extends BaseEntity {



    public String company_id;

    public String flow_id;

    public String uid;

    public String uname;

    public String sn_code;

    public String table;

    public String data_id;

    public Content_json content_json;

    public String status;

    public String current_uid;

    public String current_status;

    public String current_check;

    public String current_step;

    public String created_at;

    public String updated_at;

    public String finished_at;

    public String fileurl;

    public String imgurl;

    public String title;

    public List<Flowsteps> flowsteps;

    public List<Datalist> datalist;

    public String status_name;

    public String current_uname;

    public String current_status_name;

    public int isedit;

    public int isdel;
    public int isop;
    public List<Flowlog> flowlog;


    public static class Content_json
    {
        public int uid;

        public String uname;

        public String stime;

        public String etime;

        public String kind;

        public String qjkind;

        public String explain;

        public int status;

        public String totals;

        public String optdt;

        public int isturn;

        public String optname;

        public int company_id;

        public List<String> flow;
        public String postion;

    }

    public static class Flowsteps
    {
        public String id;

        public String name;

    }

    public class Datalist
    {
        public String title;

        public String value;


    }


    public static class Flowlog
    {
        public String id;

        public String company_id;

        public String bill_id;

        public String uid;

        public String thetype;

        public String content;

        public String fileurl;

        public String imgurl;

        public String created_at;

        public String thetype_name;

        public String name;

        public String userface;


    }



}
