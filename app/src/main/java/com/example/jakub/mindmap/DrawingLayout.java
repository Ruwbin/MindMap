package com.example.jakub.mindmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;

import com.example.jakub.mindmap.handlers.NodesHandler;

import java.util.List;

/**
 * Created by Jakub on 2015-07-15.
 * Wlasny Layout, zeby mozna bylo rysowac
 */
public class DrawingLayout extends RelativeLayout {
    Paint drawPaint, drawPaint2;
    Path path = new Path();
    Path restorePath = new Path();
    Path path2 = new Path();
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f, oldmScaleFactor = 1.f;
    float pivX = 0, pivY = 0;
    float focusX = 1.f, focusY = 1.f, oldfocusX = 0.f, oldfocusY = 0.f;
    NodesHandler nodesHandler;

    public DrawingLayout(Context context) { //trzy konstruktory musza byc
        this(context, null, 0);
    }

    public DrawingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false); // inaczej nie wywola onDraw
        setFocusable(true);
        setFocusableInTouchMode(true);
        drawPaint = new Paint();
        drawPaint.setColor(Color.RED);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(30);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint2 = new Paint(drawPaint);
        drawPaint2.setColor(Color.CYAN);
        mScaleDetector = new ScaleGestureDetector(context, mScaleListener);
        nodesHandler = new NodesHandler(context);
        this.setTranslationX(0);
        this.setTranslationY(0);
        this.setPivotY(0);
        this.setPivotX(0);
        restore();
        restorePaths();
    }

    private void restorePaths() {
        System.out.println("chuj" + Node.nodeList.size());
        int i = 0;
        for (Node node : Node.nodeList) {
            System.out.println("dupa " + i++ + " " + node.getParent());
            if (node.parent != null) {
                restorePath.moveTo(node.x, node.y);
                restorePath.lineTo(node.parent.x, node.parent.y);
            }
        }
    }

    public void restore(){
        List<NodesHandler.NodeBuilder> nodeBuilderList = nodesHandler.readNodeBuilderList();
        if(nodeBuilderList.isEmpty()){
            int width = this.getWidth();
            int height = this.getHeight();;
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
    }

    Float lastX = null;
    Float lastY = null;
    Node begNode = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                touch_up(event.getX(), event.getY());
                break;
        }
        return true;
    }

    public void touch_start(float rawX, float rawY) {
        if (Node.clickedNode((int) rawX, (int) rawY) != null) {
            Node node = Node.clickedNode((int) rawX, (int) rawY);
            System.out.println("Clicked " + node);
            lastX = rawX;
            lastY = rawY;
            begNode = Node.nodeList.get(node.id);
            path.moveTo(begNode.x, begNode.y);
            path2.moveTo(begNode.x, begNode.y);
        } else {
            System.out.println("Clicked none");
            begNode = null;
        }
    }

    public void touch_move(float rawX, float rawY) {
        System.out.println("move");
        if (begNode != null) {
            path2.lineTo(rawX, rawY);
            invalidate();
        }
    }

    public void touch_up(float rawX, float rawY) {
        path2.reset();
        System.out.println("touch up");
        if (begNode != null && Node.clickedNode((int) rawX, (int) rawY) == null) {
            Node node = new Node((int) rawX, (int) rawY, begNode);
            node.paint(this);
            nodesHandler.addNode(node);
            path.moveTo(begNode.x, begNode.y);
            path.lineTo(rawX, rawY);
        }

    }

    private void scaleLayout() {
        oldfocusX = (focusX - getPivotX()) * mScaleFactor + getPivotX();
        oldfocusY = (focusY - getPivotY()) * mScaleFactor + getPivotY();
        System.out.println("Transl: X: " + (getTranslationX() + oldfocusX - focusX) + "; Y: " + (getTranslationY() + oldfocusY - focusY));
        System.out.println("Focusy: X: " + focusX + "; Y: " + focusY);
        System.out.println("Oldfoc: X: " + oldfocusX + "; Y: " + oldfocusY);
        System.out.println("MScale: X: " + getScaleX() + "; Y: " + getScaleY());
        System.out.println("Pivoty: X: " + getPivotX() + "; Y: " + getPivotY());

        this.setPivotY(focusY);
        this.setPivotX(focusX);

        this.setTranslationX(getTranslationX() + oldfocusX - focusX);
        this.setTranslationY(getTranslationY() + oldfocusY - focusY);

        return;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);
        canvas.drawPath(path2, drawPaint2);
        canvas.drawPath(restorePath, drawPaint);
        super.onDraw(canvas);
        if (Math.max(oldfocusX - focusX, focusX - oldfocusX) > 3) {
            scaleLayout();
        }
        this.setScaleX(mScaleFactor);
        this.setScaleY(mScaleFactor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(0, 0);
    }

    private final ScaleGestureDetector.OnScaleGestureListener mScaleListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        private float lastSpan;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            lastSpan = mScaleDetector.getCurrentSpan();


            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float spana = mScaleDetector.getCurrentSpan();
            mScaleFactor = mScaleFactor * spana / lastSpan;
            focusX = scaleGestureDetector.getFocusX();
            focusY = scaleGestureDetector.getFocusY();
            invalidate();
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

            return;
        }
    };

}
