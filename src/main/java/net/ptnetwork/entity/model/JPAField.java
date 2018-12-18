package net.ptnetwork.entity.model;

import cn.org.rapid_framework.generator.util.typemapping.JavaPrimitiveTypeMapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Description 属性
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
public class JPAField extends JPABaseBean {

    public static String PACKAGE_JAVA_LANG = "java.lang";

    private JPAEntity jpaEntity;
    private Class javaType;
    private Set<JPAFieldAnnotation> annotations = new HashSet<>();
    private boolean show = true;
    private boolean search;
    private boolean required = true;

    public Set<JPAFieldAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<JPAFieldAnnotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * 获取属性的类型（eg:String ,HashSet等）
     *
     * @return
     */
    public String getFieldJavaType() {
        return JavaPrimitiveTypeMapping.getWrapperType(javaType.getSimpleName());
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public boolean getShow() {
        return show;
    }

    /**
     * 是否显示(页面)
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean getSearch() {
        return search;
    }

    /**
     * 是否搜索条件(页面)
     */
    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean getRequired() {
        return required;
    }


    /**
     * 是否必填(页面)
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 获取完整的包名（eg:java.lang.String）
     *
     * @return
     */
    public String getPackageName() {
        return JavaPrimitiveTypeMapping.getWrapperClass(javaType).getName();
    }

    public JPAEntity getJpaEntity() {
        return jpaEntity;
    }

    public void setJpaEntity(JPAEntity jpaEntity) {
        this.jpaEntity = jpaEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPAField)) return false;
        JPAField field = (JPAField) o;
        return Objects.equals(this.getName(), field.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
