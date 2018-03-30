package com.changxiang.vod.common.utils.Bitmap;

/**
 * Bitmap操作类, 不要轻易修改
 */
public class Function {



		public static int FClamp(final int t, final int tLow, final int tHigh)
		{
			if (t < tHigh)
			{
				return ((t > tLow) ? t : tLow) ;
			}
			return tHigh ;
		}

		public static double FClampDouble(final double t, final double tLow, final double tHigh)
		{
			if (t < tHigh)
			{
				return ((t > tLow) ? t : tLow) ;
			}
			return tHigh ;
		}

		public static int FClamp0255(final double d)
		{
			return (int)(FClampDouble(d, 0.0, 255.0) + 0.5) ;
		}
	}
