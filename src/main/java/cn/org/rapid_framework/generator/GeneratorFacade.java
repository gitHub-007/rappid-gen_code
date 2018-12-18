package cn.org.rapid_framework.generator;

import cn.org.rapid_framework.generator.Generator.GeneratorModel;
import cn.org.rapid_framework.generator.provider.java.model.JavaClass;
import cn.org.rapid_framework.generator.util.*;
import net.ptnetwork.entity.model.JPAEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static cn.org.rapid_framework.generator.GeneratorConstants.GENERATOR_TOOLS_CLASS;

/**
 * 代码生成器的主要入口类,包装相关方法供外部生成代码使用 使用GeneratorFacade之前，需要设置Generator的相关属性
 *
 * @author badqiu
 */
//modyfied by noah at 2018-6-15
@SuppressWarnings("all")
public class GeneratorFacade {
    private static Logger logger = LoggerFactory.getLogger(GeneratorFacade.class);
    private Generator generator;
    private  GeneratorProperties generatorProperties;

    private ExecutorService excutorService = Executors.newFixedThreadPool(20);

    public GeneratorFacade(Map propertiesMap) {
        this.generatorProperties = new GeneratorProperties();
        generatorProperties.reload(propertiesMap);
        this.generator = new Generator(generatorProperties);
        if (StringHelper.isNotBlank(generatorProperties.getProperty("outRoot"))) {
            generator.setOutRootDir(generatorProperties.getProperty("outRoot"));
        }
    }

    public void deleteOutRootDir() throws IOException {
        generator.deleteOutRootDir();
    }

    /**
     * 自定义变量，生成文件,文件路径与模板引用的变量相同
     *
     * @throws Exception
     */
    public void generateByMap(Map... maps) throws Exception {
        for (Map map : maps) {
            new ProcessUtils().processByMap(map, false);
        }
    }

    /**
     * 自定义变量，删除生成的文件,文件路径与模板引用的变量相同
     *
     * @throws Exception
     */
    public void deleteByMap(Map... maps) throws Exception {
        for (Map map : maps) {
            new ProcessUtils().processByMap(map, true);
        }
    }

    /**
     * 自定义变量，生成文件,可以自定义文件路径与模板引用的变量
     *
     * @throws Exception
     */
    public void generateBy(GeneratorModel... models) throws Exception {
        for (GeneratorModel model : models) {
            new ProcessUtils().processByGeneratorModel(model, false);
        }
    }

    /**
     * 自定义变量，删除生成的文件,可以自定义文件路径与模板引用的变量
     *
     * @throws Exception
     */
    public void deleteBy(GeneratorModel... models) throws Exception {
        for (GeneratorModel model : models) {
            new ProcessUtils().processByGeneratorModel(model, true);
        }
    }

