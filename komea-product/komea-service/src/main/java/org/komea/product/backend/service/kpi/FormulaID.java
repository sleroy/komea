/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.apache.commons.lang3.Validate;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;



/**
 * @author sleroy
 */
public class FormulaID
{
    
    
    private static final Logger LOGGER          = LoggerFactory.getLogger(FormulaID.class);
    /**
     *
     */
    private static final String TOCEA_QUERY_KPI = "tocea-query-kpi";
    
    
    
    public static FormulaID of(final Kpi _kpi) {
    
    
        if (_kpi.getEsperRequest() == null || _kpi.getEsperRequest().isEmpty()) {
            throw new IllegalArgumentException(
                    "Illegal kpi provided, the kpi should be loaded with its formula (selectWithBlobs");
        }
        return new FormulaID(_kpi.getEsperRequest());
    }
    
    
    public static FormulaID of(final String _formula) {
    
    
        return new FormulaID(_formula);
    }
    
    
    /**
     * BUilds a formula id with only its id.
     * 
     * @param _id
     * @return
     */
    public static FormulaID ofRawID(final String _id) {
    
    
        return new FormulaID(_id, _id);
    }
    
    
    
    private final String formula;
    
    private final String id;
    
    
    
    public FormulaID(final String _formula) {
    
    
        formula = _formula;
        Validate.notNull(_formula);
        
        id = new Md5PasswordEncoder().encodePassword(formula, TOCEA_QUERY_KPI);
        
    }
    
    
    private FormulaID(final String _string, final String _id) {
    
    
        formula = _string;
        id = _id;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormulaID other = (FormulaID) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * @deprecated Should not be used, possible confusion with getId) method.
     * @return
     */
    @Deprecated
    public String getFormula() {
    
    
        return formula;
    }
    
    
    /**
     * Returns the value of the field id.
     * 
     * @return the id
     */
    public String getId() {
    
    
        return id;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }
    
    
    /**
     * @return
     */
    public boolean isValid() {
    
    
        return !id.isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "FormulaID [formula=" + formula + ", id=" + id + "]";
    }
    
}
