package com.toolsmi.gridfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GridLayoutFactory {
    private Context mContext;
    private LayoutInflater mInflater;
    private OnBindViewListener mBindViewListener;
    private OnItemClickListener mItemClickListener;
    private float mDensity;

    public GridLayoutFactory(@NonNull Context context, @NonNull OnBindViewListener bindViewListener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBindViewListener = bindViewListener;
        mDensity = mContext.getResources().getDisplayMetrics().density;
    }

    /**
     * @param config format
     *               tag|layout,rowCount,columnCount,rowSpace,columnSpace|layout,row,column,rowSpan,columnSpan,rowWeight,columnWeight;layout,row,column,rowSpan,columnSpan,rowWeight,columnWeight;...
     * @param data
     * @return
     */
    public GridLayout getView(@NonNull String config, @NonNull List data) {
        if (TextUtils.isEmpty(config)) return null;
        if (!config.matches("\\S+\\|\\S*,\\d+,\\d+,\\d+,\\d+\\|(\\S*,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+;)*(\\S*,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+)"))
            throw new RuntimeException("配置信息错误");
        String[] parts = config.split("\\|");
        String tag = parts[0];
        String[] parentParams = parts[1].split(",");
        parts = parts[2].split(";");
        GridParent parent = new GridParent(getLayoutId(parentParams[0]), parentParams);
        List<GridItem> items = new ArrayList<>();
        String[] itemParams;
        for (String itemPart : parts) {
            itemParams = itemPart.split(",");
            items.add(new GridItem(getLayoutId(itemParams[0]), itemParams));
        }
        GridLayout view = generateView(parent, items, data);
        view.setTag(tag);
        return view;
    }

    private int getLayoutId(String name) {
        if (TextUtils.isEmpty(name)) return 0;
        return mContext.getResources().getIdentifier(name, "layout", mContext.getPackageName());
    }

    public GridLayout getView(@NonNull InputStream is, @NonNull List data) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            return getView(bos.toString(), data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    private GridLayout generateView(GridParent parent, List<GridItem> items, List data) {
        GridLayout gridLayout = new GridLayout(mContext);
        gridLayout.setLayoutParams(new LinearLayout.MarginLayoutParams(LinearLayout.MarginLayoutParams.MATCH_PARENT, LinearLayout.MarginLayoutParams.WRAP_CONTENT));
        gridLayout.setColumnCount(parent.columnCount);
        gridLayout.setRowCount(parent.rowCount);
        GridLayout.LayoutParams lp = null;
        GridItem item;
        int dataSize = data.size();
        for (int i = 0; i < items.size() && i < dataSize; i++) {
            item = items.get(i);
            if (item == null) continue;
            int layout = item.layout == 0 ? parent.layout : item.layout;
            if (layout == 0) {
                throw new RuntimeException("generate view error：missing param 'layout’");
            }
            View child = createView(layout, parent, item, data.get(i));
            if (child == null) continue;
            GridLayout.Spec columnSpec = GridLayout.spec(item.column, item.columnSpan, item.columnWeight);
            GridLayout.Spec rowSpec = GridLayout.spec(item.row, item.rowSpan, item.rowWeight);
            lp = new GridLayout.LayoutParams(rowSpec, columnSpec);
            lp.width = item.columnSpan;
            lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
            int hm = (int) (parent.columnSpace * mDensity / 2);
            int vm = (int) (parent.rowSpace * mDensity / 2);
            lp.leftMargin = hm;
            lp.rightMargin = hm;
            lp.topMargin = vm;
            lp.bottomMargin = vm;
            gridLayout.addView(child, lp);
        }
        return gridLayout;
    }

    private View createView(int layout, GridParent parent, GridItem item, Object obj) {
        View child = mInflater.inflate(layout, null);
        child.setId(item.row * parent.columnCount + item.column);
        child.setOnClickListener(itemClick);
        child.setTag(obj);
        mBindViewListener.onBindView(child, layout, obj, child.getId());
        return child;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    private View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(((View) v.getParent()).getTag().toString(), v, v.getTag());
            }
        }
    };

    public interface OnBindViewListener {
        void onBindView(View item, int layout, Object obj, int id);
    }

    public interface OnItemClickListener {
        void onItemClick(String tag, View item, Object data);
    }
}
