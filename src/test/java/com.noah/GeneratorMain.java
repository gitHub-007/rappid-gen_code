package com.noah;

import cn.org.rapid_framework.generator.GeneratorFacade;
import net.ptnetwork.entity.model.JPAEntity;
import net.ptnetwork.entity.model.JPAField;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 */

public class GeneratorMain {
    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     *
     * @throws Exception
     */
    @Test
    public void main() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("gg_isOverride", true);
        map.put("basepackage", "net");
        map.put("subpackage", "ptnetwork");
        map.put("copyright", "ptnetwork");
        map.put("namespace", "gencode");
        map.put("author", "noah");
        map.put("outRoot", "./auto_generator_code-output");
        JPAEntity entity = new JPAEntity();
        entity.setTableName("t_test");
        entity.setName("Test");
        entity.setRemark("测试");
        JPAField field = new JPAField();
        field.setName("name");
        field.setRemark("姓名");
        field.setJavaType(String.class);
        field.setSearch(true);
        entity.getFields().add(field);
        field = new JPAField();
        field.setRemark("地址");
        field.setName("addr");
        field.setJavaType(HashSet.class);
        field.setSearch(true);
        entity.getFields().add(field);
        field = new JPAField();
        field.setRemark("地址");
        field.setName("addr");
        field.setJavaType(HashSet.class);
        field.setSearch(true);
        entity.getFields().add(field);
        GeneratorFacade generatorFacade = new GeneratorFacade(map);
        generatorFacade.getGenerator().addTemplateRootDir
                ("F:\\java_workspace\\rapid-generator-4.0.6-sources\\template\\template-entity");
        generatorFacade.generateByEntity(entity);
        //显示输出文件夹
        generatorFacade.showOutPath();

    }
}
