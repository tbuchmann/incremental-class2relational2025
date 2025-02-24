package transformations.incremental;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import metamodels.Relational.RelationalFactory;
import metamodels.Relational.Named;
import metamodels.Relational.Type;
import metamodels.ClassDiagram.Class;
import metamodels.ClassDiagram.DataType;
import metamodels.ClassDiagram.NamedElt;
import metamodels.ClassDiagram.impl.AttributeImpl;
import metamodels.ClassDiagram.impl.ClassImpl;
import metamodels.ClassDiagram.impl.DataTypeImpl;
import metamodels.ClassDiagram.Attribute;
import transformations.util.trace.Tracer;
import transformations.util.traverse.Traverser;
import util.CONSTANTS;
import util.IO;

// Class NA NA NA NA Value 0
public class Class2RelationalIncremental {

    // Class Setup NA NA NA Value 8
    private static final RelationalFactory RELATIONALFACTORY = RelationalFactory.eINSTANCE;

    // Class Setup NA NA NA Value 8
    private static final Tracer TRACER = new Tracer();

    // Class Setup NA NA NA Value 9
    private static final List <DataType> allDataTypes = new LinkedList<>();

    // Class Setup NA NA NA Value 5
    private static Traverser PRETRAVERSER;

    // Class Setup NA NA NA Value 5
    private static Traverser TRAVERSER;

    // Class Setup NA NA NA Value 5
    private static Type objectIdType;

    // Class Helper Helper NA NA Value 4
    private static Type objectIdType()
    {
        // Class Helper Helper ControlFlow Vanilla Value 4
        if (objectIdType == null)
        {
            // Class Helper Helper VarDec Vanilla Value 59
            objectIdType = allDataTypes.stream().filter($ -> $.getName().equals("Integer")).findFirst().map($ ->
            {
                // Class Helper Helper VarDec Vanilla Value 5
                var t = RELATIONALFACTORY.createType();
                // Class Helper Helper Statement Vanilla Value 4
                t.setName($.getName());
                // Class Helper Helper Statement Vanilla Value 2
                return t;
            }).get();
        }
        // Class Helper Helper Statement Vanilla Value 2
        return objectIdType;
    }

    // Class Setup NA NA NA Value 8
    public static Resource start(String inPath, String outPath) 
    {
        // Class Setup NA VarDec Vanilla Value 6
        var class2relationalIN = IO.getResource(inPath);
        // Class Setup NA VarDec Vanilla Value 6
        var class2relationalOUT = IO.createResource(outPath);
        // Class Setup NA Statement Vanilla Value 16
        class2relationalOUT.getContents().addAll(Class2RelationalIncremental.transform(class2relationalIN.getContents().stream().map($ -> (EObject) $).collect(Collectors.toList())));
        // Class Setup NA Statement Vanilla Value 3
        IO.persist(class2relationalOUT);
        // Class Incrementality NA VarDec Vanilla Value 195
        Adapter adapterIn = new AdapterImpl() {

            // Class Incrementality NA Statement Vanilla Value 5
            public void notifyChanged(Notification notification) 
            {
                // Class Incrementality NA VarDec Vanilla Value 5
                var notificationType = notification.getEventType();
                // Class Incrementality NA ControlFlow Vanilla Value 2
                switch(notificationType) {
                    case Notification.ADD:
                        {
                            // Class Incrementality NA VarDec Vanilla Value 11
                            var iterable = Stream.of((EObject) notification.getNewValue()).collect(Collectors.toList());
                            // Class Incrementality NA Statement PureIncrementality Value 4
                            PRETRAVERSER.traverseAndAcceptPre(iterable.iterator());
                            // Class Incrementality NA Statement PureIncrementality Value 4
                            TRAVERSER.traverseAndAccept(iterable.iterator());
                            // Class Incrementality NA VarDec PureIncrementality Value 8
                            var allTargets = TRACER.resolveAll((EObject) notification.getNewValue());
                            // Class Incrementality NA ControlFlow PureIncrementality Value 5
                            for (EObject target : allTargets) 
                            {
                                // Class Incrementality NA Statement PureIncrementality Value 4
                                class2relationalOUT.getContents().add(target);
                            }
                        }
                    case Notification.REMOVE:
                        
                        {
                            // Class Incrementality NA VarDec PureIncrementality Value 8
                            var allTargets = TRACER.resolveAll((EObject) notification.getOldValue());
                            // Class Incrementality NA ControlFlow PureIncrementality Value 5
                            for (EObject target : allTargets) 
                            {
                                // Class Incrementality NA Statement PureIncrementality Value 4
                                class2relationalOUT.getContents().remove(target);
                            }
                        }
                }
            }
        };
        // Class Incrementality NA Statement Vanilla Value 4
        class2relationalIN.eAdapters().add(adapterIn);
        // Class Incrementality NA VarDec Vanilla Value 66
        Adapter adapterOut = new EContentAdapter() {

            // Class Incrementality NA Statement Vanilla Value 5
            public void notifyChanged(Notification notification) 
            {
                // Class Incrementality NA Statement PureIncrementality Value 3
                class2relationalOUT.eSetDeliver(false);
                // Class Incrementality NA Statement PureIncrementality Value 3
                IO.persist(class2relationalOUT);
                // Class Incrementality NA Statement PureIncrementality Value 3
                class2relationalOUT.eSetDeliver(true);
            }
        };
        // Class Incrementality NA Statement Vanilla Value 4
        class2relationalOUT.eAdapters().add(adapterOut);
        // Class Setup NA Statement Vanilla Value 2
        return class2relationalIN;
    }

