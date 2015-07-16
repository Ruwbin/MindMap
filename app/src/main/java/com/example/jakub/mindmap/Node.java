package com.example.jakub.mindmap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub on 2015-07-15.
 */
public class Node {
    DrawingLayout drawingLayout;
    TextView textView;
    int id = 0;
    volatile static int ID = 0;
    public static List<Node> nodeList = new LinkedList<Node>(); //zmienic na mape
    int x,y;

    public Node(DrawingLayout drawingLayout,int x, int y) {
        this.x = x;
        this.y = y;
        id = ID++;
        this.drawingLayout = drawingLayout;
        nodeList.add(this);

        textView = new TextView(drawingLayout.getContext());
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT ));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        textView.setTextSize(22);
        setText("id: " + id);
        setBgColor(Color.GREEN);
        drawingLayout.addView(textView,params);
    }
    public void setText(String text){
        textView.setText(text);
    }
    public void setBgColor(int color){
        textView.setBackgroundColor(color);
    }
    public int getHeight(){
        textView.measure(0, 0);
        return textView.getMeasuredHeight();
    }
    public int getWidth(){
        textView.measure(0, 0);
        return textView.getMeasuredWidth();
    }
    static Node clickedNode(int clickedX,int clickedY){

        System.out.println("clickedX,Y" + clickedX + " " + clickedY);
        Node tempNode = null;
        for(Node node : nodeList){
            if(node.x <= clickedX && clickedX<= node.x + node.getWidth()
                    && node.y <= clickedY && clickedY <= node.y + node.getHeight()){
                tempNode = node;
                break;
            }
        }
        return tempNode;
    }

    @Override
    public String toString() {
        return "Node "+id;
    }
}
