package com.example.demoyan.netty.advertise;

import com.example.demoyan.config.Config;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LockClient {
    private static LockClient lockClient;
    private String ip;
    private int port;

    private LockClient(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    public static LockClient getClient(){
        if(lockClient!=null){
            return lockClient;
        }else{
            lockClient = new LockClient(Config.server_ip,Config.server_port);
            return lockClient;
        }
    }

    public void sendMessage(String message){
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new LockHandler());


             b.connect(ip,port).sync().channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(message
     				.getBytes()), new InetSocketAddress(ip, port)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            group.shutdownGracefully();
        }

    }

}
