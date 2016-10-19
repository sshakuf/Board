package com.sshakuf.board.board;

import android.os.Handler;
import android.util.Log;

import java.util.Currency;
import java.util.Random;

import de.greenrobot.event.Subscribe;

/**
 * Created by sshakuf on 9/21/15.
 */
public class MemoryGameLogic extends GameLogic {

    public class HideCards implements Runnable {

        public volatile boolean stop = false;
        public int pos1;
        public int pos2;

        @Override
        public void run() {

            currentState = 0;
            if (pos1 != -1 ) {
                mBoard.SetValue(pos1, Constants.COLOR_NONE, false);
            }
            if (pos2 != -1) {
                mBoard.SetValue(pos2, Constants.COLOR_NONE);
            }

        }
    }

    private HideCards runnable = new HideCards();

    private Handler handler = new Handler();



    int currentPlayer;
    int currentState;

    int mData[];

    public MemoryGameLogic(Board inBoard)
    {
        super(inBoard);
    }


    public void Initialize(Board inBoard)
    {
        super.Initialize(inBoard);
        currentPlayer =0;
        currentState = 0;
        RestartGame();
    }

    public void RestartGame()
    {
        mBoard.InitializeArray();

        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        mData = new int[rows*cols];

        Random r = new Random();

        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                mData[i*rows + j] = r.nextInt(8)+1;
            }
        }



    }


    @Subscribe
    public void onEventMainThread(HandleTouchEvent event){
        HandleInput(event.getPosition());
    }

    @Override
    public void HandleInput(int inPosition)
    {

        Log.d(Board.TAG, "Handle Input pos-" + inPosition);

        int row = inPosition / mBoard.GetCols();
        row = inPosition - row * mBoard.GetRows();

        // check if we are hitting a cell that already has a color
        if (mBoard.GetValue(inPosition) == Constants.COLOR_NONE) {

            if (currentState == 0) {
                runnable.pos1 = inPosition;
                runnable.pos2 = -1;
                currentState = 1;
                mBoard.SetValue(inPosition, mData[inPosition]);

            } else if (currentState == 1) {
                currentState = 2;
                runnable.pos2 = inPosition;
                mBoard.SetValue(inPosition, mData[inPosition]);
                // start a timer
                if (mData[runnable.pos1] != mData[inPosition]) {
                    handler.postDelayed(runnable, 2000);
                } else {
                    runnable.pos1 = -1;
                    runnable.pos2 = -1;
                    currentState = 0;
                }
            } else if (currentState == 2) {      //to not good if press while in delay
                handler.postDelayed(runnable, 100);
            }
        }

}



    @Override
    public void Idle()
    {

    }

    @Override
    public  void CheckWinner() {
    }


    int CheckWinnerLogic() {
        int i, j;
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();
        int[] s = mBoard.GetData();
        //7 row 6 col

//        //check horizontals
//        for (i = 0; i <= rows - 4; i++)
//            for (j = 0; j < cols; j++) {
//                if (s[i + j * rows] == COLOR_RED && s[i + 1 + j * rows] == COLOR_RED && s[i + 2 + j * rows] == COLOR_RED && s[i + 3 + j * rows] == COLOR_RED)
//                    return COLOR_RED;
//                if (s[i + j * rows] == COLOR_BLUE && s[i + 1 + j * rows] == COLOR_BLUE && s[i + 2 + j * rows] == COLOR_BLUE && s[i + 3 + j * rows] == COLOR_BLUE)
//                    return COLOR_BLUE;
//            }
//

        return -1; //stalemate - board is full

    }

    @Override
    public int Animate()
    {
//        if (winner != COLOR_NONE)
//        {
//            retVal = AnimateWinner();
//        }
//        else {
//            retVal = AnimateFall();
//        }
//        return retVal;
        return 150;
    }

    public int AnimateFall()
    {
//        int rows = mBoard.GetRows();
//        int cols = mBoard.GetCols();
//
//        int changed = 0;
//

        return 150;
    }

    public int AnimateWinner()
    {
//        int rows = mBoard.GetRows();
//        int cols = mBoard.GetCols();
//
//
        return 150;
    }

}
