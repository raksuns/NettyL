package org.helios.netty;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jboss.netty.channel.ChannelHandler;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * <p>Title: ChannelHandlerDump</p>
 * <p>Description: Utility class to analyze and categorize all ChannelHandler types found in the classpath</p> 
 * <p><code>org.helios.netty.ChannelHandlerDump</code></p>
 */

public class ChannelHandlerDump {

	/**
	 * Boots the analyzer
	 * @param args None
	 */
	public static void main(String[] args) {
		Set<Class<? extends ChannelHandler>> sharable = new HashSet<Class<? extends ChannelHandler>>();
		Set<Class<? extends ChannelHandler>> notSharable = new HashSet<Class<? extends ChannelHandler>>();
		Set<URL> urls = new HashSet<URL>();
		for(Iterator<URL> urlIter = ClasspathHelper.forClassLoader().iterator(); urlIter.hasNext();) {
			URL url = urlIter.next();
			if(url.toString().toLowerCase().endsWith(".jar")) {
				urls.add(url);
			}
		}
		
		Reflections ref = new Reflections(new ConfigurationBuilder().setUrls(urls));
		for(Class<? extends ChannelHandler> clazz: ref.getSubTypesOf(ChannelHandler.class)) {
			
			if(Modifier.isAbstract(clazz.getModifiers())) {
				continue;
			}
			ChannelHandler.Sharable shareable = clazz.getAnnotation(ChannelHandler.Sharable.class);
			
			if(shareable==null) {
				notSharable.add(clazz);
			}
			else {
				sharable.add(clazz);
			}
		}
		
		StringBuilder b = new StringBuilder("\n\t==========================\n\tSharable Channel Handlers\n\t==========================");
		
		for(Class<? extends ChannelHandler> clazz: sharable) {
			b.append("\n\t\t").append(clazz.getName());
		}
		
		b.append("\n");
		log(b);
		
		b = new StringBuilder("\n\t==========================\n\tNon Sharable Channel Handlers\n\t==========================");
		
		for(Class<? extends ChannelHandler> clazz: notSharable) {
			b.append("\n\t\t").append(clazz.getName());
		}
		
		b.append("\n");
		log(b);		

	}
	
	/**
	 * Logger
	 * @param msg The message to print
	 */
	public static void log(Object msg) {
		System.out.println(msg);
	}

}
