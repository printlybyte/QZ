package com.qz.app.control;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.app.R;
import com.qz.app.entity.DepEntity;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by du on 2017/2/16.
 */

public class GetDeptController implements BaseContoller {

    public static final int DEPT_DATA = 1;
    private DepEntity depentity;
    private View rootView;
    private Context context;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    depentity= (DepEntity) msg.obj;
                    setDetpViews(depentity);
                    clearWaiting();
                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = Resources.getSystem().getString(R.string.fail_getdata);
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }

        }
    };

    /**
     * @param type 1=转移成员  2.部门选择
     */
    public GetDeptController(Activity context, ViewGroup rootView, int type) {
        this.context = context;
        this.rootView = rootView;
    }

    public void setDetpViews(final DepEntity entity) {
        if (null == entity || null == entity.rows || entity.rows.size() <= 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int a = 0; a < entity.rows.size(); a++) {
            final View parent = inflater.inflate(R.layout.item_dep_parent, null);
            final DepEntity.Children parentdata = entity.rows.get(a);
            parent.setTag(parentdata);
            final ImageView fatherimg = (ImageView) parent.findViewById(R.id.checkimg);
            final TextView name = (TextView) parent.findViewById(R.id.name);
            final ViewGroup childfather = (ViewGroup) parent.findViewById(R.id.chidview);

            parentdata.isSlected = false;
            fatherimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetChidView(entity, parentdata);
                    if (!parentdata.isSlected) {
                        fatherimg.setImageResource(R.drawable.selected_icon);
                    } else {
                        fatherimg.setImageResource(R.drawable.select_normal);
                    }
                    parentdata.isSlected = !parentdata.isSlected;
                }
            });
            name.setText(parentdata.name);
            ((ViewGroup) rootView.findViewById(R.id.treeviewroot)).addView(parent);
            if (null != parentdata.children && parentdata.children.size() > 0) {
                parentdata.haschild = true;
                for (int i = 0; i < parentdata.children.size(); i++) {
                    final View child = inflater.inflate(R.layout.item_dep_parent, null);
                    final DepEntity.Children childdata = parentdata.children.get(i);
                    child.setTag(parentdata);
                    final ImageView childimg = (ImageView) child.findViewById(R.id.checkimg);
                    final TextView childname = (TextView) child.findViewById(R.id.name);
                    childname.setText(childdata.name);
                    childdata.isSlected = false;
                    childname.setTextColor(context.getResources().getColor(R.color.disable));
                    if (childdata.isSlected) {
                        childimg.setImageResource(R.drawable.selected_icon);
                    } else {
                        childimg.setImageResource(R.drawable.select_normal);
                    }

                    childimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resetChidView(entity, childdata);
                            if (!childdata.isSlected) {
                                childimg.setImageResource(R.drawable.selected_icon);
                            } else {
                                childimg.setImageResource(R.drawable.select_normal);
                            }
                            childdata.isSlected = !childdata.isSlected;

                        }
                    });
                    childfather.addView(child);
                    final ImageView childcontrol = (ImageView) parent.findViewById(R.id.showchild);
                    if (parentdata.ischildshow) {
                        childcontrol.setImageResource(R.drawable.dismsschid);
                        childfather.setVisibility(View.VISIBLE);
                    } else {
                        childcontrol.setImageResource(R.drawable.showchid);
                        childfather.setVisibility(View.GONE);
                    }
                    childcontrol.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            parentdata.ischildshow = !parentdata.ischildshow;
                            if (parentdata.ischildshow) {
                                childcontrol.setImageResource(R.drawable.dismsschid);
                                childfather.setVisibility(View.VISIBLE);
                            } else {
                                childcontrol.setImageResource(R.drawable.showchid);
                                childfather.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        }
    }

    private void resetChidView(DepEntity entity, DepEntity.Children childdata) {
        if (entity.rows.size() <= 0) {
            return;
        }
        ViewGroup listroot = ((ViewGroup) rootView.findViewById(R.id.treeviewroot));
        for (int a = 0; a < entity.rows.size(); a++) {
            if (null != entity.rows.get(a)) {
                if (entity.rows.get(a) != childdata) {
                    entity.rows.get(a).isSlected = false;
                }
                ((ImageView) listroot.getChildAt(a).findViewById(R.id.checkimg)).setImageResource(R.drawable.select_normal);
                if (null != entity.rows.get(a).children && entity.rows.get(a).children.size() > 0) {
                    for (int i = 0; i < entity.rows.get(a).children.size(); i++) {
                        if (entity.rows.get(a).children.get(i) != childdata) {
                            entity.rows.get(a).children.get(i).isSlected = false;
                        }
                        ((ImageView) ((ViewGroup) listroot.getChildAt(a).findViewById(R.id.chidview)).getChildAt(i).findViewById(R.id.checkimg)).setImageResource(R.drawable.select_normal);
                    }
                }
            }
        }
//        if (null != childlist && childlist.size() > 0) {
//            for (int i = 0; i < childlist.size(); i++) {
//                childlist.get(i).isSlected = ischecked;
//                ((ImageView) chidFather.getChildAt(i).findViewById(R.id.checkimg)).setImageResource(R.drawable.select_normal);
//            }
//        }
    }

    public DepEntity.Children getSelectedDep() {
        for (int a = 0; a < depentity.rows.size(); a++) {
            if (null != depentity.rows.get(a)) {
                if (depentity.rows.get(a).isSlected) {
                    return depentity.rows.get(a);
                }
                if (null != depentity.rows.get(a).children && depentity.rows.get(a).children.size() > 0) {
                    for (int i = 0; i < depentity.rows.get(a).children.size(); i++) {
                        if (depentity.rows.get(a).children.get(i).isSlected) {
                            return depentity.rows.get(a).children.get(i);
                        }

                    }
                }
            }
        }
        return null;
    }

    @Override
    public void clearWaiting() {

    }

    @Override
    public void dissmissWaitting() {

    }

    public void getDeptData(String pid) {
        Message message = new Message();
        message.arg1 = DEPT_DATA;
        message.setTarget(mHandler);
        API.getDepDatas(message, pid);
    }

}
