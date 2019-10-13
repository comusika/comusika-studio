package com.frinika.gui.util.handler;

import com.frinika.gui.util.OkCancelListener;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Service for ok cancel provider.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface OkCancelService {

    @Nonnull
    OkCancelListener getOkCancelListener();
}
