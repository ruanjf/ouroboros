package com.runjf.ouroboros;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author rjf
 * @since 2019-08-27
 */
@Data
@Accessors(chain = true)
public class CodeInfo {
    /**
     * 项目文件前缀
     */
    private String projectPrefix = "";
    /**
     * 模块文件前缀
     */
    private String modulePrefix = "";
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

}
