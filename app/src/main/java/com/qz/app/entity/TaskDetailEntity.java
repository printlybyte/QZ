package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by du on 2017/2/25.
 */

public class TaskDetailEntity extends BaseEntity {

    public Data data;
    public List<Data> subtask;
    public List<Commitlist> commitlist;


    public static class Members{
        public String userface;
        public String username;
        public String memberid;
    }

    public static class Data implements Serializable
    {
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
        public String membernames;
        public String creater;
        public String status_name;
        public int isedit;
        public int isdel;
        public int isflow;
        public List<String> logarr;
        public List<String> flowinfor;
        public List<String> readarr;
        public String  liable_person;
        public String  liable_id;
        public ArrayList<Members> members;

//        public String  memberid;
    }


    

}
