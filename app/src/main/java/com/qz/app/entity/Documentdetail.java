package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by du on 2017/2/22.
 */

public class Documentdetail extends BaseEntity {

    public DocDetail data;

    public static class DocDetail{
        public String id;
        public String title;
        public String content;
        public String dept;
        public String dept_name;
        public String created_at;
        public List<ReadMan> read_log;
    }
    public static class ReadMan implements Serializable{
        public String log_id;
        public String name;
        public String uid;
        public String idface;
        public String created_at;
        public String face;

    }

}
