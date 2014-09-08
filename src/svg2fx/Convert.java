package svg2fx;

import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import svg2fx.fxObjects.EShape;
import svg2fx.svgObjects.G;
import svg2fx.svgObjects.SVG;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Convert extends Application {
	
	public static ScriptEngineManager manager = new ScriptEngineManager();
	public static ScriptEngine engine = manager.getEngineByName("JavaScript");
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static boolean wasInRoot = false;
	private Node getFXgroup(G g, boolean isInRoot, SVG svg) {
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
				group.getChildren().add(getFXgroup(itGroup, isInRootFinal, svg));
			});
			if (isInRoot) {
				return g.transformed(new EShape(group), g.getTransform());
			} else {
				return g.transformed(group, g.getTransform());
			}
		} else {			
			group.getChildren().add(g.getNode(svg));
			if (isInRoot) {
				return new EShape(group);
			} else {
				return group;
			}
		}
	}
	
	public Node getNodeBySVG(String filePath) {
		EntityFromXML efx = new EntityFromXML();
		SVG svg = (SVG)efx.getObject(filePath, SVG.class);
		
		return getFXgroup(svg.getG(), false, svg);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		double start = System.currentTimeMillis();
		
		Node group = getNodeBySVG("d:/01_43.svg");
		
		Group g = new Group();
		g.getChildren().add(group);
		//group.getTransforms().add(new Scale(4, 4));
		
        ScrollPane sp = new ScrollPane();
        sp.setContent(g);
        
        primaryStage.setScene(new Scene(sp, 500, 500));
        primaryStage.show();
        System.out.println(System.currentTimeMillis() - start);
	}
}
