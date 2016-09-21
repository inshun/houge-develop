package com.ysp.houge.app;

import com.ysp.houge.utility.CallbackInfo;

/**
 * @author Stay
 * @version create time：Mar 1, 2014 2:36:58 PM
 */
public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumException mExceptionType;
	private String detailMessage;
	private CallbackInfo info;
	public AppException(EnumException type, String detailMessage) {
		super(detailMessage);
		mExceptionType = type;
		this.detailMessage = detailMessage;
	}

	public AppException(EnumException type, String detailMessage,
			CallbackInfo errorInfo) {
		super(detailMessage);
		mExceptionType = type;
		this.detailMessage = detailMessage;
		this.info = errorInfo;
	}

	/**
	 * @return the mExceptionType
	 */
	public EnumException getmExceptionType() {
		return mExceptionType;
	}

	/**
	 * @param mExceptionType
	 *            the mExceptionType to set
	 */
	public void setmExceptionType(EnumException mExceptionType) {
		this.mExceptionType = mExceptionType;
	}

	/**
	 * @return the detailMessage
	 */
	public String getDetailMessage() {
		return detailMessage;
	}

	/**
	 * @param detailMessage
	 *            the detailMessage to set
	 */
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	/**
	 * @return the info
	 */
	public CallbackInfo getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(CallbackInfo info) {
		this.info = info;
	}

	public CallbackInfo getErrorInfo() {
		return info;
	}

	public enum EnumException {
		ParseException, //
		CancelException, //
		IOException, //
		NormalException, //
		ClientProtocolException, //
		ConnectionException, //
		FileException, //
		CloudException, //
		JsonSyntaxException, //
		EmptyException, //
		ConnectTimeoutException, //
		SocketTimeoutException, //
		ReconnectFailed, //
		ReconnectSuccessed
	}

}
