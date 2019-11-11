package com.runjf.ouroboros;

import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author rjf
 * @since 2019-08-27
 */
public class CodeInfo {
    /**
     * 是否为JPA
     */
    private boolean jpa;
    /**
     * 源码目录
     */
    private String dir;
    /**
     * 数据库地址
     */
    private String databaseUrl;
    /**
     * 数据库用户名
     */
    private String databaseUsername;
    /**
     * 数据库密码
     */
    private String databasePassword;
    /**
     * 模块标识
     */
    private String moduleName;
    /**
     * 模块名称
     */
    private String moduleTitle;
    /**
     * 表
     */
    private String[] tables;
    /**
     * 表字段复杂类型
     */
    private Map<String, Map<String, String>> tableColumnTypes = new HashMap<>();
    /**
     * 包路径
     */
    private String basePackage;
    /**
     * 逻辑删除属性名称
     */
    private String logicDeleteFieldName = "is_deleted";
    /**
     * 乐观锁属性名称
     */
    private String versionFieldName;
    /**
     * 基础实体，默认值：cn.bespinglobal.amg.common.base.BaseEntity
     */
    private String baseEntity = "cn.bespinglobal.amg.common.base.BaseEntity";
    /**
     * 基础实体字段，默认值：<code>new String[]{"id", "create_time", "create_user", "update_time", "update_user"}</code>
     */
    private String[] baseEntityColumns = new String[]{"id", "create_time", "create_user", "update_time", "update_user"};
    /**
     * 定制基础类型
     */
    private Map<String, String> customBaseEntity = new HashMap<>();
    /**
     * 基础类型字段
     */
    private Map<String, Set<String>> customBaseEntityColumns = new HashMap<>();
    /**
     * 基础BaseMapper接口，默认值：com.baomidou.mybatisplus.core.mapper.BaseMapper
     */
    private String baseMapper = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    /**
     * 基础Service接口，默认值：cn.bespinglobal.amg.common.base.IBaseService
     */
    private String baseService = "cn.bespinglobal.amg.common.base.IBaseService";
    /**
     * 基础Service实现，默认值：cn.bespinglobal.amg.common.base.BaseServiceImpl
     */
    private String baseServiceImpl = "cn.bespinglobal.amg.common.base.BaseServiceImpl";
    /**
     * 基础Controller，默认值：cn.bespinglobal.amg.common.base.BaseController
     */
    private String baseController = "cn.bespinglobal.amg.common.base.BaseController";

    /**
     * 基础VO，默认值：cn.bespinglobal.amg.common.base.BaseVO
     */
    private String baseVoClass = "cn.bespinglobal.amg.common.base.BaseVO";

    /**
     * 指定基础VO
     */
    private Map<String, String> customBaseVoClass = new HashMap<>();

    /**
     * 是否覆盖存在文件，实体类始终会被覆盖
     */
    private boolean fileOverride;

    /**
     * 仅在文件存在且全局不覆盖文件情况下，满足正则表达式条件的文件将被覆盖
     */
    private Pattern fileOverridePattern;

    /**
     * 作者，默认值：读取环境变量中的用户
     */
    private String author = System.getProperty("user.name");

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 是否为Spring Boot应用
     */
    private boolean springBoot = true;

    /**
     * 添加表包装对象列
     *
     * @param tableName 表名
     * @param columnName 列名
     * @param className 类名
     * @return 本实例
     */
    public CodeInfo addTableColumnType(String tableName, String columnName, String className) {
        this.tableColumnTypes
                .computeIfAbsent(tableName, t -> new HashMap<>(10))
                .put(columnName, className);
        return this;
    }

    /**
     * 设置需要进行代码生成的表名
     *
     * @param tableNames 表名
     * @return 本实例
     */
    public CodeInfo setTableNames(String... tableNames) {
        this.tables = tableNames;
        return this;
    }

    /**
     * 指定实体的父级
     *
     * @param tableName 表名
     * @param classType 类名
     * @return 本实例
     */
    public CodeInfo addCustomBaseEntity(String tableName, String classType) {
        this.customBaseEntity.put(tableName, classType);
        return this;
    }

    /**
     * 指定实体的父级
     *
     * @param classType 类名
     * @param tableColumns 表列名
     * @return 本实例
     */
    public CodeInfo setCustomBaseEntityColumns(String classType, String... tableColumns) {
        if (tableColumns == null || tableColumns.length == 0) {
            return this;
        }
        this.customBaseEntityColumns.put(classType, new HashSet<>(Arrays.asList(tableColumns)));
        return this;
    }

