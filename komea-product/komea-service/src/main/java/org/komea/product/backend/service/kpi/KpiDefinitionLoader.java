/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.groovy.GroovyEngineService;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class KpiDefinitionLoader
{


    private static final String SCRIPTS = "scripts/";



    /**
     * Loads a kpi.
     *
     * @param _resourceName
     *            the resource name
     * @return the kpi definition.
     */
    public static KpiDefinition load(final String _resourceName) {


        return new KpiDefinitionLoader(_resourceName).load();

    }



    private final String               resourceName;


    private final IGroovyEngineService service;



    public KpiDefinitionLoader(final IGroovyEngineService _service, final String _resourceName) {


        service = _service;
        resourceName = _resourceName;


    }


    public KpiDefinitionLoader(final String _resourceName) {


        service = GroovyEngineService.initStandalone();
        resourceName = _resourceName;


    }


    public KpiDefinition load() {


        final Kpi kpi = new Kpi();
        kpi.setName(resourceName);
        kpi.setEsperRequest(new GroovyScriptLoader(SCRIPTS + resourceName).load());
        return service.parseQuery(kpi);
    }

}
