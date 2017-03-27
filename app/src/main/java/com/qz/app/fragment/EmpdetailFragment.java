package com.qz.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.qz.app.R;
import com.qz.app.base.BaseEntity;
import com.qz.app.base.BaseFragment;
import com.qz.app.constant.Constant;
import com.qz.app.entity.DepEntity;
import com.qz.app.entity.UserDetail;
import com.qz.app.entity.UserDetailNet;
import com.qz.app.http.API;
import com.qz.app.utils.CommonUtils;
import com.qz.app.utils.FragmentManager;
import com.qz.app.utils.GlideUtils;
import com.qz.app.view.CommAlertDialog;
import com.qz.app.view.DateScrollerDialog;
import com.qz.app.view.DialogButtonsListener;
import com.qz.app.view.GenderSelectionDialog;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Created by Administrator on 2015/6/26.
 */
public class EmpdetailFragment extends BaseFragment {
    private ImageView leftimg;
    private TextView title;
    private TextView editbt;
    private EditText name_data;
    private EditText worknum_data;

    private RelativeLayout changegenderbt;
    private TextView gender_data;
    private ImageView changegender;
    private EditText phone_data;

    private RelativeLayout changedepbt;
    private TextView dept_data;
    private ImageView changedep;
    private EditText jobs_data;
    private EditText email_data;

