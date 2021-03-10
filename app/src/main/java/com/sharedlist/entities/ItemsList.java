package com.sharedlist.entities;

import java.util.HashMap;
import java.util.Map;

public class ItemsList {
    Map<String, Integer> sharedList = new HashMap<>();  // item names and quantities

    public ItemsList() {
    }

    public ItemsList(Map<String, Integer> sharedList) {
        this.sharedList = sharedList;
    }

    public Map<String, Integer> getSharedList() {
        return sharedList;
    }

    public void setSharedList(Map<String, Integer> sharedList) {
        this.sharedList = sharedList;
    }
}
