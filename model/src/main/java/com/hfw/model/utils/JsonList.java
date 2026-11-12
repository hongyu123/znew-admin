package com.hfw.model.utils;

import java.util.ArrayList;
import java.util.Collection;

public class JsonList<T> extends ArrayList<T> {
    public JsonList() {
        super();
    }
    public JsonList(Collection<? extends T> collection) {
        super(collection);
    }
}
