package com.frinika.gui.util.handler;

import org.jspecify.annotations.NullMarked;

/**
 * Handler for options control panel.
 *
 * @author hajdam
 */
@NullMarked
public interface OptionsControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @NullMarked
    public interface OptionsControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        OptionsControlEnablementListener createEnablementListener();
    }

    @NullMarked
    public interface OptionsControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        SAVE, APPLY_ONCE, CANCEL
    }
}
