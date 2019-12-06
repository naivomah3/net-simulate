/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.object;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author RANAIVOSON
 */
public class LineObject extends Line {
    public String hostNameBegin;
    public String hostNameEnd;
    public String interfaceNameBegin;
    public String interfaceNameEnd;
    public DoubleProperty startX;
    public DoubleProperty startY;
    public DoubleProperty endX;
    public DoubleProperty endY;
    
    
    public LineObject(DoubleProperty startXParam, DoubleProperty startYParam, DoubleProperty endXParam, DoubleProperty endYParam, String routerNameBeginP, String interfaceNameBeginP, String routerNanmeEndP, String interfaceNameEndP) {
        hostNameBegin = routerNameBeginP;
        hostNameEnd = routerNanmeEndP;
        interfaceNameBegin = interfaceNameBeginP;
        interfaceNameEnd = interfaceNameEndP;
        startX = startXParam;
        startY = startYParam;
        endX = endXParam;
        endY = endYParam;
        startXProperty().bind(startXParam);
        startYProperty().bind(startYParam);
        endXProperty().bind(endXParam);
        endYProperty().bind(endYParam);
        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(20.0, 1.0);
        setMouseTransparent(true);
    }

    public void updateStartXY(DoubleProperty startXParam, DoubleProperty startYParam) {
        startXProperty().bind(startXParam);
        startYProperty().bind(startYParam);
        startX = startXParam;
        startY = startYParam;
    }

    public void updateEndXY(DoubleProperty endXParam, DoubleProperty endYParam) {
        endXProperty().bind(endXParam);
        endYProperty().bind(endYParam);
        endX = endXParam;
        endY = endYParam;
    }
    
    /**********************
     * Getters and setters* 
     **********************/
    
    
}
