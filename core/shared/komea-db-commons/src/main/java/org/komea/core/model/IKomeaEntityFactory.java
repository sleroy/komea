package org.komea.core.model;

import org.komea.core.schema.IEntityType;

/**
 * Factory used to create instances of any {@link IEntityType}.
 * 
 * @author afloch
 *
 */
public interface IKomeaEntityFactory {
	IKomeaEntity create(IEntityType type);
}
