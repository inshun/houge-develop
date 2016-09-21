package com.ysp.houge.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ysp.houge.R;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午5:16:12
 * @version 1.0
 */
@SuppressLint("NewApi")
public class ChatSendView extends RelativeLayout implements OnClickListener {
	private EditText mChatSendEdit;
	private Button mChatSendBtn;
	private ChatSendViewListener chatSendViewListener;
	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	public ChatSendView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ChatSendView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 */
	public ChatSendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 */
	public ChatSendView(Context context) {
		super(context);
		initViews(context);
	}

	/**
	 * @return the chatSendViewListener
	 */
	public ChatSendViewListener getChatSendViewListener() {
		return chatSendViewListener;
	}

	/**
	 * @param chatSendViewListener
	 *            the chatSendViewListener to set
	 */
	public void setChatSendViewListener(
			ChatSendViewListener chatSendViewListener) {
		this.chatSendViewListener = chatSendViewListener;
	}

	private void initViews(Context context) {
		LayoutInflater.from(context)
				.inflate(R.layout.view_chat_send_edit, this);
		this.mChatSendEdit = (EditText) findViewById(R.id.mChatSendEdit);
		this.mChatSendBtn = (Button) findViewById(R.id.mChatSendBtn);
		this.mChatSendBtn.setOnClickListener(this);
		this.mChatSendEdit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mChatSendBtn:
			if (chatSendViewListener != null) {
				chatSendViewListener.onClickSendButton(mChatSendEdit.getText()
						.toString().trim());
			}
			break;
		case R.id.mChatSendEdit:
			if (chatSendViewListener != null) {
				chatSendViewListener.onClikEditText();
			}
			break;
		default:
			break;
		}
	}

	public void clearEdit() {
		mChatSendEdit.setText("");
	}

	public interface ChatSendViewListener {
		void onClickSendButton(String content);

		void onClikEditText();
	}

}
