package com.sshakuf.board.board;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public abstract class BoardActivity extends AppCompatActivity {

    public class SRunnable implements Runnable {

        public volatile boolean stop = false;

        @Override
        public void run() {


      /* do what you need to do */
            int time = mLogic.Animate();
      /* and here comes the "trick" */
            if (!stop) {
                handlerAni.postDelayed(this, time);
            }
        }
    }


    private SRunnable runnableAni = new SRunnable();

    private Handler handlerAni = new Handler();

    Board mBoard;
    GameLogic mLogic;
    BT mBT;
    String shouldAnimate;

    GridView mGrid;
    ImageAdapter adapter;

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putSerializable("myBoard", mBoard);
    }

    @Override protected void onPause(){
        super.onPause();
        runnableAni.stop = true;
        //mBT.Disconnect();


    }

    @Override protected void onStop(){
        super.onStop();
        runnableAni.stop = true;
        mBT.Disconnect();
    }

    @Override protected void onResume(){
        super.onResume();
        runnableAni.stop = false;
        if (shouldAnimate.compareTo("0") !=0) {
            handlerAni.postDelayed(runnableAni, 1000);
        }
        mBT.Connect();
    }


    protected abstract GameLogic CreateGameLogic();
    protected abstract Board CreateBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        if (savedInstanceState != null) {
            mBoard = (Board)savedInstanceState.getSerializable("myBoard");
        }else {
            mBoard = CreateBoard();//new Board(8, 8);
        }

        mLogic = CreateGameLogic();//new FourinRowLogic(mBoard);

        mBT = new BT();
        mBT.context = getBaseContext();
        mBT.Initialize();

        mGrid = (GridView)findViewById(R.id.BoardGrid);

        adapter = new ImageAdapter(this, mBoard);
        mGrid.setAdapter(adapter);

        EventBus.getDefault().register(this);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.SharedPrefrencesKey, Context.MODE_PRIVATE);
        shouldAnimate = sharedpreferences.getString(Constants.sp_ShouldAnimateFall, "1");

//        if (shouldAnimate.compareTo("0") !=0) {
//            handler.postDelayed(runnable, 1000);
//        }

        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mLogic.HandleInput(position);

                Log.d(Board.TAG, "key pressed - " + position);

            }
        });


    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(ChangedEvent event){
        adapter.notifyDataSetChanged();
        mGrid.invalidateViews();

        // send new board viaBluetooth
        //mBT.sendData(mBoard.Print());


    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GameEndEvent<Board> event){


        Button b = (Button)findViewById(R.id.ButtonNewGame);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogic.RestartGame();
                adapter.notifyDataSetChanged();
                mGrid.invalidateViews();

                // send new board viaBluetooth
                mBT.sendData(mBoard.Print());
                v.setVisibility(View.GONE);
            }
        });
        b.setVisibility(View.VISIBLE);
        b.bringToFront();
        adapter.notifyDataSetChanged();
        mGrid.invalidateViews();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(DataRecivedEvent event) {
        String data = event.getData();
        Log.d(Board.TAG, data);
        if (data.length() > 3)
        {
            byte[] bytes = data.getBytes();
            if (bytes[0] == 'c' || bytes[0] == 'C')
            {

                int pos1 = Character.getNumericValue(bytes[1]);
                int pos2 = Character.getNumericValue(bytes[2]);

                //mLogic.HandleInput(pos1*mBoard.GetRows() + pos2);
                HandleTouchEvent e = new HandleTouchEvent(pos1 + pos2*mBoard.GetRows());
                EventBus.getDefault().post(e);

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board, menu);
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
