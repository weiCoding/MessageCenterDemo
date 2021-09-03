package com.demo.messagecenterdemo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.messagecenterdemo.lib.BaseItemAdapter;
import com.demo.messagecenterdemo.lib.vh.AbListItem;
import com.demo.messagecenterdemo.lib.vh.BaseItem;
import com.jingdong.app.mall.bundle.CommonMessageCenter.image.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shijizong
 * @description:
 * @date : 2021/8/13 6:15 下午
 */
public class ImgTestActivity extends AppCompatActivity {

    private RecyclerView rv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_test);
        rv=findViewById(R.id.rv);
        LinearLayoutManager lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(lm);
        ITadapter adapter=new ITadapter();
        rv.setAdapter(adapter);


        List<String> imgs=new ArrayList<>();
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");
        imgs.add("https://img2018.cnblogs.com/blog/1001990/201905/1001990-20190509005819587-2111053716.png");

        adapter.setList(imgs);
        adapter.notifyDataSetChanged();
    }



    private static class ITadapter extends BaseItemAdapter<String> {

        @Override
        protected AbListItem createItem(String model) {
            return new ITitem();
        }
    }

    private static class ITitem extends BaseItem<String> {

        private ImageView iv;
        private TextView tv;
        @Override
        protected void initView(RecyclerView.ViewHolder viewHolder) {
            iv=viewHolder.itemView.findViewById(R.id.iv);
            tv=viewHolder.itemView.findViewById(R.id.tv);
        }

        @Override
        protected void bindView(RecyclerView.ViewHolder viewHolder, String data) {
            ImageUtils.with()
                    .url(data)
                    .into(iv);
            tv.setText(""+position);
        }

        @Override
        public int layoutId() {
            return R.layout.item_image_test;
        }
    }

}
