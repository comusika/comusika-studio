package com.frinika.gui.util.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for multi-step control panel.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface MultiStepControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @ParametersAreNonnullByDefault
    public interface MultiStepControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        @Nonnull
        MultiStepControlEnablementListener createEnablementListener();
    }

    @ParametersAreNonnullByDefault
    public interface MultiStepControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        FINISH, CANCEL, PREVIOUS, NEXT
    }
}