    // Class Traversal Entry NA NA Value 8
    public static List <Named> transform(List <EObject> input)
    {
        // Class Traversal Entry ControlFlow Vanilla Value 5
        for (EObject namedElt : input)
        {
            // Class Traversal Entry ControlFlow Vanilla Value 4
            if (namedElt instanceof DataType)
            {
                // Class Traversal Entry Statement Vanilla Value 4
                allDataTypes.add((DataType) namedElt);
            }
        }
        // Class Traversal Entry Statement Vanilla Value 2
        preTransform(input);
        // Class Traversal Entry Statement Vanilla Value 3
        return actualTransform(input);
    }

    // Class Traversal PreActual NA NA Value 7
    private static void preTransform(List <EObject> input) 
    {
        // Class Traversal PreActual VarDec Vanilla Value 5
        var iterator = input.iterator();
        // Class Traversal PreActual VarDec Vanilla Value 6
        var traverser = new Traverser(TRACER);
        // Class Traversal PreActual Statement Vanilla Value 9
        traverser.addFunction(ClassImpl.class, x -> Class2TablePre((Class) x));
        // Class Traversal PreActual Statement Vanilla Value 9
        traverser.addFunction(DataTypeImpl.class, x -> DataType2TypePre((DataType) x));
        // Class Traversal PreActual Statement Vanilla Value 18
        traverser.addFunction(AttributeImpl.class, x -> 
        {
            // Class Traversal PreActual Statement Vanilla Value 3
            DataTypeAttribute2ColumnPre((Attribute) x);
            // Class Traversal PreActual Statement Vanilla Value 3
            MultiValuedDataTypeAttribute2ColumnPre((Attribute) x);
            // Class Traversal PreActual Statement Vanilla Value 3
            ClassAttribute2ColumnPre((Attribute) x);
            // Class Traversal PreActual Statement Vanilla Value 3
            MultiValuedClassAttribute2ColumnPre((Attribute) x);
        });
        // Class Traversal PreActual Statement Vanilla Value 3
        traverser.traverseAndAcceptPre(iterator);
    }

