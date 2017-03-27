package com.qz.app.constant;

import java.io.Serializable;

/**
 * Created by du on 2017/2/10.
 */

public class Constant {

    public static final String PHOTO_PATH = "PHOTO_PATH";



    public static String QINU_URL = "http://om9r2c8k9.bkt.clouddn.com/";
    public static String QINNUI_SMAILL_TASK = "-task.200";

    public static int NET_PAGE_PER_NUM = 20;//每页返回的数量
    public static final String QINIU_TOKEN = "";//七牛token
    public static final String TASK_PARENT_ID = "TASK_PARENT_ID";
    /**
     * 英文格式时间
     */
    public static final String FORMAT_E = "yyyy-M-d HH:mm:ss";
    public static final String FORMAT_C = "年-月-日 HH:mm";
    public static final String SP_TOKEN = "token_1";
    public static final String BD_SELECTED_USER = "SELECTED_USER";
    //    public static final String SHOW_DPET="SHOW_DPET";
//    public static final String SHOW_DPET_FROM_EMP="SHOW_DPET_FROM_EMP";
    public static final String DEP_NAME = "dep_name";
    public static final String DEP_ID = "dep_id";
    public static final String DEP_PARENT_NAME = "DEP_PARENT_NAME";//父类组名称
    public static final String ENP_DETAIL = "ENP_DETAIL";
    public static final String DEP_GO_CHILD = "DEP_GO_CHILD";
    public static final String DEP_CHILD_DATA = "DEP_CHILD_DATA";
    public static final java.lang.String DEP_FROM = "DEP_FROM";
    public static final int EMP_FROM_HR = 4;//从hR界面进入部门员工界面
    public static final String EMP_FROM = "EMP_FROM";
    public static final int EMP_FROM_SELECT_CHARGEMAN = 8;//任务负责人选择 ;
    public static final int EMP_FROM_SELECT_TASK_MEMBER = 9;//任务负责人选择 ;
    public static final int EMP_FROM_SELECT_SHENPI = 10;//选择抄送人
    /**
     * 搜索类型
     */
    public static final String SEARCH_TYPE = "SEARCH_TYPE";


    public static final String ADD_FROM = "ADD_FROM";//部门添加
    public static final int ADD_FROM_SHOW = 14;
    public static final int ADD_FROM_ADD = 15;
    public static final String EMPLIST_FROM = "EMPLIST_FROM";//员工列表


    public static final int EMPLIST_FROM_NORMAL = 18;
    public static final java.lang.String EMP_DETAIL_FROM = "EMP_DETAIL_FROM";//员工详情
    public static final int EMP_DETAIL_FROM_SHOW = 19;
    public static final int EMP_DETAIL_FROM_ADD = 20;
    public static final java.lang.String DOC_ID = "DOC_ID";

    public static final String RENWU_TYPE = "RENWU_TYPE";
    public static final int RENWU_TYPE_FABU = 21; //发布任务标识
    public static final int RENWU_TYPE_ZHIXING = 22;//执行任务标识
    public static final java.lang.String SORTNUM = "SORTNUM";
    public static final java.lang.String FILTER_NUM = "FILTER_NUM";
    public static final String RENWU_SEARCH_FROM = "RENWU_SEARCH_FROM";//进入任务搜索界面的类型
    public static final int RENWU_SEARCH_FROM_RELEASE = 23;//我发布
    public static final int RENWU_SEARCH_FROM_ZHIXING = 24;//执行的
    public static final java.lang.String TASK_ID = "TASK_ID";
    public static final String TASK_DETAIL = "TASK_DETAIL";

    public static final String EDIT_TYPE = "EDIT_TYPE";//任务编辑类型

    public static final String PHOTO_POS = "PHOTO_POS";
    public static final int TASK_EDIT = 25;
    public static final int TASK_CREAT = 26;
    public static final int TASK_GET_SUBTASK = 27;

    public static final int TASK_DETAIL_GET_PIC = 28;//获取任务图片

