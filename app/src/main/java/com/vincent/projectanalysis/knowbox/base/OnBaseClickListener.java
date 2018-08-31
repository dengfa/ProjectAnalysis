package com.vincent.projectanalysis.knowbox.base;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @name 单击事件处理器（基础）
 * @author Fanjb
 * @date 2015年8月4日
 */
public abstract class OnBaseClickListener implements OnClickListener {

	public final void onClick(View v) {
		try {
			onBaseClick(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void onBaseClick(View v);
}