    // Class Traversal NA NA NA Value 8
    private static List <Named> actualTransform(List <EObject> input) 
    {
        // Class Traversal NA VarDec NA Value 5
        var newRoot = Classes2List(input);
        // Class Traversal NA VarDec NA Value 5
        var iterator = input.iterator();
        // Class Traversal NA VarDec NA Value 5
        TRAVERSER = new Traverser(TRACER);
        // Class Traversal NA Statement Vanilla Value 69
        TRAVERSER.addFunction(ClassImpl.class, x -> 
        {
            // Class Traversal NA Statement NA Value 3
            Class2Table((Class) x);
            // Class Incrementality NA VarDec Vanilla Value 48
            var adapter = new EContentAdapter() {

                // Class Incrementality NA Statement Vanilla Value 5
                public void notifyChanged(Notification notification) 
                {
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    Class2Table((Class) notification.getNotifier());
                }
            };
            // Class Incrementality NA Statement Vanilla Value 4
            x.eAdapters().add(adapter);
        });
        // Class Traversal NA Statement Vanilla Value 85
        TRAVERSER.addFunction(DataTypeImpl.class, x -> 
        {
            // Class Traversal NA Statement Vanilla Value 3
            DataType2Type((DataType) x);
            // Class Incrementality NA VarDec Vanilla Value 64
            var adapter = new EContentAdapter() {

                // Class Incrementality NA Statement Vanilla Value 5
                public void notifyChanged(Notification notification) 
                {
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    DataType2Type((DataType) notification.getNotifier());
                }
            };
            // Class Incrementality NA Statement Vanilla Value 4
            x.eAdapters().add(adapter);
        });
        // Class Traversal NA Statement Vanilla Value 162
        TRAVERSER.addFunction(AttributeImpl.class, x -> 
        {
            // Class Traversal NA Statement Vanilla Value 3
            DataTypeAttribute2Column((Attribute) x);
            // Class Traversal NA Statement Vanilla Value 3
            MultiValuedDataTypeAttribute2Column((Attribute) x);
            // Class Traversal NA Statement Vanilla Value 3
            ClassAttribute2Column((Attribute) x);
            // Class Traversal NA Statement Vanilla Value 3
            MultiValuedClassAttribute2Column((Attribute) x);
            // Class Incrementality NA VarDec Vanilla Value 52
            var adapter = new EContentAdapter() {

                // Class Incrementality NA Statement Vanilla Value 5
                public void notifyChanged(Notification notification) 
                {
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    DataTypeAttribute2Column((Attribute) notification.getNotifier());
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    MultiValuedDataTypeAttribute2Column((Attribute) notification.getNotifier());
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    ClassAttribute2Column((Attribute) notification.getNotifier());
                    // Class Incrementality NA Statement PureIncrementality Value 4
                    MultiValuedClassAttribute2Column((Attribute) notification.getNotifier());
                }
            };
            // Class Incrementality NA Statement Vanilla Value 4
            x.eAdapters().add(adapter);
        });
        // Class Traversal NA Statement Vanilla Value 3
        TRAVERSER.traverseAndAccept(iterator);
        // Class Traversal NA Statement Vanilla Value 2
        return newRoot;
    }

