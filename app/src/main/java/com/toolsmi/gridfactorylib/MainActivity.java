package com.toolsmi.gridfactorylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        gridLayoutFactory.setOnItemClickListener(mItemClickListener);
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


    private GridLayoutFactory.OnItemClickListener mItemClickListener = new GridLayoutFactory.OnItemClickListener() {
        @Override
        public void onItemClick(String tag, View item, Object data) {
            if ("test".equals(tag)) {
                Toast.makeText(MainActivity.this, tag + "------" + item.getId(), Toast.LENGTH_SHORT).show();
                switch (item.getId()) {
                    case 0:
                        break;
                    case 3:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
            }
        }
    };

    private GridLayoutFactory.OnBindViewListener bindViewListener = new GridLayoutFactory.OnBindViewListener() {
        @Override
        public void onBindView(String tag, View item, int layout, Object obj, int id) {
            HashMap<String, Object> map = (HashMap<String, Object>) obj;
            switch (layout) {
                case R.layout.layout_find_item:
                    ImageView img = item.findViewById(R.id.img);
                    img.setImageResource((Integer) map.get("img"));
                case R.layout.layout_grid_title:
                    TextView tv = item.findViewById(R.id.title);
                    tv.setText(map.get("title").toString());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "--title-", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
}
