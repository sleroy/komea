/**
 * 
 */

package org.komea.eventory;



import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.RunningMode;



/**
 * This class defines the CEP Configuration
 * 
 * @author sleroy
 */
public class CEPConfiguration implements ICEPConfiguration
{
    
    
    private String      bridgeImplementation;
    private String      cacheImplementation;
    private RunningMode mode                 = RunningMode.AGILE;
    private int         numberQueryListeners = 1;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPConfiguration#getBridgeImplementation()
     */
    @Override
    public String getBridgeImplementation() {
    
    
        return bridgeImplementation;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPConfiguration#getCacheImplementation()
     */
    @Override
    public String getCacheImplementation() {
    
    
        return cacheImplementation;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPConfiguration#getMode()
     */
    @Override
    public RunningMode getMode() {
    
    
        return mode;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPConfiguration#getNumberQueryListeners()
     */
    @Override
    public int getNumberQueryListeners() {
    
    
        return numberQueryListeners;
    }
    
    
    public void setBridgeImplementation(final String _bridgeImplementation) {
    
    
        bridgeImplementation = _bridgeImplementation;
    }
    
    
    public void setCacheImplementation(final String _cacheImplementation) {
    
    
        cacheImplementation = _cacheImplementation;
    }
    
    
    public void setMode(final RunningMode _mode) {
    
    
        mode = _mode;
    }
    
    
    public void setNumberQueryListeners(final int _numberQueryListeners) {
    
    
        numberQueryListeners = _numberQueryListeners;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPConfiguration [mode="
                + mode + ", numberQueryListeners=" + numberQueryListeners + "]";
    }
    
}
