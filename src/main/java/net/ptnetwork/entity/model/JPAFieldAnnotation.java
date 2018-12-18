package net.ptnetwork.entity.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 属性对应的注解
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
public class JPAFieldAnnotation extends JPABaseBean{

    private Class anntationType;

    private List<Map<String, Object>> annotaionParams = new LinkedList<>();

    private JPAField jpaField;

    public Class getAnntationType() {
        return anntationType;
    }

    public void setAnntationType(Class anntationType) {
        this.anntationType = anntationType;
    }

    public List<Map<String, Object>> getAnnotaionParams() {
        return annotaionParams;
    }

    public void setAnnotaionParams(List<Map<String, Object>> annotaionParams) {
        this.annotaionParams = annotaionParams;
    }

    public JPAField getJpaField() {
        return jpaField;
    }

    public void setJpaField(JPAField jpaField) {
        this.jpaField = jpaField;
    }

    public String getSimpleName(){
        return anntationType.getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JPAFieldAnnotation)) return false;
        JPAFieldAnnotation annotation = (JPAFieldAnnotation) o;
        return Objects.equals(this.anntationType.getName(), annotation.anntationType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.anntationType.getName());
    }
}
