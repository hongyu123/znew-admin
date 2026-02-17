package com.hfw.model.mybatis.typehandler;

import java.util.ArrayList;
import java.util.Collection;

public class DBList extends ArrayList<String> {
    public DBList(){
        super();
    }
    public DBList(Collection<String> c){
        super(c);
    }
}
