package com.qz.app.entity;


import com.qz.app.base.BaseEntity;

import java.util.List;


public  class ShenPiEntity extends BaseEntity
{
    public String totalCount;

    public List<Rows> rows;


    public static class Rows
    {
        public String id;

        public String company_id;

        public String flow_id;

        public String uid;

        public String sn_code;

        public String table;

        public String data_id;

        public contentJson content_json;

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

        public String uname;

        public String title;

        public String content;

        public String status_name;

        public String current_uname;

        public String current_status_name;

    }

    public static class contentJson{
     public String uid;
     public String uname;
     public String stime;
     public String etime;
     public String kind;
     public String qjkind;
     public String explain;
     public String status;
     public String totals;
     public String optdt;
     public String isturn;
     public String optname;
     public String company_id;
     public List<String> flow;


    }

}



