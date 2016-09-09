package org.core.still_object;

import org.core.GameElement;
import org.core.GameManager;

public interface StillObject extends GameElement {
	
	public void behavior(GameManager gameManager);
	
}
