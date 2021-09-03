package com.demo.messagecenterdemo.lib;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.messagecenterdemo.lib.vh.AbListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author shijizong
 * @description:
 * @date : 2021/3/30 4:36 PM
 */
public abstract class BaseItemAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int DEFAULT_TYPE = -100;
    private List<T> mDatas = new ArrayList();
    private List<AbListItem> items = new ArrayList<>();//缓存，每一个数据都有一个对应的viewItem
    private SparseArray<AbListItem> mSparseArray = new SparseArray();//用于获取viewType
    private LayoutInflater inflate;
    private Map<String, Object> extras = new HashMap<>();

    public void setList(List<T> list) {
        clear();
        List<AbListItem> abListItems = filterList(list);
        if (list != null && !list.isEmpty()) {
            mDatas = list;
            items.addAll(abListItems);
        }
    }

    public List<T> getList() {
        return this.mDatas;
    }

    public void clear() {
        mDatas.clear();
        items.clear();
        mSparseArray.clear();
    }

    public void addList(List<T> list) {
        List<AbListItem> abListItems = filterList(list);
        if (list != null && !list.isEmpty()) {
            mDatas.addAll(list);
            items.addAll(abListItems);
        }
    }

    public void addList(int pos, List<T> list) {
        List<AbListItem> abListItems = filterList(list);
        if (list != null && !list.isEmpty() && pos >= 0 && pos <= mDatas.size()) {
            mDatas.addAll(pos, list);
            items.addAll(pos, abListItems);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder vh = null;
        try {
            AbListItem item = mSparseArray.get(viewType);
            View itemView;
            if (item != null && item.layoutId() != DEFAULT_TYPE) {
                if (inflate == null) {
                    inflate = LayoutInflater.from(viewGroup.getContext());
                }
                itemView = inflate.inflate(item.layoutId(), viewGroup, false);
                vh = item.createVH(itemView);
            } else {
                itemView = new View(viewGroup.getContext());
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(1, 1);
                itemView.setLayoutParams(layoutParams);
                itemView.setTag("default");
            }
            if (vh == null) {
                vh = new RecyclerView.ViewHolder(itemView) {
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vh;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        try {
            if (viewHolder.itemView.getTag() == null || !"default".equals(viewHolder.itemView.getTag())) {
                AbListItem item = getItem(position);
                if (item != null) {
                    item.bindPosition(position);
                    item.bind(viewHolder);
                }
//                if (onItemClickListener != null && (item == null || !item.hasItemClick())) {
//                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (onItemClickListener != null) {
//                                onItemClickListener.itemClick(viewHolder.itemView, position, getData(position));
//                            }
//                        }
//                    });
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getItemViewType(int position) {
        AbListItem item = getItem(position);
        if (item == null) {
            return DEFAULT_TYPE;
        }
        int id = item.layoutId();
        if (mSparseArray.get(id) == null) {
            this.mSparseArray.put(id, item);
        }
        return id;
    }

    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public T getData(int position) {
        if (position >= 0 && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }

    public AbListItem getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public void putExtra(String key, Object value) {
        extras.put(key, value);
    }

    public Object getExtra(String key) {
        return extras.get(key);
    }

    protected List<AbListItem> filterList(List<T> list) {
        List<AbListItem> abList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return abList;
        }
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T data = iterator.next();
            if (data == null) {
                iterator.remove();
                continue;
            }
            AbListItem item = null;
            if (data instanceof AbListItem) {
                item = (AbListItem) data;
            } else {
                item = createItem(data);
                if (item != null) {
                    item.bindData(data);
                }
            }
            if (item == null) {
                iterator.remove();
                continue;
            }
            item.bindAdapter(this);
            abList.add(item);
        }
        return abList;
    }

//    public WrapRecyclerAdapter getWrapAdapter(){
//        return wrapAdapter;
//    }
//
//    public void setWrapAdapter(WrapRecyclerAdapter wrapAdapter){
//        this.wrapAdapter=wrapAdapter;
//    }

    protected abstract AbListItem createItem(T model);//创建AbListItem

}
