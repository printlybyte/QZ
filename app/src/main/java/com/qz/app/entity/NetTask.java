package com.qz.app.entity;


import com.qz.app.base.BaseEntity;

import java.util.List;

public class NetTask extends BaseEntity
{
    public String totalCount;
    public List<Rows> rows;
    public static class Rows{
        public String id;
        public String pid;
        public String uid;
        public String title;
        public String enddate;
        public String member;
        public String status;
        public String filepath;
        public String filename;
        public String fileext;
        public String filesize;
        public String adddt;
        public String updatedate;
        public String thumbpath;
        public String company_id;
        public String img;
        public String member_names;
        public String status_name;
        public String username;
        public int task_num;
    }

}
