package com.luopc.myrpcversion5.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        //System.out.println("RandomLoadBalance choose " + choose + " server");
        return addressList.get(choose);
    }
}
