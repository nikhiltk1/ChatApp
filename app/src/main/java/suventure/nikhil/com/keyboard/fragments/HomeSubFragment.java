package suventure.nikhil.com.keyboard.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import suventure.nikhil.com.chatlibrary.model.Chat;
import suventure.nikhil.com.chatlibrary.model.MyContact;
import suventure.nikhil.com.keyboard.R;
import suventure.nikhil.com.keyboard.adapter.ChatAdapter;
import suventure.nikhil.com.keyboard.adapter.ContactsAdapter;

public class HomeSubFragment extends Fragment {


    RecyclerView recyclerChat;
    ArrayList<Chat> chats;
    Context context;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_sub, container, false);


        context=getActivity();
        position = getArguments().getInt("position");
        recyclerChat=(RecyclerView)view.findViewById(R.id.recycler_chat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerChat.setLayoutManager(mLayoutManager);
        recyclerChat.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerChat.setLayoutManager(linearLayoutManager);
        recyclerChat.setHasFixedSize(true);

        if(position == 0)
        {

            Chat chat=new Chat();
            chats=chat.getAllChats(context);
            ChatAdapter requestsAdapter=new ChatAdapter(context,chats);
            recyclerChat.setAdapter(requestsAdapter);

        }else if(position == 1)
        {

            ArrayList<MyContact> myContacts=new ArrayList<>();

            ContentResolver contentResolver=context.getContentResolver();

            Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if(cur.getCount()>0)
            {
                while (cur.moveToNext()) {
                    MyContact myContact = new MyContact();
                    String s = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    myContact.setName(s);

                    Cursor cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                            new String[]{id},
                            null);

                    if (cursorPhone.moveToFirst()) {
                        String contactNumber;
                        contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        myContact.setNumber(contactNumber);
                    }

                    cursorPhone.close();
                    myContacts.add(myContact);
                }
                cur.close();

                Collections.sort(myContacts,new SortBasedOnName());
            }
            cur.close();


            ContactsAdapter contactsAdapter=new ContactsAdapter(context,myContacts);
            recyclerChat.setAdapter(contactsAdapter);

        }
    }
    private class SortBasedOnName implements Comparator{
        @Override
        public int compare(Object o, Object t1) {
            String s1=((MyContact)o).getName();
            String s2=((MyContact)t1).getName();
            return s1.compareToIgnoreCase(s2);
        }
    }
}
