2.用法：
    JPAEntity entity = new JPAEntity();
    entity.setName("Test");
    entity.setRemark("测试");
    JPAField field = new JPAField();
    field.setName("name");
    field.setRemark("姓名");
    field.setJavaType(String.class);
    field.setSearch(true);
    entity.getFields().add(field);
    field = new  JPAField();
    field.setRemark("地址");
    field.setName("addr");
    field.setJavaType(String.class);
    field.setSearch(true);
    entity.getFields().add(field);
    GeneratorFacade generatorFacade = new GeneratorFacade();
    generatorFacade.getGenerator().addTemplateRootDir
            ("classpath:template-entity");
    generatorFacade.generateByEntity(entity);
   //显示输出文件夹
   generatorFacade.showOutPath();
2.配置文件：拷贝jar包的default-generator.xml到项目资源路径下面重命名为generator.xml(可选)
参考配置文件进行修改
