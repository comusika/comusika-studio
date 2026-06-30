package com.frinika.gui.util.handler;

import org.jspecify.annotations.NullMarked;

/**
 * Handler for multi-step control panel.
 *
 * @author hajdam
 */
@NullMarked
public interface MultiStepControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @NullMarked
    public interface MultiStepControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        MultiStepControlEnablementListener createEnablementListener();
    }

    @NullMarked
    public interface MultiStepControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        FINISH, CANCEL, PREVIOUS, NEXT
    }
}
