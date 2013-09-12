package com.raks.netty.longpolling;

import java.io.InputStream;

public class ContentData {
	private transient InputStream _stream; // The stream of input data

	public ContentData(InputStream stream) {
		_stream = stream;
	}

	// Accessor to the data stream
	public InputStream datastream() {
		return _stream;
	}
}
