/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class KpiDefinitionLoader
{


    private static final String        SCRIPTS = "scripts/";
    private final String               resourceName;
    private final IGroovyEngineService service;



    public KpiDefinitionLoader(final IGroovyEngineService _service, final String _resourceName) {


        service = _service;
        resourceName = _resourceName;


    }


    public KpiDefinition load() {


        final Kpi kpi = new Kpi();
        kpi.setName(resourceName);
        kpi.setEsperRequest(new GroovyScriptLoader(SCRIPTS + resourceName).load());
        return service.parseQuery(kpi);
    }

}
