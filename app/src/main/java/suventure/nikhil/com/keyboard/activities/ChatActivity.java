package suventure.nikhil.com.keyboard.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import suventure.nikhil.com.keyboard.adapter.ChatListAdapter;
import suventure.nikhil.com.keyboard.adapter.EmoticonsGridAdapter;
import suventure.nikhil.com.keyboard.adapter.EmoticonsPagerAdapter;
import suventure.nikhil.com.keyboard.R;


/**
 * Main activity for chat window
 *
 * @author Nikhil T K
 */
public class ChatActivity extends AppCompatActivity implements EmoticonsGridAdapter.KeyClickListener {

	private static final int NO_OF_EMOTICONS = 54;

	private ListView chatList;
	private View popUpView;
	private ArrayList<Spanned> chats;
	private ChatListAdapter mAdapter;

	private LinearLayout emoticonsCover;
	private PopupWindow popupWindow;
	private int keyboardHeight;
	private EditText content;
	private LinearLayout parentLayout;

	private boolean isKeyBoardVisible;

	private Bitmap[] emoticons;

	private EmoticonsPagerAdapter mCustomPagerAdapter;
	ArrayList<ArrayList<Integer>> icons=new ArrayList<>();
	ViewPager pager;
	View view;
	TabLayout tabLayout;
	ImageView imageBack;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		chatList = (ListView) findViewById(R.id.chat_list);
		view=findViewById(R.id.footer_layout);

		parentLayout = (LinearLayout) findViewById(R.id.list_parent);

		emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);

		popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);

		// Setting adapter for chat list
		chats = new ArrayList<Spanned>();
		mAdapter = new ChatListAdapter(getApplicationContext(), chats);
		chatList.setAdapter(mAdapter);
		chatList.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow.isShowing())
					popupWindow.dismiss();
				return false;
			}
		});

		// Defining default height of keyboard which is equal to 230 dip
		final float popUpheight = getResources().getDimension(
				R.dimen.keyboard_height);
		changeKeyboardHeight((int) popUpheight);

		content = (EditText) findViewById(R.id.chat_content);

		tabLayout = (TabLayout)popUpView.findViewById(R.id.tabs);
		imageBack= (ImageView) popUpView.findViewById(R.id.back);
		imageBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				int length = content.getText().toString().length();
				if (length > 1) {
					Pattern pattern = Pattern.compile("^[a-zA-Z0-9 ]*$");
					Matcher mat = pattern.matcher(content.getText().toString().substring(content.getText().length()-1));

					if(!mat.matches())
					{
						content.getText().delete(length - 2, length);
					}else {
						content.getText().delete(length - 1, length);
					}
				}else if(length==1)
				{
					content.getText().delete(length - 1, length);
				}
			}
		});
		enablePopUpView();
		checkKeyboardHeight(parentLayout);
		enableFooterView();
		// Showing and Dismissing pop up on clicking emoticons button
		ImageView emoticonsButton = (ImageView) findViewById(R.id.emoticons_button);
		emoticonsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!popupWindow.isShowing()) {
					checkKeyboardHeight(parentLayout);


					popupWindow.setHeight((int) (keyboardHeight));

					popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);
					if (isKeyBoardVisibleMethod()) {
						// keyboard is opened
						emoticonsCover.setVisibility(LinearLayout.GONE);
					}
					else {
						// keyboard is closed
						emoticonsCover.setVisibility(LinearLayout.VISIBLE);
					}

				} else {
					popupWindow.dismiss();
				}

			}
		});

	}

	private void enableFooterView() {


		content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (popupWindow.isShowing()) {

					popupWindow.dismiss();

				}

			}
		});
		final ImageView imageSend = (ImageView) findViewById(R.id.image_send);

		imageSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (content.getText().toString().length() > 0) {

					Spanned sp = content.getText();
					chats.add(sp);
					content.setText("");
					mAdapter.notifyDataSetChanged();

				}

			}
		});
	}

	/**
	 * Overriding onKeyDown for dismissing keyboard on key down
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * Checking keyboard height and keyboard visibility
	 */
	int previousHeightDiffrence = 0;


	private boolean isKeyBoardVisibleMethod()
	{
		Rect r = new Rect();
		parentLayout.getWindowVisibleDisplayFrame(r);
		int screenHeight = parentLayout.getRootView().getHeight();

		// r.bottom is the position above soft keypad or device button.
		// if keypad is shown, the r.bottom is smaller than that before.
		int keypadHeight = screenHeight - r.bottom;

		Log.d("ChatActivity", "keypadHeight = " + keypadHeight);

		if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
			// keyboard is opened

			return true;
		}
		else {
			// keyboard is closed
			return false;
		}
	}
	private void checkKeyboardHeight(final View parentLayout) {

		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {

						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);

						int screenHeight = parentLayout.getRootView()
								.getHeight();
						int heightDifference = screenHeight - (r.bottom);

						if (previousHeightDiffrence - heightDifference > 50) {
							popupWindow.dismiss();
						}

						previousHeightDiffrence = heightDifference;
						if (heightDifference > 100) {

							isKeyBoardVisible = true;
							changeKeyboardHeight(heightDifference-110);

						} else {

							isKeyBoardVisible = false;

						}

					}
				});

	}

	/**
	 * change height of emoticons keyboard according to height of actual
	 * keyboard
	 *
	 * @param height
	 *            minimum height by which we can make sure actual keyboard is
	 *            open or not
	 */
	private void changeKeyboardHeight(int height) {

		if (height > 100) {
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, keyboardHeight);
			emoticonsCover.setLayoutParams(params);
		}

	}

	/**
	 * Defining all components of emoticons keyboard
	 */
	private void enablePopUpView() {

		pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);

