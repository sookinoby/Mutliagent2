package com.sooki.utility;

import java.util.Random;

public class Helper {

	public static Random generator = new Random();
	
	public static Position Mapper2D(int n,int width)
	{
		int x = n/width;
		int y=  n%width;
		Position p = new Position(x,y);
		return p;
	}
	
	public static int Mapper1D(int i,int j,int width)
	{
		int x = i*width + j;
		return x;
	}
	
	public static int RandomPosition(int boardWidth)
	{
		
		int number = generator.nextInt(boardWidth*boardWidth);
		return number;
		
	}
}
