package com.example.accountasleep.ui.dashboard;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.accountasleep.R;


public class ImagesAdapter extends BaseAdapter {

    private final Context mContext;
    private final Uri[] images;
//    private final Integer[] images;

    // 1
    public ImagesAdapter(Context context, Uri[] images) {
        this.mContext = context;
        this.images = images;
    }

    // 2
    @Override
    public int getCount() {
        return images.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
//        final Integer image = images[position];
        final Uri image = images[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.image_item, null);
        }

        // 3
        // Uri path = Uri.parse("android.resource://com.example.accountasleep/" + images[position]);
        Uri path = images[position];
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        imageView.setImageURI(path);
//        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_book_name);
//        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);
//        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        // 4
//        imageView.setImageResource(book.getImageResource());
//        nameTextView.setText(mContext.getString(book.getName()));
//        authorTextView.setText(mContext.getString(book.getAuthor()));

        return convertView;
    }

}