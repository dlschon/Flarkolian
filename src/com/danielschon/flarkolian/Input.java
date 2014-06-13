package com.danielschon.flarkolian;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.SparseArray;
import android.view.MotionEvent;

/**
 * Manages touch and tilt inputs
 */
public class Input 
{
	public static int numPointers = 0;
	public static SparseArray<Vec2> pointers = new SparseArray<Vec2>();
	
	public static void processTouchEvent(MotionEvent e)
	{
		int actionMasked = e.getActionMasked();
		int pointerIndex = e.getActionIndex();
		int pointerId = e.getPointerId(pointerIndex);
		
		if (actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_POINTER_DOWN)
		{
			//Add a new Pointer
			Vec2 pointer = new Vec2(e.getX(pointerIndex), e.getY(pointerIndex)).mirrorY();
			pointers.put(pointerId, pointer);
			numPointers++;
		}
		
		if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_POINTER_UP)
		{
			//Delete a pointer
			pointers.delete(pointerId);
			numPointers--;
		}
		
		if (actionMasked == MotionEvent.ACTION_MOVE)
		{
			for (int i = 0; i < e.getPointerCount(); i++)
			{
				Vec2 pointer = pointers.get(e.getPointerId(i));
				pointer.set(e.getX(i), e.getY(i));
				pointer.mirrorY();
			}
		}
	}
	
	/**
	 * Returns true if there is a pointer within the selected region
	 * @param loc
	 * @param size
	 * @return
	 */
	public static boolean testRegion(Rectangle collisionRect)
	{
		if (numPointers == 0)
			return false;
		//Iterate through the SparseArray of Pointers
		for(int i = 0; i < pointers.size(); i++) 
		{
			int key = pointers.keyAt(i);
			Vec2 pointer = pointers.get(key);
			if (collisionRect.contains(pointer))
				return true;
		}
		return false;
	}
}
