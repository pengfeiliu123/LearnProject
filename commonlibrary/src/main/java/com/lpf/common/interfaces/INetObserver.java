package com.lpf.common.interfaces;
/**
 * observer network change interface
 *@author liupengfei
 *@time 17/2/7 下午3:27
 */

public interface INetObserver {
	public void updateNetState(boolean connected);
}
