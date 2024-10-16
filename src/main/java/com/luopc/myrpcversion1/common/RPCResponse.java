package com.luopc.myrpcversion1.common;
/**
 * V0中response传输的是User对象，
 * 将传输对象抽象成为Object
 * Rpc需要经过网络传输，有可能失败，类似于http，引入状态码和状态信息表示服务调用成功还是失败
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPCResponse implements Serializable {
    //状态信息
    private int StateCode;
    private String ErrorMessage;
    //数据
    private Object Data;

    public static RPCResponse success(Object data) {
        return RPCResponse.builder().StateCode(200).Data(data).build();
    }
    public static RPCResponse fail() {
        return RPCResponse.builder().StateCode(500).ErrorMessage("RPCResponse Error").build();
    }

}
