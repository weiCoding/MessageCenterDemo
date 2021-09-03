package com.demo.messagecenterdemo.lib.vh;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.messagecenterdemo.lib.BaseItemAdapter;


/**
 * @author shijizong
 * @description:
 * @date : 2021/3/30 5:36 PM
 */
public interface AbListItem<T> {

    int layoutId();

    RecyclerView.ViewHolder createVH(View itemView);

    void bind(RecyclerView.ViewHolder viewHolder);

    void bindData(T data);

    void bindAdapter(BaseItemAdapter adapter);

    void bindPosition(int pos);

    boolean hasItemClick();

    void onResume(RecyclerView.ViewHolder vh, boolean isCompleteVisibility);

}

