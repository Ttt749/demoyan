package com.example.demoyan.netty.advertise.lock;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class AdvertiseServer extends Thread {
    private int port;

    public AdvertiseServer(int port){
        this.port = port;
    }

    @Override
    public void run() {

        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new AdvertiseServerHandler());

        try {
            b.bind(port).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
