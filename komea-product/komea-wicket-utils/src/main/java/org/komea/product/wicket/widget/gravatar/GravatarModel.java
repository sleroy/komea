
package org.komea.product.wicket.widget.gravatar;



import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;



/**
 * Gravatar model to convert email into url for gravatar
 * 
 * @author sleroy
 */
public class GravatarModel extends AbstractReadOnlyModel<String>
{
    
    
    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";
    
    String                      email;
    String                      gravatarKey;
    int                         hsize;
    
    
    
    public GravatarModel(final IModel<String> model, final int _hSize) {
    
    
        email = model.getObject();
        gravatarKey = new Md5PasswordEncoder().encodePassword(email, null);
        hsize = _hSize;
    }
    
    
    @Override
    public String getObject() {
    
    
        final StringBuilder sb = new StringBuilder();
        sb.append(GRAVATAR_URL);
        sb.append(gravatarKey);
        sb.append("?s=");
        sb.append(hsize);
        return sb.toString();
    }
}
