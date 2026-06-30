package com.frinika.gui.util.handler;

import org.jspecify.annotations.NullMarked;

/**
 * Handler for control panel with support for remove action.
 *
 * @author hajdam
 */
@NullMarked
public interface RemovalControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @NullMarked
    public interface RemovalControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        RemovalControlEnablementListener createEnablementListener();
    }

    @NullMarked
    public interface RemovalControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        OK, CANCEL, REMOVE
    }
}
