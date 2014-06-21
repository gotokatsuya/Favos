package jp.goka.favos.helper;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ThreadHelper {
	
	private static final int MAX_POOL_SIZE = 16;
	private static final int KEEP_ALIVE = 1;
	private static final AtomicInteger threadId = new AtomicInteger();
	private static UncaughtExceptionHandler mExceptionHandler = new UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			ex.printStackTrace();
		}
	};
	private static final ThreadFactory mThreadFactory = new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("ThreadManager-" + threadId.getAndIncrement());
			t.setUncaughtExceptionHandler(mExceptionHandler);
			return t;
		}
	};
	private static final BlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue<Runnable>();
	private static final ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, mPoolWorkQueue, mThreadFactory);
	private static Handler mHandler = new Handler(Looper.getMainLooper());

	static {
		mExecutor.allowCoreThreadTimeOut(true);
	}
 
	public static void runOnUi(Runnable runnable) {
		mHandler.post(runnable);
	}
 
	public static void runInBackground(Runnable runnable) {
		mExecutor.execute(runnable);
	}
	
	public static void delayOnMainThread(final Runnable runnable, final int delayMs) {
		mHandler.postDelayed(runnable, delayMs);
	}
	
	public static void runInBackgroundThenUi(final Runnable backgroundRunnable, final Runnable uiRunnable) {
		runInBackground(new Runnable() {
			@Override
			public void run() {
				backgroundRunnable.run();
				runOnUi(uiRunnable);
			}
		});
	}
}