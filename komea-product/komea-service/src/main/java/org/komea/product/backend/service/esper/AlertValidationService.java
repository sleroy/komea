
package org.komea.product.backend.service.esper;



import org.komea.product.database.alert.IAlert;
import org.komea.product.database.alert.enums.Criticity;
import org.springframework.stereotype.Service;



@Service
public class AlertValidationService implements IAlertValidationService
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IAlertValidationService#validate(org.komea.product.database.alert.IAlert)
     */
    @Override
    public void validate(final IAlert _alert) {
    
    
        if (_alert.getProvider() == null || _alert.getProvider().isEmpty()) { throw new IllegalArgumentException(
                "invalid provider."); }
        if (_alert.getCategory() == null) { throw new IllegalArgumentException("invalid category."); }
        if (_alert.getType() == null || _alert.getType().isEmpty()) { throw new IllegalArgumentException(
                "invalid type"); }
        if (_alert.getCriticity() == Criticity.NONE) { throw new IllegalArgumentException(
                "invalid criticity"); }
        if (_alert.getDate() == null) { throw new IllegalArgumentException("invalid date"); }
        if (_alert.getMessage() == null) { throw new IllegalArgumentException("invalid message"); }
        // if (_alert.getProject() == null || _alert.getProject().isEmpty()) {
        // throw new IllegalArgumentException("invalid project");
        // }
        if (_alert.getUsers() == null) { throw new IllegalArgumentException("invalid users"); }
    }
    
}
