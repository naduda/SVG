package jaxb.objects;

import java.util.StringTokenizer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public abstract class AShape extends Group {
	
	private static final double RECT_LINE_WIDTH = 1;
	private static final double MOUSE_DURATION_MILLS = 250;	
	private final Timeline timeline = new Timeline();
	
	private final Rectangle rect = new Rectangle();
	
	public AShape(boolean canSelecting) {
		if (canSelecting) {
			//Пунктирна лінія
			rect.getStrokeDashArray().addAll(2d, 5d);
			rect.setStrokeWidth(RECT_LINE_WIDTH);
			rect.setFill(Color.TRANSPARENT);
	
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.setAutoReverse(true);
			final KeyValue kv = new KeyValue(rect.strokeProperty(), Color.BLACK);
			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
			timeline.getKeyFrames().add(kf);
			
			Duration maxTimeBetweenSequentialClicks = Duration.millis(MOUSE_DURATION_MILLS);
	        PauseTransition clickTimer = new PauseTransition(maxTimeBetweenSequentialClicks);
	        final IntegerProperty sequentialClickCount = new SimpleIntegerProperty(0);
	        clickTimer.setOnFinished(event -> {
	            int count = sequentialClickCount.get();
	            if (count == 2) {
	            	onDoubleClick();
	            }
	
	            sequentialClickCount.set(0);
	        });
	        
		    setOnMouseClicked(event -> {
		    	setSelection(true);
		    	sequentialClickCount.set(sequentialClickCount.get() + 1);
	            clickTimer.playFromStart();
			});
		}
	}
			
	public AShape(Node g, boolean canSelecting) {		
		this(canSelecting);
		
		if (canSelecting) {
			rect.setX(g.getBoundsInLocal().getMinX());
			rect.setY(g.getBoundsInLocal().getMinY());
			rect.setWidth(g.getBoundsInLocal().getWidth());
			rect.setHeight(g.getBoundsInLocal().getHeight());
			((Group) g).getChildren().add(rect);		
		}
		getChildren().add(g);
	}
	
	private void setSelection(boolean isSelected) {
		if (isSelected) {
			rect.setStroke(Color.BLUE);
			rect.getStrokeDashArray().addAll(2d, 5d);
		} else {
			rect.setStroke(Color.TRANSPARENT);
		}
	}
	
	public Node getNodeById(String path) {
		Node ret = getChildren().get(0);
		StringTokenizer st = new StringTokenizer(path, "/");
		while (st.hasMoreElements()) {
			int id = Integer.parseInt(st.nextElement().toString());
			ret = ((Group)ret).getChildren().get(id);
		}
		ret = ((Group)ret).getChildren().get(0);
		return ret;
	}
	
	public abstract void onDoubleClick();
	
	public void rotate(Node n, double deg) {
		while (Math.abs(deg) > 180) {
			deg = deg > 0 ? deg - 360 : deg + 360;
		}
		n.setRotate(-deg);

		double adeg = Math.abs(deg);
		double sWidth = n.getBoundsInLocal().getWidth();
		double sHeight = n.getBoundsInLocal().getHeight();
		
		double sinD = Math.abs(Math.sin(Math.toRadians(adeg)));
		double cosD = Math.abs(Math.cos(Math.toRadians(adeg)));
		
		double sX = deg > 0 ? 
				deg <= 90 ? 
						0.5 * (sinD * sHeight - cosD * sWidth + sWidth) : 0.5 * (-sinD * sHeight + cosD * sWidth + sWidth) : 
				deg >= -90 ? 
						0.5 * (cosD * sWidth + sinD * sHeight - sWidth) : 0.5 * (cosD * sWidth + sinD * sHeight + sWidth);
		n.setTranslateX(-sX);
		
		double sY = deg > 0 ? 0.5 * (sinD * sWidth + cosD * sHeight - sHeight) : 0.5 * (sinD * sWidth - cosD * sHeight + sHeight);
		n.setTranslateY(deg > 0 ? -sY : sY);
	}
	
	public void setColor(Shape sh, String color) {
		sh.setStroke(Color.valueOf(color.toUpperCase()));
	}
	
	public void setFill(Shape sh, String color) {
		sh.setFill(Color.valueOf(color.toUpperCase()));
	}
}
