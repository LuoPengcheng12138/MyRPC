https://gitee.com/cjwwarren/MyRPCFromZero#myrpcfromzero

Version 0: 
    最基础RPC通信
RPCServer.java:
P1: 当 while (true) 放在 new Thread(() -> {}内部时，整个程序无法运行下去；
    当 while (true) 放在 new Thread(() -> {}外部时，程序正常运行;
A: while (true) 放在 new Thread(() -> {},thread会轮流查询，查不到id后 发生EOFException
    
P2: 先写 ObjectOutputStream 再 ObjectInputStream 

Version 1:
    反射+动态代理
1 common层：定义通用Response/Request格式
2 IOClient.java:底层通信函数
3 ClientProxy.java:动态代理封装request对象 （调用了 IOClient 的 send 方法）
4 客户端调用不同的方法时 proxy.getUserByUserId(10) 会触发override invoke函数
5 而服务端的 method.invoke 调用的是 UserServiceImpl 里的函数
6 语法错误：class<T> clazz -> Class<T> clazz

Version 2:
    代码解耦+线程池
1 ServiceProvider：使用HashMap保存多个服务的接口
2 把RPCServer抽象成 interface，以后的服务端（Simple/ThreadRPCRPCServer）实现这个接口即可
3 单独写一个 WorkThread 解耦
4 线程池 可以了解一下

Version 3:
    Netty+NIO
    阅读博客文章
    有空可以看下Netty源码

Version 4:
    自定义 encode与decode
bug 1:
    //更新
    private Class<?> dataType;
    public static RPCResponse success(Object data) {
    return RPCResponse.builder().dataType(data.getClass()).StateCode(200).Data(data).build();
    }//增加dataType属性
bug 2:
    out.write*Int*(bytes.length);

Version 5:
记得启动zookeeper ：zkServer start -> zkcli  -> ls /my_rpc_version5
定义服务注册借口： ZkServiceRegister implements ServiceRegister
客户端初始化注册中心
服务端需要把自己的ip，端口给注册中心：ServiceProvider.java:
-> serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));

Version 6:
实现负载均衡





