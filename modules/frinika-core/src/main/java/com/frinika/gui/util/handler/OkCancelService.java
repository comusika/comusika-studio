package com.frinika.gui.util.handler;

import com.frinika.gui.util.OkCancelListener;
import org.jspecify.annotations.NullMarked;

/**
 * Service for ok cancel provider.
 *
 * @author hajdam
 */
@NullMarked
public interface OkCancelService {

    OkCancelListener getOkCancelListener();
}
