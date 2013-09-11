package org.helios.netty.examples.state;

import org.jboss.netty.channel.ChannelHandler;

/**
 * <p>Title: ChannelHandlerProvider</p>
 * <p>Description: A common parent type for providers of shareable and non-shareable channel handler providers</p> 
 * <p>Company: Helios Development Group LLC</p>
 * @author Whitehead (nwhitehead AT heliosdev DOT org)
 * <p><code>org.helios.netty.examples.state.ChannelHandlerProvider</code></p>
 */

public interface ChannelHandlerProvider {
	/**
	 * Returns a channel handler for insertion into a pipeline
	 * @return a channel handler 
	 */
	public ChannelHandler getHandler();
	
	/**
	 * Returns the channel handler name
	 * @return the channel handler name
	 */
	public String getHandlerName();	
}
