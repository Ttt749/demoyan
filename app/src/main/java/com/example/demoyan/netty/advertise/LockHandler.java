package com.example.demoyan.netty.advertise;

import android.os.Message;
import android.util.Log;

import com.example.demoyan.MainActivity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class LockHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String TAG = "";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        Message message = new Message();
        message.what=1;
        message.obj = str;
        MainActivity.handler.sendMessage(message);
        Log.e(TAG, "channelRead0: ");
    }
}
