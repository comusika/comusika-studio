package com.frinika.gui.util.handler;

import org.jspecify.annotations.NullMarked;

/**
 * Handler for default control panel.
 *
 * @author hajdam
 */
@NullMarked
public interface DefaultControlHandler {

    void controlActionPerformed(ControlActionType actionType);

    @NullMarked
    public interface DefaultControlService extends OkCancelService {

        void performClick(ControlActionType actionType);

        DefaultControlEnablementListener createEnablementListener();
    }

    @NullMarked
    public interface DefaultControlEnablementListener {

        void actionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        OK, CANCEL
    }
}
