package org.komea.product.wicket.settings;

import org.apache.wicket.model.IModel;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.web.dto.SettingsDTO;

/**
 * The class <code>SettingFormTest</code> contains tests for the class <code>{@link SettingForm}</code>.
 *
 * @generatedBy CodePro at 17/02/14 22:41
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class SettingFormTest
{
    /**
     * Run the SettingForm(ISettingService,String,IModel<SettingsDTO>) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testSettingForm_1()
        throws Exception {
        ISettingService _settingsService = EasyMock.createMock(ISettingService.class);
        String _id = "";
        IModel<SettingsDTO> _dto = EasyMock.createMock(IModel.class);
        // add mock object expectations here

        EasyMock.replay(_settingsService);
        EasyMock.replay(_dto);

        SettingForm result = new SettingForm(_settingsService, _id, _dto);

        // add additional test code here
        EasyMock.verify(_settingsService);
        EasyMock.verify(_dto);
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        assertNotNull(result);
        // unverified
    }

    /**
     * Run the void onInitialize() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testOnInitialize_1()
        throws Exception {
        SettingForm fixture = new SettingForm(EasyMock.createNiceMock(ISettingService.class), "", EasyMock.createNiceMock(IModel.class));

        fixture.onInitialize();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testOnSubmit_1()
        throws Exception {
        SettingForm fixture = new SettingForm(EasyMock.createNiceMock(ISettingService.class), "", EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testOnSubmit_2()
        throws Exception {
        SettingForm fixture = new SettingForm(EasyMock.createNiceMock(ISettingService.class), "", EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testOnSubmit_3()
        throws Exception {
        SettingForm fixture = new SettingForm(EasyMock.createNiceMock(ISettingService.class), "", EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        // unverified
    }

    /**
     * Run the void onSubmit() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @Test
    public void testOnSubmit_4()
        throws Exception {
        SettingForm fixture = new SettingForm(EasyMock.createNiceMock(ISettingService.class), "", EasyMock.createNiceMock(IModel.class));

        fixture.onSubmit();

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    org.apache.wicket.WicketRuntimeException: Null or empty component ID's are not allowed.
        //       at org.apache.wicket.Component.setId(Component.java:4279)
        //       at org.apache.wicket.Component.<init>(Component.java:682)
        //       at org.apache.wicket.MarkupContainer.<init>(MarkupContainer.java:121)
        //       at org.apache.wicket.markup.html.WebMarkupContainer.<init>(WebMarkupContainer.java:52)
        //       at org.apache.wicket.markup.html.form.Form.<init>(Form.java:313)
        //       at org.komea.product.wicket.settings.SettingForm.<init>(SettingForm.java:44)
        // unverified
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 17/02/14 22:41
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
     * @generatedBy CodePro at 17/02/14 22:41
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }
}