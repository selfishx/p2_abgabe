package views;

import controller.AbstractController;
import models.AbstractModel;

public interface InterfaceView {

	public void setModel(AbstractModel model);

	void setController(AbstractController controller);

}