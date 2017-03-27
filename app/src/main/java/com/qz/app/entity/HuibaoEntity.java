package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.util.List;

/**
 * Created by du on 2017/3/9.
 */

public class HuibaoEntity extends BaseEntity {
    public String totalCount;
    public List<Rows> rows;
    public class Rows {
        public String id;
        public String uname;
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

        public String fujian_num;

        public String read_num;

        public String commit_num;

        public String promoter_names;

        public String receive_names;

        public String type_name;



        public String face;

    }

}
