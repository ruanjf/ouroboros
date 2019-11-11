package ${package.Controller};


<#if superControllerClass??>
import ${package.VO}.${tableVoName};
import ${package.Param}.${tableName}CreateParam;
import ${package.Param}.${tableName}QueryParam;
import ${package.Param}.${tableName}UpdateParam;
</#if>
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${moduleTitle} ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${moduleTitle}-${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/api<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${tableEntityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceName}, ${tableVoName},
        ${tableName}CreateParam, ${tableName}QueryParam, ${tableName}UpdateParam> {
<#else>
public class ${table.controllerName} {
</#if>

    public ${table.controllerName}(${table.serviceName} service) {
        super(service);
    }

}
</#if>
