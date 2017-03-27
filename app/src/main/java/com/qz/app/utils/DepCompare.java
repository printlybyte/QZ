package com.qz.app.utils;

import com.qz.app.entity.SearchDemp;

import java.util.Comparator;

/**
 * Created by du on 2017/2/20.
 */

public class DepCompare implements Comparator {
    public int compare(Object obj1,Object obj2){
        SearchDemp.TRows point1=(SearchDemp.TRows)obj1;
        SearchDemp.TRows point2=(SearchDemp.TRows)obj2;
        if(point1.deptname!=point2.deptname)
            return 1;
        else
            return 0;
    }
}

