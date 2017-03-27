//package com.qz.app.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.widget.TextView;
//
//import com.xingfuhuaxia.app.R;
//import com.xingfuhuaxia.app.base.BaseAdapter;
//import com.xingfuhuaxia.app.mode.HuxingEntity;
//import com.xingfuhuaxia.app.mode.BuyIntentEntity.DefaultAnswerList;
//
//public class HuxingAdapter extends BaseAdapter {
//
//	public HuxingAdapter(Context context, List data) {
//		super(context, data);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public int getLayoutId() {
//		// TODO Auto-generated method stub
//		return R.layout.item_userfrom_gride;
//	}
//
//	@Override
//	public int[] getConvertItemIds() {
//
//		return new int[] { R.id.Answerdesc_data };
//	}
//
//	@Override
//	public void initViews(final ViewHolder vh, int position) {
//		final HuxingEntity question = (HuxingEntity) entitys.get(position);
//		((TextView) vh.getView(R.id.Answerdesc_data)).setText(question.HuXing);
//		if (question.isSelected) {
//			((TextView) vh.getView(R.id.Answerdesc_data)).setBackgroundResource(R.drawable.btnlightbluexml);
//			((TextView) vh.getView(R.id.Answerdesc_data)).setTextColor(Color.WHITE);
//		} else {
//			((TextView) vh.getView(R.id.Answerdesc_data))
//					.setBackgroundResource(R.drawable.btnwhitexml);
//			((TextView) vh.getView(R.id.Answerdesc_data)).setTextColor(Color.rgb(5, 90, 109));
//		}
//	}
//
//}