    public boolean isProcessByAllTableFinish() {
        excutorService.shutdown();
        try {
            excutorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        return excutorService.isTerminated();
    }

    /**
     * 根据Class生成文件,模板引用的变量名称为: clazz 实体类为:
     * cn.org.rapid_framework.generator.provider.java.model.JavaClass
     */
    public void generateByClass(Class... clazzes) throws Exception {
        for (Class clazz : clazzes) {
            new ProcessUtils().processByClass(clazz, false);
        }
    }

    /**
     * 根据Entity生成文件,模板引用的变量名称为: entity 实体类为:
     * JPAEntity
     */
    public void generateByEntity(JPAEntity... entities) throws Exception {
        for (JPAEntity entity : entities) {
            new ProcessUtils().processByEntity(entity, false);
        }
    }

    public void showOutPath() {
        String outPath = Paths.get(this.generatorProperties.getRequiredProperty("outRoot")).toAbsolutePath().toString();
        try {
            if (isProcessByAllTableFinish()) {
                if (SystemHelper.isWindowsOS) {
                    Runtime.getRuntime().exec("cmd.exe /c start " + outPath);
                } else {
                    logger.warn("文件输出路径为:{}", outPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Class删除生成的文件,模板引用的变量名称为: clazz 实体类为:
     * cn.org.rapid_framework.generator.provider.java.model.JavaClass
     */
    public void deleteByClass(Class... clazzes) throws Exception {
        for (Class clazz : clazzes) {
            new ProcessUtils().processByClass(clazz, true);
        }
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public class ProcessUtils {

        public void processByGeneratorModel(GeneratorModel model, boolean isDelete) throws Exception,
                FileNotFoundException {
            Generator g = getGenerator();

            GeneratorModel targetModel = new GeneratorModelUtils().newDefaultGeneratorModel();
            targetModel.filePathModel.putAll(model.filePathModel);
            targetModel.templateModel.putAll(model.templateModel);
            processByGeneratorModel(isDelete, g, targetModel);
        }

        public void processByEntity(JPAEntity entity, boolean isDelete) throws Exception, FileNotFoundException {
            Generator g = getGenerator();
            GeneratorModel m = new GeneratorModelUtils().newGeneratorModel("entity", entity);
            PrintUtils.printBeginProcess("entity:" + entity.getName(), isDelete);
            processByGeneratorModel(isDelete, g, m);
        }

        public void processByMap(Map params, boolean isDelete) throws Exception, FileNotFoundException {
            Generator g = getGenerator();
            GeneratorModel m = new GeneratorModelUtils().newFromMap(params);
            processByGeneratorModel(isDelete, g, m);
        }

        public void processByClass(Class clazz, boolean isDelete) throws Exception, FileNotFoundException {
            Generator g = getGenerator();
            GeneratorModel m = new GeneratorModelUtils().newGeneratorModel("clazz", new JavaClass(clazz));
            PrintUtils.printBeginProcess("JavaClass:" + clazz.getSimpleName(), isDelete);
            processByGeneratorModel(isDelete, g, m);
        }

        private void processByGeneratorModel(boolean isDelete, Generator g, GeneratorModel m) throws Exception,
                FileNotFoundException {
            try {
                if (isDelete) g.deleteBy(m.templateModel, m.filePathModel);
                else g.generateBy(m.templateModel, m.filePathModel);
            } catch (GeneratorException ge) {
                PrintUtils.printExceptionsSumary(ge.getMessage(), getGenerator().getOutRootDir(), ge.getExceptions());
                throw ge;
            }
        }

    }

    public  class GeneratorModelUtils {

        public  GeneratorModel newGeneratorModel(String key, Object valueObject) {
            GeneratorModel gm = newDefaultGeneratorModel();
            gm.templateModel.put(key, valueObject);
            gm.filePathModel.putAll(BeanHelper.describe(valueObject));
            return gm;
        }

        public  GeneratorModel newFromMap(Map params) {
            GeneratorModel gm = newDefaultGeneratorModel();
            gm.templateModel.putAll(params);
            gm.filePathModel.putAll(params);
            return gm;
        }

        public  GeneratorModel newDefaultGeneratorModel() {
            Map templateModel = new HashMap();
            templateModel.putAll(getShareVars());

            Map filePathModel = new HashMap();
            filePathModel.putAll(getShareVars());
            return new GeneratorModel(templateModel, filePathModel);
        }

        public  Map getShareVars() {
            Map templateModel = new HashMap();
            templateModel.putAll(System.getProperties());
            templateModel.putAll(GeneratorFacade.this.generatorProperties.getProperties());
            templateModel.put("env", System.getenv());
            templateModel.put("now", new Date());
//            templateModel.put(GeneratorConstants.DATABASE_TYPE.code, GeneratorProperties.getDatabaseType
//                    (GeneratorConstants.DATABASE_TYPE.code));
            templateModel.putAll(GeneratorContext.getContext());
            templateModel.putAll(getToolsMap());
            return templateModel;
        }

        /**
         * 得到模板可以引用的工具类
         */
        private  Map getToolsMap() {
            Map toolsMap = new HashMap();
//            String[] tools = GeneratorProperties.getStringArray(GENERATOR_TOOLS_CLASS);
            String[] tools = GeneratorFacade.this.generatorProperties.getStringArray(GENERATOR_TOOLS_CLASS);
            for (String className : tools) {
                try {
                    Object instance = ClassHelper.newInstance(className);
                    toolsMap.put(Class.forName(className).getSimpleName(), instance);
                    logger.debug("put tools class:{} with key:{}", className, Class.forName(className).getSimpleName());
                } catch (Exception e) {
                    logger.error("cannot load tools by className:{} cause:", className, e);
                }
            }
            return toolsMap;
        }

    }

    private static class PrintUtils {

        private static void printExceptionsSumary(String msg, String outRoot, List<Exception> exceptions) throws
                FileNotFoundException {
            File errorFile = new File(outRoot, "generator_error.log");
            if (exceptions != null && exceptions.size() > 0) {
                System.err.println("[Generate Error Summary] : " + msg);
                errorFile.getParentFile().mkdirs();
                PrintStream output = new PrintStream(new FileOutputStream(errorFile));
                for (int i = 0; i < exceptions.size(); i++) {
                    Exception e = exceptions.get(i);
                    System.err.println("[GENERATE ERROR]:" + e);
                    if (i == 0) e.printStackTrace();
                    e.printStackTrace(output);
                }
                output.close();
                System.err.println("***************************************************************");
                System.err.println("* " + "* 输出目录已经生成generator_error.log用于查看错误 ");
                System.err.println("***************************************************************");
            }
        }

        private static void printBeginProcess(String displayText, boolean isDatele) {
            logger.info("***************************************************************");
            logger.info("* BEGIN {}{}", (isDatele ? " delete by " : " generate by "), displayText);
            logger.info("***************************************************************");
        }

    }

}
