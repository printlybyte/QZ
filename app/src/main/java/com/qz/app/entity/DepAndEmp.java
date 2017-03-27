package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by du on 2017/2/15.
 */

public class DepAndEmp extends BaseEntity{


    public boolean success;
    public Data data;

    public static class Deptjson {
        public int is_has;
        public int id;


        public String num;


        public String name;


        public int pid;


        public int sort;


        public String optdt;


        public String headman;


        public String headid;


        public int company_id;
    }

    public static class Userjson implements Serializable{

        public String id;


        public String name;


        public String face;


        public String deptid;


        public String ranking;

        public String pingyin;
        public String deptname;


        public boolean fromNet;
    }

    public static class Data {

        public List<Deptjson> deptjson;


        public List<Userjson> userjson;


//        public List<Groupjson> groupjson;
    }

}
