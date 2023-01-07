package com.hfw.basesystem.gen;

import com.hfw.basesystem.entity.SysGenColumn;
import com.hfw.basesystem.entity.SysGenTable;
import com.hfw.basesystem.mapper.GenMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.GeneralException;
import com.hfw.common.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码生成器实现
 * @author zyh
 * @date 2022-04-15
 */
@Service
public class GenServiceImpl implements GenService {

    @Autowired
    private GenProperty genProperty;
    @Autowired
    private GenMapper genMapper;

    @Override
    public List<Table> tableList(String tableLike){
        return genMapper.tableList(genProperty.getDb(), tableLike);
    }
    @Override
    public PageResult<Table> tablePage(String tableLike, Integer pageNumber, Integer pageSize){
        PageResult<Table> page = new PageResult<>(pageNumber,pageSize);
        page.startPage();
        List<Table> list = genMapper.tableList(genProperty.getDb(), tableLike);
        page.setList(list);
        return page;
    }
    @Override
    public Table tableInfo(String tableName){
        return genMapper.table(genProperty.getDb(), tableName);
    }
    @Override
    public List<Column> tableColumn(String tableName){
        return genMapper.tableColumn(genProperty.getDb(), tableName);
    }
    @Override
    public Table tableColumnInfo(String tableName){
        //表信息处理
        Table table = this.tableInfo(tableName);
        if(tableName.startsWith(genProperty.getTablePrefix())){
            tableName = tableName.substring(genProperty.getTablePrefix().length());
        }
        table.setBeanName(StrUtil.lineToHump( tableName ));
        table.setClassName(StrUtil.upperCase(table.getBeanName()));
        table.setPackageName(genProperty.getPack());

        //表字段信息处理
        List<Column> columnList = this.tableColumn(tableName);
        for(Column column : columnList){
            //主键,逻辑删除处理
            if("PRI".equals(column.getColumnKey())){
                table.setPk(column);
            }else if(column.getColumnName().equals(genProperty.getLogicDeleteField())){
                table.setLogicDeleted(column);
            }else{
                table.getColumnList().add(column);
            }
            //java字段类型处理
            if("bigint".equals(column.getDataType())){
                column.setJavaType("Long");
            }else if(column.getDataType().contains("int")){
                column.setJavaType("Integer");
            }else if("float".equals(column.getDataType())){
                column.setJavaType("Float");
            }else if("double".equals(column.getDataType())){
                column.setJavaType("Double");
            }else if("decimal".equals(column.getDataType())){
                column.setJavaType("java.math.BigDecimal");
            }else if(column.getDataType().contains("char") || column.getDataType().contains("text")){
                column.setJavaType("String");
            }else if( "date".equals(column.getDataType()) ){
                column.setJavaType("java.time.LocalDate");
                //table.setDateField(true);
            }else if( "datetime".equals(column.getDataType()) ){
                column.setJavaType("java.time.LocalDateTime");
                //table.setDateField(true);
            }
            //注释为空处理
            if(!StrUtil.hasText(column.getColumnComment())){
                column.setColumnComment(column.getColumnName());
            }
            //字段名下划线转驼峰
            column.setFieldName(StrUtil.lineToHump(column.getColumnName()));
        }
        return table;
    }

