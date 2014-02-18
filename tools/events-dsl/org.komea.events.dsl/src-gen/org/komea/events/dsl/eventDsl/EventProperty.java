/**
 */
package org.komea.events.dsl.eventDsl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.komea.events.dsl.eventDsl.EventProperty#getType <em>Type</em>}</li>
 *   <li>{@link org.komea.events.dsl.eventDsl.EventProperty#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEventProperty()
 * @model
 * @generated
 */
public interface EventProperty extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEventProperty_Type()
   * @model
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link org.komea.events.dsl.eventDsl.EventProperty#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#getEventProperty_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.komea.events.dsl.eventDsl.EventProperty#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // EventProperty
