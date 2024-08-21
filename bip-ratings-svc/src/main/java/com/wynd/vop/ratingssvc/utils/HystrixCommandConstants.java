package com.wynd.vop.ratingssvc.utils;

/**
 * The Class HystrixCommandConstants.
 */
public final class HystrixCommandConstants {

	/** RatingsSvc Service Thread Pool Group. */
	public static final String RATINGSSVC_SERVICE_GROUP_KEY = "RatingsSvcServiceGroup";

	/**
	 * Do not instantiate
	 */
	private HystrixCommandConstants() {
		throw new UnsupportedOperationException("HystrixCommandConstants is a static class. Do not instantiate it.");
	}
}