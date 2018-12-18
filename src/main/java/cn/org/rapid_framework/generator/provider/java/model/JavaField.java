package cn.org.rapid_framework.generator.provider.java.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cn.org.rapid_framework.generator.util.typemapping.ActionScriptDataTypesUtils;
import org.apache.commons.lang3.StringUtils;

public class JavaField {
	private Field field;
	private JavaClass clazz; //与field相关联的class
	
	public JavaField(Field field, JavaClass clazz) {
		super();
		this.field = field;
		this.clazz = clazz;
	}

	public String getFieldName() {
		String name = field.getName();
		if (StringUtils.startsWith(name,"$cglib")){
			name = StringUtils.substringAfterLast(name,"_");
		}
		return name;
	}
	
	public JavaClass getType() {
		return new JavaClass(field.getType());
	}

	public boolean isAccessible() {
        return field.isAccessible();
    }

    public boolean isEnumConstant() {
        return field.isEnumConstant();
    }

    public String toGenericString() {
        return field.toGenericString();
    }

    public JavaClass getClazz() {
		return clazz;
	}

	public String getJavaType() {
		String type = field.getType().getName();
		if (type.contains("java.lang")){
			type = type.substring(type.lastIndexOf(".")+1);
		}
		return type;
	}

	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());
	}

	public Annotation[] getAnnotations() {
		return field.getAnnotations();
	}

	public boolean getIsDateTimeField() {
		return  getJavaType().equalsIgnoreCase("java.util.Date")
				|| getJavaType().equalsIgnoreCase("java.sql.Date")
				|| getJavaType().equalsIgnoreCase("java.sql.Timestamp")
				|| getJavaType().equalsIgnoreCase("java.sql.Time");
	}
	
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaField other = (JavaField) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        return true;
    }

    public String toString() {
		return "JavaClass:"+clazz+" JavaField:"+getFieldName();
	}
}
