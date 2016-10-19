package com.sshakuf.board.board;

import android.util.Log;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by sshakuf on 9/7/15.
 */
public class FourinRowLogic extends GameLogic {


    protected int currentColor;
    protected int winner;

    public FourinRowLogic(Board inBoard)
    {
        super(inBoard);
        Initialize(inBoard);
//        EventBus.getDefault().register(this);
    }


    public void Initialize(Board inBoard)
    {
        super.Initialize(inBoard);
        currentColor = Constants.COLOR_RED;
        winner = Constants.COLOR_NONE;
    }

    public void RestartGame()
    {
        mBoard.InitializeArray();
        currentColor = Constants.COLOR_RED;
        winner = Constants.COLOR_NONE;
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


        // todo check that there is no value in this cell
        if (winner == Constants.COLOR_NONE) {
            //if (mBoard.GetValue(inPosition) == Constants.COLOR_NONE) {
                mBoard.SetValue(row, currentColor);
                NextColor();

                CheckWinner();
            //}
            //mBoard->Print();
        }
    }


// computer logic
// check if EOG

    void NextColor()
    {
        currentColor = currentColor == Constants.COLOR_RED ? Constants.COLOR_BLUE : Constants.COLOR_RED;
    }

    @Override
    public void Idle()
    {

    }

    @Override
    public  void CheckWinner() {
        winner = CheckWinnerLogic();
    }


    int CheckWinnerLogic() {
        int i, j;
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();
        int[] s = mBoard.GetData();
        //7 row 6 col

        //check horizontals
        for (i = 0; i <= rows - 4; i++)
            for (j = 0; j < cols; j++) {
                if (s[i + j * rows] == Constants.COLOR_RED && s[i + 1 + j * rows] == Constants.COLOR_RED && s[i + 2 + j * rows] == Constants.COLOR_RED && s[i + 3 + j * rows] == Constants.COLOR_RED)
                    return Constants.COLOR_RED;
                if (s[i + j * rows] == Constants.COLOR_BLUE && s[i + 1 + j * rows] == Constants.COLOR_BLUE && s[i + 2 + j * rows] == Constants.COLOR_BLUE && s[i + 3 + j * rows] == Constants.COLOR_BLUE)
                    return Constants.COLOR_BLUE;
            }

        //check verticals
        for (i = 0; i < rows; i++)
            for (j = 0; j <= cols - 4; j++) {
                if (s[i + j * rows] == Constants.COLOR_RED && s[i + (j + 1) * rows] == Constants.COLOR_RED && s[i + (j + 2) * rows] == Constants.COLOR_RED && s[i + (j + 3) * rows] == Constants.COLOR_RED)
                    return Constants.COLOR_RED;
                if (s[i + j * rows] == Constants.COLOR_BLUE && s[i + (j + 1) * rows] == Constants.COLOR_BLUE && s[i + (j + 2) * rows] == Constants.COLOR_BLUE && s[i + (j + 3) * rows] == Constants.COLOR_BLUE)
                    return Constants.COLOR_BLUE;
            }

        //check main diagonals (\)
        for (i = 0; i <= rows - 4; i++)
            for (j = cols - 1; j >= 4 - 1; j--) {
                if (s[i + j * rows] == Constants.COLOR_RED && s[i + 1 + (j - 1) * rows] == Constants.COLOR_RED && s[i + 2 + (j - 2) * rows] == Constants.COLOR_RED && s[i + 3 + (j - 3) * rows] == Constants.COLOR_RED)
                    return Constants.COLOR_RED;
                if (s[i + j * rows] == Constants.COLOR_BLUE && s[i + 1 + (j - 1) * rows] == Constants.COLOR_BLUE && s[i + 2 + (j - 2) * rows] == Constants.COLOR_BLUE && s[i + 3 + (j - 3) * rows] == Constants.COLOR_BLUE)
                    return Constants.COLOR_BLUE;
            }

        //check other diagonals (/)
        for (i = 0; i <= rows - 4; i++)
            for (j = 0; j <= cols - 4; j++) {
                if (s[i + j * rows] == Constants.COLOR_RED && s[i + 1 + (j + 1) * rows] == Constants.COLOR_RED && s[i + 2 + (j + 2) * rows] == Constants.COLOR_RED && s[i + 3 + (j + 3) * rows] == Constants.COLOR_RED)
                    return Constants.COLOR_RED;
                if (s[i + j * rows] == Constants.COLOR_BLUE && s[i + 1 + (j + 1) * rows] == Constants.COLOR_BLUE && s[i + 2 + (j + 2) * rows] == Constants.COLOR_BLUE && s[i + 3 + (j + 3) * rows] == Constants.COLOR_BLUE)
                    return Constants.COLOR_BLUE;
            }

        //check if stalement
        for (i = 0; i < rows; i++) {
                if (s[i] == 0)
                    return Constants.COLOR_NONE; //game haven't finished yet - there's at least one empty cell in a top of a row
        }

        return -1; //stalemate - board is full

    }

    @Override
    public int Animate()
    {
        int retVal;
        if (winner != Constants.COLOR_NONE)
        {
            retVal = AnimateWinner();
        }
        else {
            retVal = AnimateFall();
        }
        return retVal;
    }

    public int AnimateFall()
    {
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        int changed = 0;

        //Log.d(Board.TAG, "animate");
        for (int r=rows-2; r >=0  ; r--)
        {
            for (int c=0; c < cols; c++)
            {
                if (mBoard.GetValue((r)*rows + c) != Constants.COLOR_NONE && mBoard.GetValue((r+1)*rows + c) == Constants.COLOR_NONE)
                {
                    mBoard.SetValue(((r+1)*rows + c) ,mBoard.GetValue(r*rows + c), false);
                    mBoard.SetValue((r*rows + c), Constants.COLOR_NONE);
                    changed = 1;
                    break;
                }

            }
        }
        if (changed == 1) {
            CheckWinner();
        }

        return 150;
    }

    public int AnimateWinner()
    {
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        boolean done = true;

        //Log.d(Board.TAG, "animateWinner");
        for (int r=rows-1; r >=0  ; r--)
        {
            for (int c=0; c < cols; c++)
            {
                if (mBoard.GetValue((r)*rows + c) != winner)
                {
                    mBoard.SetValue((r*rows + c), winner, false);
                    done = false;
                    break;
                }

            }
        }
        mBoard.SetValue(0,0,winner);

        if (done) {
            winner = winner == 1? 2:1;
            //GameEndEvent<Board> event = new GameEndEvent<Board>(mBoard);
            //EventBus.getDefault().post(event);
        }

        return 150;
    }


    /*
    public int AnimateWinner()
    {
        int rows = mBoard.GetRows();
        int cols = mBoard.GetCols();

        boolean done = true;

        //Log.d(Board.TAG, "animateWinner");
        for (int r=rows-1; r >=0  ; r--)
        {
            for (int c=0; c < cols; c++)
            {
                if (mBoard.GetValue((r)*rows + c) != winner)
                {
                    mBoard.SetValue((r*rows + c), winner);
                    done = false;
                    break;
                }

            }
        }

        if (done) {
            GameEndEvent<Board> event = new GameEndEvent<Board>(mBoard);
            EventBus.getDefault().post(event);
        }

        return 200;
    }
*/

}
