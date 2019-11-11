package ${package.Service};

import ${package.VO}.${tableVoName};
import ${superServiceClassPackage};

/**
 * <p>
 * ${moduleTitle} ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${tableVoName}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${tableVoName}> {

}
</#if>
