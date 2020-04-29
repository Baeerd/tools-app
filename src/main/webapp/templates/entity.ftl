package ${package};

import com.sgai.core.base.entity.AbstractEntity;
import java.util.Date;
import java.math.BigDecimal;

public class ${class} extends AbstractEntity {

    <#list properlist as prop>
    /**
    * ${prop.fieldRemark}
    */
    private ${prop.fieldType} ${prop.fieldName};

    </#list>

    <#list properlist as prop>
    /**
    * 获取${prop.fieldRemark}
    */
    public ${prop.fieldType} get${prop.fieldNameUpper}() {
        return ${prop.fieldName};
    }

    /**
    * 设置${prop.fieldRemark}
    */
    public void set${prop.fieldNameUpper}(${prop.fieldType} ${prop.fieldName}) {
        this.${prop.fieldName} = ${prop.fieldName};
    }

    </#list>
}
