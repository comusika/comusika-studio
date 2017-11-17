
package com.frinika.audio.gui;

public interface ChannelListProvider extends ListProvider {
	/**
	 * The position in the returned array must correspond to the channel number.
	 * null entries are allowed. 
	 * 
	 * @return list of channels provided.
	 */
	public Object[] getList();
}
