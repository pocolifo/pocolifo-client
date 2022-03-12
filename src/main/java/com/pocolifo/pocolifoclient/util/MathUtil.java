package com.pocolifo.pocolifoclient.util;

public class MathUtil {
	public static float contain_float(float val, float min, float max) {
		if (val > max) {
			return max;
		} else return Math.max(min, val);
	}
}