    // Class Transformation A2B NA NA Value 8
    private static List <Named> Classes2List(List <EObject> input) 
    {
        // Class Transformation A2B VarDec Vanilla Value 6
        var nameds = new LinkedList <Named>();
        // Class Transformation A2B ControlFlow Vanilla Value 5
        for (EObject eObject : input) 
        {
            // Class Transformation A2B Statement PureBinding Value 4
        	// Class Transformation A2B Statement PureTracing Value 10
            nameds.addAll(TRACER.resolveAll(eObject).stream().map($ -> (Named) $).collect(Collectors.toList()));
        }
        // Class Transformation A2B Statement PureBinding Value 3
        nameds.add(objectIdType());
        // Class Transformation A2B Statement Vanilla Value 2
        return nameds;
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void Class2TablePre(Class c) 
    {
        // Class Tracing A2BPre Statement PureTracing Value 5
        TRACER.addTrace(c, RELATIONALFACTORY.createTable());
        // Class Tracing A2BPre Statement PureTracing Value 6
        TRACER.addTrace(c, "key", RELATIONALFACTORY.createColumn());
    }

    // Class Transformation A2B NA NA Value 6
    public static void Class2Table(Class c) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var out = TRACER.resolve(c, RELATIONALFACTORY.createTable());
        // Class Transformation A2B VarDec PureTracing Value 9
        var key = TRACER.resolve(c, "key", RELATIONALFACTORY.createColumn());
        // Class Transformation A2B Statement PureBinding Value 4
        out.setName(c.getName());
        // Class Transformation A2B Statement Vanilla Value 3
        out.getCol().clear();
        // Class Transformation A2B Statement PureBinding Value 4
        out.getCol().add(key);
        // Class Transformation A2B Statement PureBinding Value 12
        // Class Transformation A2B Statement PureTracing Value 16
        out.getCol().addAll(c.getAttr().stream().filter(e -> !e.isMultiValued()).map($ -> TRACER.resolve($, RELATIONALFACTORY.createColumn())).filter($ -> $ != null).collect(Collectors.toList()));
        // Class Transformation A2B Statement PureBinding Value 12
        // Class Transformation A2B Statement PureTracing Value 17
        out.getCol().addAll(c.getAttr().stream().filter(e -> !e.isMultiValued()).map($ -> TRACER.resolve($, "key", RELATIONALFACTORY.createColumn())).filter($ -> $ != null).collect(Collectors.toList()));
        // Class Transformation A2B Statement PureBinding Value 12
        // Class Transformation A2B Statement PureTracing Value 17
        out.getCol().addAll(c.getAttr().stream().filter(e -> !e.isMultiValued()).map($ -> TRACER.resolve($, "id", RELATIONALFACTORY.createColumn())).filter($ -> $ != null).collect(Collectors.toList()));
        // Class Transformation A2B Statement PureBinding Value 12
        // Class Transformation A2B Statement PureTracing Value 17
        out.getCol().addAll(c.getAttr().stream().filter(e -> !e.isMultiValued()).map($ -> TRACER.resolve($, "value", RELATIONALFACTORY.createColumn())).filter($ -> $ != null).collect(Collectors.toList()));
        // Class Transformation A2B Statement PureBinding Value 12
        // Class Transformation A2B Statement PureTracing Value 17
        out.getCol().addAll(c.getAttr().stream().filter(e -> !e.isMultiValued()).map($ -> TRACER.resolve($, "foreignKey", RELATIONALFACTORY.createColumn())).filter($ -> $ != null).collect(Collectors.toList()));
        // Class Transformation A2B Statement Vanilla Value 3
        out.getKey().clear();
        // Class Transformation A2B Statement PureBinding Value 4
        out.getKey().add(key);
        // Class Transformation A2B Statement PureBinding Value 3
        key.setName("objectId");
        // Class Transformation A2B Statement PureBinding Value 3
        key.setType(objectIdType());
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void DataType2TypePre(DataType dt) 
    {
        // Class Tracing A2BPre Statement PureTracing Value 5
        TRACER.addTrace(dt, RELATIONALFACTORY.createType());
    }

    // Class Transformation A2B NA NA Value 6
    public static void DataType2Type(DataType dt) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var out = TRACER.resolve(dt, RELATIONALFACTORY.createType());
        // Class Transformation A2B Statement PureBinding Value 4
        out.setName(dt.getName());
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void DataTypeAttribute2ColumnPre(Attribute a) 
    {
        // Class Tracing A2BPre ControlFlow Vanilla Value 6
        if (DataType.class.isAssignableFrom(a.getClass())) 
        {
            // Class Tracing A2BPre Statement PureTracing Value 5
            TRACER.addTrace(a, RELATIONALFACTORY.createColumn());
        }
    }

    // Class Transformation A2B NA NA Value 6
    public static void DataTypeAttribute2Column(Attribute a) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var out = TRACER.resolve(a, RELATIONALFACTORY.createColumn());
        // Class Transformation A2B Statement PureBinding Value 4
        out.setName(a.getName());
        // Class Transformation A2B Statement PureBinding Value 3
        // Class Transformation A2B Statement PureTracing Value 6
        out.setType(TRACER.resolve(a.getType(), RELATIONALFACTORY.createType()));
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void MultiValuedDataTypeAttribute2ColumnPre(Attribute a) 
    {
        // Class Tracing A2BPre ControlFlow Vanilla Value 9
        if (DataType.class.isAssignableFrom(a.getClass()) && a.isMultiValued()) 
        {
            // Class Tracing A2BPre Statement PureTracing Value 5
            TRACER.addTrace(a, RELATIONALFACTORY.createTable());
            // Class Tracing A2BPre Statement PureTracing Value 6
            TRACER.addTrace(a, "id", RELATIONALFACTORY.createColumn());
            // Class Tracing A2BPre Statement PureTracing Value 6
            TRACER.addTrace(a, "value", RELATIONALFACTORY.createColumn());
        }
    }

    // Class Transformation A2B NA NA Value 6
    public static void MultiValuedDataTypeAttribute2Column(Attribute a) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var out = TRACER.resolve(a, RELATIONALFACTORY.createTable());
        // Class Transformation A2B VarDec PureTracing Value 9
        var id = TRACER.resolve(a, "id", RELATIONALFACTORY.createColumn());
        // Class Transformation A2B VarDec PureTracing Value 9
        var value = TRACER.resolve(a, "value", RELATIONALFACTORY.createColumn());
        // Class Transformation A2B Statement PureBinding Value 10
        out.setName(a.getOwner().getName() + "_" + a.getName());
        // Class Transformation A2B Statement Vanilla Value 3
        out.getCol().clear();
        // Class Transformation A2B Statement PureBinding Value 4
        out.getCol().add(id);
        // Class Transformation A2B Statement PureBinding Value 4
        out.getCol().add(value);
        // Class Transformation A2B VarDec PureBinding Value 6
        var ownerName = a.getOwner().getName();
        // Class Transformation A2B VarDec PureBinding Value 12
        var name = Character.toLowerCase(ownerName.charAt(0)) + ownerName.substring(1);
        // Class Transformation A2B Statement PureBinding Value 5
        id.setName(name + "Id");
        // Class Transformation A2B Statement PureBinding Value 3
        id.setType(objectIdType());
        // Class Transformation A2B Statement PureBinding Value 4
        value.setName(a.getName());
        // Class Transformation A2B Statement PureBinding Value 3
        // Class Transformation A2B Statement PureTracing Value 6
        value.setType(TRACER.resolve(a.getType(), RELATIONALFACTORY.createType()));
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void ClassAttribute2ColumnPre(Attribute a) 
    {
        // Class Tracing A2BPre ControlFlow Vanilla Value 9
        if (DataType.class.isAssignableFrom(a.getClass()) && !a.isMultiValued()) 
        {
            // Class Tracing A2BPre Statement PureTracing Value 5
            TRACER.addTrace(a, RELATIONALFACTORY.createColumn());
        }
    }

    // Class Transformation A2B NA NA Value 6
    public static void ClassAttribute2Column(Attribute a) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var foreignKey = TRACER.resolve(a, RELATIONALFACTORY.createColumn());
        // Class Transformation A2B Statement PureBinding Value 6
        foreignKey.setName(a.getName() + "Id");
        // Class Transformation A2B Statement PureBinding Value 3
        foreignKey.setType(objectIdType());
    }

