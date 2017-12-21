package at.am.friedman.gui.export;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Table;

import at.am.common.logging.LogFactory;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelExporter {
	
	private static Logger log = LogFactory.makeLogger();
	
	WritableCellFormat headerFormat;
	
	String filePath;
	
	
	public void setFilePath(String path){
		this.filePath = path;
		if(!filePath.endsWith(".xls")){
			filePath = filePath + ".xls";
		}
	}
	
	public void write(Table table){
		File file = new File(filePath);
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			workbook.createSheet("Tabelle", 0);
			WritableSheet sheet = workbook.getSheet(0);
			createLabel(sheet, table);
			createContent(sheet, table);
			
			
			workbook.write();
			workbook.close();
			
		} catch (IOException | WriteException e) {
			log.log(Level.SEVERE, "cannot create excel sheet", e);
		}
		
	}

	private void createContent(WritableSheet sheet, Table table) {
		int k= 0;
		int l = 0;
		for(int i = 0; i < table.getItemCount(); i++){
			for(int j = 0; j < table.getColumnCount(); j++){
				try {
					if(table.getColumn(j).getWidth()>0){
						sheet.addCell(new Label(l, k+1, table.getItem(i).getText(j)));
						l++;
					}
				} catch (WriteException e) {
					log.log(Level.SEVERE, "cannot create excel sheet", e);
				}	
			}
			l = 0;
			k++;
		}
		
	}

	private void createLabel(WritableSheet sheet, Table table) {
		int k = 0;
		for(int i = 0; i < table.getColumnCount(); i++){
			try {
				if(table.getColumn(i).getWidth()>0){
					sheet.addCell(new Label(k, 0, table.getColumn(i).getText()));
					k++;
				}
			} catch (WriteException e) {
				log.log(Level.SEVERE, "cannot create excel sheet", e);
			}
		}
		
	}
	
	

}
