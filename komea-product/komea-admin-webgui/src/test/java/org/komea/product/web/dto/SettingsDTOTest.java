package org.komea.product.web.dto;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.Setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The class <code>SettingsDTOTest</code> contains tests for the class <code>{@link SettingsDTO}</code>.
 *
 * @generatedBy CodePro at 17/02/14 21:27
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class SettingsDTOTest
{
    /**
     * Run the SettingsDTO() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    @Test 
    public void testSettingsDTO_1()
        throws Exception {

        SettingsDTO result = new SettingsDTO();

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the List<Setting> getSettings() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    @Test 
    public void testGetSettings_1()
        throws Exception {
        SettingsDTO fixture = new SettingsDTO();
        fixture.setSettings(EasyMock.createNiceMock(List.class));

        List<Setting> result = fixture.getSettings();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Run the void setSettings(List<Setting>) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    @Test 
    public void testSetSettings_1()
        throws Exception {
        SettingsDTO fixture = new SettingsDTO();
        fixture.setSettings(EasyMock.createNiceMock(List.class));
        List<Setting> _settings = EasyMock.createMock(List.class);
        // add mock object expectations here

        EasyMock.replay(_settings);

        fixture.setSettings(_settings);

        // add additional test code here
        EasyMock.verify(_settings);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 17/02/14 21:27
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(SettingsDTOTest.class);
    }
}