    // Class Tracing A2BPre NA NA Value 6
    public static void MultiValuedClassAttribute2ColumnPre(Attribute a) 
    {
        // Class Tracing A2BPre ControlFlow Vanilla Value 9
        if (Class.class.isAssignableFrom(a.getClass()) && a.isMultiValued()) 
        {
            // Class Tracing A2BPre Statement PureTracing Value 5
            TRACER.addTrace(a, RELATIONALFACTORY.createTable());
            // Class Tracing A2BPre Statement PureTracing Value 6
            TRACER.addTrace(a, "id", RELATIONALFACTORY.createColumn());
            // Class Tracing A2BPre Statement PureTracing Value 6
            TRACER.addTrace(a, "foreignKey", RELATIONALFACTORY.createColumn());
        }
    }

    // Class Transformation A2B NA NA Value 6
    public static void MultiValuedClassAttribute2Column(Attribute a) 
    {
        // Class Transformation A2B VarDec PureTracing Value 8
        var t = TRACER.resolve(a, RELATIONALFACTORY.createTable());
        // Class Transformation A2B VarDec PureTracing Value 9
        var id = TRACER.resolve(a, "id", RELATIONALFACTORY.createColumn());
        // Class Transformation A2B VarDec PureTracing Value 9
        var foreignKey = TRACER.resolve(a, "foreignKey", RELATIONALFACTORY.createColumn());
        // Class Transformation A2B Statement PureBinding Value 10
        t.setName(a.getOwner().getName() + "_" + a.getName());
        // Class Transformation A2B Statement Vanilla Value 3
        t.getCol().clear();
        // Class Transformation A2B Statement PureBinding Value 4
        t.getCol().add(id);
        // Class Transformation A2B Statement PureBinding Value 4
        t.getCol().add(foreignKey);
        // Class Transformation A2B VarDec PureBinding Value 6
        var ownerName = a.getOwner().getName();
        // Class Transformation A2B VarDec PureBinding Value 12
        var name = Character.toLowerCase(ownerName.charAt(0)) + ownerName.substring(1);
        // Class Transformation A2B Statement PureBinding Value 5
        id.setName(name + "Id");
        // Class Transformation A2B Statement PureBinding Value 3
        id.setType(objectIdType());
        // Class Transformation A2B Statement PureBinding Value 6
        foreignKey.setName(a.getName() + "Id");
        // Class Transformation A2B Statement PureBinding Value 3
        // Class Transformation A2B Statement PureTracing Value 6
        foreignKey.setType(TRACER.resolve(a.getType(), RELATIONALFACTORY.createType()));
    }
}
