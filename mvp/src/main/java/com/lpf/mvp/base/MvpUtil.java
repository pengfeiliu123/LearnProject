package com.lpf.mvp.base;

import java.lang.reflect.ParameterizedType;

/**
 * Created by liupengfei on 2017/5/12 18:51.
 */

public class MvpUtil {

    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
