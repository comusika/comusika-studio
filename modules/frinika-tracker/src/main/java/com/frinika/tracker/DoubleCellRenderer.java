/*
 * Created on Jan 9, 2005
 *
 * Copyright (c) 2005 Peter Johan Salomonsen (http://www.petersalomonsen.com)
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.tracker;

import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author peter
 *
 */
public class DoubleCellRenderer extends DefaultTableCellRenderer {

    static DecimalFormat numberFormat = new DecimalFormat("0.00");

    /* (non-Javadoc)
	 * @see javax.swing.JLabel#getText()
     */
    @Override
    public String getText() {
        try {
            return (numberFormat.format(Double.parseDouble(super.getText())));
        } catch (NumberFormatException e) {
            return (super.getText());
        }
    }
}
