package suventure.nikhil.com.keyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import suventure.nikhil.com.chatlibrary.model.Chat;
import suventure.nikhil.com.keyboard.activities.ChatActivity;
import suventure.nikhil.com.keyboard.R;

/**
 * Created by suventure on 3/4/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  {


    private Context context;
    private ArrayList<Chat> chats;
    private final static String TAG="ChatAdapter";

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public ChatAdapter(Context context, ArrayList<Chat> chats)
    {
        this.context=context;
        this.chats=chats;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.row_chat, parent, false);
        return new ChatAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Chat chat=chats.get(position);
        holder.textName.setText(chat.getName());
        Log.v(TAG,"Name : "+chat.getName());
        holder.layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class));

            }
        });


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textName;
        final LinearLayout layoutName;
        /*final TextView textToDate;
        final TextView hiphen;*/
        private ViewHolder(final View itemView) {
            super(itemView);
            textName=(TextView) itemView.findViewById(R.id.text_name);
            layoutName=(LinearLayout)itemView.findViewById(R.id.layout_name);

        }
    }

}
