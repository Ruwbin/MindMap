package com.example.jakub.mindmap.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jakub.mindmap.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub on 2015-08-06.
 */
public class NodesHandler {
    private NodeDbHelper dbHandler;
    SQLiteDatabase db;

    public NodesHandler(Context context) {
        dbHandler = new NodeDbHelper(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    public long addNode(Node node){
        open();
        ContentValues values = new ContentValues();
        values.put(MapContract.NodeEntry.COLUMN_TEXT,node.getText());
        values.put(MapContract.NodeEntry.COLUMN_X,node.getX());
        values.put(MapContract.NodeEntry.COLUMN_Y, node.getY());
        values.put(MapContract.NodeEntry.COLUMN_PARENT, node.getParent().getId());

        long idRet = -1;
        try {
            idRet = db.insert(MapContract.NodeEntry.TABLE_NAME, null, values);
            System.out.println("ustawiam x na " + values.get(MapContract.NodeEntry.COLUMN_TEXT));

            Cursor cursor = db.query(MapContract.NodeEntry.TABLE_NAME,null,null,null,null,null,null,null);
            cursor.moveToFirst();

            System.out.println(cursor.getString(0) + "&" + cursor.getString(1) +"&"+ cursor.getString(2)+"&"+cursor.getString(3));
            cursor.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        close();
        return idRet;
    }

    public List<NodeBuilder> readNodeBuilderList(){
        open();
        LinkedList<NodeBuilder> nodeBuilder = new LinkedList<>();

        Cursor cursor = db.query(MapContract.NodeEntry.TABLE_NAME,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            System.out.println("x w builderze" + cursor.getInt(1)+cursor.getString(0));
            nodeBuilder.add(fromCursor(cursor));
            cursor.moveToNext();
        }
        close();
        cursor.close();
        return nodeBuilder;
    }

    private NodeBuilder fromCursor(Cursor cursor) {
        return new NodeBuilder(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4));

    }

    public class NodeBuilder {
        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        int parent;
        String text;
        int x;
        int y;

        public NodeBuilder(String text, int x, int y,int parent) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.parent = parent;
        }



        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
