/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.Flag;



/**
 * This class defines a kpi based on counting the number of bugs answering to a collection of criterias.
 * 
 * @author sleroy
 */
public class BZBugCountKPI implements IDynamicDataQuery
{
    
    
    private final Set<Flag>           flags      = new HashSet<Flag>();
    
    private final Map<String, Object> parameters = new HashMap<String, Object>();
    
    
    
    /**
     * 
     */
    public BZBugCountKPI(final Bug _bug) {
    
    
        super();
        
    }
    
    
    /**
     * Adds a flag
     * 
     * @param _flag
     *            the flag to add
     */
    public void addFlag(final Flag _flag) {
    
    
        flags.add(_flag);
    }
    
    
    /**
     * Returns the resolution of this {@link Bug} if it is closed, or null if it is still open.
     * 
     * @return A {@code String} representing the resolution of a {@link Bug}.
     * @see {@link @link com.j2bugzilla.rpc.GetLegalValues GetLegalValues} to retrieve a list of the defined resolutions for a specific
     *      installation.
     */
    public void getResolution(final String _resolution) {
    
    
        setParameter("resolution", _resolution);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.cep.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public ICEPResult getResult() {
    
    
        return compute(null, null);
    }
    
    
    /**
     * Returns the operating system this bug was discovered to affect.
     * 
     * @return A {@code String} representing the name of the affected operating system.
     */
    public void setOperatingSystem(final String _internalState) {
    
    
        setParameter("op_sys", _internalState);
        
    }
    
    
    /**
     * Set a new parameter
     * 
     * @param _key
     *            the key
     * @param _value
     *            the value
     */
    public void setParameter(final String _key, final Object _value) {
    
    
        parameters.put(_key, _value);
        
    }
    
    
    /**
     * Returns the hardware platform this bug was discovered to affect. Since this field can be edited between installations, you may wish
     * to {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @return A {@code String} representing the name of the affected platform.
     */
    public void setPlatform(final String _platform) {
    
    
        setParameter("platform", _platform);
    }
    
    
    /**
     * Returns how highly this bug is ranked. Since this field can be edited between installations, you may wish to
     * {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @param _priority
     * @return a {@code String} describing the relative importance of this bug
     */
    public void setPriority(final String _priority) {
    
    
        setParameter("priority", _priority);
    }
    
    
    /**
     * Returns the product this {@link Bug} belongs to.
     * 
     * @return the Product category this {@link Bug} is filed under.
     */
    public void setProduct(final String _product) {
    
    
        setParameter("product", _product);
    }
    
    
    /**
     * Returns How severe the bug is, or whether it's an enhancement. Since this field can be edited between installations, you may wish to
     * {@link com.j2bugzilla.rpc.GetLegalValues check its legal values}.
     * 
     * @param _severity
     * @return a {@code String} describing the relative severity of this bug
     */
    public void setSeverity(final String _severity) {
    
    
        setParameter("severity", _severity);
    }
    
    
    /**
     * Returns the status of this {@link Bug} indicating whether it is open or closed.
     * 
     * @return A {@code String} representing the status of a {@link Bug}.
     */
    public void setStatus(final String _status) {
    
    
        setParameter("status", _status);
    }
    
    
    /**
     * Returns the one-line summary included with the original bug report.
     * 
     * @return A {@code String} representing the summary entered for this {@link Bug}.
     */
    public void setSummary(final String _summary) {
    
    
        setParameter("summary", _summary);
    }
    
    
    /**
     * Returns the version number of the product this {@link Bug} is associated with.
     * 
     * @return the version associated with this {@link Bug}
     */
    public void setVersion(final String _version) {
    
    
        setParameter("version", _version);
    }
}
