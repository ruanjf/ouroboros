package ${package.DTO};

<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>

/**
 * <p>
 * ${moduleTitle} ${table.comment!} DTO
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
</#if>
public class ${tableDTOName} {

}
