package com.luopc.myrpcversion4.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luopc.myrpcversion4.common.RPCRequest;
import com.luopc.myrpcversion4.common.RPCResponse;

public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }
    @Override
    public Object deserialize(byte[] bytes,int messageType){
        Object obj=null;
        switch (messageType){
            case 0: //RPCRequest格式
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                if(request.getParams()==null) return request;

                Object[] objects=new Object[request.getParams().length];

                for(int i=0;i<objects.length;i++){
                    Class<?> paramsType = request.getParamTypes()[i];
                    if(!paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                        objects[i]=JSONObject.toJavaObject((JSONObject) request.getParams()[i],request.getParamTypes()[i]);
                    }else {
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj=request;
                break;
            case 1://RPCResponse 格式
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if(! dataType.isAssignableFrom(response.getData().getClass())){
                    response.setData(JSONObject.toJavaObject((JSONObject)response.getData(),dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("JsonSerializer：Not Support This Type Message");
                throw new RuntimeException();

        }
        return obj;
    }
    @Override
    public int getType() {
        return 1;
    }
}
