package com.example.jakub.mindmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;

import com.example.jakub.mindmap.handlers.NodesHandler;

/**
 * Created by Jakub on 2015-07-15.
 * Wlasny Layout, zeby mozna bylo rysowac
 */
public class DrawingLayout extends RelativeLayout{
    Paint drawPaint,drawPaint2;
    Path path = new Path();
    Path path2 = new Path();
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f, oldmScaleFactor=1.f;
    float focusX=1000.f, focusY=1000.f, oldfocusX =1000.f, oldfocusY =1000.f;
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
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint2 = new Paint(drawPaint);
        drawPaint2.setColor(Color.CYAN);
        mScaleDetector = new ScaleGestureDetector(context, mScaleListener);
        nodesHandler = new NodesHandler(context);

    }
    Float lastX = null;
    Float lastY = null;
    Node begNode = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float scaledX=((event.getRawX()-focusX)/mScaleFactor)+focusX,
                scaledY=((event.getRawY()-focusY)/mScaleFactor)+focusY;
        System.out.println("clicked X: " + event.getRawX() + " Y: " + event.getRawY());
        mScaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(scaledX,scaledY);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(scaledX,scaledY);
                break;
            case MotionEvent.ACTION_UP:
                touch_up(scaledX,scaledY);
                break;
        }
        return true;
    }

    public void touch_start(float rawX, float rawY) {
        if(Node.clickedNode((int) rawX, (int) rawY) != null) {
            Node node = Node.clickedNode((int) rawX, (int) rawY);
            System.out.println("Clicked "+ node);
            lastX = rawX;
            lastY = rawY;
            begNode = Node.nodeList.get(node.id);
            path.moveTo(begNode.x,begNode.y);
            path2.moveTo(begNode.x,begNode.y);
        }else{
            System.out.println("Clicked none");
            begNode = null;
        }
    }

    public void touch_move(float rawX, float rawY) {
        System.out.println("move");
        if(begNode != null)
        path2.lineTo(rawX, rawY);
    }

    public void touch_up(float rawX, float rawY) {
        path2.reset();
        System.out.println("touch up");
        if(begNode != null && Node.clickedNode((int) rawX, (int) rawY) == null) {
            Node node = new Node((int) rawX, (int) rawY, this);
            node.paint(this);
            nodesHandler.addNode(node);
            path.moveTo(begNode.x,begNode.y);
            path.lineTo(rawX, rawY);
        }

    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, drawPaint);
        canvas.drawPath(path2, drawPaint2);
        canvas.save();
        super.onDraw(canvas);
        float a,b;
        focusX=Math.max(0,Math.min(focusX,this.getWidth()));
        focusY=Math.max(0, Math.min(focusY, this.getHeight()));
        this.setScaleX(1.f);
        this.setScaleY(1.f);
        this.setPivotX(this.getWidth() / 2);
        this.setPivotY(this.getHeight() / 2);
        if (oldmScaleFactor*mScaleFactor-1!=0) {
            this.setTranslationX(0);
            this.setTranslationY(0);
            a = (oldfocusX) + ((focusX - oldfocusX) * (1 - mScaleFactor) / (1 - (oldmScaleFactor * mScaleFactor)));
            b = (oldfocusY) + ((focusY - oldfocusY) * (1 - mScaleFactor) / (1 - (oldmScaleFactor * mScaleFactor)));
            System.out.println("obecne a: " + a + " Obecne b: " + b);
            //Find new Pivot X
            this.setPivotX(a);
            //Find new Pivot Y
            this.setPivotY(b);
            }
        else{
            this.setTranslationX((focusX - oldfocusX) * (1 - mScaleFactor));
            this.setTranslationY((focusY - oldfocusY) * (1 - mScaleFactor));
        }
        this.setScaleX(mScaleFactor*oldmScaleFactor);
        this.setScaleY(mScaleFactor*oldmScaleFactor);
        canvas.restore();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(0, 0);
    }

    private final ScaleGestureDetector.OnScaleGestureListener mScaleListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        /**
         * This is the active focal point in terms of the viewport. Could be a local
         * variable but kept here to minimize per-frame allocations.
         */
        private PointF viewportFocus = new PointF();
        private float lastSpan;
        //private float lastSpanY;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            lastSpan = mScaleDetector.getCurrentSpan();
            oldmScaleFactor=mScaleFactor*oldmScaleFactor;
            mScaleFactor=1.f;
            if (oldmScaleFactor*mScaleFactor-1!=0) {
                oldfocusX = (oldfocusX) + ((focusX - oldfocusX) * (1 - mScaleFactor) / (1 - (oldmScaleFactor * mScaleFactor)));
                oldfocusY = (oldfocusY) + ((focusY - oldfocusY) * (1 - mScaleFactor) / (1 - (oldmScaleFactor * mScaleFactor)));
            }
            else{
                oldfocusX =focusX;
                oldfocusY =focusY;
            }
            focusX = scaleGestureDetector.getFocusX();
            focusY = scaleGestureDetector.getFocusY();
            System.out.println("FOCUSY: X: " + focusX + "; Y: "+ focusY );
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float spana = mScaleDetector.getCurrentSpan();
            mScaleFactor =  mScaleFactor*spana/lastSpan;
            lastSpan = spana;
            invalidate();
            return true;
             /*oldfocusX=focusX;
            oldfocusY=focusY;
            focusX = scaleGestureDetector.getFocusX();
            focusY = scaleGestureDetector.getFocusY();
            oldmScaleFactor=mScaleFactor;
            mScaleFactor=1.f;*/
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector){
           /* this.setPivotX(0f);
            this.setPivotY(0f);*/

            return;
        }
    };

}
