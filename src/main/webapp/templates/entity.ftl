package ${package};

import com.sgai.core.base.entity.AbstractEntity;
import java.util.Date;

public class ${class} extends AbstractEntity {

    <#list properlist as prop>
    private ${prop.fieldType} ${prop.fieldName};

    </#list>

    <#list properlist as prop>
    public ${prop.fieldType} get${prop.fieldNameUpper}() {
        return ${prop.fieldName};
    }

    public void set${prop.fieldNameUpper}(${prop.fieldType} ${prop.fieldName}) {
        this.${prop.fieldName} = ${prop.fieldName};
    }

    </#list>
}
