/**
 */
package org.komea.events.dsl.eventDsl.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.komea.events.dsl.eventDsl.Event;
import org.komea.events.dsl.eventDsl.EventDslFactory;
import org.komea.events.dsl.eventDsl.EventDslPackage;
import org.komea.events.dsl.eventDsl.EventFactory;
import org.komea.events.dsl.eventDsl.EventProperty;
import org.komea.events.dsl.eventDsl.ListProperty;
import org.komea.events.dsl.eventDsl.SimpleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EventDslPackageImpl extends EPackageImpl implements EventDslPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eventFactoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eventEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eventPropertyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass simplePropertyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass listPropertyEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.komea.events.dsl.eventDsl.EventDslPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private EventDslPackageImpl()
  {
    super(eNS_URI, EventDslFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link EventDslPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static EventDslPackage init()
  {
    if (isInited) return (EventDslPackage)EPackage.Registry.INSTANCE.getEPackage(EventDslPackage.eNS_URI);

    // Obtain or create and register package
    EventDslPackageImpl theEventDslPackage = (EventDslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EventDslPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EventDslPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theEventDslPackage.createPackageContents();

    // Initialize created meta-data
    theEventDslPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theEventDslPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(EventDslPackage.eNS_URI, theEventDslPackage);
    return theEventDslPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEventFactory()
  {
    return eventFactoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEventFactory_Package()
  {
    return (EAttribute)eventFactoryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEventFactory_Path()
  {
    return (EAttribute)eventFactoryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEventFactory_Name()
  {
    return (EAttribute)eventFactoryEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEventFactory_Events()
  {
    return (EReference)eventFactoryEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEvent()
  {
    return eventEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEvent_Name()
  {
    return (EAttribute)eventEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEvent_Properties()
  {
    return (EReference)eventEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEventProperty()
  {
    return eventPropertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEventProperty_Type()
  {
    return (EAttribute)eventPropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEventProperty_Name()
  {
    return (EAttribute)eventPropertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSimpleProperty()
  {
    return simplePropertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSimpleProperty_Value()
  {
    return (EAttribute)simplePropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getListProperty()
  {
    return listPropertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getListProperty_Values()
  {
    return (EAttribute)listPropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EventDslFactory getEventDslFactory()
  {
    return (EventDslFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    eventFactoryEClass = createEClass(EVENT_FACTORY);
    createEAttribute(eventFactoryEClass, EVENT_FACTORY__PACKAGE);
    createEAttribute(eventFactoryEClass, EVENT_FACTORY__PATH);
    createEAttribute(eventFactoryEClass, EVENT_FACTORY__NAME);
    createEReference(eventFactoryEClass, EVENT_FACTORY__EVENTS);

    eventEClass = createEClass(EVENT);
    createEAttribute(eventEClass, EVENT__NAME);
    createEReference(eventEClass, EVENT__PROPERTIES);

    eventPropertyEClass = createEClass(EVENT_PROPERTY);
    createEAttribute(eventPropertyEClass, EVENT_PROPERTY__TYPE);
    createEAttribute(eventPropertyEClass, EVENT_PROPERTY__NAME);

    simplePropertyEClass = createEClass(SIMPLE_PROPERTY);
    createEAttribute(simplePropertyEClass, SIMPLE_PROPERTY__VALUE);

    listPropertyEClass = createEClass(LIST_PROPERTY);
    createEAttribute(listPropertyEClass, LIST_PROPERTY__VALUES);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    simplePropertyEClass.getESuperTypes().add(this.getEventProperty());
    listPropertyEClass.getESuperTypes().add(this.getEventProperty());

    // Initialize classes and features; add operations and parameters
    initEClass(eventFactoryEClass, EventFactory.class, "EventFactory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEventFactory_Package(), ecorePackage.getEString(), "package", null, 0, 1, EventFactory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEventFactory_Path(), ecorePackage.getEString(), "path", null, 0, 1, EventFactory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEventFactory_Name(), ecorePackage.getEString(), "name", null, 0, 1, EventFactory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEventFactory_Events(), this.getEvent(), null, "events", null, 0, -1, EventFactory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEvent_Name(), ecorePackage.getEString(), "name", null, 0, 1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEvent_Properties(), this.getEventProperty(), null, "properties", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eventPropertyEClass, EventProperty.class, "EventProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEventProperty_Type(), ecorePackage.getEString(), "type", null, 0, 1, EventProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEventProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, EventProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(simplePropertyEClass, SimpleProperty.class, "SimpleProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSimpleProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1, SimpleProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(listPropertyEClass, ListProperty.class, "ListProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getListProperty_Values(), ecorePackage.getEString(), "values", null, 0, -1, ListProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //EventDslPackageImpl
