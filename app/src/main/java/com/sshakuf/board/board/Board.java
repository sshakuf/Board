package com.sshakuf.board.board;

import android.util.Log;

import java.io.Serializable;

import de.greenrobot.event.EventBus;

/**
 * Created by sshakuf on 9/7/15.
 */


public class Board implements Serializable{


    public static final String TAG = "Board";

    protected int mRow;
    protected int mCol;

    protected int[] mData;

    public Board(int inRow, int inCol)
    {
        mRow = inRow;
        mCol = inCol;

        InitializeArray();
    }

    public void InitializeArray()
    {
        mData = new int[mRow*mCol];

        for(int i=0; i<mRow; i++) {
            for(int j=0; j<mCol; j++) {
                mData[i*mRow + j] = 0;
            }
        }

        ChangedEvent event = new ChangedEvent(this);
        EventBus.getDefault().post(event);


    }

    public int GetCols() { return mCol; }
    public int GetRows() { return mRow; }
    public int[] GetData() {return mData; }


    String Print()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("B");
        //Log.d(TAG, "col " + mCol + ", row " + mRow);
        for (int r=0; r < mRow; r++)
        {
            for (int c=0; c < mCol; c++)
            {
                sb.append("" + mData[r * mRow + c]);
                //Log.d(TAG, "" + mData[r*mRow + c]);
                //Log.d(TAG, " | ");
            }
           // Log.d(TAG, "");
        }
        Log.d(TAG, sb.toString());
        return sb.toString();
    }


    void SetValue(int inRow, int inCol, int inColor)
    {
        SetValue(inRow + inCol * mRow, inColor);
//        ChangedEvent event = new ChangedEvent(this);
//        mData[inRow + inCol*mRow] = inColor;
//
//        EventBus.getDefault().post(event);

    }

    int GetValue(int inRow, int inCol)
    {
        return mData[inRow + inCol*mRow];
    }

    void SetValue(int inPosition, int inColor)
    {
        SetValue(inPosition, inColor, true);
    }
    void SetValue(int inPosition, int inColor, boolean inRaiseChanged)
    {
        mData[inPosition] = inColor;

        ChangedEvent event = new ChangedEvent(this);
        if (inRaiseChanged == true) {
            EventBus.getDefault().post(event);
        }


    }

    int GetValue(int inPosition)
    {
        return mData[inPosition];
    }



}
