package com.frinika.gui.util.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for control panel with support for remove action.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface RemovalControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @ParametersAreNonnullByDefault
    public interface RemovalControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        @Nonnull
        RemovalControlEnablementListener createEnablementListener();
    }

    @ParametersAreNonnullByDefault
    public interface RemovalControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        OK, CANCEL, REMOVE
    }
}
