package com.example.jakub.mindmap;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub on 2015-07-15.
 */
public class Node {
    Activity activity;
    EditText textView;
    int id = 0;
    volatile static int ID = 0;
    static List<Node> nodeList = new LinkedList<Node>();

    public Node(Activity activity,int x, int y) {
        id = ID++;
        this.activity = activity;
        nodeList.add(this);

        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.mainLayout);
        textView = new EditText(activity);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT ));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        setText("^^");
        setBgColor(Color.GREEN);
        layout.addView(textView,params);
    }
    public void setText(String text){
        textView.setText(text);
    }
    public void setBgColor(int color){
        textView.setBackgroundColor(color);
    }
}
