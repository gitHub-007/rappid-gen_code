package net.ptnetwork.entity;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

import java.io.Serializable;
import java.util.Map;

public class CglibBean implements Serializable {

    private static final long serialVersionUID = -3943974499257164717L;
    /**
     * 实体Object
     */
    private Object object = null;

    private String classPackage;
    private String className;

    private NamingPolicy namingPolicy = new DefaultNamingPolicy(){
        @Override
        public String getClassName(String prefix, String source, Object key, Predicate names) {
            return String.format("%s.%s", classPackage, className);
        }
    };

    /**
     * 属性map
     */
    private BeanMap beanMap = null;

    public CglibBean(String classPackage, String className, Map propertyMap) {
        this.classPackage = classPackage;
        this.className = className;
        this.object = generateBean(propertyMap);
//      this.beanMap = BeanMap.create(this.object);
    }

//    /**
//      * 给bean属性赋值
//      * @param property 属性名
//      * @param value 值
//      */
//    public void setValue(String property, Object value) {
//      beanMap.put(property, value);
//    }
//
//    /**
//      * 通过属性名得到属性值
//      * @param property 属性名
//      * @return 值
//      */
//    public Object getValue(String property) {
//      return beanMap.get(property);
//    }
//

    /**
     * 得到该实体bean对象
     *
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        generator.setNamingPolicy(this.namingPolicy);
        propertyMap.forEach((key, value) -> generator.addProperty((String) key, (Class) value));
        return generator.create();
    }
}
