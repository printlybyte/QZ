package com.qz.app.entity;

import com.qz.app.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by du on 2017/2/16.
 */

public class DepEntity extends BaseEntity {

    public int totalCount;

    public List<Children> rows ;
    public static class Children implements Serializable {

        public boolean isSlected = false;
        public boolean haschild;
        public boolean ischildshow;
        public int id;
        public String num;
        public String name;
        public int pid;
        public int sort;
        public String optdt;
        public String headman;
        public String headid;
        public int company_id;
        public int level;
        public List<Children> children;
        public int stotal;
        public String parentDepName;

    }

}
