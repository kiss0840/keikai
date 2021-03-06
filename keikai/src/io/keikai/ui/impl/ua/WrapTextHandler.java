/* WrapTextHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/5 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl.ua;

import io.keikai.api.AreaRef;
import io.keikai.api.CellOperationUtil;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.CellSelectionType;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.CellStyleAction;

/**
 * @author dennis
 *
 */
public class WrapTextHandler extends AbstractCellHandler {
	private static final long serialVersionUID = -7083063834138133156L;

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		CellSelectionType type = ctx.getSelectionType();
		Range range = Ranges.range(sheet, selection);
		//ZSS-576
		if(range.isProtected()) {
			switch(type) {
			case ROW:
				if (!range.getSheetProtection().isFormatRowsAllowed()) {
					showProtectMessage();
					return true;
				}
				break;
			case COLUMN:
			case ALL:
				if (!range.getSheetProtection().isFormatColumnsAllowed()) {
					showProtectMessage();
					return true;
				}
				break;
			case CELL:
				if (!range.getSheetProtection().isFormatCellsAllowed()) {
					showProtectMessage();
					return true;
				}
			}
		}		
		//zss-623, extends to row,column area
		switch(type){
		case ROW:
			range = range.toRowRange();
			break;
		case COLUMN:
			range = range.toColumnRange();
			break;
		case ALL:
			//we don't allow to set whole sheet style, use column range instead 
			range = range.toColumnRange();
		}
		selection = new AreaRef(range.getRow(),range.getColumn(),range.getLastRow(),range.getLastColumn());
		
		boolean wrapped = !range.getCellStyle().isWrapText();
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new CellStyleAction(Labels.getLabel("zss.undo.cellStyle"),sheet, selection.getRow(), selection.getColumn(), 
			selection.getLastRow(), selection.getLastColumn(), 
			CellOperationUtil.getWrapTextApplier(wrapped)));
		return true;
	}

}
