package suventure.nikhil.com.keyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import suventure.nikhil.com.chatlibrary.model.Chat;
import suventure.nikhil.com.chatlibrary.model.MyContact;
import suventure.nikhil.com.keyboard.R;
import suventure.nikhil.com.keyboard.activities.ChatActivity;

/**
 * Created by suventure on 3/4/17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<MyContact> chats;
    private final static String TAG="ChatAdapter";

    public void setChats(ArrayList<MyContact> chats) {
        this.chats = chats;
    }

    public ContactsAdapter(Context context, ArrayList<MyContact> chats)
    {
        this.context=context;
        this.chats=chats;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.row_contact, parent, false);
        return new ContactsAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final MyContact myContact=chats.get(position);
        holder.textName.setText(myContact.getName());
        holder.textNumber.setText(myContact.getNumber());
        Log.v(TAG,"Name : "+myContact.getName());
        holder.layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textName,textNumber;
        final LinearLayout layoutName;
        private ViewHolder(final View itemView) {
            super(itemView);
            textName=(TextView) itemView.findViewById(R.id.text_name);
            textNumber=(TextView) itemView.findViewById(R.id.text_number);
            layoutName=(LinearLayout)itemView.findViewById(R.id.layout_name);

        }
    }

}
