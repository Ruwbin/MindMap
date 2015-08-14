package com.example.jakub.mindmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jakub.mindmap.handlers.NodesHandler;

import java.util.List;
import java.util.Objects;


public class ProjectModeActivity extends ActionBarActivity {

    NodesHandler nodesHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodesHandler = new NodesHandler(this);
        setContentView(R.layout.activity_main);
        //restore();
    }

   /* public void restore(){
        List<NodesHandler.NodeBuilder> nodeBuilderList = nodesHandler.readNodeBuilderList();
        if(nodeBuilderList.isEmpty()){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Node node = new Node(width/2,height/2,null);
            node.paint((DrawingLayout) findViewById(R.id.mainLayout));
            node.setText("MainNode");
            nodesHandler.addNode(node);
        }
        int i =0;
        for(NodesHandler.NodeBuilder nodeBuilder: nodeBuilderList){
            System.out.println("size "+i++ +" "+Node.nodeList.size());
            Node node = new Node(nodeBuilder.getX(),nodeBuilder.getY(),null);
            node.setParent(Node.nodeList.get(nodeBuilder.getParent()));

            node.paint((DrawingLayout) findViewById(R.id.mainLayout));
            node.setText(nodeBuilder.getText());
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        //kupastraszna
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
