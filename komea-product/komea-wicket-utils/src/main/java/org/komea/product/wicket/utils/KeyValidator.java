/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.backend.service.generic.IGenericService;

/**
 *
 * @author rgalerme
 */
public class KeyValidator implements IValidator<String> {

    private final IGenericService service;
    private final String fieldName;

    public KeyValidator(IGenericService service, String fieldName) {
        this.service = service;
        this.fieldName = fieldName;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String value = validatable.getValue();
        if (service.exists(value)) {
            ValidationError error = new ValidationError();
            error.setMessage("Value of " + fieldName + " is already used");
            validatable.error(error);
        }
    }

}
