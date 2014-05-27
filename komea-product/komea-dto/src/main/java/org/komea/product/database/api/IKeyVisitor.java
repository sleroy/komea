/**
 * 
 */

package org.komea.product.database.api;



import org.komea.product.database.model.ActivityFilter;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.Tag;



/**
 * @author sleroy
 */
public interface IKeyVisitor
{
    
    
    /**
     * @param _activityFilter
     */
    public void visit(ActivityFilter _activityFilter);
    
    
    /**
     * @param _customer
     */
    public void visit(Customer _customer);
    
    
    /**
     * @param _eventType
     */
    public void visit(EventType _eventType);
    
    
    /**
     * @param _kpi
     */
    public void visit(Kpi _kpi);
    
    
    /**
     * @param _kpiAlertType
     */
    public void visit(KpiAlertType _kpiAlertType);
    
    
    public void visit(KpiGoal _kpiGoal);
    
    
    /**
     * @param _link
     */
    public void visit(Link _link);
    
    
    /**
     * @param _measure
     */
    public void visit(Measure _measure);
    
    
    public void visit(Person _person);
    
    
    /**
     * @param _personGroup
     */
    public void visit(PersonGroup _personGroup);
    
    
    /**
     * @param _personRole
     */
    public void visit(PersonRole _personRole);
    
    
    /**
     * @param _project
     */
    public void visit(Project _project);
    
    
    /**
     * @param _provider
     */
    public void visit(Provider _provider);
    
    
    /**
     * @param _setting
     */
    public void visit(Setting _setting);
    
    
    /**
     * @param _tag
     */
    public void visit(Tag _tag);
}
