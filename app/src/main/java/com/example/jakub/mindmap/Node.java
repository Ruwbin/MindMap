package com.example.jakub.mindmap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
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
    TextView textView;
    String text;
    int id = 0;
    DrawingLayout mDrawingLayout;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    volatile static int ID = 0;
    public static List<Node> nodeList = new LinkedList<Node>(); //zmienic na mape
    int x, y;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        id = ID++;
        nodeList.add(this);
    }

    public Node(int x, int y, DrawingLayout drawingLayout) {
        this.x = x;
        this.y = y;
        id = ID++;
        this.mDrawingLayout=drawingLayout;
        nodeList.add(this);
    }

    public void paint(DrawingLayout drawingLayout) {
        this.mDrawingLayout=drawingLayout;
        textView = new TextView(drawingLayout.getContext());
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        textView.setTextSize(22);

        setText("id: " + id);
        text = String.valueOf("id: " + id);
        setBgColor(Color.GREEN);
        drawingLayout.addView(textView, params);
    }

    public void setText(String text) {
        textView.setText(text);
        this.text = text;
    }

    public void setBgColor(int color) {
        textView.setBackgroundColor(color);
    }

    public int getHeight() {
        textView.measure(0, 0);
        return textView.getMeasuredHeight();
    }

    public int getWidth() {
        textView.measure(0, 0);
        return textView.getMeasuredWidth();
    }


    static Node clickedNode(int clickedX, int clickedY) {
        System.out.println("clickedX,Y" + clickedX + " " + clickedY);
        Node tempNode = null;
        for (Node node : nodeList) {
            if (node.x <= clickedX && clickedX <= node.x + node.getWidth()
                    && node.y <= clickedY && clickedY <= node.y + node.getHeight()) {
                tempNode = node;
                break;
            }
        }
        return tempNode;
    }

    @Override
    public String toString() {
        return "Node " + id;
    }
}
