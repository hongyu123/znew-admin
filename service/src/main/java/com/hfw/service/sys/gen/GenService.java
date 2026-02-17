package com.hfw.service.sys.gen;

import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysGenColumn;
import com.hfw.model.po.sys.SysGenTable;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.component.EnumScan;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 代码生成器实现
 * @author farkle
 * @date 2022-04-15
 */
@Service
public class GenService{
    @Autowired
    private GenProperty genProperty;
//    @Autowired
//    private GenMapper genMapper;
    @Autowired
    private PGGenMapper pgGenMapper;
    @Autowired
    private EnumScan enumScan;

    private static final Map<String,String> COLUMN_FIELD_MAP = new HashMap<>();
    private static final Map<String,String> COLUMN_FORM_MAP = new HashMap<>();
    static {
        /*COLUMN_FIELD_MAP.put("tinyint","Integer");
        COLUMN_FIELD_MAP.put("int","Integer");
        COLUMN_FIELD_MAP.put("bigint","Long");
        COLUMN_FIELD_MAP.put("float","Float");
        COLUMN_FIELD_MAP.put("double","Double");
        COLUMN_FIELD_MAP.put("decimal","java.math.BigDecimal");
        COLUMN_FIELD_MAP.put("char","String");
        COLUMN_FIELD_MAP.put("varchar","String");
        COLUMN_FIELD_MAP.put("tinytext","String");
        COLUMN_FIELD_MAP.put("text","String");
        COLUMN_FIELD_MAP.put("mediumtext","String");
        COLUMN_FIELD_MAP.put("longtext","String");
        COLUMN_FIELD_MAP.put("date","java.time.LocalDate");
        COLUMN_FIELD_MAP.put("datetime","java.time.LocalDateTime");

        COLUMN_FORM_MAP.put("tinyint","number");
        COLUMN_FORM_MAP.put("int","number");
        COLUMN_FORM_MAP.put("bigint","number");
        COLUMN_FORM_MAP.put("float","number");
        COLUMN_FORM_MAP.put("double","number");
        COLUMN_FORM_MAP.put("decimal","number");
        COLUMN_FORM_MAP.put("char","input");
        COLUMN_FORM_MAP.put("varchar","input");
        COLUMN_FORM_MAP.put("tinytext","richtext");
        COLUMN_FORM_MAP.put("text","richtext");
        COLUMN_FORM_MAP.put("mediumtext","richtext");
        COLUMN_FORM_MAP.put("longtext","richtext");
        COLUMN_FORM_MAP.put("date","date");
        COLUMN_FORM_MAP.put("datetime","datetime");*/

        COLUMN_FIELD_MAP.put("int2","Integer");
        COLUMN_FIELD_MAP.put("int4","Integer");
        COLUMN_FIELD_MAP.put("int8","Long");
        COLUMN_FIELD_MAP.put("float4","Float");
        COLUMN_FIELD_MAP.put("float8","Double");
        COLUMN_FIELD_MAP.put("numeric","java.math.BigDecimal");
        COLUMN_FIELD_MAP.put("bpchar","String");
        COLUMN_FIELD_MAP.put("varchar","String");
        COLUMN_FIELD_MAP.put("text","String");
        COLUMN_FIELD_MAP.put("date","java.time.LocalDate");
        COLUMN_FIELD_MAP.put("timestamp","java.time.LocalDateTime");
        COLUMN_FIELD_MAP.put("timestamptz","java.time.LocalDateTime");
        COLUMN_FIELD_MAP.put("json","com.hfw.model.mybatis.typehandler.DBMap");
        COLUMN_FIELD_MAP.put("jsonb","com.hfw.model.mybatis.typehandler.DBMap");

        COLUMN_FORM_MAP.put("int2","number");
        COLUMN_FORM_MAP.put("int4","number");
        COLUMN_FORM_MAP.put("int8","number");
        COLUMN_FORM_MAP.put("float4","number");
        COLUMN_FORM_MAP.put("float8","number");
        COLUMN_FORM_MAP.put("numeric","number");
        COLUMN_FORM_MAP.put("bpchar","input");
        COLUMN_FORM_MAP.put("varchar","input");
        COLUMN_FORM_MAP.put("text","richtext");
        COLUMN_FORM_MAP.put("date","date");
        COLUMN_FORM_MAP.put("timestamp","datetime");
        COLUMN_FORM_MAP.put("timestamptz","datetime");
        COLUMN_FORM_MAP.put("json","input");
        COLUMN_FORM_MAP.put("jsonb","input");
    }

    public List<Table> tableList(String tableLike){
        return pgGenMapper.tableList(genProperty.getSchema(), tableLike);
    }
    public PageResult<Table> tablePage(Page<Table> page, String tableLike){
        page.startPage();
        List<Table> list = pgGenMapper.tableList(genProperty.getSchema(), tableLike);
        return PageResult.of(list);
    }

