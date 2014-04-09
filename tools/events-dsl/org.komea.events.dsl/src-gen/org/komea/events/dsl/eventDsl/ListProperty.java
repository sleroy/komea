/**
 */
package org.komea.events.dsl.eventDsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.komea.events.dsl.eventDsl.ListProperty#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.komea.events.dsl.eventDsl.EventDslPackage#getListProperty()
 * @model
 * @generated
 */
public interface ListProperty extends EventProperty
{
  /**
   * Returns the value of the '<em><b>Values</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' attribute list.
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#getListProperty_Values()
   * @model unique="false"
   * @generated
   */
  EList<String> getValues();

} // ListProperty
