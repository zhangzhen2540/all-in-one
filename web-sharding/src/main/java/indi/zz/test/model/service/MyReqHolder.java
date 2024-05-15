package indi.zz.test.model.service;

import com.alibaba.ttl.TransmittableThreadLocal;

public class MyReqHolder {
    private static final TransmittableThreadLocal<String> holder = new TransmittableThreadLocal<>();


    public static void set(String msg) {
        holder.set(msg);
    }

    public static String get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
