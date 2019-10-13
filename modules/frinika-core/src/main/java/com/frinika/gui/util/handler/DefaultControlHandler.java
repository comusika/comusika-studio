package com.frinika.gui.util.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for default control panel.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface DefaultControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @ParametersAreNonnullByDefault
    public interface DefaultControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        @Nonnull
        DefaultControlEnablementListener createEnablementListener();
    }

    @ParametersAreNonnullByDefault
    public interface DefaultControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        OK, CANCEL
    }
}
