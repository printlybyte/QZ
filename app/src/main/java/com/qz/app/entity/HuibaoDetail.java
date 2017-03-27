package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by du on 2017/3/10.
 */

public class HuibaoDetail extends BaseEntity {


    public Data data;

    public  List<Commitlist> commitlist ;

    public List<Documentdetail.ReadMan> readlist;


    public static class Data implements Serializable{
        public String id;

        public String uid;

        public String promoter;

        public String receive;

        public String starttime;

        public String endtime;

        public String summary;

        public String plan;

        public String imgurl;

        public String fileurl;

        public String filename;

        public String thetype;

        public String money;

        public String customer;

        public String company_id;

        public String created_at;

        public String promoter_names;

        public String receive_names;

        public String promoter_names_face;

        public String receive_names_face;
        public String type_name;
        public String commit_num;
        public String read_num;
        public String fujian_num;
        public String uname;
        public String face;
    }

//    public static class Readlist {
//        public String id;
//
//        public String report_id;
//
//        public String thetype;
//
//        public String uid;
//
//        public String adddt;
//
//        public String content;
//
//        public String is_del;
//
//        public String company_id;
//
//        public String uname;
//
//        public String name;
//
//        public String face;
//
//    }


}
