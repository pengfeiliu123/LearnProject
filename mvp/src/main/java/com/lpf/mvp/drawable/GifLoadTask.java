package com.lpf.mvp.drawable;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class GifLoadTask extends FutureTask<ByteBuffer> {
	private static final String GIF_URL = "https://d1d7glqtmsbpdn.cloudfront.net/full/3dcfe0e1e6fd7e59af1dce3d6bd20e3409aff10b.gif";
//	private final WeakReference<HttpFragment> mFragmentReference;

	GifLoadTask() {
		super(new Callable<ByteBuffer>() {
			@Override
			public ByteBuffer call() throws Exception {
				URLConnection urlConnection = new URL(GIF_URL).openConnection();
				urlConnection.connect();
				final int contentLength = urlConnection.getContentLength();
				if (contentLength < 0) {
					throw new IOException("Content-Length not present");
				}
				ByteBuffer buffer = ByteBuffer.allocateDirect(contentLength);
				ReadableByteChannel channel = Channels.newChannel(urlConnection.getInputStream());
				while (buffer.remaining() > 0)
					channel.read(buffer);
				channel.close();
				return buffer;
			}
		});
//		mFragmentReference = new WeakReference<>(httpFragment);
	}

	@Override
	protected void done() {
//		final HttpFragment httpFragment = mFragmentReference.get();
//		if (httpFragment == null) {
//			return;
//		}
//		try {
//			httpFragment.onGifDownloaded(get());
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		} catch (ExecutionException e) {
//			httpFragment.onDownloadFailed(e);
//		}

	}
}
