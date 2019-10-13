package com.frinika.gui.util.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for close control panel.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface CloseControlHandler {

    void controlActionPerformed();

    public interface CloseControlService extends OkCancelService {

        void performCloseClick();

        @Nonnull
        CloseControlEnablementListener createEnablementListener();
    }

    public interface CloseControlEnablementListener {

        void closeActionEnabled(boolean enablement);
    }
}
