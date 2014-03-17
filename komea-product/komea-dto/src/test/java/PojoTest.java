import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class PojoTest
{
    
    
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int    EXPECTED_CLASS_COUNT = 1;
    
    // The package to test
    private static final String POJO_PACKAGE         = "org.komea.product.database";
    
    private List<PojoClass>     pojoClasses;
    private PojoValidator       pojoValidator;
    
    
    
    @Test
    public void ensureExpectedPojoCount() {
    
    
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }
    
    
    @Before
    public void setup() {
    
    
        pojoClasses = PojoClassFactory.getPojo(POJO_PACKAGE, new FilterPackageInfo());
        
        pojoValidator = new PojoValidator();
        
        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicFieldsRule());
        pojoValidator.addRule(new NoPrimitivesRule());
        pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new NoFieldShadowingRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new NoNestedClassRule());
        pojoValidator.addRule(new BusinessKeyMustExistRule());
        
        // Create Testers to validate behaviour for POJO_PACKAGE
        pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new BusinessIdentityTester());
    }
    
    
    @Test
    public void testPojoStructureAndBehavior() {
    
    
        for (final PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }
}
