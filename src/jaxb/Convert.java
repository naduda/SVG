package jaxb;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import jaxb.objects.G;
import jaxb.objects.SVG;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Convert extends Application {
	static SVG svg;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Node getFXgroup(G g) {
		Group group = new Group();
		if (g.getCustProps() != null) {
			g.getCustProps().getCustomProps().forEach(System.out::println);
		}
		List<G> listG = g.getListG();
		if (listG != null) {
			listG.forEach(itGroup -> {
				group.getChildren().add(getFXgroup(itGroup));
			});
			return g.transformed(group, g.getTransform());
		} else {
			group.getChildren().add(g.getNode(svg));
			return group;
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		EntityFromXML efx = new EntityFromXML();
		svg = (SVG)efx.getObject("d:/01_4.svg", SVG.class);
		
		Node group = getFXgroup(svg.getG());
		
//		group = new Group();
//		SVGPath p1 = new SVGPath();
//		p1.setContent("M7.99 49.68 L8.35 49.68 L48.77 49.68");
//		p1.setStroke(Color.RED);
//		p1.setStrokeWidth(2.25);
//		
//		SVGPath path = new SVGPath();
//		path.setContent("M 2 1 L 0 0 L 1.98117 -0.993387 C 1.67173 -0.364515 1.67301 0.372641 1.98465 1.00043 ");
//		path.setStrokeWidth(0);
//		path.setStroke(Color.GREEN);
//		path.setFill(Color.RED);
//		path.getTransforms().add(new Translate(-3.55*2.25, 0));
//		path.getTransforms().add(new Translate(7.99, 49.68));	
//		path.getTransforms().add(new Scale(2.12, 2.12));
//		path.getTransforms().add(new Scale(2.25, 2.25));
//		
//		Group gg = new Group();
//		gg.getChildren().addAll(path, p1);
//		gg.getTransforms().add(new Rotate(-35.5377));
//		((Group) group).getChildren().add(gg);
		
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
