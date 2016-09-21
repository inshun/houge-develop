package com.ysp.houge.dbmanager;

/**
 * @author Stay
 * @version create time：Sep 5, 2014 11:37:46 AM
 */
public class DBController {
	private static DBController instance;
	private DatabaseHelper mDBhelper;

	private DBController() {
		mDBhelper = DatabaseHelper.getHelper();
	}

	public static DBController getInstance() {
		if (instance == null) {
			instance = new DBController();
		}
		return instance;
	}

	public DatabaseHelper getDB() {
		return mDBhelper;
	}

}
