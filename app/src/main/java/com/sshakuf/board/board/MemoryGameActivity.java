package com.sshakuf.board.board;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MemoryGameActivity extends BoardActivity {




    @Override
    protected GameLogic CreateGameLogic()
    {
        return new MemoryGameLogic(mBoard);
    }

    @Override
    protected Board CreateBoard()
    {
        return new Board(8,8);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memory_game, menu);
        return true;
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
