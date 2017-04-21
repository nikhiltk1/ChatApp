package suventure.nikhil.com.keyboard.adapter;

import java.util.ArrayList;


import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import suventure.nikhil.com.keyboard.R;

public class EmoticonsPagerAdapter extends PagerAdapter {

	private ArrayList<Integer> emoticons;
	private static final int NO_OF_EMOTICONS_PER_PAGE = 20;
	private FragmentActivity mActivity;
	private EmoticonsGridAdapter.KeyClickListener mListener;
	EditText content;

	public EmoticonsPagerAdapter(FragmentActivity activity,
			ArrayList<Integer> emoticons, EmoticonsGridAdapter.KeyClickListener listener,EditText content) {
		this.emoticons = emoticons;
		this.mActivity = activity;
		this.mListener = listener;
		this.content=content;
	}

	@Override
	public int getCount() {
		return (int) Math.ceil((double) emoticons.size()
				/ (double) NO_OF_EMOTICONS_PER_PAGE);
	}

	@Override
	public Object instantiateItem(View collection, int position) {

		View layout = mActivity.getLayoutInflater().inflate(
				R.layout.emoticons_grid, null);

		int initialPosition = position * NO_OF_EMOTICONS_PER_PAGE;
		ArrayList<Integer> emoticonsInAPage = new ArrayList<Integer>();

		for (int i = initialPosition; i < initialPosition
				+ NO_OF_EMOTICONS_PER_PAGE
				&& i < emoticons.size(); i++) {
			emoticonsInAPage.add(emoticons.get(i));
		}

		GridView grid = (GridView) layout.findViewById(R.id.emoticons_grid);
		EmoticonsGridAdapter adapter = new EmoticonsGridAdapter(
				mActivity.getApplicationContext(), emoticonsInAPage, position,
				mListener,content);
		grid.setAdapter(adapter);

		((ViewPager) collection).addView(layout);

		return layout;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

/*
	@Override
	public CharSequence getPageTitle(int position) {
		return "A";
	}*/

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}