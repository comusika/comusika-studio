package com.frinika.gui.util.handler;

import org.jspecify.annotations.NullMarked;

/**
 * Handler for close control panel.
 *
 * @author hajdam
 */
@NullMarked
public interface CloseControlHandler {

    void controlActionPerformed();

    public interface CloseControlService extends OkCancelService {

        void performCloseClick();

        CloseControlEnablementListener createEnablementListener();
    }

    public interface CloseControlEnablementListener {

        void closeActionEnabled(boolean enablement);
    }
}
