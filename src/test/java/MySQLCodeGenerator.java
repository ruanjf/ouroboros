import com.runjf.code.generator.CodeGenerator;
import com.runjf.code.generator.CodeInfo;

import java.util.regex.Pattern;

/**
 * @author rjf
 * @since 2019/11/7
 */
public class MySQLCodeGenerator {

    public static void main(String[] args) {

        String moduleTitle = "首页模块";
        String module = "home";
        String[] tables = {
//                "borrow_record",
//                "test_list",
                "user_group"
        };

        // 以下内容无需修改
        new CodeGenerator()
                .generate(new CodeInfo()
                        .setJpa(true)
                        .setDatabaseUrl("jdbc:mysql://macnana.rwlb.rds.aliyuncs.com:3306/aims")
                        .setDatabaseUsername("macnana")
                        .setDatabasePassword("1qazxsw@")
                        .setDir(MySQLCodeGenerator.class.getResource("/").getPath() + "../../../AIMSWEBServ")
                        // 覆盖特定规则在文件，覆盖以DO.java结尾的文件
                        .setFileOverridePattern(Pattern.compile(".*(DO)\\.java$"))

                        .setModule(moduleTitle, module)
                        .setTableNames(tables)

                        // 生成代码包路径
                        .setBaseController("com.bjinto.aims.core.base.BaseController")
                        .setBaseService("com.bjinto.aims.core.base.IBaseService")
                        .setBaseServiceImpl("com.bjinto.aims.core.base.BaseServiceImpl")
                        .setBaseMapper("org.springframework.data.repository.PagingAndSortingRepository")
                        .setBaseVoClass("com.bjinto.aims.core.base.BaseVO")
                        .setBaseEntity("com.bjinto.aims.core.base.BaseEntity")
                        .setBasePackage("com.bjinto.aims"));
    }

}
