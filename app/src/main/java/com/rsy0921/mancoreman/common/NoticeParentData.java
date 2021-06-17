package com.rsy0921.mancoreman.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NoticeParentData {

    String title, reg_date;
    public ArrayList<String> child;

    public NoticeParentData(String title, String reg_date) {
        this.title = title;
        this.reg_date = reg_date;
        this.child = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getReg_date() {
        return reg_date;
    }
}