    @Override
    public void genToPath(String tableName) throws Exception{
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            throw new GeneralException(tableName+"表名不存在!");
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
        template = configuration.getTemplate("entityDTO.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"DTO.java")){
            template.process(table, out);
        }
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
        template = configuration.getTemplate("serviceimpl.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"ServiceImpl.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("controller.java.ftl");
        try(Writer out = new FileWriter(outDir+table.getClassName()+"Controller.java")){
            template.process(table, out);
        }
    }
    @Override
    public void genToProject(String tableName) throws Exception{
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            throw new GeneralException(tableName+"表名不存在!");
        }
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //输出文件
        Template template = configuration.getTemplate("entity.java.ftl");
        try(Writer out = new FileWriter("./model/src/main/java/com/hfw/model/entity/"+table.getClassName()+".java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("entityDTO.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/dto/"+table.getClassName()+"DTO.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("mapper.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/mapper/"+table.getClassName()+"Mapper.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("mapper.xml.ftl");
        try(Writer out = new FileWriter("./admin/src/main/resources/mapper/"+table.getClassName()+"Mapper.xml")){
            template.process(table, out);
        }
        template = configuration.getTemplate("service.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/service/"+table.getClassName()+"Service.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("serviceimpl.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/service/impl/"+table.getClassName()+"ServiceImpl.java")){
            template.process(table, out);
        }
        template = configuration.getTemplate("controller.java.ftl");
        try(Writer out = new FileWriter("./admin/src/main/java/com/hfw/admin/controller/"+table.getClassName()+"Controller.java")){
            template.process(table, out);
        }
    }
    @Override
    public String javaCode(String tableName, String templateName) throws Exception{
        Table table = this.tableColumnInfo(tableName);
        if(table==null){
            throw new GeneralException(tableName+"表名不存在!");
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
        Template template = configuration.getTemplate(templateName+".ftl");
        try(StringWriter out = new StringWriter()){
            template.process(table, out);
            return out.toString();
        }
    }

    @Override
    public SysGenTable formGenTableInfo(String tableName){
        Table table = this.tableColumnInfo(tableName);
        List<SysGenColumn> formColumns = table.getColumnList().stream().map(c -> {
            SysGenColumn column = new SysGenColumn();
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
            if("bigint".equals(c.getDataType())){
                column.setFormType("number");
            }else if(c.getDataType().contains("int")){
                column.setFormType("number");
            }else if("float".equals(c.getDataType())){
                column.setFormType("number");
            }else if("double".equals(c.getDataType())){
                column.setFormType("number");
            }else if("decimal".equals(c.getDataType())){
                column.setFormType("number");
            }else if(c.getDataType().contains("text")){
                column.setFormType("richtext");
            }else if( "date".equals(c.getDataType()) ){
                column.setFormType("date");
            }else if( "datetime".equals(c.getDataType()) ){
                column.setFormType("datetime");
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
    private CommonDao commonDao;

    //表单生成设置
    private void genTableSet(SysGenTable table){
        table.setBeanName(StrUtil.lineToHump( table.getTableName() ));
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
            }
        }
    }
    //保存生成记录
    private void saveGenFormRecord(SysGenTable table){
        //table.setId(null);
        table.getColumnList().forEach( c->c.setId(null));
        //commonDao.delete(new SysGenTable().setTableName(table.getTableName()));
        commonDao.delete(new SysGenColumn().setTableName(table.getTableName()));
        //commonDao.insert(table);
        commonDao.insertBatch(table.getColumnList());
    }

    @Override
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
        Template template = configuration.getTemplate("api.ts.ftl");
        try(Writer out = new FileWriter(outDir+table.getBeanName()+".ts")){
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
        this.saveGenFormRecord(table);
    }
    @Override
    public void genFormToProject(SysGenTable table) throws Exception{
        this.genTableSet(table);
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //模板
        String outDir = "./Geeker-Admin/src/views/"+table.getBeanName();
        Files.createDirectories(Paths.get(outDir));
        Template template = configuration.getTemplate("api.ts.ftl");
        try(Writer out = new FileWriter("./Geeker-Admin/src/api/modules/"+table.getBeanName()+".ts")){
            template.process(table, out);
        }
        template = configuration.getTemplate("index.vue.ftl");
        try(Writer out = new FileWriter("./Geeker-Admin/src/views/"+table.getBeanName()+"/index.vue")){
            template.process(table, out);
        }
        template = configuration.getTemplate("edit.vue.ftl");
        try(Writer out = new FileWriter("./Geeker-Admin/src/views/"+table.getBeanName()+"/edit.vue")){
            template.process(table, out);
        }
        //保存生成记录
        this.saveGenFormRecord(table);
    }
    @Override
    public String formCode(SysGenTable table, String templateName) throws Exception{
        this.genTableSet(table);
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板路径
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("gen").getPath()));
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        //模板
        String outDir = genProperty.getPath()+File.separator+table.getBeanName()+File.separator;
        Files.createDirectories(Paths.get(outDir));
        Template template = configuration.getTemplate(templateName+".ftl");
        try(StringWriter out = new StringWriter()){
            template.process(table, out);
            return out.toString();
        }
    }
}
