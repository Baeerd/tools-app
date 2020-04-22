package ${package};

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgai.core.base.controller.BaseController;
import ${entityClass};

@RestController
@Scope("prototype")
@RequestMapping("/pro/${requestUrl}")
public class ${controllerName} extends BaseController<${entityName}> {

}
