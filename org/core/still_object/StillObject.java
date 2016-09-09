package org.core.still_object;

import org.core.GameObject;
import org.core.GameManager;

public interface StillObject extends GameObject {
	
	public void behavior(GameManager gameManager);
	
}
