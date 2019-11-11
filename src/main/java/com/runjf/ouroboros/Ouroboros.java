package com.runjf.ouroboros;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.toolkit.PackageHelper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 代码生成
 * <p></p>
 * 参考 https://github.com/baomidou/mybatis-plus-samples/blob/master/mybatis-plus-sample-generator/src/main/java/com/baomidou/mybatisplus/samples/generator/MysqlGenerator.java
 *
 * @author rjf
 * @since 2019-08-27
 */
public class Ouroboros {

    public void generate(CodeInfo codeInfo) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(codeInfo.getDatabaseUrl());
        dataSourceProperties.setUsername(codeInfo.getDatabaseUsername());
        dataSourceProperties.setPassword(codeInfo.getDatabasePassword());

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setEntityName("%sDO");
//        String projectPath = CodeGenerator.class.getResource("/").getPath() + "../..";
        String projectPath = codeInfo.getDir();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(codeInfo.isFileOverride());
        gc.setAuthor(codeInfo.getAuthor());
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setDateType(codeInfo.getDateType());
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceProperties.getUrl());
        dsc.setDriverName(dataSourceProperties.determineDriverClassName());
        dsc.setUsername(dataSourceProperties.getUsername());
        dsc.setPassword(dataSourceProperties.getPassword());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setController("web");
        pc.setModuleName(codeInfo.getModuleName());
        pc.setParent(codeInfo.getBasePackage());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }

            @Override
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                objectMap.put("isJPA", codeInfo.isJpa());
                Object config = objectMap.get("config");
                if (config instanceof ConfigBuilder) {
                    Map<String, String> packageInfo = ((ConfigBuilder) config).getPackageInfo();
                    packageInfo.put("VO", pc.getParent() + ".vo");
                    packageInfo.put("Param", pc.getParent() + ".vo.param");
                    packageInfo.put("DTO", pc.getParent() + ".dto");
                    packageInfo.put("Repository", pc.getParent() + ".repository");
                }
                Object table = objectMap.get("table");
                if (table instanceof TableInfo) {
                    TableInfo tableInfo = (TableInfo) table;
                    //noinspection unchecked
                    Map<String, String> pkg = (Map<String, String>) objectMap.get("package");
                    String entityPath = getEntityName(tableInfo.getEntityPath());
                    if (entityPath != null && entityPath.startsWith(pc.getModuleName())) {
                        entityPath = entityPath.substring(pc.getModuleName().length());
                        entityPath = entityPath.substring(0, 1).toLowerCase() + entityPath.substring(1);
                    }
                    objectMap.put("tableEntityPath", entityPath);
                    String entityName = getEntityName(tableInfo.getEntityName());
                    objectMap.put("tableVoName", entityName + "VO");
                    objectMap.put("tableDTOName", entityName + "DTO");
                    objectMap.put("tableName", entityName);
                    objectMap.put("moduleTitle", codeInfo.getModuleTitle());

                    // 设置表定制字段类型
                    List<TableField> fields = tableInfo.getFields();
                    Map<String, Map<String, String>> tableColumnTypes = codeInfo.getTableColumnTypes();
                    if (tableColumnTypes != null) {
                        Map<String, String> columnTypes = tableColumnTypes.get(tableInfo.getName());
                        if (columnTypes != null) {
                            fields.stream()
                                    .filter(f -> columnTypes.containsKey(f.getName()))
                                    .forEach(f -> {
                                        String className = columnTypes.get(f.getName());
                                        String type = getClassName(className);
                                        f.setColumnType(new IColumnType() {
                                            @Override
                                            public String getType() {
                                                return type;
                                            }
                                            @Override
                                            public String getPkg() {
                                                return className;
                                            }
                                        });
                                        tableInfo.getImportPackages().add(className);
                                    });
                        }
                    }

                    // 当存在转换类型时需要导入类
                    long count = fields.stream().filter(TableField::isConvert).count();
                    if (count > 0) {
                        tableInfo.getImportPackages().add("com.baomidou.mybatisplus.annotation.TableField");
                    }

                    // 添加vo包路径
                    Set<String> importPackages = tableInfo.getImportPackages();
                    if (codeInfo.isJpa()) {
                        importPackages.remove("com.baomidou.mybatisplus.annotation.TableName");
                    }
                    Set<String> voImportPackages = importPackages.stream()
                            .filter(s -> !s.contains("com.baomidou.mybatisplus.annotation") && !s.contains(".BaseEntity"))
                            .collect(Collectors.toSet());
                    objectMap.put("voImportPackages", voImportPackages);

                    // 定制实体父级
                    String superEntity = codeInfo.getCustomBaseEntity().get(tableInfo.getName());
                    if (superEntity != null && !superEntity.trim().isEmpty()) {
                        Set<String> superColumns = codeInfo.getCustomBaseEntityColumns().get(superEntity);
                        if (superColumns != null) {
                            String superEntityName = getClassName(superEntity);
                            // 同一包下无需import
                            if (!superEntity.equals(pkg.get("Entity") + "." + superEntityName)) {
                                tableInfo.getImportPackages().add(superEntity);
                            }
                            tableInfo.getImportPackages().remove(codeInfo.getBaseEntity());
                            objectMap.put("superEntityClass", superEntityName);
                            List<TableField> commonFields = tableInfo.getFields().stream()
                                    .filter(f -> superColumns.contains(f.getName()))
                                    .collect(Collectors.toList());
                            tableInfo.getCommonFields().addAll(commonFields);
                            tableInfo.getFields().removeAll(commonFields);
                            // 重新生成字段
                            tableInfo.setFieldNames(null);
                            tableInfo.getFieldNames();
                        }
                    }

                    // 设置VO
                    String baseVoClass = codeInfo.getBaseVoClass();
                    String customBaseVoClass = codeInfo.getCustomBaseVoClass().get(tableInfo.getName());
                    if (customBaseVoClass != null && !customBaseVoClass.trim().isEmpty()) {
                        baseVoClass = customBaseVoClass;
                    }
                    String baseVoClassName = getClassName(baseVoClass);
                    objectMap.put("superVoClass", baseVoClassName);
                    // 同一包下无需import
                    if (!baseVoClass.equals(pkg.get("VO") + "." + baseVoClassName)) {
                        voImportPackages.add(baseVoClass);
                    }
                }
                return objectMap;
            }
        };
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                File file = new File(filePath);
                boolean exist = file.exists();
                if (!exist) {
                    PackageHelper.mkDir(file.getParentFile());
                }
                // 仅在文件存在且全局不覆盖文件情况下，正则判断文件是否要覆盖
                if (exist && !configBuilder.getGlobalConfig().isFileOverride()
                        && codeInfo.getFileOverridePattern() != null) {
                    return codeInfo.getFileOverridePattern().matcher(filePath).matches();
                }
                return !exist || configBuilder.getGlobalConfig().isFileOverride();
            }
        });
        List<FileOutConfig> focList = new ArrayList<>();
        if (codeInfo.isJpa()) {
            focList.add(new FileOutConfig("/templates/jpa/repository.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
                            + "/repository/" + getEntityName(tableInfo.getEntityName()) + "Repository" + StringPool.DOT_JAVA;
                }
            });
        } else {
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                            + "/" + getEntityName(tableInfo.getEntityName()) + "Mapper" + StringPool.DOT_XML;
                }
            });
        }
        focList.add(new FileOutConfig("/templates/vo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
                        + "/vo/" + getEntityName(tableInfo.getEntityName()) + "VO" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/paramCreate.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
                        + "/vo/param/" + getEntityName(tableInfo.getEntityName()) + "CreateParam" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/paramQuery.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
                        + "/vo/param/" + getEntityName(tableInfo.getEntityName()) + "QueryParam" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/paramUpdate.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
                        + "/vo/param/" + getEntityName(tableInfo.getEntityName()) + "UpdateParam" + StringPool.DOT_JAVA;
            }
        });