    public static final String SHENPI_TYPE = "shenpitype"; //审批界面参数

    public static final int SHENPI_TYPE_FABU = 29;
    public static final int SHENPI_TYPE_DAIWO = 30;
    public static final int SHENPI_TYPE_SHENPI = 31;
    public static final int SHENPI_TYPE_CHAOSONG = 32;


    public static final String SHENPI_ID = "SHENPI_ID";//审批
    public static final java.lang.String SHENPI_SEARCH_FROM = "SHENPI_SEARCH_FROM";//审批搜索
    public static final int SHENPI_SEARCH_FROM_FABU = 33;
    public static final int RENWU_SEARCH_FROM_DAIWO = 34;
    public static final int RENWU_SEARCH_FROM_SHNEPI = 35;
    public static final int RENWU_SEARCH_FROM_CHAOSONG = 36;

    public static final String SHENPI_DEAL_TYPE = "SHENPI_DEAL_TYPE";
    public static final int SHENPI_DEAL_TYPE_PASS = 1;//0发起，1通过，2拒绝，3评论4转交
    public static final int SHENPI_DEAL_TYPE_REFUSE = 2;
    public static final int SHENPI_DEAL_TYPE_COMMENT = 3;
    public static final java.lang.String PUBLICK_SHENPITYPE = "PUBLICK_SHENPITYPE";
    public static final int PUBLICK_SHENPITYPE_QINGJIA = 40;
    public static final java.lang.String PUBLICK_SHENPITYPE_CURRENT_NAME = "PUBLICK_SHENPITYPE_CURRENT_NAME";
    public static final int SHENPI_SHENPIREN = 41;
    public static final int SHENPI_CHAOSONG = 42;
    public static final int SHENPI_JIAOJIE = 43;

    public static final String HUIBAO_TYPE = "HUIBAO_TYPE";
    public static final int MY_HUIBAO = 44;
    public static final int TO_ME_HUIBAO = 45;
    public static final int CHAOSONG_ME_HUIBAO = 46;
    public static final int HUIBAO_RIBAO = 1;
    public static final int HUIBAO_YEJI = 4;
    public static final int HUIBAO_ZHOUBAO = 2;
    public static final int HUIBAO_YUEBAO = 3;
    public static final int EMP_FROM_SELECT_COPYTOHUIBAO = 51;
    public static final int EMP_FROM_SELECT_HUIBAOTO = 52;

    public static final java.lang.String HUIBAO_ID = "report/show";

    public static final String EMP_TRANSDATA = "EMP_TRANSDATA";
    public static final java.lang.String HUIBAO_FROM = "HUIBAO_FROM";
    public static final String HUIBAO_ENTITY = "HUIBAO_FROM";

    public static final String KAOQIN_PAGE = "KAOQIN_PAGE";
    public static final int HUIBAO_GUANLI = 53;
    public static final int HUIBAO_TONGJI = 54;

    public static final String PIANCHA ="PIANCHA";
    public static final java.lang.String PIANCHA_TYPE ="PIANCHA_TYPE";
    public static final int PIANCHA_TYPE_SELECT = 55;
    public static final int PIANCHA_TYPE_ZDY = 56;
    public static final int CGTYPE_SELECTION = 57;
    public static final int PAY_TYPE =58 ;
    public static final java.lang.String CGMXNUM ="CGMXNUM" ;
    public static final java.lang.String CGMXTYPE = "CGMXTYPE";
    public static final int CGMXTYPE_FROM_NEW = 59;
    public static final int CGMXTYPE_FROM_SHOW =60 ;

    /**
     * 借款类型
     */
    public static final int JIEKUANTYPE_SELECTION =61 ;
    public static final int FUKUANTYPE_SELECTION =62 ;

    public static final java.lang.String HETONG_FROM = "HETONG_FROM";
    public static final int HT_NEW = 62;
    public static final int HT_SHOW = 63;
    public static final String HT_CONTENT = "HT_CONTENT";

}
