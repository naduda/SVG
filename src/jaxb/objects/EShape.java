package jaxb.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import javax.script.Invocable;
import javax.script.ScriptException;

import javafx.scene.Group;
import jaxb.Convert;

public class EShape extends AShape {
	
	public enum typeSignalRef {
		 TI(1), TS(2), TU(3);
		 
		 private int code;
		 
		 private typeSignalRef(int c) {
		   code = c;
		 }
		 
		 public int getCode() {
		   return code;
		 }
	}
	
	public boolean isON = false;
	
	public EShape(boolean canSelecting) {
		super(canSelecting);
	}
	
	private HashMap<String, String> custProps;
	@SuppressWarnings("unchecked")
	public EShape(Group g, boolean canSelecting) {
		super(g, canSelecting);
		
		if (g.getUserData() != null) {
			custProps = (HashMap<String, String>) g.getUserData();
		}
	}
	
	public void changeTS(int val) {
		isON = val == 0 ? false : true;
	}
	
	public void setTS(boolean val) {
		isON = !isON;
	}

	@Override
	public void onDoubleClick() {
		double start = System.currentTimeMillis();
		if (custProps != null && custProps.get("Name") != null) {
			String sName = custProps.get("Name");
			
			if (sName.startsWith("DisConnector") || sName.startsWith("Breaker")) {
				try {
					Convert.engine.eval(new FileReader(new File("d:/"+ sName.substring(0, sName.indexOf(".")) +".js")));
					Invocable inv = (Invocable) Convert.engine;
		            inv.invokeFunction("dblClick", this );
				} catch (FileNotFoundException | ScriptException | NoSuchMethodException e) {
					System.out.println("Script not found");
				}
			}
		}
    	System.out.println(System.currentTimeMillis() - start);
	}
}
