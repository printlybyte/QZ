package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class NetDocument extends BaseEntity {

    public List<Document> rows;

    public static class Document{
        public String id;
        public String title;
        public String dept;
        public String read_num;
        public String dept_name;
        public String created_at;
    }

}
