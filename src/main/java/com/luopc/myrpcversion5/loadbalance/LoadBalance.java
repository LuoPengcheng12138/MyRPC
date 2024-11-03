package com.luopc.myrpcversion5.loadbalance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> addressList);
}
