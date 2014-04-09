/**
 */
package org.komea.events.dsl.eventDsl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.komea.events.dsl.eventDsl.EventDslFactory
 * @model kind="package"
 * @generated
 */
public interface EventDslPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "eventDsl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.komea.org/events/dsl/EventDsl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "eventDsl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EventDslPackage eINSTANCE = org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl.init();

  /**
   * The meta object id for the '{@link org.komea.events.dsl.eventDsl.impl.EventFactoryImpl <em>Event Factory</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.komea.events.dsl.eventDsl.impl.EventFactoryImpl
   * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEventFactory()
   * @generated
   */
  int EVENT_FACTORY = 0;

  /**
   * The feature id for the '<em><b>Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FACTORY__PACKAGE = 0;

  /**
   * The feature id for the '<em><b>Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FACTORY__PATH = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FACTORY__NAME = 2;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FACTORY__EVENTS = 3;

  /**
   * The number of structural features of the '<em>Event Factory</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FACTORY_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link org.komea.events.dsl.eventDsl.impl.EventImpl <em>Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.komea.events.dsl.eventDsl.impl.EventImpl
   * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEvent()
   * @generated
   */
  int EVENT = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__PROPERTIES = 1;

  /**
   * The number of structural features of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.komea.events.dsl.eventDsl.impl.EventPropertyImpl <em>Event Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.komea.events.dsl.eventDsl.impl.EventPropertyImpl
   * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEventProperty()
   * @generated
   */
  int EVENT_PROPERTY = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PROPERTY__TYPE = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PROPERTY__NAME = 1;

  /**
   * The number of structural features of the '<em>Event Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_PROPERTY_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.komea.events.dsl.eventDsl.impl.SimplePropertyImpl <em>Simple Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.komea.events.dsl.eventDsl.impl.SimplePropertyImpl
   * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getSimpleProperty()
   * @generated
   */
  int SIMPLE_PROPERTY = 3;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_PROPERTY__TYPE = EVENT_PROPERTY__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_PROPERTY__NAME = EVENT_PROPERTY__NAME;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_PROPERTY__VALUE = EVENT_PROPERTY_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Simple Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIMPLE_PROPERTY_FEATURE_COUNT = EVENT_PROPERTY_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.komea.events.dsl.eventDsl.impl.ListPropertyImpl <em>List Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.komea.events.dsl.eventDsl.impl.ListPropertyImpl
   * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getListProperty()
   * @generated
   */
  int LIST_PROPERTY = 4;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_PROPERTY__TYPE = EVENT_PROPERTY__TYPE;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_PROPERTY__NAME = EVENT_PROPERTY__NAME;

  /**
   * The feature id for the '<em><b>Values</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_PROPERTY__VALUES = EVENT_PROPERTY_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>List Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LIST_PROPERTY_FEATURE_COUNT = EVENT_PROPERTY_FEATURE_COUNT + 1;


  /**
   * Returns the meta object for class '{@link org.komea.events.dsl.eventDsl.EventFactory <em>Event Factory</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Factory</em>'.
   * @see org.komea.events.dsl.eventDsl.EventFactory
   * @generated
   */
  EClass getEventFactory();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.EventFactory#getPackage <em>Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Package</em>'.
   * @see org.komea.events.dsl.eventDsl.EventFactory#getPackage()
   * @see #getEventFactory()
   * @generated
   */
  EAttribute getEventFactory_Package();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.EventFactory#getPath <em>Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Path</em>'.
   * @see org.komea.events.dsl.eventDsl.EventFactory#getPath()
   * @see #getEventFactory()
   * @generated
   */
  EAttribute getEventFactory_Path();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.EventFactory#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.komea.events.dsl.eventDsl.EventFactory#getName()
   * @see #getEventFactory()
   * @generated
   */
  EAttribute getEventFactory_Name();

  /**
   * Returns the meta object for the containment reference list '{@link org.komea.events.dsl.eventDsl.EventFactory#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Events</em>'.
   * @see org.komea.events.dsl.eventDsl.EventFactory#getEvents()
   * @see #getEventFactory()
   * @generated
   */
  EReference getEventFactory_Events();

  /**
   * Returns the meta object for class '{@link org.komea.events.dsl.eventDsl.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event</em>'.
   * @see org.komea.events.dsl.eventDsl.Event
   * @generated
   */
  EClass getEvent();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.Event#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.komea.events.dsl.eventDsl.Event#getName()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Name();

  /**
   * Returns the meta object for the containment reference list '{@link org.komea.events.dsl.eventDsl.Event#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Properties</em>'.
   * @see org.komea.events.dsl.eventDsl.Event#getProperties()
   * @see #getEvent()
   * @generated
   */
  EReference getEvent_Properties();

  /**
   * Returns the meta object for class '{@link org.komea.events.dsl.eventDsl.EventProperty <em>Event Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Property</em>'.
   * @see org.komea.events.dsl.eventDsl.EventProperty
   * @generated
   */
  EClass getEventProperty();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.EventProperty#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see org.komea.events.dsl.eventDsl.EventProperty#getType()
   * @see #getEventProperty()
   * @generated
   */
  EAttribute getEventProperty_Type();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.EventProperty#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.komea.events.dsl.eventDsl.EventProperty#getName()
   * @see #getEventProperty()
   * @generated
   */
  EAttribute getEventProperty_Name();

  /**
   * Returns the meta object for class '{@link org.komea.events.dsl.eventDsl.SimpleProperty <em>Simple Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Simple Property</em>'.
   * @see org.komea.events.dsl.eventDsl.SimpleProperty
   * @generated
   */
  EClass getSimpleProperty();

  /**
   * Returns the meta object for the attribute '{@link org.komea.events.dsl.eventDsl.SimpleProperty#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.komea.events.dsl.eventDsl.SimpleProperty#getValue()
   * @see #getSimpleProperty()
   * @generated
   */
  EAttribute getSimpleProperty_Value();

  /**
   * Returns the meta object for class '{@link org.komea.events.dsl.eventDsl.ListProperty <em>List Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>List Property</em>'.
   * @see org.komea.events.dsl.eventDsl.ListProperty
   * @generated
   */
  EClass getListProperty();

  /**
   * Returns the meta object for the attribute list '{@link org.komea.events.dsl.eventDsl.ListProperty#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Values</em>'.
   * @see org.komea.events.dsl.eventDsl.ListProperty#getValues()
   * @see #getListProperty()
   * @generated
   */
  EAttribute getListProperty_Values();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  EventDslFactory getEventDslFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.komea.events.dsl.eventDsl.impl.EventFactoryImpl <em>Event Factory</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.komea.events.dsl.eventDsl.impl.EventFactoryImpl
     * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEventFactory()
     * @generated
     */
    EClass EVENT_FACTORY = eINSTANCE.getEventFactory();

    /**
     * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_FACTORY__PACKAGE = eINSTANCE.getEventFactory_Package();

    /**
     * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_FACTORY__PATH = eINSTANCE.getEventFactory_Path();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_FACTORY__NAME = eINSTANCE.getEventFactory_Name();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_FACTORY__EVENTS = eINSTANCE.getEventFactory_Events();

    /**
     * The meta object literal for the '{@link org.komea.events.dsl.eventDsl.impl.EventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.komea.events.dsl.eventDsl.impl.EventImpl
     * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEvent()
     * @generated
     */
    EClass EVENT = eINSTANCE.getEvent();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

    /**
     * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT__PROPERTIES = eINSTANCE.getEvent_Properties();

    /**
     * The meta object literal for the '{@link org.komea.events.dsl.eventDsl.impl.EventPropertyImpl <em>Event Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.komea.events.dsl.eventDsl.impl.EventPropertyImpl
     * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getEventProperty()
     * @generated
     */
    EClass EVENT_PROPERTY = eINSTANCE.getEventProperty();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_PROPERTY__TYPE = eINSTANCE.getEventProperty_Type();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_PROPERTY__NAME = eINSTANCE.getEventProperty_Name();

    /**
     * The meta object literal for the '{@link org.komea.events.dsl.eventDsl.impl.SimplePropertyImpl <em>Simple Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.komea.events.dsl.eventDsl.impl.SimplePropertyImpl
     * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getSimpleProperty()
     * @generated
     */
    EClass SIMPLE_PROPERTY = eINSTANCE.getSimpleProperty();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SIMPLE_PROPERTY__VALUE = eINSTANCE.getSimpleProperty_Value();

    /**
     * The meta object literal for the '{@link org.komea.events.dsl.eventDsl.impl.ListPropertyImpl <em>List Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.komea.events.dsl.eventDsl.impl.ListPropertyImpl
     * @see org.komea.events.dsl.eventDsl.impl.EventDslPackageImpl#getListProperty()
     * @generated
     */
    EClass LIST_PROPERTY = eINSTANCE.getListProperty();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LIST_PROPERTY__VALUES = eINSTANCE.getListProperty_Values();

  }

} //EventDslPackage
