package org.komea.product.database;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;



public class DtoPojoTest
{
    
    
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int    EXPECTED_CLASS_COUNT = 15;
    
    // The package to test
    private static final String POJO_PACKAGE         = "org.komea.product.database.dto";
    
    private List<PojoClass>     pojoClasses;
    private PojoValidator       pojoValidator;
    
    
    
    @Test
    public void ensureExpectedPojoCount() {
    
    
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }
    
    
    @Before
    public void setup() {
    
    
        pojoClasses =
                PojoClassFactory.getPojoClassesRecursively(POJO_PACKAGE, new FilterPackageInfo());
        
        pojoValidator = new PojoValidator();
        
        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicFieldsRule());
        // pojoValidator.addRule(new NoPrimitivesRule());
        // pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new NoFieldShadowingRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new NoNestedClassRule());
        // pojoValidator.addRule(new BusinessKeyMustExistRule());
        
        // Create Testers to validate behaviour for POJO_PACKAGE
        // pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
        // pojoValidator.addTester(new BusinessIdentityTester());
    }
    
    
    @Test
    public void testPojoStructureAndBehavior() {
    
    
        for (final PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }
}
