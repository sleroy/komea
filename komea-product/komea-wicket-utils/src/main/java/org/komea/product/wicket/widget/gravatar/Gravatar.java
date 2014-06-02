
package org.komea.product.wicket.widget.gravatar;



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.DigestUtils;



/**
 * A gravatar is a dynamic image resource that is requested from the
 * gravatar.com server. This class calculates the gravatar url and fetches
 * gravatar images. See http://en.gravatar.com/site/implement/url .
 * This class is thread-safe, Gravatar objects can be shared.
 * Usage example: <code>
 * Gravatar gravatar = new Gravatar();
 * gravatar.setSize(50);
 * gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
 * gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
 * String url = gravatar.getUrl("iHaveAn@email.com");
 * byte[] jpg = gravatar.download("info@ralfebert.de");
 * </code>
 */
public final class Gravatar
{
    
    
    private static final GravatarDefaultImage DEFAULT_DEFAULT_IMAGE = GravatarDefaultImage.HTTP_404;
    private static final GravatarRating       DEFAULT_RATING        =
                                                                            GravatarRating.GENERAL_AUDIENCES;
    private final static int                  DEFAULT_SIZE          = 80;
    private final static String               GRAVATAR_URL          =
                                                                            "http://www.gravatar.com/avatar/";
    
    private GravatarDefaultImage              defaultImage          = DEFAULT_DEFAULT_IMAGE;
    private GravatarRating                    rating                = DEFAULT_RATING;
    private int                               size                  = DEFAULT_SIZE;
    
    
    
    /**
     * Downloads the gravatar for the given URL using Java {@link URL} and
     * returns a byte array containing the gravatar jpg, returns null if no
     * gravatar was found.
     */
    public byte[] download(final String email) throws GravatarDownloadException {
    
    
        InputStream stream = null;
        try {
            final URL url = new URL(getUrl(email));
            stream = url.openStream();
            return IOUtils.toByteArray(stream);
        } catch (final FileNotFoundException e) {
            return null;
        } catch (final Exception e) {
            throw new GravatarDownloadException(e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
    
    
    /**
     * Returns the Gravatar URL for the given email address.
     */
    public String getUrl(final String _email) {
    
    
        final String emailString = _email == null ? "" : _email;
        
        // hexadecimal MD5 hash of the requested user's lowercased email address
        // with all whitespace trimmed
        final String emailHash =
                DigestUtils.md5DigestAsHex(emailString.toLowerCase().trim().getBytes());
        final String params = formatUrlParameters();
        return GRAVATAR_URL + emailHash + ".jpg" + params;
    }
    
    
    /**
     * Specify the default image to be produced if no gravatar image was found.
     */
    public void setDefaultImage(final GravatarDefaultImage defaultImage) {
    
    
        Validate.notNull(defaultImage, "defaultImage");
        this.defaultImage = defaultImage;
    }
    
    
    /**
     * Specify a rating to ban gravatar images with explicit content.
     */
    public void setRating(final GravatarRating rating) {
    
    
        Validate.notNull(rating, "rating");
        this.rating = rating;
    }
    
    
    /**
     * Specify a gravatar size between 1 and 512 pixels. If you omit this, a
     * default size of 80 pixels is used.
     */
    public void setSize(final int sizeInPixels) {
    
    
        Validate.isTrue(sizeInPixels >= 1 && sizeInPixels <= 512,
                "sizeInPixels needs to be between 1 and 512");
        size = sizeInPixels;
    }
    
    
    private String formatUrlParameters() {
    
    
        final List<String> params = new ArrayList<String>();
        
        if (size != DEFAULT_SIZE) {
            params.add("s=" + size);
        }
        if (rating != DEFAULT_RATING) {
            params.add("r=" + rating.getCode());
        }
        if (defaultImage != GravatarDefaultImage.GRAVATAR_ICON) {
            params.add("d=" + defaultImage.getCode());
        }
        
        if (params.isEmpty()) {
            return "";
        } else {
            return "?" + StringUtils.join(params.iterator(), "&");
        }
    }
    
}
