package com.sshakuf.board.board;

/**
 * Created by sshakuf on 9/8/15.
 */

public class BoardEvents {}

     class ChangedEvent {

        protected Object sender;

        public ChangedEvent(Object inSender) {
            sender = inSender;
        }

        public Object getSender() {
            return sender;
        }
    }

     class GameEndEvent<T>{
        protected T sender;

        public GameEndEvent(T inSender) {
            sender = inSender;
        }

        public T getSender() {
            return sender;
        }
    }

    class DataRecivedEvent {
        protected String data;

        public DataRecivedEvent(String inData) {
            data = inData;
        }

        public String getData() {
            return data;
        }

    }

    class HandleTouchEvent{
        protected int position;

        public HandleTouchEvent(int inPosition) {
            position = inPosition;
        }

        public int getPosition() {
            return position;
        }


    }