//        focList.add(new FileOutConfig("/templates/dto.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return projectPath + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/")
//                        + "/dto/" + getEntityName(tableInfo.getEntityName()) + "DTO" + StringPool.DOT_JAVA;
//            }
//        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        if (codeInfo.isJpa()) {
            templateConfig
                    .setMapper(null)
                    .setEntity(templateConfig.getEntity(false).replace("/templates/", "/templates/jpa/"))
                    .setServiceImpl(templateConfig.getServiceImpl().replace("/templates/", "/templates/jpa/"));
        }
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperMapperClass(codeInfo.getBaseMapper());
        strategy.setSuperEntityClass(codeInfo.getBaseEntity());
        strategy.setSuperServiceClass(codeInfo.getBaseService());
        strategy.setSuperServiceImplClass(codeInfo.getBaseServiceImpl());
        strategy.setSuperControllerClass(codeInfo.getBaseController());
        strategy.setEntityLombokModel(true);
        strategy.setInclude(codeInfo.getTables());
        strategy.setSuperEntityColumns(codeInfo.getBaseEntityColumns());
        strategy.setLogicDeleteFieldName(codeInfo.getLogicDeleteFieldName());
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setRestControllerStyle(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static String getClassName(String clazz) {
        return clazz.replaceAll(".*\\.(\\w+)$", "$1");
    }

    private static String getEntityName(String entityName) {
        if (entityName != null && entityName.endsWith("DO")) {
            entityName = entityName.substring(0, entityName.length() - 2);
        }
        return entityName;
    }

}
