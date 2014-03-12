/**
 * 
 */

package org.komea.product.cep;



import org.komea.product.cep.api.ICEPConfiguration;
import org.komea.product.cep.api.RunningMode;



/**
 * This class defines the CEP Configuration
 * 
 * @author sleroy
 */
public class CEPConfiguration implements ICEPConfiguration
{
    
    
    private RunningMode mode                 = RunningMode.AGILE;
    private int         numberQueryListeners = 1;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPConfiguration#getMode()
     */
    @Override
    public RunningMode getMode() {
    
    
        return mode;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPConfiguration#getNumberQueryListeners()
     */
    @Override
    public int getNumberQueryListeners() {
    
    
        return numberQueryListeners;
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
