
package org.komea.demo.gitspy.widgets.gravatar;



public enum GravatarDefaultImage {
    
    GRAVATAR_ICON(""),
    
    HTTP_404("404"),
    
    IDENTICON("identicon"),
    
    MONSTERID("monsterid"),
    
    WAVATAR("wavatar");
    
    
    private String code;
    
    
    
    private GravatarDefaultImage(final String code) {
    
    
        this.code = code;
    }
    
    
    public String getCode() {
    
    
        return code;
    }
    
}
