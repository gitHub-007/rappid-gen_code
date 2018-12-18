package net.ptnetwork.entity.model;

import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.typemapping.JavaImport;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 实体
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
public class JPAEntity extends JPABaseBean {

    private Set<JPAField> fields = new HashSet<>();

    private String tableName;

    /**
     * 获取实体名称，用于文件夹生成
     * @return
     */
    public String getEntityName() {
        return this.getName();
    }

    /**
     * 获取搜索字段(页面)
     */
    public List<JPAField> getSearchFields() {
        return fields.stream().filter(field -> field.getSearch()).collect(Collectors.toList());
    }

    /**
     * 获取显示字段(页面[add.ftl,edit.ftl])
     */
    public List<JPAField> getShowFields() {
        return fields.stream().filter(field -> field.getShow()).collect(Collectors.toList());
    }

    public Set<JPAField> getFields() {
        return fields;
    }

    public void setFields(Set<JPAField> fields) {
        this.fields = fields;
    }

    public boolean getTableNameExsit(){
        return !StringUtils.isBlank(tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUnderCrossName(){
        return StringHelper.toUnderscoreName(this.getName());
    }

    /**
     * 获取需要导入的类
     * @return
     */
    public Set<String> getImportClasses() {
        JavaImport javaImports = new JavaImport();
        for (JPAField field : fields) {
            field.getAnnotations().forEach(annotation ->  javaImports.addImport(annotation.getAnntationType().getName()));
            javaImports.addImport(field.getPackageName());
        }
        return javaImports.getImports();
    }
}
