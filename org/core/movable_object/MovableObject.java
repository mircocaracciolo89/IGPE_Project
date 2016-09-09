package org.core.movable_object;

import org.core.GameElement;
import org.core.GameManager;

public interface MovableObject extends GameElement {
	
	public void update(GameManager gameManager);
	
}
