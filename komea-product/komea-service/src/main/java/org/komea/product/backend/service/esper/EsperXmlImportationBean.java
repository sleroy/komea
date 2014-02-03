/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.komea.product.backend.api.IEsperEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.EPStatement;



/**
 * This type defines the esper catalog statements.
 * 
 * @author sleroy
 */
@Component
public class EsperXmlImportationBean
{
    
    
    @Autowired
    private IAlertPushService            engine;
    
    @Autowired
    private IEsperEngine          esperEngine;
    
    private final Map<String, EPStatement> statements = new HashMap<String, EPStatement>();
    
    private final Logger                   LOGGER     =
                                                              LoggerFactory
                                                                      .getLogger(EsperXmlImportationBean.class);
    
    
    
    /**
     * New catalog.
     */
    public EsperXmlImportationBean() {
    
    
        super();
    }
    
    
    @PostConstruct
    public void init() throws IOException, JAXBException {
    
    
        importationRulesFromPropertyFile();
        // importationRulesFromXML();
    }
    
    
/**
		 * Check {@link http://esper.codehaus.org/esper-4.10.0/doc/api/index.html }
		 * and {@link http://esper.codehaus.org/esper-4.10.0/doc/api/com/espertech/esper/client/EPAdministrator.html#createEPL(java.lang.String,%20java.lang.String,%20java.lang.Object)
		 * for more information
		 * 
		 * @throws IOException
		 */
    private void importationRulesFromPropertyFile() throws IOException {
    
    
        if (getClass().getResource("/data/statements/catalog.properties") == null) {
            LOGGER.info("No statement available");
            return;
        }
        final Properties prop = new Properties();
        prop.load(getClass().getResourceAsStream("/data/statements/catalog.properties"));
        for (final Entry<Object, Object> entry : prop.entrySet()) {
            LOGGER.info("Loads new Query in Esper ##{}##", entry.getKey());
            final EPStatement createEPL =
                    esperEngine.getEsper().getEPAdministrator()
                            .createEPL((String) entry.getValue(), (String) entry.getKey());
            statements.put((String) entry.getKey(), createEPL);
            
        }
    }
    
    // private void importationRulesFromXML() throws MalformedURLException,
    // IOException, JAXBException {
    //
    // final File file = new File(
    // "src/main/resources/data/statements/eplStatements.xml");
    // if (!file.exists()) {
    // LOGGER.error("File not found {}", file);
    // return;
    // }
    // final Catalog catalog = XmlUtils.unmarshallXML(file, Catalog.class);
    // for (final Statement stmt : catalog.getStatement()) {
    // LOGGER.info("Loads new Query in Esper ##{}##", stmt.getName());
    // if (stmt.getParameters() != null
    // && !stmt.getParameters().getParameter().isEmpty()) {
    // throw new UnsupportedOperationException(
    // "Parameterized statements are not supported");
    // }
    // engine.getEsperEngine().getEPAdministrator()
    // .createEPL(stmt.getQuery(), stmt.getName());
    // }
    //
    // }
}
