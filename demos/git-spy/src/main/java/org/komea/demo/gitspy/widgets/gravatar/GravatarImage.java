/**
 *
 */

package org.komea.demo.gitspy.widgets.gravatar;

import org.apache.wicket.markup.html.image.Image;

/**
 * This class defines a wicket component to display a gravatar picture.
 *
 * @author sleroy
 */
public class GravatarImage extends Image {

	/**
	 *
	 */
	public GravatarImage(final String _id, final String _email,
			final GravatarDefaultImage _defaultImage, final int _expectedSize) {

		super(_id, new GravatarImageResource(_email, _expectedSize,
				_defaultImage));

	}

}
