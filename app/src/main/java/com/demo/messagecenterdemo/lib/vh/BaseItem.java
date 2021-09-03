package com.demo.messagecenterdemo.lib.vh;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.messagecenterdemo.lib.BaseItemAdapter;


/**
 * @author shijizong
 * @description:
 * @date : 2021/3/30 5:35 PM
 */
public abstract class BaseItem<T> implements AbListItem<T>{

    protected T data;
    protected BaseItemAdapter adapter;
    private RecyclerView.ViewHolder vh;
    protected int position;
    protected View.OnClickListener mClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                click(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public RecyclerView.ViewHolder createVH(View itemView) {
        return new RecyclerView.ViewHolder(itemView) {};
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder) {
        try {
            if(vh!=viewHolder){
                vh=viewHolder;
            }
            initView(viewHolder);
            if(hasItemClick()){
                vh.itemView.setOnClickListener(mClickListener);
            }
            bindView(viewHolder,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void bindPosition(int pos) {
        this.position=pos;
    }

    @Override
    public void  bindData(T data) {
        this.data=data;
    }

    @Override
    public void bindAdapter(BaseItemAdapter adapter) {
        this.adapter=adapter;
    }

    protected  <T extends View> T findViewById(int resId) {
        if(vh!=null&&vh.itemView!=null){
            return vh.itemView.findViewById(resId);
        }
        return null;
    }

    protected void click(View v){
        if(vh!=null&&v==vh.itemView){
            itemClick();
        }
    }

    @Override
    public boolean hasItemClick() {
        return true;
    }

    protected void itemClick(){}

    protected Object getExtra(String key){
        if(adapter==null){
            return null;
        }
        return adapter.getExtra(key);
    }

    protected void notifySelfItem() {
        if (adapter != null) {
            adapter.notifyItemChanged(position);
        }
    }
    protected void notifyAllItem() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(RecyclerView.ViewHolder vh,boolean isCompleteVisibility) {}

    protected abstract void initView(@NonNull RecyclerView.ViewHolder viewHolder);
    protected abstract void bindView(@NonNull RecyclerView.ViewHolder viewHolder, T data);
}
