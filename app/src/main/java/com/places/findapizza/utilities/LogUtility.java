/*
 * 10Pearls - Android Framework v1.0
 * 
 * The contributors of the framework are responsible for releasing 
 * new patches and make modifications to the code menu. Any bug or
 * suggestion encountered while using the framework should be
 * communicated to any of the contributors.
 * 
 * Contributors:
 * 
 * Ali Mehmood       - ali.mehmood@tenpearls.com
 * Arsalan Ahmed     - arsalan.ahmed@tenpearls.com
 * M. Azfar Siddiqui - azfar.siddiqui@tenpearls.com
 * Syed Khalilullah  - syed.khalilullah@tenpearls.com
 */
package com.places.findapizza.utilities;


import android.util.Log;

/**
 * Serves as a logging layer. You must never write logs directly to console.
 * Instead, use this class to give a single point of change for your logging
 * code.
 *
 * 
 */
public class LogUtility {

	private static final String CONFIGURATION_VALUE_ENABLE_LOGGING = "enable_logging";

	/**
	 * Writes log with the given log level and class.
	 * 
	 * @param logLevel
	 *            The log level, e.g. Log.DEBUG
	 * @param tag
	 *            Log tag
	 * @param message
	 *            Log message
	 * @param loggingClass
	 *            The class calling this method
	 */
	public static void log(int logLevel, String tag, String message,
			Class<?> loggingClass) {

		logByLevel(logLevel, tag, message, loggingClass);
	}

	/**
	 * Writes log with the given log level.
	 * 
	 * @param logLevel
	 *            The log level, e.g. Log.DEBUG
	 * @param tag
	 *            Log tag
	 * @param message
	 *            Log message
	 */
	public static void log(int logLevel, String tag, String message) {

		logByLevel(logLevel, tag, message, null);
	}

	private static void logByLevel(int logLevel, String tag, String message,
			Class<?> loggingClass) {

		switch (logLevel) {

		case Log.DEBUG:

			if (loggingClass != null)
				Log.d("[" + loggingClass.getSimpleName() + "->" + tag + "]",
						message);
			else
				Log.d("[" + tag + "]", message);
			break;

		case Log.INFO:

			if (loggingClass != null)
				Log.i("[" + loggingClass.getSimpleName() + "->" + tag + "]",
						message);
			else
				Log.i("[" + tag + "]", message);
			break;

		case Log.ERROR:

			if (loggingClass != null)
				Log.e("[" + loggingClass.getSimpleName() + "->" + tag + "]",
						message);
			else
				Log.e("[" + tag + "]", message);
			break;

		case Log.WARN:

			if (loggingClass != null)
				Log.w("[" + loggingClass.getSimpleName() + "->" + tag + "]",
						message);
			else
				Log.w("[" + tag + "]", message);
			break;

		default:
			break;
		}
	}

	public static void i(String tag, String message) {
		log(Log.INFO, tag, message, null);
	}

	public static void e(String tag, String message) {
		log(Log.ERROR, tag, message, null);
	}

	public static void d(String tag, String message) {
		log(Log.DEBUG, tag, message, null);
	}

	public static void w(String tag, String message) {
		log(Log.WARN, tag, message, null);
	}


}