/*
		LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
		linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.GRAY);
		drawable.setSize(1, 1);
		linearLayout.setDividerPadding(10);
		linearLayout.setDividerDrawable(drawable);
*/


		ArrayList<Integer> unicodes=new ArrayList<>();
		unicodes.add(0x1F601);
		unicodes.add(0x1F602);
		unicodes.add(0x1F603);
		unicodes.add(0x1F604);
		unicodes.add(0x1F605);
		unicodes.add(0x1F606);
		unicodes.add(0x1F609);
		unicodes.add(0x1F60A);
		unicodes.add(0x1F60B);
		unicodes.add(0x1F60E);
		unicodes.add(0x1F60D);
		unicodes.add(0x1F618);
		unicodes.add(0x1F617);
		unicodes.add(0x1F619);
		unicodes.add(0x1F61A);
		unicodes.add(0x1F60B);
		unicodes.add(0x1F60C);
		unicodes.add(0x1F60D);
		unicodes.add(0x1F60F);
		unicodes.add(0x1F612);
		unicodes.add(0x1F613);
		unicodes.add(0x1F614);
		unicodes.add(0x1F616);
		unicodes.add(0x1F618);
		unicodes.add(0x1F61A);
		unicodes.add(0x1F61C);
		unicodes.add(0x1F61D);
		unicodes.add(0x1F61E);
		unicodes.add(0x1F620);
		unicodes.add(0x1F621);
		unicodes.add(0x1F621);
		unicodes.add(0x1F621);
		icons.add(unicodes);
		unicodes=new ArrayList<>();
		unicodes.add(0x1F61A);
		unicodes.add(0x1F61C);
		unicodes.add(0x1F61D);
		unicodes.add(0x1F61E);
		unicodes.add(0x1F620);
		unicodes.add(0x1F621);
		unicodes.add(0x1F622);
		unicodes.add(0x1F623);
		unicodes.add(0x1F624);
		unicodes.add(0x1F625);
		unicodes.add(0x1F628);
		unicodes.add(0x1F629);
		unicodes.add(0x1F62A);
		unicodes.add(0x1F62B);
		unicodes.add(0x1F62D);
		unicodes.add(0x1F630);
		unicodes.add(0x1F631);
		unicodes.add(0x1F632);
		unicodes.add(0x1F633);
		unicodes.add(0x1F635);
		unicodes.add(0x1F637);
		unicodes.add(0x1F638);
		unicodes.add(0x1F639);
		unicodes.add(0x1F63A);
		unicodes.add(0x1F63B);
		unicodes.add(0x1F63C);
		unicodes.add(0x1F63D);
		unicodes.add(0x1F63E);
		unicodes.add(0x1F63F);
		unicodes.add(0x1F640);
		unicodes.add(0x1F645);
		unicodes.add(0x1F646);
		unicodes.add(0x1F647);
		unicodes.add(0x1F648);
		unicodes.add(0x1F649);
		unicodes.add(0x1F64A);
		unicodes.add(0x1F64B);
		unicodes.add(0x1F64C);
		unicodes.add(0x1F64D);
		unicodes.add(0x1F64E);
		unicodes.add(0x1F64F);

		icons.add(unicodes);



		mCustomPagerAdapter = new EmoticonsPagerAdapter(ChatActivity.this,unicodes,this,content);

		pager.setAdapter(mCustomPagerAdapter);
		pager.destroyDrawingCache();


		tabLayout.post(new Runnable() {
			@Override
			public void run() {
				tabLayout.setupWithViewPager(pager);
				tabLayout.getTabAt(0).setIcon(R.drawable.smile_human_24);
				tabLayout.getTabAt(1).setIcon(R.drawable.smile_monkey_24);
				tabLayout.getTabAt(2).setIcon(R.drawable.smiley_cup_24);
				for (int i = 0; i < tabLayout.getTabCount(); i++) {
					//tabLayout.getTabAt(i).setIcon(R.drawable.);
				}
               /* for (int j = 0; j < tabLayout.getTabCount(); j++) {
                    Log.v("Home_Fragment","aaa for loop");
                    tabLayout.getTabAt(j).setIcon(R.drawable.ic_newspaper_black_36dp);
                }*/
			}
		});

		// Creating a pop window for emoticons keyboard
		popupWindow = new PopupWindow(popUpView, LayoutParams.MATCH_PARENT,
				(int) keyboardHeight, false);
		/*
		TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
		backSpace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
				content.dispatchKeyEvent(event);	
			}
		});*/

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				emoticonsCover.setVisibility(LinearLayout.GONE);
			}
		});
	}














