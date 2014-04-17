/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;

import com.j2bugzilla.base.Bug;



/**
 * This class defines a kpi based on counting the number of bugs answering to a collection of criterias.
 * 
 * @author sleroy
 */
public class BZBugCountKPI implements ICEPQueryImplementation
{
    
    
    private final BZBugCountFormula formula;
    
    
    
    /**
     * 
     */
    public BZBugCountKPI(final Bug _bug) {
    
    
        super();
        formula = new BZBugCountFormula();
    }
    
    
    // public void addFlag(final String _name, final String _status) {
    //
    //
    // final Flag flag = new Flag(_name, _status);
    // formula.addFlag(flag);
    // }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return Collections.EMPTY_LIST;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return formula;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.EMPTY_MAP;
    }
    
    
    /**
     * Returns the resolution of this {@link Bug} if it is closed, or null if it is still open.
     * 
     * @return A {@code String} representing the resolution of a {@link Bug}.
     * @see {@link @link com.j2bugzilla.rpc.GetLegalValues GetLegalValues} to retrieve a list of the defined resolutions for a specific
     *      installation.
     */
    public void getResolution(final String _resolution) {
    
    
        formula.setParameter("resolution", _resolution);
    }
    
    
    /**
     * Returns the operating system this bug was discovered to affect.
     * 
     * @return A {@code String} representing the name of the affected operating system.
     */
    public void setOperatingSystem(final String _internalState) {
    
    
        formula.setParameter("op_sys", _internalState);
        
    }
    
    
    public void setParameter(final String _key, final Object _value) {
    
    
        formula.setParameter(_key, _value);
    }
    
    
    /**
     * Returns the hardware platform this bug was discovered to affect. Since this field can be edited between installations, you may wish
     * to {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @return A {@code String} representing the name of the affected platform.
     */
    public void setPlatform(final String _platform) {
    
    
        formula.setParameter("platform", _platform);
    }
    
    
    /**
     * Returns how highly this bug is ranked. Since this field can be edited between installations, you may wish to
     * {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @param _priority
     * @return a {@code String} describing the relative importance of this bug
     */
    public void setPriority(final String _priority) {
    
    
        formula.setParameter("priority", _priority);
    }
    
    
    /**
     * Returns the product this {@link Bug} belongs to.
     * 
     * @return the Product category this {@link Bug} is filed under.
     */
    public void setProduct(final String _product) {
    
    
        formula.setParameter("product", _product);
    }
    
    
    /**
     * Returns How severe the bug is, or whether it's an enhancement. Since this field can be edited between installations, you may wish to
     * {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @param _severity
     * @return a {@code String} describing the relative severity of this bug
     */
    public void setSeverity(final String _severity) {
    
    
        formula.setParameter("severity", _severity);
    }
    
    
    /**
     * Returns the status of this {@link Bug} indicating whether it is open or closed.
     * 
     * @return A {@code String} representing the status of a {@link Bug}.
     */
    public void setStatus(final String _status) {
    
    
        formula.setParameter("status", _status);
    }
    
    
    /**
     * Returns the one-line summary included with the original bug report.
     * 
     * @return A {@code String} representing the summary entered for this {@link Bug}.
     */
    public void setSummary(final String _summary) {
    
    
        formula.setParameter("summary", _summary);
    }
    
    
    /**
     * Returns the version number of the product this {@link Bug} is associated with.
     * 
     * @return the version associated with this {@link Bug}
     */
    public void setVersion(final String _version) {
    
    
        formula.setParameter("version", _version);
    }
    
    
}
