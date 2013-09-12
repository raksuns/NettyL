package com.raks.netty.longpolling;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

//import java.util.concurrent.atomic.AtomicInteger;

public class RequestDataScope {
	private String _uri; // the uri of this request
	private String _path; // the path of this request
	private transient MessageEvent _externalEvent; // the event as received by
													// the netty server
	private ContentData _data;

	public RequestDataScope(String client, MessageEvent evt) throws Exception {
		understandData(client, evt);
	}

	private void understandData(String client, MessageEvent evt)
			throws Exception {
		HttpRequest request = (HttpRequest) evt.getMessage();
		_externalEvent = evt;
		_uri = request.getUri();
		QueryStringDecoder decoder = new QueryStringDecoder(_uri);
		_path = decoder.getPath();
		understandContent(request);
	}

	private void understandContent(HttpRequest request) {
		ChannelBuffer buffer = request.getContent();
		ChannelBufferInputStream stream = new ChannelBufferInputStream(buffer);
		ContentData content = new ContentData(stream);
		_data = content;
	}

	public ContentData data() throws Exception {
		return _data;
	}

	public void transmit(String data) throws Exception {
		try {
			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			{
				System.out.println("Send across: " + data);
				response.setContent(ChannelBuffers.copiedBuffer(data,
						CharsetUtil.UTF_8));
			}
			response.setHeader(CONTENT_LENGTH, response.getContent()
					.readableBytes());
			response.setHeader(CONTENT_TYPE, "text/html");

			if (_externalEvent.getChannel().isOpen()) {
				ChannelFuture future = _externalEvent.getChannel().write(
						response);
				future.awaitUninterruptibly();// wait till this writes only then
												// next can be written
			} else {
				System.out.println("Channel Closed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
