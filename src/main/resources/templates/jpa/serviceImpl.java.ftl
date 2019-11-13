package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Repository}.${tableName}Repository;
import ${package.Service}.${table.serviceName};
import ${package.VO}.${tableVoName};
import ${superServiceImplClassPackage};
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${moduleTitle} ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${tableName}Repository, ${entity}, ${tableVoName}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${tableName}Repository, ${entity}, ${tableVoName}>
        implements ${table.serviceName} {

    private final ${tableName}Repository repository;

    public ${table.serviceImplName}(${tableName}Repository repository) {
        super(repository);
        this.repository = repository;
    }

}
</#if>
