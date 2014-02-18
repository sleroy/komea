
package org.komea.product.wicket.widget.validation;



import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;



public class EmptyStringValidator extends Behavior implements INullAcceptingValidator<String>
{
    
    
    public EmptyStringValidator() {
    
    
        super();
    }
    
    
    @Override
    public void validate(final IValidatable<String> _validatable) {
    
    
        System.out.println("Validation of form : " + _validatable.getValue());
        if (Strings.isEmpty(_validatable.getValue())) {
            System.err.println("FOund error");
            _validatable.error(new ValidationError("This field should not be empty"));
            
            
        }
        
        
    }
    
}
