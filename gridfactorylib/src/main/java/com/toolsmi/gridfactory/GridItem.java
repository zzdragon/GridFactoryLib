package com.toolsmi.gridfactory;

public class GridItem {
    int layout;
    int row;
    int column;
    int rowWeight;
    int columnWeight;
    int rowSpan;
    int columnSpan;

    public GridItem() {
    }

    public GridItem(int layout, String[] items) {
        this.layout = layout;
        row = Integer.parseInt(items[1]);
        column = Integer.parseInt(items[2]);
        rowSpan = Integer.parseInt(items[3]);
        columnSpan = Integer.parseInt(items[4]);
        rowWeight = Integer.parseInt(items[5]);
        columnWeight = Integer.parseInt(items[6]);
    }
}
