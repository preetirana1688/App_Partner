package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData> {
    public ChatsArrayAdapter(Context context, List<ChatData> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatCell chatCell = new ChatCell();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);
        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.userImageView = (NetworkImageView) convertView.findViewById(R.id.userImageView);
        ChatData chatData = getItem(position);
        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);

        RequestQueue mRequestQueue;
        ImageLoader mImageLoader;
        mRequestQueue = Volley.newRequestQueue(getContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        chatCell.userImageView.setImageUrl(chatData.avatarURL, mImageLoader);
        return convertView;
    }

    private static class ChatCell {
        TextView usernameTextView;
        TextView messageTextView;
        NetworkImageView userImageView;
    }
}
