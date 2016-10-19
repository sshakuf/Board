package com.sshakuf.board.board;

import android.os.Handler;
import android.util.Log;

import java.util.Random;

import de.greenrobot.event.Subscribe;

/**
 * Created by sshakuf on 9/28/15.
 */
public class FallGameLogic extends GameLogic {

    public class FallGameRunnable implements Runnable {

        public volatile boolean stop = false;
        public int pos1;
        public int pos2;

        int delay = 2000;
        @Override
        public void run() {

            Step();
            if (currentState > 8)
            {
                delay-=200;
                currentState = 0;
                delay = delay < 800 ? 800 : delay;
            }
            if (!stop) {
                handler.postDelayed(runnable, delay);
            }

        }
    }

    private FallGameRunnable runnable;

    private Handler handler;


    int currentPlayer;
    int currentState;
    int currentColor;
    int winner;

    int mData[];

    public FallGameLogic(Board inBoard) {
        super(inBoard);
    }


    public void Initialize(Board inBoard) {
        super.Initialize(inBoard);
        currentPlayer = 0;
        currentState = 0;
        currentColor = 0;
        winner = 0;
        RestartGame();

        runnable = new FallGameRunnable();
        handler = new Handler();
        handler.postDelayed(runnable, 100);
    }

    public void RestartGame() {
        mBoard.InitializeArray();

        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        mData = new int[rows * cols];

        Random r = new Random();

        currentColor = r.nextInt(8) + 1;

        for (int i = 0; i < rows-1; i++) {
            for (int j = 0; j < cols; j++) {
                mBoard.SetValue(i * rows + j , Constants.COLOR_NONE, false);
            }
        }

        for (int j = 0; j < cols; j++) {
            mBoard.SetValue((cols-1) * rows + j, currentColor);
        }

    }


    public void Step()
    {
        currentState ++;
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        // check end game!
        CheckWinner();

        if (winner == 0) {
            Random r = new Random();
            for (int i = rows - 2; i > 0; i--) {
                for (int j = 0; j < cols; j++) {
                    mBoard.SetValue(i * rows + j, mBoard.GetValue((i - 1) * rows + j), false);
                }
            }

            for (int j = 0; j < cols - 1; j++) {
                mBoard.SetValue(j, r.nextInt(8) + 1, false);
            }
            mBoard.SetValue(cols - 1, r.nextInt(8) + 1);
        }

    }


    @Subscribe
    public void onEventMainThread(HandleTouchEvent event) {
        HandleInput(event.getPosition());
    }

    @Override
    public void HandleInput(int inPosition) {

        Log.d(Board.TAG, "Handle Input pos-" + inPosition);

        int row = inPosition / mBoard.GetCols();
        row = inPosition - row * mBoard.GetRows();

        // check if we are hitting a cell that already has a color
        if (mBoard.GetValue(inPosition) == currentColor) {
            mBoard.SetValue(inPosition, Constants.COLOR_NONE);
        }
    }

    @Override
    public  void CheckWinner() {

        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        for (int j = 0; j < cols; j++) {
            if (mBoard.GetValue((cols - 2) * rows + j) == currentColor)
            {
                winner = 1;
                runnable.stop=true;
            }
        }

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


}
