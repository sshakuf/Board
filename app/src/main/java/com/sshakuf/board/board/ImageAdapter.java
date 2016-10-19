package com.sshakuf.board.board;

/**
 * Created by sshakuf on 9/7/15.
 */


        import android.content.Context;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Board mBoard;

    public ImageAdapter(Context context, Board inBoard) {
        this.context = context;
        this.mBoard = inBoard;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.board_piece_layout, null);


        } else {
            gridView = (View) convertView;
        }

        // set value into textview
//            TextView textView = (TextView) gridView
//                    .findViewById(R.id.grid_item_label);
//            textView.setText("" + mBoard.GetValue(position));

        // set image based on selected text
        ImageView imageView = (ImageView) gridView
                .findViewById(R.id.grid_item_image);


        int val = mBoard.GetValue(position);

        switch(val)
        {
            case Constants.COLOR_NONE:
                imageView.setBackgroundColor(0xff577078);
                break;
            case Constants.COLOR_BLUE:
                imageView.setBackgroundColor(0xff3651EB);
                break;
            case Constants.COLOR_RED:
                imageView.setBackgroundColor(0xffF21B1B);
                break;
            case Constants.COLOR_CYAN:
                imageView.setBackgroundColor(0xff008989);
                break;
            case Constants.COLOR_GREEN:
                imageView.setBackgroundColor(0xff21BF00);
                break;
            case Constants.COLOR_LIGHTGREEN:
                imageView.setBackgroundColor(0xff7CB96F);
                break;
            case Constants.COLOR_PINK:
                imageView.setBackgroundColor(0xffE4218F);
                break;
            case Constants.COLOR_PURPLE:
                imageView.setBackgroundColor(0xff9775AA);
                break;
            case Constants.COLOR_YELLOW:
                imageView.setBackgroundColor(0xffFFFF25);
                break;

        }

//        if (val == Constants.COLOR_RED) {
//            //imageView.setImageResource(R.drawable.ios_logo);
//            imageView.setBackgroundColor(0xffF21B1B);
//        } else if (val == Constants.COLOR_BLUE) {
////                imageView.setImageResource(R.drawable.blackberry_logo);
//            imageView.setBackgroundColor(0xff3651EB);
//        } else {
////                imageView.setImageResource(R.drawable.android_logo);
//            imageView.setBackgroundColor(0xff577078);
//        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mBoard.GetData().length;
    }

    @Override
    public Object getItem(int position) {
        return mBoard.GetValue(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
