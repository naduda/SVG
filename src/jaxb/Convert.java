package jaxb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import jaxb.objects.EShape;
import jaxb.objects.G;
import jaxb.objects.SVG;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Convert extends Application {
	private static SVG svg;
	
	public static ScriptEngineManager manager = new ScriptEngineManager();
	public static ScriptEngine engine = manager.getEngineByName("JavaScript");
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static boolean wasInRoot = false;
	public static Node getFXgroup(G g, boolean isInRoot) {
		Group group = new Group();
		if (g.getCustProps() != null) {
			HashMap<String, String> gData = new HashMap<>();
			g.getCustProps().getCustomProps().forEach(p -> { gData.put(p.getNameU(), p.getVal()); }); 
			group.setUserData(gData);
		}
		boolean isInRootFinal = !isInRoot && !wasInRoot;
		List<G> listG = g.getListG();
		if (listG != null) {
			listG.forEach(itGroup -> {				
				group.getChildren().add(getFXgroup(itGroup, isInRootFinal));
			});
			if (isInRoot) {
				return g.transformed(new EShape(group, true), g.getTransform());
			} else {
				return g.transformed(group, g.getTransform());
			}
		} else {			
			group.getChildren().add(g.getNode(svg));
			if (isInRoot) {
				return new EShape(group, true);
			} else {
				return group;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		EntityFromXML efx = new EntityFromXML();
		svg = (SVG)efx.getObject("d:/01_4.svg", SVG.class);
		
		Node group = getFXgroup(svg.getG(), false);
		
		((Group)group).getChildren().forEach(c -> {
			if (c instanceof Group) {
				Group cg = (Group) c;
				if (cg.getUserData() != null) {
					HashMap<String, String> hm = (HashMap<String, String>) cg.getUserData();
					Set<String> ks = hm.keySet();
					for (Iterator<String> iterator = ks.iterator(); iterator.hasNext();) {
						String key = (String) iterator.next();
						//System.out.println(key + " / " + hm.get(key));
					}
					//System.out.println("**************************************");
				}
			}
		});
		
		Group g = new Group();
		g.getChildren().add(group);
//		group.getTransforms().add(new Scale(4, 4));
		
        ScrollPane sp = new ScrollPane();
        sp.setContent(g);
        
        primaryStage.setScene(new Scene(sp, 500, 500));
        primaryStage.show();
	}

	public static SVG getSvg() {
		return svg;
	}
}
