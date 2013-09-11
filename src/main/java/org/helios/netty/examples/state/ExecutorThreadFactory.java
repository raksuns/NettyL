package org.helios.netty.examples.state;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.netty.util.ThreadNameDeterminer;
import org.jboss.netty.util.ThreadRenamingRunnable;

/**
 * <p>Title: ExecutorThreadFactory</p>
 * <p>Description: A Thread</p> 
 * <p><code>org.helios.netty.examples.state.ExecutorThreadFactory</code></p>
 */

public class ExecutorThreadFactory implements ThreadFactory {
	/** The thread group that this factory's threads are created in */
	protected final ThreadGroup threadGroup;
	/** The thread serial number generator */
	protected final AtomicLong serial = new AtomicLong(0L);
	/** The thread group and thread name prefix */
	protected final String prefix;
	/** Indicates if the thread factory should create daemon threads */
	protected final boolean daemon;
	
	
	
	/**
	 * Creates a new ExecutorThreadFactory
	 * @param prefix The thread group and thread name prefix
	 * @param daemon Indicates if the thread factory should create daemon threads
	 */
	public ExecutorThreadFactory(String prefix, boolean daemon) {
		this.prefix = prefix;
		this.daemon = daemon;
		threadGroup = new ThreadGroup(prefix + "ThreadGroup");
	}

	static {
		ThreadRenamingRunnable.setThreadNameDeterminer(new ThreadNameDeterminer(){
			/**
			 * {@inheritDoc}
			 * @see org.jboss.netty.util.ThreadNameDeterminer#determineThreadName(java.lang.String, java.lang.String)
			 */
			@Override
			public String determineThreadName(String currentThreadName, String proposedThreadName) throws Exception {
				return currentThreadName;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(threadGroup, r, prefix + "Thread#" + serial.incrementAndGet());
		t.setDaemon(daemon);		
		return t;
	}

}