    public Table tableInfo(String tableName){
        return pgGenMapper.table(genProperty.getSchema(), tableName);
    }
    public List<Column> tableColumn(String tableName){
        return pgGenMapper.tableColumn(genProperty.getSchema(), tableName);
    }

    /**
     * 表信息及字段信息获取
     * @param tableName 表名
     * @return 表信息及字段信息
     */
    private Table tableColumnInfo(String tableName){
        //表信息处理
        Table table = this.tableInfo(tableName);
        List<Column> columnList = this.tableColumn(tableName);
        if(tableName.startsWith(genProperty.getTablePrefix())){
            tableName = tableName.substring(genProperty.getTablePrefix().length());
        }
        table.setBeanName(StrUtil.lineToHump( tableName ));
        table.setClassName(StrUtil.upperCase(table.getBeanName()));
        table.setPackageName(genProperty.getPack());
        table.setProjectName(genProperty.getProjectName());
        table.setAuthor(genProperty.getAuthor());

        //表字段信息处理
        for(Column column : columnList){
            //主键,逻辑删除处理
            //if("PRI".equals(column.getColumnKey())){
            if("PRIMARY KEY".equals(column.getColumnKey())){
                table.setPk(column);
            }else if(column.getColumnName().equals(genProperty.getLogicDeleteField())){
                table.setLogicDeleted(column);
            }else{
                table.getColumnList().add(column);
            }
            //java字段类型处理
            column.setJavaType("String");
            String javaType = COLUMN_FIELD_MAP.get(column.getDataType());
            if(javaType!=null){
                column.setJavaType(javaType);
            }
            String columnComment = column.getColumnComment();
            //注释为空处理
            if(!StrUtil.hasText(columnComment)){
                column.setColumnComment(column.getColumnName());
            }else if(columnComment.startsWith("enum")){
                //枚举处理
                String[] comentArr = columnComment.split("_");
                if(comentArr.length>1){
                    Collection<Class<?>> enumClasses = enumScan.getEnumClasses(comentArr[1]);
                    if(!CollectionUtils.isEmpty(enumClasses)){
                        column.setEnumClass(enumClasses.iterator().next().getName());
                        column.setJavaType(comentArr[1]);
                    }
                }
                column.setColumnComment(comentArr.length>2 ?comentArr[2] :column.getColumnName());
            }
            //字段名下划线转驼峰
            column.setFieldName(StrUtil.lineToHump(column.getColumnName()));
        }
        return table;
    }

    public Result<Void> genToPath(String tableName) throws Exception {
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            return Result.error(tableName+"表名不存在!");
        }
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //模板
        String outDir = genProperty.getPath()+File.separator+table.getBeanName()+File.separator;
        Files.createDirectories(Paths.get(outDir));
        //输出文件
        Template template = configuration.getTemplate("entity.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+".java")){
            template.process(table, out);
        }
        /*template = configuration.getTemplate("entityDTO.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"DTO.java")){
            template.process(table, out);
        }*/
        template = configuration.getTemplate("mapper.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"Mapper.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("mapper.xml.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"Mapper.xml")){
            template.process(table, out);
        }
        template = configuration.getTemplate("service.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"Service.java")){
            template.process(table, out);
        }
        /*template = configuration.getTemplate("serviceimpl.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"ServiceImpl.java")){
            template.process(table, out);
        }*/
        template = configuration.getTemplate("controller.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"Controller.java")){
            template.process(table, out);
        }
        return Result.success();
    }

    public Result<Void> genToProject(String tableName) throws Exception{
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            return Result.error(tableName+"表名不存在!");
        }
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        String outDir = "./model/src/main/java/com/hfw/model/po/"+table.getProjectName();
        Files.createDirectories(Paths.get(outDir));
        //输出文件
        Template template = configuration.getTemplate("entity.java.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getClassName()+".java")){
            template.process(table, out);
        }
        /*template = configuration.getTemplate("entityDTO.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/dto/"+table.getClassName()+"DTO.java")){
            template.process(table, out);
        }*/
        outDir = "./service/src/main/java/com/hfw/service/"+table.getProjectName()+"/"+table.getBeanName();
        Files.createDirectories(Paths.get(outDir));
        template = configuration.getTemplate("mapper.java.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getClassName()+"Mapper.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("mapper.xml.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getClassName()+"Mapper.xml")){
            template.process(table, out);
        }
        template = configuration.getTemplate("service.java.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getClassName()+"Service.java")){
            template.process(table, out);
        }
       /* template = configuration.getTemplate("serviceimpl.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/service/impl/"+table.getClassName()+"ServiceImpl.java")){
            template.process(table, out);
        }*/
        outDir = "./admin/src/main/java/com/hfw/admin/controller/"+table.getProjectName();
        Files.createDirectories(Paths.get(outDir));
        template = configuration.getTemplate("controller.java.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getClassName()+"Controller.java")){
            template.process(table, out);
        }
        return Result.success();
    }

