package view;

import java.awt.Canvas;
import java.awt.Color;

public class GraphicBar extends Canvas{
	
	public GraphicBar(int h,int x,int y,Color b)
	{
		setLocation(x, y);
		this.setSize(50,h);
		this.setBackground(b);
	}
}
