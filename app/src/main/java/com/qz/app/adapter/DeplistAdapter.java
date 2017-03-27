//package com.qz.app.adapter;
//
//import java.util.List;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//import com.qz.app.R;
//import com.qz.app.constant.Constant;
//import com.qz.app.entity.DepEntity;
//import com.qz.app.fragment.DepFragment_add;
//import com.qz.app.utils.FragmentManager;
//
//import static android.R.attr.data;
//
//public class DeplistAdapter extends com.qz.app.base.BaseAdapter{
//
//	public boolean showSelectItem = true;
//	public int swapitemPos;
//	public DeplistAdapter(Context context, List data) {
//		super(context, data);
//	}
//
//	public  int getLayoutId(){
//		return  R.layout.item_depinfo;
//	}
//    public  int[]getConvertItemIds(){
//		return new int[]{R.id.depName,R.id.showdetail_click,R.id.changpos,R.id.content};
//    }
//    public  void initViews(ViewHolder vh,int position){
////		 item =()entitys.get(position);
//		final DepEntity.Children entity = (DepEntity.Children) entitys.get(position);
//		((TextView)vh.getView(R.id.depName)).setText(entity.name);
//
//		if (swapitemPos == position && !showSelectItem) {
//			vh.getView(R.id.content).setVisibility(View.INVISIBLE);
//		} else {
//			vh.getView(R.id.content).setVisibility(View.VISIBLE);
//		}
//		vh.getView(R.id.showdetail_click).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Bundle bundle = new Bundle();
//				bundle.putInt(Constant.ADD_FROM,Constant.ADD_FROM_SHOW);
//				bundle.putSerializable("children",entity);
//				FragmentManager.addStackFragment((FragmentActivity) context, com.qz.app.base.BaseFragment.getInstance((FragmentActivity) context, DepFragment_add.class.getName(),bundle));
//			}
//		});
//
//    }
//
//
//}
