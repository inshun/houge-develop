package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

public class TimeEntity implements Serializable {
	public static final int WEEK_MON = 1;
	public static final int WEEK_TUE = 2;
	public static final int WEEK_WEN = 3;
	public static final int WEEK_THUR = 4;
	public static final int WEEK_FRI = 5;
	public static final int WEEK_SAT = 6;
	public static final int WEEK_SUN = 7;
	public static final int no_tinme = 0;
	public static final int have_time = 1;
	private static final long serialVersionUID = -8059601889459760527L;
	private int week_day; // 周几

	private int morning; // 早上
	private int noon; // 下午
	private int night; // 晚上

	public TimeEntity(int week_day, int morning, int noon, int night) {
		super();
		this.week_day = week_day;
		this.morning = morning;
		this.noon = noon;
		this.night = night;
	}

	public TimeEntity() {
		super();
	}

	public int getWeek_day() {
		return week_day;
	}

	public void setWeek_day(int week_day) {
		this.week_day = week_day;
	}

	public int getMorning() {
		return morning;
	}

	public void setMorning(int morning) {
		this.morning = morning;
	}

	public int getNoon() {
		return noon;
	}

	public void setNoon(int noon) {
		this.noon = noon;
	}

	public int getNight() {
		return night;
	}

	public void setNight(int night) {
		this.night = night;
	}

}
