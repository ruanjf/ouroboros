package ${package.Repository};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * ${moduleTitle} ${table.comment!} Repository 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Repository
public interface ${tableName}Repository extends ${superMapperClass}<${entity}, Integer>,
        JpaSpecificationExecutor<${entity}>, QueryByExampleExecutor<${entity}> {

}
</#if>
