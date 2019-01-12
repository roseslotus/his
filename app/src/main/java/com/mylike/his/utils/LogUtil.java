/*
 * Copyright (C) 2014 The PTMISClientForInit Project
 * All right reserved.
 * Version 1.00 Jul 3, 2014
 * Author tianhl@foxmail.com
 */
package com.mylike.his.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Author tianhl@foxmail.com
 * @Create Jul 3, 2014
 */
public class LogUtil {

	private final static boolean LOG_NEED_PRINT = true;
	private final static boolean LOG_NEED_WRITE = true;

	public static String LOG_TAG = "log_wcs";

//	private static class SingleHolder {
//		public static LogUtil mLogUtil = new LogUtil();
//	}
//
//	public static LogUtil getInstance() {
//		return SingleHolder.mLogUtil;
//	}
//
//	public void init(String logTag) {
//		LOG_TAG = logTag;
//	}

	/**
	 * 打印 DEBUG 日志
	 * 
	 * @param clz
	 * @param msg
	 */
	public static void debug(Class<?> clz, String msg) {
		if (TextUtils.isEmpty(LOG_TAG))
			throw new IllegalArgumentException("LOG_TAG is null, please call init() method first");
		if (clz == null) {
			clz = LogUtil.class;
		}
		if (LOG_NEED_PRINT)
			Log.d(LOG_TAG, clz.getSimpleName() + " > " + msg);
		writeLog(clz.getSimpleName() + " > " + msg);
	}

	/**
	 * 打印 INFO 日志
	 * 
	 * @param clz
	 * @param msg
	 */
	public static void info(Class<?> clz, String msg) {
		if (TextUtils.isEmpty(LOG_TAG))
			throw new IllegalArgumentException("LOG_TAG is null, please call init() method first");
		if (clz == null) {
			clz = LogUtil.class;
		}

		if (LOG_NEED_PRINT)
			Log.i(LOG_TAG, clz.getSimpleName() + " > " + msg);
		writeLog(clz.getSimpleName() + " > " + msg);
	}

	/**
	 * 打印 Exception 日志
	 * 
	 * @param clz
	 * @param e
	 */
	public static void exception(Class<?> clz, Exception e) {
		if (TextUtils.isEmpty(LOG_TAG))
			throw new IllegalArgumentException("LOG_TAG is null, please call init() method first");
		if (clz == null) {
			clz = LogUtil.class;
		}

		if (LOG_NEED_PRINT)
			Log.i(LOG_TAG, clz.getSimpleName() + " > " + e.toString());
		writeLog(clz.getSimpleName() + " > " + e.toString());
	}

	/**
	 * 向文件写入日志信息
	 * 
	 * @param msg
	 */
	private static void writeLog(String msg) {
//		if (!LOG_NEED_WRITE)
//			return;
//		if(!existSD()) return;
//		Date date = new Date(System.currentTimeMillis());
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
//		String logFileName = sf.format(date) + ".log";
//		FileWriter fileWriter = null;
//		File logFile = new File("/mnt/sdcard/wcs/log" + File.separator + logFileName);
//		try {
//			if (!logFile.getParentFile().exists())
//				logFile.getParentFile().mkdirs();
//			if (!logFile.exists()) {
//				if (!logFile.createNewFile()) {
//					return;
//				}
//			}
//			fileWriter = new FileWriter(logFile, true);
//			fileWriter.write(getCurrentTime() + ":" + msg);
//			fileWriter.write("\n");
//			fileWriter.flush();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		} finally {
//			try {
//				if (fileWriter != null)
//					fileWriter.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	public static boolean existSD() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return true;
		}
		return false;
	}

	/**
	 * 取得当前时间 </br>
	 * 如:2013-01-01 00:00:00
	 * 
	 * @return
	 */
	private static String getCurrentTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
}
