package core.still_object;

import core.GameElement;
import core.GameManager;

public interface StillObject extends GameElement {
	
	public void behavior(GameManager gameManager);
	
}