    private RelativeLayout changegrandtimebt;
    private TextView grandtime_data;
    private RelativeLayout changeincombt;
    private TextView incomme_data;
    private TextView national_data;
    private TextView native_data;
    private TextView idcard_data;
    private TextView birthday_data;
    private TextView marry_data;
    private TextView adress_data;
    private TextView emergencyphone_data;
    private TextView emergencypepole_data, cancel;
    private String namtv, worknumtv, gendertv, phonetv, depttv, jobstv, emailtv, grandtimetv, incommetv, nationaltv, nativetv, idcardtv, birthdaytv, marrytv, adresstv, emergencyphonetv, emergencypepoletv;
    private UserDetail entity;
    private ImageView headimg;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {
                case API.REQUEST_BEGIN:

                    break;
                case API.REQUEST_SUCCESS:
                    clearWaiting();

                    if (msg.arg1 == 2) {
                        entity = ((UserDetailNet) msg.obj).data;
                        initViewWithEntity(entity);
                    } else if (msg.arg1 == 0) {

                        showDialog();
                    } else if (msg.arg1 == 1) {
                        CommonUtils.showToast("编辑成功!");
                        setEditAble(false);
                        editbt.setText("编辑");
                        cancel.setVisibility(View.INVISIBLE);
                        leftimg.setVisibility(View.VISIBLE);
//                        getArguments().putInt(Constant.EMP_DETAIL_FROM,Constant.EMP_DETAIL_FROM_SHOW);
//                        getArguments().putString(Constant.ENP_DETAIL,((BaseEntity)msg.obj).id);
                    }

                    break;
                case API.REQUEST_FAIL:
                    String message = "";
                    if (null != msg.obj) {
                        message = (String) msg.obj;
                    } else {
                        message = getString(R.string.fail_getdata);
                    }
                    CommonUtils.showToast(message);
                    clearWaiting();
                    break;

            }
        }
    };
    private RelativeLayout marry_click;
    private ImageView headimg_black, name_black, worknum_black, phone_black, jobs_black, email_black, national_black, native_back, idcard_back, emergencypepole_black, emergencyphone_black, adress_black;
    private ImageView marry_icon_click, incomme_back, changegrandtime;
    public static DepEntity.Children selectedDept;
    private RelativeLayout birthday_layout_click;
    private View phone_click;

    @Override
    public void initViews(ViewGroup rootView) {
        selectedDept = null;
        headimg_black = (ImageView) rootView.findViewById(R.id.headimg_black);
        name_black = (ImageView) rootView.findViewById(R.id.name_black);
        worknum_black = (ImageView) rootView.findViewById(R.id.worknum_black);
        phone_black = (ImageView) rootView.findViewById(R.id.phone_black);
        jobs_black = (ImageView) rootView.findViewById(R.id.jobs_black);
        email_black = (ImageView) rootView.findViewById(R.id.email_black);
        national_black = (ImageView) rootView.findViewById(R.id.national_black);
        native_back = (ImageView) rootView.findViewById(R.id.native_back);
        idcard_back = (ImageView) rootView.findViewById(R.id.idcard_back);
        emergencypepole_black = (ImageView) rootView.findViewById(R.id.emergencypepole_black);
        emergencyphone_black = (ImageView) rootView.findViewById(R.id.emergencyphone_black);
        adress_black = (ImageView) rootView.findViewById(R.id.adress_black);
        marry_icon_click = (ImageView) rootView.findViewById(R.id.marry_icon_click);
        incomme_back = (ImageView) rootView.findViewById(R.id.incomme_back);
        changegrandtime = (ImageView) rootView.findViewById(R.id.changegrandtime);
        changedep = (ImageView) rootView.findViewById(R.id.changedep);

        leftimg = (ImageView) rootView.findViewById(R.id.leftimg);
        title = (TextView) rootView.findViewById(R.id.title);
        editbt = (TextView) rootView.findViewById(R.id.edit_click);
        name_data = (EditText) rootView.findViewById(R.id.name_data);
        worknum_data = (EditText) rootView.findViewById(R.id.worknum_data);
        changegenderbt = (RelativeLayout) rootView.findViewById(R.id.changegender_click);
        gender_data = (TextView) rootView.findViewById(R.id.gender_data);
        changegender = (ImageView) rootView.findViewById(R.id.changegender);
        phone_data = (EditText) rootView.findViewById(R.id.phone_data);
        changedepbt = (RelativeLayout) rootView.findViewById(R.id.changedep_click);
        dept_data = (TextView) rootView.findViewById(R.id.dept_data);
        jobs_data = (EditText) rootView.findViewById(R.id.jobs_data);
        email_data = (EditText) rootView.findViewById(R.id.email_data);
        changegrandtimebt = (RelativeLayout) rootView.findViewById(R.id.changegrandtime_click);
        marry_click = (RelativeLayout) rootView.findViewById(R.id.marry_click);
        grandtime_data = (TextView) rootView.findViewById(R.id.grandtime_data);
        changegrandtime = (ImageView) rootView.findViewById(R.id.changegrandtime);
        changeincombt = (RelativeLayout) rootView.findViewById(R.id.changeincom_click);
        birthday_layout_click = (RelativeLayout) rootView.findViewById(R.id.birthday_layout_click);
        incomme_data = (TextView) rootView.findViewById(R.id.incomme_data);
        national_data = (TextView) rootView.findViewById(R.id.national_data);
        native_data = (TextView) rootView.findViewById(R.id.native_data);
        idcard_data = (TextView) rootView.findViewById(R.id.idcard_data);
        birthday_data = (TextView) rootView.findViewById(R.id.birthday_data);
        marry_data = (TextView) rootView.findViewById(R.id.marry_data);
        adress_data = (TextView) rootView.findViewById(R.id.adress_data);
        emergencyphone_data = (TextView) rootView.findViewById(R.id.emergencyphone_data);
        emergencypepole_data = (TextView) rootView.findViewById(R.id.emergencypepole_data);
        cancel = (TextView) rootView.findViewById(R.id.cancel);
        headimg = (ImageView) rootView.findViewById(R.id.head_data);
        phone_click = rootView.findViewById(R.id.phone_click);

        cancel.setVisibility(View.INVISIBLE);
        String title = "查看成员";
        if (getArguments().getInt(Constant.EMP_DETAIL_FROM) == Constant.EMP_DETAIL_FROM_SHOW) {
            cancel.setVisibility(View.INVISIBLE);
            leftimg.setVisibility(View.VISIBLE);
            editbt.setText("编辑");
            setEditAble(false);
            getDetail();
        } else if (getArguments().getInt(Constant.EMP_DETAIL_FROM) == Constant.EMP_DETAIL_FROM_ADD) {
            title = "添加成员";
            cancel.setVisibility(View.VISIBLE);
            leftimg.setVisibility(View.INVISIBLE);
            editbt.setText("完成");
            setEditAble(true);
        }
        initTitledView(title);
        View.OnClickListener clicListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edit_click:
                        String text = editbt.getText().toString();
                        if (text.equals("编辑")) {
                            editbt.setText("完成");
                            cancel.setText("取消");
                            cancel.setVisibility(View.VISIBLE);
                            leftimg.setVisibility(View.INVISIBLE);
                            EmpdetailFragment.this.title.setText("编辑");
                            setEditAble(true);
                        } else if (text.equals("完成")) {
                            int type = -1;
                            if (getArguments().getInt(Constant.EMP_DETAIL_FROM) == Constant.EMP_DETAIL_FROM_ADD) {
                                type = 0;
                            } else if (getArguments().getInt(Constant.EMP_DETAIL_FROM) == Constant.EMP_DETAIL_FROM_SHOW) {
                                type = 1;
                            }
                            add_updateuser(type);

                        }
                        break;
                    case R.id.changegender_click:
                        String gender = gender_data.getText().toString();
                        GenderSelectionDialog dialog = new GenderSelectionDialog(getActivity());
                        if ("男".equals(gender)) {
                            dialog.showPopupDialog(0, "0");
                        } else if (("女".equals(gender))) {
                            dialog.showPopupDialog(0, "1");
                        } else {
                            dialog.showPopupDialog(0, null);
                        }
                        dialog.setOnGenderSelected(new GenderSelectionDialog.OnGenderSelect() {
                            @Override
                            public void onselected(String gender) {
                                if ("0".equals(gender)) {
                                    gender_data.setText("男");
                                } else if ("1".equals(gender)) {
                                    gender_data.setText("女");

                                }
                            }
                        });
                        break;
                    case R.id.changegrandtime_click:
                        DateScrollerDialog dateDialog = DateScrollerDialog.newInstance();
                        dateDialog.setListener(new DateScrollerDialog.TimerDialogWheel() {
                            @Override
                            public void onOkclick(String year, String month, String day,String hour) {
                                if (!TextUtils.isEmpty(day) && day.length() == 1) {
                                    day = "0" + day;
                                }
                                grandtime_data.setText(year + "-" + month + "-" + day);
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        });
//
                        String grandtime_str = grandtime_data.getText().toString();
                        if (!TextUtils.isEmpty(grandtime_str)) {
                            String[] strs = grandtime_str.split("-");
                            dateDialog.setDate(strs);
                        } else {
                            dateDialog.setDate(null);
                        }
                        dateDialog.show(getFragmentManager(), "DateScrollerDialog");
                        break;
                    case R.id.changeincom_click:
                        DateScrollerDialog dateDialog2 = DateScrollerDialog.newInstance();
                        dateDialog2.setListener(new DateScrollerDialog.TimerDialogWheel() {
                            @Override
                            public void onOkclick(String year, String month, String day,String hour) {
                                if (!TextUtils.isEmpty(day) && day.length() == 1) {
                                    day = "0" + day;
                                }
                                incomme_data.setText(year + "-" + month + "-" + day);
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        });
                        String incomme_str = incomme_data.getText().toString();
                        if (!TextUtils.isEmpty(incomme_str)) {
                            String[] strs = incomme_str.split("-");
                            dateDialog2.setDate(strs);
                        } else {
                            dateDialog2.setDate(null);
                        }

                        dateDialog2.show(getFragmentManager(), "DateScrollerDialog");
                        break;
                    case R.id.cancel:

                        if (getArguments().getInt(Constant.EMP_DETAIL_FROM) == Constant.EMP_DETAIL_FROM_ADD) {
                            FragmentManager.popFragment(getActivity());
                            break;
                        }

                        cancel.setVisibility(View.INVISIBLE);
                        leftimg.setVisibility(View.VISIBLE);
                        setEditAble(false);
                        editbt.setText("编辑");
                        EmpdetailFragment.this.title.setText("查看成员");
                        break;
                    case R.id.birthday_layout_click:
                        DateScrollerDialog birthdayDialog = DateScrollerDialog.newInstance();
                        birthdayDialog.setListener(new DateScrollerDialog.TimerDialogWheel() {
                            @Override
                            public void onOkclick(String year, String month, String day,String hour) {
                                if (!TextUtils.isEmpty(day) && day.length() == 1) {
                                    day = "0" + day;
                                }
                                birthday_data.setText(year + "-" + month + "-" + day);
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        });
                        String birthday_str = birthday_data.getText().toString();
                        if (!TextUtils.isEmpty(birthday_str)) {
                            String[] strs = birthday_str.split("-");
                            birthdayDialog.setDate(strs);
                        } else {
                            birthdayDialog.setDate(null);
                        }
                        birthdayDialog.show(getFragmentManager(), "DateScrollerDialog");
                        break;
                    case R.id.marry_click:
                        GenderSelectionDialog marry = new GenderSelectionDialog(getActivity());
                        String marrydata = marry_data.getText().toString();


                        marry.setOnGenderSelected(new GenderSelectionDialog.OnGenderSelect() {
                            @Override
                            public void onselected(String gender) {
                                if ("0".equals(gender)) {
                                    marry_data.setText("未婚");
                                } else if ("1".equals(gender)) {
                                    marry_data.setText("已婚");
                                }
                            }
                        });

                        if (marrydata.equals("已婚")) {
                            marry.showPopupDialog(1, "1");
                        } else if (marrydata.equals("未婚")) {
                            marry.showPopupDialog(1, "0");
                        } else {
                            marry.showPopupDialog(1, null);
                        }
                        break;
                    case R.id.head_data:

                        break;
                    case R.id.phone_click:
                    case R.id.phone_data:
                        call(phone_data.getText().toString());
                        break;
                }

            }
        };

        phone_click.setOnClickListener(clicListener);
//        editbt.setOnClickListener(clicListener);
//        changegenderbt.setOnClickListener(clicListener);
//        changedepbt.setOnClickListener(clicListener);
//        changegrandtimebt.setOnClickListener(clicListener);
//        changeincombt.setOnClickListener(clicListener);
//        cancel.setOnClickListener(clicListener);
//        marry_click.setOnClickListener(clicListener);
//        birthday_layout_click.setOnClickListener(clicListener);
//        headimg.setOnClickListener(clicListener);

//        phone_data.setOnClickListener(clicListener);
    }

    public void setEditAble(boolean b) {

        name_data.setEnabled(b);
        worknum_data.setEnabled(b);
        gender_data.setEnabled(b);
        changegenderbt.setEnabled(b);
        phone_data.setEnabled(b);
        changedepbt.setEnabled(b);
        jobs_data.setEnabled(b);
        email_data.setEnabled(b);
        changeincombt.setEnabled(b);
        national_data.setEnabled(b);
        native_data.setEnabled(b);
        idcard_data.setEnabled(b);
        birthday_data.setEnabled(b);
        marry_click.setEnabled(b);
        adress_data.setEnabled(b);
        emergencyphone_data.setEnabled(b);
        emergencypepole_data.setEnabled(b);
        changegrandtimebt.setEnabled(b);
        if (b) {

            dept_data.setTextColor(getResources().getColor(R.color.sign_tag));
            name_data.setTextColor(getResources().getColor(R.color.sign_tag));
            phone_data.setTextColor(getResources().getColor(R.color.sign_tag));
            worknum_data.setTextColor(getResources().getColor(R.color.sign_tag));
            gender_data.setTextColor(getResources().getColor(R.color.sign_tag));
            phone_data.setTextColor(getResources().getColor(R.color.sign_tag));
            jobs_data.setTextColor(getResources().getColor(R.color.sign_tag));
            email_data.setTextColor(getResources().getColor(R.color.sign_tag));


            headimg_black.setVisibility(View.INVISIBLE);
            name_black.setVisibility(View.INVISIBLE);
            worknum_black.setVisibility(View.INVISIBLE);
            phone_black.setVisibility(View.INVISIBLE);
            jobs_black.setVisibility(View.INVISIBLE);
            email_black.setVisibility(View.INVISIBLE);
            national_black.setVisibility(View.INVISIBLE);
            native_back.setVisibility(View.INVISIBLE);
            idcard_back.setVisibility(View.INVISIBLE);
            emergencypepole_black.setVisibility(View.INVISIBLE);
            emergencyphone_black.setVisibility(View.INVISIBLE);
            adress_black.setVisibility(View.INVISIBLE);
            changegender.setVisibility(View.VISIBLE);
            marry_icon_click.setVisibility(View.VISIBLE);
            changedep.setVisibility(View.VISIBLE);
            incomme_back.setVisibility(View.VISIBLE);
            changegrandtime.setVisibility(View.VISIBLE);
        } else {

            dept_data.setTextColor(getResources().getColor(R.color.disable));
            name_data.setTextColor(getResources().getColor(R.color.disable));
            phone_data.setTextColor(getResources().getColor(R.color.disable));
            worknum_data.setTextColor(getResources().getColor(R.color.disable));
            gender_data.setTextColor(getResources().getColor(R.color.disable));
            phone_data.setTextColor(getResources().getColor(R.color.disable));
            jobs_data.setTextColor(getResources().getColor(R.color.disable));
            email_data.setTextColor(getResources().getColor(R.color.disable));

            phone_data.setTextColor(getResources().getColor(R.color.blue));
            headimg_black.setVisibility(View.GONE);
            name_black.setVisibility(View.GONE);
            worknum_black.setVisibility(View.GONE);
            phone_black.setVisibility(View.GONE);
            jobs_black.setVisibility(View.GONE);
            email_black.setVisibility(View.GONE);
            national_black.setVisibility(View.GONE);
            native_back.setVisibility(View.GONE);
            idcard_back.setVisibility(View.GONE);
            emergencypepole_black.setVisibility(View.GONE);
            emergencyphone_black.setVisibility(View.GONE);
            adress_black.setVisibility(View.GONE);
            changedep.setVisibility(View.GONE);
            changegender.setVisibility(View.GONE);
            marry_icon_click.setVisibility(View.GONE);
            incomme_back.setVisibility(View.GONE);
            changegrandtime.setVisibility(View.GONE);
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_empdetail;
    }

    @Override
    public void setViews(View rootView) {
        if (null != selectedDept) {
            dept_data.setText(selectedDept.name);

        }

    }


    public void resetViews() {
        selectedDept = null;
        name_data.setText("");
        worknum_data.setText("");
        gender_data.setText("");
        phone_data.setText("");
        dept_data.setText("");
        jobs_data.setText("");
        email_data.setText("");
        grandtime_data.setText("");
        incomme_data.setText("");
        national_data.setText("");
        native_data.setText("");
        idcard_data.setText("");
        birthday_data.setText("");
        marry_data.setText("");
        adress_data.setText("");
        emergencyphone_data.setText("");
        emergencypepole_data.setText("");
        headimg.setImageResource(R.drawable.default_head);

    }

    public void initViewWithEntity(UserDetail entity) {

        name_data.setText(entity.name);
        worknum_data.setText(entity.jobnumber);
        gender_data.setText(entity.sex);
        phone_data.setText(entity.mobile);
        dept_data.setText(entity.deptname);
        jobs_data.setText(entity.ranking);
        email_data.setText(entity.email);
        grandtime_data.setText(entity.graduationtime);
        incomme_data.setText(entity.workdate);
        national_data.setText(entity.nation);
        native_data.setText(entity.birthplace);
        idcard_data.setText(entity.idnumber);
        birthday_data.setText(entity.birthday);
        if ("1".equals(entity.marital_status)) {
            marry_data.setText("已婚");
        } else if ("0".equals(entity.marital_status)) {
            marry_data.setText("未婚");
        }
        adress_data.setText(entity.address);
        emergencyphone_data.setText(entity.contact_lx);
        emergencypepole_data.setText(entity.contact);

        GlideUtils.setRoundImage(context, entity.face, R.drawable.default_head, R.drawable.default_head, headimg);
    }


    private void getDetail() {
        showWaiting();
        String id = getArguments().getString(Constant.ENP_DETAIL);
        Message message = new Message();
        message.setTarget(mHandler);
        message.arg1 = 2;
        API.getEmpDetail(message, id);
    }

    /**
     * @param type 0=更新 1=添加
     */
    public void add_updateuser(int type) {
        if (type == -1) {
            CommonUtils.showToast("客户端参数错误");
            return;
        }
        namtv = name_data.getText().toString();
        worknumtv = worknum_data.getText().toString();
        gendertv = gender_data.getText().toString();
        phonetv = phone_data.getText().toString();
        depttv = dept_data.getText().toString();
        jobstv = jobs_data.getText().toString();
        emailtv = email_data.getText().toString();
        grandtimetv = grandtime_data.getText().toString();
        incommetv = incomme_data.getText().toString();
        nationaltv = national_data.getText().toString();
        nativetv = native_data.getText().toString();
        idcardtv = idcard_data.getText().toString();
        birthdaytv = birthday_data.getText().toString();
        marrytv = marry_data.getText().toString();
        adresstv = adress_data.getText().toString();
        emergencyphonetv = emergencyphone_data.getText().toString();
        emergencypepoletv = emergencypepole_data.getText().toString();
        if (TextUtils.isEmpty(namtv)) {
            CommonUtils.showToast("姓名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(worknumtv)) {
            CommonUtils.showToast("工号不能为空!");
            return;
        }
        if (TextUtils.isEmpty(gendertv)) {
            CommonUtils.showToast("性别不能为空!");
            return;
        }
        if (TextUtils.isEmpty(phonetv)) {
            CommonUtils.showToast("手机不能为空!");
            return;
        }
        if (TextUtils.isEmpty(depttv)) {
            CommonUtils.showToast("部门不能为空!");
            return;
        }
        if (TextUtils.isEmpty(jobstv)) {
            CommonUtils.showToast("岗位不能为空!");
            return;
        }
        if (TextUtils.isEmpty(emailtv)) {
            CommonUtils.showToast("邮箱不能为空!");
            return;
        }

        String id = "";
        try {
            id = getArguments().getString(Constant.ENP_DETAIL);
        } catch (Exception e) {

        }
        Message message = new Message();
        message.setTarget(mHandler);
        message.arg1 = type;
        String depId = "";
        try {
            depId = selectedDept.id + "";
        } catch (Exception e) {

        }

        API.add_update_user(message, id, namtv, worknumtv, gendertv, phonetv, depttv, depId, jobstv, emailtv, grandtimetv, incommetv, nationaltv, nativetv, idcardtv, birthdaytv, marrytv, adresstv, emergencyphonetv, emergencypepoletv);
    }

    public void showDialog() {
        final CommAlertDialog dialog = new CommAlertDialog(getActivity());
        dialog.setContentInfo("添加成功!");
        dialog.setButtonColor(R.color.blue);
        dialog.setCannelBtnName("关闭");
        dialog.setOkBtnName("继续添加");
        dialog.hidtitle();
        dialog.setButtonsListener(new DialogButtonsListener() {
            @Override
            public void onOKClick(Objects objects) {
                resetViews();
                dialog.dismiss();
            }

            @Override
            public void onCancleClick(Objects objects) {
                dialog.dismiss();
                FragmentManager.popFragment(getActivity());

            }
        });
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:

                    uri = SelectionDialogFragment.imagUrl;
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra("crop", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, SelectionDialogFragment.imagUrl);
                    startActivityForResult(intent, 2);
                    break;
                case 3:
                    if (null != data && null != data.getData()) {
                        uri = data.getData();
                    }
                    intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra("crop", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, SelectionDialogFragment.imagUrl);
                    startActivityForResult(intent, 2);
                    /**
                     * 图库要用data.getData()
                     */


                    break;
                case 2:
                    uri = SelectionDialogFragment.imagUrl;
                    GlideUtils.setLocalRoundImage2(getContext(), uri, R.drawable.default_head, R.drawable.default_head, headimg);
//                    Bitmap bitmap = null;
//                    try {
//                        bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    break;

            }

        }
    }

    public void call(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

}
