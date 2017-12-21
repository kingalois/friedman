package at.am.friedman.designer.data;

import static org.junit.Assert.assertEquals;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.junit.Test;

public class FigureDataObjectTest {
	
	@Test
	public void test(){
		FigureDataObject object = new FigureDataObject();
		object.setName("Name of Object");
		
		
		FigureDataObject clone = new FigureDataObject();
		
		IMemento mem = XMLMemento.createWriteRoot("figures");
		object.saveState(mem);
		
		
		clone.restoreState(mem.getChild("figure"));
		
		assertEquals(object.getName(), clone.getName());
	}

}
