package org.helios.netty.examples.state;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;

/**
 * <p>Title: InstrumentedDelimiterBasedFrameDecoder</p>
 * <p>Description: Wrapped extension of {@link DelimiterBasedFrameDecoder} that counts the number of times it was called before returning a result.</p> 
 * <p><code>org.helios.netty.examples.state.InstrumentedDelimiterBasedFrameDecoder</code></p>
 */

public class InstrumentedDelimiterBasedFrameDecoder extends DelimiterBasedFrameDecoder {
	/** The number of calls with a null return */
	protected int callCount = 0;
	
    /**
     * {@inheritDoc}
     * @see org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
    	Object decoded = super.decode(ctx, channel, buffer);
    	callCount++;
    	if(decoded!=null) {
    		StringReporter.frameDecodeCalls.set(channel, callCount);
    		//System.out.println("FD:"+  channel.getClass().getName());
    		callCount = 0;
    	}
    	return decoded;
    }


	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param delimiter
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			ChannelBuffer delimiter) {
		super(maxFrameLength, delimiter);
	}

	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param delimiters
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			ChannelBuffer... delimiters) {
		super(maxFrameLength, delimiters);
	}

	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param stripDelimiter
	 * @param delimiter
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			boolean stripDelimiter, ChannelBuffer delimiter) {
		super(maxFrameLength, stripDelimiter, delimiter);
	}

	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param stripDelimiter
	 * @param delimiters
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			boolean stripDelimiter, ChannelBuffer... delimiters) {
		super(maxFrameLength, stripDelimiter, delimiters);
	}

	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param stripDelimiter
	 * @param failFast
	 * @param delimiter
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			boolean stripDelimiter, boolean failFast, ChannelBuffer delimiter) {
		super(maxFrameLength, stripDelimiter, failFast, delimiter);
	}

	/**
	 * Creates a new InstrumentedDelimiterBasedFrameDecoder
	 * @param maxFrameLength
	 * @param stripDelimiter
	 * @param failFast
	 * @param delimiters
	 */
	public InstrumentedDelimiterBasedFrameDecoder(int maxFrameLength,
			boolean stripDelimiter, boolean failFast,
			ChannelBuffer... delimiters) {
		super(maxFrameLength, stripDelimiter, failFast, delimiters);
	}

}
