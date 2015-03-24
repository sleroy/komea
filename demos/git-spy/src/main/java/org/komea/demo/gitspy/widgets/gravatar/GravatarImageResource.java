/**
 * 
 */
package org.komea.demo.gitspy.widgets.gravatar;

import org.apache.wicket.request.resource.DynamicImageResource;

/**
 * @author sleroy
 *
 */
public final class GravatarImageResource extends DynamicImageResource
{
    
    
    /**
     * 
     */
    private final String               email;
    /**
     * 
     */
    private final int                  expectedSize;
    /**
     * 
     */
    private final GravatarDefaultImage defaultImage;
    
    
    
    /**
     * @param _email
     * @param _expectedSize
     * @param _defaultImage
     */
    public GravatarImageResource(
            String _email,
            int _expectedSize,
            GravatarDefaultImage _defaultImage) {
    
    
        email = _email;
        expectedSize = _expectedSize;
        defaultImage = _defaultImage;
    }
    
    
    @Override
    protected byte[] getImageData(final Attributes _attributes) {
    
    
        final Gravatar gravatar = new Gravatar();
        gravatar.setDefaultImage(defaultImage);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setSize(expectedSize);
        
        return gravatar.download(email);
    }
}