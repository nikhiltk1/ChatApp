package suventure.nikhil.com.keyboard.adapter;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import suventure.nikhil.com.keyboard.R;

public class EmoticonsGridAdapter extends BaseAdapter{
	
	private ArrayList<Integer> paths;
	private int pageNumber;
	private Context mContext;
	
	KeyClickListener mListener;
	EditText content;
	LinearLayout layout_emoticons_item;
	
	public EmoticonsGridAdapter(Context context, ArrayList<Integer> paths, int pageNumber, KeyClickListener listener, EditText content) {
		this.mContext = context;
		this.paths = paths;
		this.pageNumber = pageNumber;
		this.mListener = listener;
		this.content=content;
	}
	public View getView(final int position, View convertView, ViewGroup parent){

		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.emoticons_item, null);
		}


		layout_emoticons_item=(LinearLayout)v.findViewById(R.id.layout_emoticons_item);
		
		TextView text = (TextView) v.findViewById(R.id.text_emoji);
		text.setText(getEmojiByUnicode(paths.get(position)));


		layout_emoticons_item.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				content.setText(content.getText().toString()+getEmojiByUnicode(paths.get(position)));

				content.setSelection(content.getText().length());
			}
		});

		return v;
	}
	
	@Override
	public int getCount() {		
		return paths.size();
	}
	
	@Override
	public Integer getItem(int position) {
		return paths.get(position);
	}
	
	@Override
	public long getItemId(int position) {		
		return position;
	}
	
	public int getPageNumber () {
		return pageNumber;
	}
	
	private Bitmap getImage (String path) {
		AssetManager mngr = mContext.getAssets();
		InputStream in = null;
		
		 try {
				in = mngr.open("emoticons/" + path);
		 } catch (Exception e){
					e.printStackTrace();
		 }
		 
		 //BitmapFactory.Options options = new BitmapFactory.Options();
		 //options.inSampleSize = chunks;
		 
		 Bitmap temp = BitmapFactory.decodeStream(in ,null ,null);
		 return temp;
	}
	
	public interface KeyClickListener {
		
		public void keyClickedIndex(String index);
	}

	public String getEmojiByUnicode(int unicode){
		return new String(Character.toChars(unicode));
	}


}