/*
	private void enablePopUpView1() {

		ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
		pager.setOffscreenPageLimit(3);

		ArrayList<String> paths = new ArrayList<String>();

		for (short i = 1; i <= NO_OF_EMOTICONS; i++) {
			paths.add(i + ".png");
		}

		EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(ChatActivity.this, paths, this);
		pager.setAdapter(adapter);

		// Creating a pop window for emoticons keyboard
		popupWindow = new PopupWindow(popUpView, LayoutParams.MATCH_PARENT,
				(int) keyboardHeight, false);

		TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
		backSpace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
				content.dispatchKeyEvent(event);
			}
		});

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				emoticonsCover.setVisibility(LinearLayout.GONE);
			}
		});
	}
*/


	/**
	 * For loading smileys from assets
	 */
	private Bitmap getImage(String path) {
		AssetManager mngr = getAssets();
		InputStream in = null;
		try {
			in = mngr.open("emoticons/" + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Bitmap temp = BitmapFactory.decodeStream(in, null, null);
		return temp;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void keyClickedIndex(final String index) {
		
		ImageGetter imageGetter = new ImageGetter() {
            public Drawable getDrawable(String source) {    
            	StringTokenizer st = new StringTokenizer(index, ".");
                Drawable d = new BitmapDrawable(getResources(),emoticons[Integer.parseInt(st.nextToken()) - 1]);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };
        
        Spanned cs = Html.fromHtml("<img src ='"+ index +"'/>", imageGetter, null);        
		
		int cursorPosition = content.getSelectionStart();		
        content.getText().insert(cursorPosition, cs);
        
	}

	public static boolean checkRtl ( String string ) {
		if (TextUtils.isEmpty(string)) {
			return false;
		}
		char c = string.charAt(0);
		return c >= 0x590 && c <= 0x6ff;
	}

}
