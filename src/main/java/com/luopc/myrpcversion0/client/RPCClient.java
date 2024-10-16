package com.luopc.myrpcversion0.client;

import com.luopc.myrpcversion0.common.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args)  {
        System.out.println("client start...");
        try (Socket socket = new Socket("127.0.0.1", 8879)){

            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());


            objectOutputStream.writeInt(new Random().nextInt());
            objectOutputStream.flush();
            System.out.println("objectOutputStream flush");

            Object obj =objectInputStream.readObject();
            System.out.println("objectInputStream readObject");
            User user=new User();
            if (obj instanceof User) {
                user = (User) obj;
                System.out.println("接收到的用户: " + user);
            } else {
                System.out.println("接收到的对象不是 User 类型");
            }
            System.out.println("User: id"+user.getId()+"name:"+user.getUsername()+"pwd:"+user.getPassword());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }

    }
}
