package main;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.notify.impl.NotifyingListImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import transformations.batch.Class2Relational;
import transformations.incremental.Class2RelationalIncremental;
import util.CONSTANTS;
import util.IO;
import metamodels.ClassDiagram.ClassDiagramPackage;
import metamodels.ClassDiagram.Class;

public class Main {
	
	private static boolean BATCH = false;
	
	public static void registerPackages() {
		ClassDiagramPackage.eINSTANCE.eClass();
	}
	
	public static void main(String[] args) {
		registerPackages();
		
		if(BATCH) {
			batch();
		} else {
			incremental();
		}
		
	}
	
	public static void batch() {
		var class2relationalIN = IO.readModels(CONSTANTS.MODEL_DIR+ "outClassXMI.ecore");
		var class2relationalOUT = new LinkedList<EObject>();
		class2relationalOUT.addAll(Class2Relational.transform(class2relationalIN.stream().map($ -> (EObject) $).collect(Collectors.toList())));
		IO.persistModel(class2relationalOUT, CONSTANTS.OUTPUT_DIR + "class2relational.xmi");
	}
	
	public static void incremental() {
		var class2relationalIN = Class2RelationalIncremental.start(CONSTANTS.MODEL_DIR+ "outClassXMI.ecore", CONSTANTS.OUTPUT_DIR_INC + "class2relationalContinuous.xmi");
		
		// changes
		System.out.println("Start");
		// modification
		Class c = (Class) class2relationalIN.getContents().get(0);
		c.setName("foo");
		// removal toplevel
		var clazz = class2relationalIN.getContents().get(1);
		class2relationalIN.getContents().remove(1);
		// additon toplevel
		class2relationalIN.getContents().add(clazz);
		System.out.println("Stop");
		
	}

}
