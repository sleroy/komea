
package org.komea.product.backend.utils;


import org.junit.Assert;
import org.junit.Test;

public class CharactereUtilsTest {
    
    //
    
    @Test
    public void test_isAlphabetica() {
    
        boolean res = CharacterUtils.isAlphabetic('a');
        
        Assert.assertTrue(res);
    }
    
    @Test
    public void test_isAlphabetic_a_accent_1() {
    
        boolean res = CharacterUtils.isAlphabetic('à');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_a_accent_1_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('À');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetica_accent_2() {
    
        boolean res = CharacterUtils.isAlphabetic('â');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_a_accent_2_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Â');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetica_accent_3() {
    
        boolean res = CharacterUtils.isAlphabetic('ä');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_a_accent_3_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Ä');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticmatch_c_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('C');
        
        Assert.assertTrue(res);
    }
    
    @Test
    public void test_isAlphabetic_match_e_accent_1() {
    
        boolean res = CharacterUtils.isAlphabetic('é');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticmatch_e_accent_1_MAJ() {
    
        boolean res = CharacterUtils.isAlphabetic('É');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_e_accent_2() {
    
        boolean res = CharacterUtils.isAlphabetic('è');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticmatch_e_accent_2_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('È');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_e_accent_3() {
    
        boolean res = CharacterUtils.isAlphabetic('ê');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticmatch_e_accent_3_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Ê');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_e_accent_4() {
    
        boolean res = CharacterUtils.isAlphabetic('ë');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticmatch_e_accent__maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Ä');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_e_accent() {
    
        boolean res = CharacterUtils.isAlphabetic('é');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticu_accent_1() {
    
        boolean res = CharacterUtils.isAlphabetic('ù');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_u_accent_1_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Ù');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabeticu_accent_2() {
    
        boolean res = CharacterUtils.isAlphabetic('û');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_u_accent_2_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Û');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_u_accent_3() {
    
        boolean res = CharacterUtils.isAlphabetic('ü');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_u_accent_3_maj() {
    
        boolean res = CharacterUtils.isAlphabetic('Ü');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_two() {
    
        boolean res = CharacterUtils.isAlphabetic('2');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isAlphabetic_match_symbol() {
    
        boolean res = CharacterUtils.isAlphabetic('%');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isDigit_two() {
    
        boolean res = CharacterUtils.isDigit('2');
        
        Assert.assertTrue(res);
    }
    
    @Test
    public void test_isDigit_zero() {
    
        boolean res = CharacterUtils.isDigit('0');
        
        Assert.assertTrue(res);
    }
    
    @Test
    public void test_isDigit_a() {
    
        boolean res = CharacterUtils.isDigit('a');
        
        Assert.assertFalse(res);
    }
    
    @Test
    public void test_isDigit_symbol() {
    
        boolean res = CharacterUtils.isDigit('*');
        
        Assert.assertFalse(res);
    }
}