    /**
     * 指定VO的父级
     *
     * @param tableName 表名
     * @param baseVoClass 类型
     * @return 本实例
     */
    public CodeInfo addCustomBaseVoClass(String tableName, String baseVoClass) {
        this.customBaseVoClass.put(tableName, baseVoClass);
        return this;
    }

    public boolean isJpa() {
        return jpa;
    }

    public CodeInfo setJpa(boolean jpa) {
        this.jpa = jpa;
        return this;
    }

    public String getDir() {
        return dir;
    }

    public CodeInfo setDir(String dir) {
        this.dir = dir;
        return this;
    }

    /**
     * 设置模块
     *
     * @param title 模块名称
     * @param name 模块标识
     * @return
     */
    public CodeInfo setModule(String title, String name) {
        this.moduleName = name;
        this.moduleTitle = title;
        return this;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    /**
     * 数据库地址
     *
     * @param databaseUrl 地址
     */
    public CodeInfo setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        return this;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    /**
     * 数据库用户名
     *
     * @param databaseUsername 用户名
     */
    public CodeInfo setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
        return this;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * 数据库密码
     *
     * @param databasePassword 密码
     */
    public CodeInfo setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public Map<String, Map<String, String>> getTableColumnTypes() {
        return tableColumnTypes;
    }

    public void setTableColumnTypes(Map<String, Map<String, String>> tableColumnTypes) {
        this.tableColumnTypes = tableColumnTypes;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public CodeInfo setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    public String getLogicDeleteFieldName() {
        return logicDeleteFieldName;
    }

    public void setLogicDeleteFieldName(String logicDeleteFieldName) {
        this.logicDeleteFieldName = logicDeleteFieldName;
    }

    public String getVersionFieldName() {
        return versionFieldName;
    }

    public void setVersionFieldName(String versionFieldName) {
        this.versionFieldName = versionFieldName;
    }

    public String getBaseEntity() {
        return baseEntity;
    }

    public CodeInfo setBaseEntity(String baseEntity) {
        this.baseEntity = baseEntity;
        return this;
    }

    public String[] getBaseEntityColumns() {
        return baseEntityColumns;
    }

    public void setBaseEntityColumns(String[] baseEntityColumns) {
        this.baseEntityColumns = baseEntityColumns;
    }

    public Map<String, String> getCustomBaseEntity() {
        return customBaseEntity;
    }

    public void setCustomBaseEntity(Map<String, String> customBaseEntity) {
        this.customBaseEntity = customBaseEntity;
    }

    public Map<String, Set<String>> getCustomBaseEntityColumns() {
        return customBaseEntityColumns;
    }

    public void setCustomBaseEntityColumns(Map<String, Set<String>> customBaseEntityColumns) {
        this.customBaseEntityColumns = customBaseEntityColumns;
    }

    public String getBaseMapper() {
        return baseMapper;
    }

    public CodeInfo setBaseMapper(String baseMapper) {
        this.baseMapper = baseMapper;
        return this;
    }

    public String getBaseService() {
        return baseService;
    }

    public CodeInfo setBaseService(String baseService) {
        this.baseService = baseService;
        return this;
    }

    public String getBaseServiceImpl() {
        return baseServiceImpl;
    }

    public CodeInfo setBaseServiceImpl(String baseServiceImpl) {
        this.baseServiceImpl = baseServiceImpl;
        return this;
    }

    public String getBaseController() {
        return baseController;
    }

    public CodeInfo setBaseController(String baseController) {
        this.baseController = baseController;
        return this;
    }

    public String getBaseVoClass() {
        return baseVoClass;
    }

    public CodeInfo setBaseVoClass(String baseVoClass) {
        this.baseVoClass = baseVoClass;
        return this;
    }

    public Map<String, String> getCustomBaseVoClass() {
        return customBaseVoClass;
    }

    public void setCustomBaseVoClass(Map<String, String> customBaseVoClass) {
        this.customBaseVoClass = customBaseVoClass;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public CodeInfo setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
        return this;
    }

    public Pattern getFileOverridePattern() {
        return fileOverridePattern;
    }

    public CodeInfo setFileOverridePattern(Pattern fileOverridePattern) {
        this.fileOverridePattern = fileOverridePattern;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }
}
