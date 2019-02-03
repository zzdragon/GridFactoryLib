package com.toolsmi.gridfactorylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolsmi.gridfactory.GridLayoutFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout root;
    private GridLayoutFactory gridLayoutFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        gridLayoutFactory = new GridLayoutFactory(this, bindViewListener);
        try {
            InputStream is = getAssets().open("gridlayout.config");
            List<Map<String, Object>> data = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("title", "推荐游园路线");
            data.add(map);
            map = new HashMap<>();
            map.put("title", "浪漫世园行《山水园艺轴》\n推荐最佳路线");
            map.put("img", R.mipmap.img1);
            data.add(map);
            map = new HashMap<>();
            map.put("title", "旅行笔记");
            map.put("img", R.mipmap.img2);
            data.add(map);
            map = new HashMap<>();
            map.put("title", "游玩去世园");
            map.put("img", R.mipmap.img3);
            data.add(map);
            map = new HashMap<>();
            map.put("title", "游玩去世园");
            map.put("img", R.mipmap.img3);
            data.add(map);
            map = new HashMap<>();
            map.put("title", "游玩去世园");
            map.put("img", R.mipmap.img3);
            data.add(map);
            View view = gridLayoutFactory.getView(is, data);
            if (view != null)
                root.addView(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GridLayoutFactory.OnBindViewListener bindViewListener = new GridLayoutFactory.OnBindViewListener() {
        @Override
        public void onBindView(View item, int layout, Object obj) {
            HashMap<String, Object> map = (HashMap<String, Object>) obj;
            switch (layout) {
                case R.layout.layout_find_item:
                    ImageView img = item.findViewById(R.id.img);
                    img.setImageResource((Integer) map.get("img"));
                case R.layout.layout_grid_title:
                    TextView tv = item.findViewById(R.id.title);
                    tv.setText(map.get("title").toString());
                    break;
            }
        }
    };
}
