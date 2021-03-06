package com.timerchina.spider.proxy.netty.base.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jason.lin@timerchina.com 2014-11-3 下午2:03:43
 * 定义Channel中各种Handler的抽象父类,一旦需要SSl认证，则初始化使用这个父类，否则可用户自定义
 */
public abstract class NettyClientSSLChannelInit extends ChannelInitializer<SocketChannel>{

	public static ChannelPipeline p = null;
	public static SocketChannel socket = null;
	
	/**
	 * 父类需要定义一些默认的启动程序
	 */
	@Override
	protected void initChannel(SocketChannel socket) throws Exception {
		NettyClient nettyClient = new NettyClient(null);
		NettyClientSSLChannelInit.socket = socket;
		p = socket.pipeline();
        if (NettyClient.sslCtx != null) {
        	p.addLast(NettyClient.sslCtx.newHandler(socket.alloc(), nettyClient.host, nettyClient.port));
        }
        addChannelHandler();
	}
	
	/**
	 * 子类需要实现的方法，在这里添加各种Handler
	 */
	public abstract void addChannelHandler();
}