    public Result<String> javaCode(String tableName, String templateName) throws Exception{
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            return Result.error(tableName+"表名不存在!");
        }
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //输出文件
        Template template = configuration.getTemplate(templateName+".ftl");
        try(StringWriter out = new StringWriter()){
            template.process(table, out);
            return Result.string(out.toString());
        }
    }

    public SysGenTable formGenTableInfo(String tableName){
        Table table = this.tableColumnInfo(tableName);
        List<SysGenColumn> formColumns = table.getColumnList().stream().map(c -> {
            SysGenColumn column = new SysGenColumn();
            column.setTableName(tableName);
            column.setFormType("input");
            column.setListFlag(1);
            column.setColumnName(c.getColumnName());
            column.setLabel(c.getColumnComment().replaceFirst("\\(.+\\)",""));
            column.setProperty(StrUtil.lineToHump(c.getColumnName()));
            if(!StringUtils.hasText(column.getLabel())){
                column.setLabel(column.getProperty());
            }
            column.setMaxlength(c.getCharacterMaximumLength());
            if(c.getIsNullable().equals("NO") && !StringUtils.hasText(c.getColumnDefault()) ){
                column.setRequired(1);
            }
            String formType = COLUMN_FORM_MAP.get(c.getDataType());
            if(formType!=null){
                column.setFormType(formType);
            }
            //枚举处理
            if(StringUtils.hasText(c.getEnumClass())){
                column.setFormType("enum");
                column.setColumnRemark(c.getJavaType());
            }
            return column;
        }).collect(Collectors.toList());

        SysGenTable genTable = new SysGenTable();
        genTable.setTableName(table.getTableName());
        genTable.setTableRemark(table.getTableComment());
        genTable.setColumnList(formColumns);
        return genTable;
    }


    @Autowired
    private SysGenTableService sysGenTableService;

    //表单生成设置
    private void genTableSet(SysGenTable table){
        String tableName = table.getTableName();
        if(tableName.startsWith(genProperty.getTablePrefix())){
            tableName = tableName.substring(genProperty.getTablePrefix().length());
        }
        table.setBeanName(StrUtil.lineToHump( tableName ));
        table.setClassName( StrUtil.upperCase(table.getBeanName()) );

        for(SysGenColumn c : table.getColumnList()){
            if(c.getFormType().equals("richtext")){
                table.setRichtext(true);
            }else if(c.getFormType().equals("date")){
                table.setDate(true);
            }else if(c.getFormType().equals("datetime")){
                table.setDatetime(true);
            }else if(c.getFormType().equals("picture")){
                table.setPicture(true);
            }else if(c.getFormType().equals("pictures")){
                table.setPictures(true);
            }else if(c.getFormType().equals("file")){
                table.setFile(true);
            }else if(c.getFormType().equals("fileInput")){
                table.setFileInput(true);
            }else if(c.getFormType().equals("map")){
                table.setMap(true);
            }else if(c.getFormType().equals("video")){
                table.setVideo(true);
            }
        }
    }

    public void genFormToPath(SysGenTable table) throws Exception{
        this.genTableSet(table);
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //模板
        String outDir = genProperty.getPath()+File.separator+table.getBeanName()+File.separator;
        Files.createDirectories(Paths.get(outDir));
        Template template = configuration.getTemplate("api.js.ftl");
        try(Writer out = new FileWriter(outDir+table.getBeanName()+".js")){
            template.process(table, out);
        }
        template = configuration.getTemplate("index.vue.ftl");
        try(Writer out = new FileWriter(outDir+"index.vue")){
            template.process(table, out);
        }
        template = configuration.getTemplate("edit.vue.ftl");
        try(Writer out = new FileWriter(outDir+"edit.vue")){
            template.process(table, out);
        }
        //保存生成记录
        sysGenTableService.saveGenFormRecord(table);
    }
    public void genFormToProject(SysGenTable table) throws Exception{
        this.genTableSet(table);
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //模板
        String outDir = "./pure-admin/src/views/"+genProperty.getProjectName()+"/"+table.getBeanName();
        Files.createDirectories(Paths.get(outDir));
        Template template = configuration.getTemplate("api.js.ftl");
        try(Writer out = new FileWriter(outDir+"/"+table.getBeanName()+".js")){
            template.process(table, out);
        }
        template = configuration.getTemplate("index.vue.ftl");
        try(Writer out = new FileWriter(outDir+"/index.vue")){
            template.process(table, out);
        }
        template = configuration.getTemplate("edit.vue.ftl");
        try(Writer out = new FileWriter(outDir+"/edit.vue")){
            template.process(table, out);
        }
        //保存生成记录
        sysGenTableService.saveGenFormRecord(table);
    }
    public String formCode(SysGenTable table, String templateName) throws Exception{
        this.genTableSet(table);
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        Template template = configuration.getTemplate(templateName+".ftl");
        try(StringWriter out = new StringWriter()){
            template.process(table, out);
            return out.toString();
        }
    }

}
