package com.example.demoyan.netty.advertise.lock;

import android.os.Message;
import android.util.Log;

import com.example.demoyan.MainActivity;
import com.example.demoyan.Projector;
import com.example.demoyan.Thread.AdvertiseHttp;
import com.example.demoyan.constant.NettyResult;
import com.example.demoyan.constant.ResultCode;
import com.example.demoyan.fragment.Fragment5;
import com.example.demoyan.fragment.Fragment6;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import static android.content.ContentValues.TAG;

public class AdvertiseServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    boolean flag = false;
    InetSocketAddress address = null;
    private Map<String,InetSocketAddress> map = new HashMap<String, InetSocketAddress>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf buf = (ByteBuf) datagramPacket.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        JSONObject jsonObject = new JSONObject(str);
        String result = jsonObject.getString("data");
        Gson gson = new Gson();
        NettyResult nettyResult = gson.fromJson(result,NettyResult.class);
        doAction(nettyResult);
//        new AdvertiseHttp(str).run();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.err.println("创建服务成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    private void doAction(NettyResult nettyResult){
        Message message = null;
        switch (nettyResult.getCode()){
            case ResultCode.SHOW_NEWIMAGE:
                new AdvertiseHttp(nettyResult.getMessage()).run();
                break;
            case ResultCode.SWITCH_FRAGMENT5:
                message = new Message();
                message.what = nettyResult.getCode();
                Projector.handler.sendMessage(message);
                break;
            case ResultCode.SWITCH_FRAGMENT6:
                message = new Message();
                message.what = nettyResult.getCode();
                Projector.handler.sendMessage(message);
                break;
            case ResultCode.SHOW_NEWVIDEO:
                message = new Message();
                message.what = ResultCode.SHOW_NEWVIDEO;
                message.obj = nettyResult.getMessage();
                Fragment6.handler.sendMessage(message);
            default:
                break;

        }
    }
}
