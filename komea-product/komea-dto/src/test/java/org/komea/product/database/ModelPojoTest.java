package org.komea.product.database;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.ActivityFilter;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.HasProjectTagKey;
import org.komea.product.database.model.HasSuccessFactorKpiKey;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderSetting;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SuccessFactor;
import org.komea.product.database.model.Tag;

import com.google.common.collect.Lists;
import com.openpojo.reflection.PojoClass;
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



public class ModelPojoTest
{
    
    
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int      EXPECTED_CLASS_COUNT = 18;
    
    // The package to test
    private static final String   POJO_PACKAGE         = "org.komea.product.database.model";
    
    private final List<PojoClass> pojoClasses          = Lists.newArrayList();
    private PojoValidator         pojoValidator;
    
    
    
    @Test
    public void ensureExpectedPojoCount() {
    
    
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }
    
    
    @Before
    public void setup() {
    
    
        pojoClasses.add(PojoClassFactory.getPojoClass(ActivityFilter.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(Customer.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(HasProjectPersonGroupKey.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(HasProjectPersonKey.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(HasProjectTagKey.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(HasSuccessFactorKpiKey.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(Kpi.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(KpiAlertType.class));
        
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Link.class));
        
        // pojoClasses.add(PojoClassFactory.getPojoClass(Measure.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Person.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(PersonGroup.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(PersonRole.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Project.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Provider.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(ProviderSetting.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Setting.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(SuccessFactor.class));
        
        pojoClasses.add(PojoClassFactory.getPojoClass(Tag.class));
        
        
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
