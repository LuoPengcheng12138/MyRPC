Version 0: 
    最基础RPC通信
RPCServer.java:
P1: 当 while (true) 放在 new Thread(() -> {}内部时，整个程序无法运行下去；
    当 while (true) 放在 new Thread(() -> {}外部时，程序正常运行;
A: while (true) 放在 new Thread(() -> {},thread会轮流查询，查不到id后 发生EOFException
    
P2: 先写 ObjectOutputStream 再 ObjectInputStream 

Version 1:
    反射+动态代理
common层：定义通用Response/Request格式
IOClient.java:客户端对不同的Service进行动态代理
ClientProxy.java:动态代理封装request对象
客户端调用不同的方法时 proxy.getUserByUserId(10) 会触发override invoke函数
语法错误：class<T> clazz -> Class<T> clazz

