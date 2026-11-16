package com.hfw.model.utils;

import java.util.ArrayList;
import java.util.Collection;

public class DBList<T> extends ArrayList<T> {
    public DBList() {
        super();
    }
    public DBList(Collection<? extends T> collection) {
        super(collection);
    }
}
