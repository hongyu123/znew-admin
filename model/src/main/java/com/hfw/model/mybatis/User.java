package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.typehandler.DBList;
import com.hfw.model.mybatis.typehandler.DBMap;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(value = "t_user", schema = "test")
public class User {
    @TableId
    private Long id;
    private String name;
    //@LogicDelete(beforeValue = "10")
    @TableField("age_info")
    private Integer age;
    private DBMap infos;
    private DBList<String> tags;
    @TableField(insertValue = "$now")
    private LocalDateTime create;
    @TableField(insertValue = "now()", updateValue = "$now")
    private LocalDateTime updateTime;
    @TableField(insertValue = "0")
    @LogicDelete(deletedValue = "id")
    private Integer deleted;

    public enum COLUMN implements Column<User>{
        id,name,age,infos,tags, create("create_time"),updateTime;

        private String column;
        COLUMN(){}
        COLUMN(String column){
            this.column = column;
        }
        public String columnName(){
            return this.column;
        }

    }

    public static void main(String[] args) {
    }

}
