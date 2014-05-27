/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.widget.builders;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.komea.product.wicket.utils.WicketTesterMethodRule;

/**
 *
 * @author rgalerme
 */
public class SelectBoxBuilderTest {
    
           @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    public SelectBoxBuilderTest() {
    }

    @Test
    public void testCreateSelectNotRequire() {
    }

    @Test
    public void testCreateWithEnum() {
           wicketRule.newWicketTester().startComponentInPage(SelectBoxBuilder.<ENtest>createWithEnum("id", this, ENtest.class).build());
    }

    @Test
    public void testCreateWithEnumCustom() {
    }

    @Test
    public void testCreateWithEnumRequire() {
    }

    @Test
    public void testWithTooltip() {
    }

    @Test
    public void testBuild() {
    }
    
    public static enum ENtest {
    TEST, TEST2 ;
    }
    
    
}
