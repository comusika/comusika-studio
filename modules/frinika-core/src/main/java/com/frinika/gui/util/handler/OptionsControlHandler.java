package com.frinika.gui.util.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for options control panel.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public interface OptionsControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @ParametersAreNonnullByDefault
    public interface OptionsControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        @Nonnull
        OptionsControlEnablementListener createEnablementListener();
    }

    @ParametersAreNonnullByDefault
    public interface OptionsControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        SAVE, APPLY_ONCE, CANCEL
    }
}
