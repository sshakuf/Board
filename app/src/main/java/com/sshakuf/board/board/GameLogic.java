package com.sshakuf.board.board;

import android.util.Log;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by sshakuf on 9/7/15.
 */
public abstract class GameLogic {

    protected Board mBoard;

    public GameLogic(Board inBoard)
    {
        Initialize(inBoard);
        EventBus.getDefault().register(this);
    }


    public void Initialize(Board inBoard)
    {
        mBoard = inBoard;

    }

    public void RestartGame()
    {
        mBoard.InitializeArray();
    }


    @Subscribe
    public void onEventMainThread(HandleTouchEvent event){
        HandleInput(event.getPosition());
    }

    public abstract  void HandleInput(int inPosition);

    public abstract void CheckWinner();


    public void Idle()
    {
    }

    public abstract int Animate();


}
