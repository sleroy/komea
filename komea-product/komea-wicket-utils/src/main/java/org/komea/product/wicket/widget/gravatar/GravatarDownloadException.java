
package org.komea.product.wicket.widget.gravatar;



public class GravatarDownloadException extends RuntimeException
{
    
    
    private static final long serialVersionUID = 1L;
    
    
    
    public GravatarDownloadException(final Throwable cause) {
    
    
        super("Gravatar could not be downloaded: " + cause.getMessage(), cause);
    }
    
}
