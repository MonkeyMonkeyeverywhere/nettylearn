package nettydemo.EncodeDecode.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import nettydemo.EncodeDecode.Request;
import nettydemo.EncodeDecode.Response;
import nettydemo.EncodeDecode.util.GzipUtils;
import nettydemo.EncodeDecode.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Request request = (Request) msg;
        System.out.println("Server:"+ request.getId() + "," + request.getName() + "," +request.getRequestMessage());
        Response response = new Response();
        response.setId(request.getId());
        response.setName("response"+request.getId());
        response.setResponseMessage("响应内容："+request.getRequestMessage());
        byte[] unGizpData = GzipUtils.unGzip(request.getAttachment());
        char separator = File.separatorChar;
        FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + separator +"recieve" + separator + "1.png");
        outputStream.write(unGizpData);
        outputStream.flush();
        outputStream.close();
        ctx.writeAndFlush(response);
    }
}
