package com.toolsmi.gridfactory;

public class GridParent {
    int rowCount;
    int columnCount;
    int layout;
    int rowSpace;
    int columnSpace;

    public GridParent(int layout, String[] params) {
        this.layout = layout;
        rowCount = Integer.parseInt(params[1]);
        columnCount = Integer.parseInt(params[2]);
        rowSpace = Integer.parseInt(params[3]);
        columnSpace = Integer.parseInt(params[4]);
    }

    public GridParent() {

    }
}
