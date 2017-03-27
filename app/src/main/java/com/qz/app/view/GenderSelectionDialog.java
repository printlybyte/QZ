package com.qz.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qz.app.R;


public class GenderSelectionDialog implements OnClickListener {
    private AlertDialog genderDialog;
    private Context context;
    private RelativeLayout male_layout, female_layout;
    private ImageView male_select_tips, female_select_tips;
    private OnGenderSelect onGenderSelect;
    /**
     * selection1 = 0  男  未婚
     */
    public static final String GEND_MALE = "0";
    /**
     * selection2=1  女   已婚
     */
    public static final String GEND_FEMALE = "1";

    private TextView selection1;
    private TextView selection2;
    public interface OnGenderSelect {
        public void onselected(String gender);
    }

    public GenderSelectionDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    /**
     *
     * @param type
     */
    public void setType(int type,View view){
        if(type == 0){
            ((TextView)view.findViewById(R.id.selection1)).setText("男");
            ((TextView)view.findViewById(R.id.selection2)).setText("女");
        }else if(type == 1){

            ((TextView)view.findViewById(R.id.selection1)).setText("未婚");
            ((TextView)view.findViewById(R.id.selection2)).setText("已婚");
        }

    }

    public void showPopupDialog(int type,String gender) {
        // L.v(TAG, "showPopupDialog user=",
        // null == user ? "null" : user.toString());
        View dialogView = LayoutInflater.from(context).inflate(
                R.layout.sex_selection_layout, null);

        genderDialog = new AlertDialog.Builder(context).create();
        genderDialog.setView(dialogView);
        genderDialog.show();
        male_layout = (RelativeLayout) dialogView
                .findViewById(R.id.male_layout);
        male_layout.setOnClickListener(this);
        male_select_tips = (ImageView) dialogView
                .findViewById(R.id.male_select_tips);
        female_layout = (RelativeLayout) dialogView
                .findViewById(R.id.female_layout);
        female_layout.setOnClickListener(this);
        female_select_tips = (ImageView) dialogView
                .findViewById(R.id.female_select_tips);
        if (!TextUtils.isEmpty(gender)) {
            if ("0".equals(gender)) {
                male_select_tips.setImageResource(R.drawable.selected_icon);
                female_select_tips.setImageResource(R.drawable.select_normal);
            } else if ("1".equals(gender)){
                male_select_tips.setImageResource(R.drawable.select_normal);
                female_select_tips.setImageResource(R.drawable.selected_icon);
            }else {
                male_select_tips.setImageResource(R.drawable.select_normal);
                female_select_tips.setImageResource(R.drawable.select_normal);
            }

        } else {
            male_select_tips.setImageResource(R.drawable.select_normal);
            female_select_tips.setImageResource(R.drawable.select_normal);
        }
        setType(type,dialogView);
    }

    public void setOnGenderSelected(OnGenderSelect onGenderSelect) {
        this.onGenderSelect = onGenderSelect;
    }

    @Override
    public void onClick(View v) {
        if (v == male_layout) {

            male_select_tips.setImageResource(R.drawable.selected_icon);
            female_select_tips.setImageResource(R.drawable.select_normal);
            onGenderSelect.onselected(GEND_MALE);
        } else if (v == female_layout) {
            onGenderSelect.onselected(GEND_FEMALE);

            male_select_tips.setImageResource(R.drawable.select_normal);
            female_select_tips.setImageResource(R.drawable.selected_icon);


        }
        dismissDialog();
    }

    public void dismissDialog() {
        if (null != genderDialog) {
            genderDialog.dismiss();
        }
    }

}
