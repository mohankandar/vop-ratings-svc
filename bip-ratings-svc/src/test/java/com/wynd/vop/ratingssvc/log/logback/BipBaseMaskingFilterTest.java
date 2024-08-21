package com.wynd.vop.ratingssvc.log.logback;

import com.wynd.vop.framework.log.logback.BipMaskingFilter;
import com.wynd.vop.framework.log.BipLoggerFactory;
import com.wynd.vop.framework.log.BipLogger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.event.Level;
import org.springframework.boot.test.system.OutputCaptureRule;

import static org.junit.Assert.assertTrue;

public class BipBaseMaskingFilterTest {

	@Rule
	public OutputCaptureRule capture = new OutputCaptureRule();

	class TestBipBaseMaskingFilter extends BipMaskingFilter {
		// test class
	}

	TestBipBaseMaskingFilter sharedBBMF;

	@Before
	public void setUp() {
		sharedBBMF = new TestBipBaseMaskingFilter();
	}

	@Test
	public final void testEvaluatePattern() {

		String msg = "Test Pattern 123-456 value";
		BipLogger logger = BipLoggerFactory.getLogger(BipMaskingFilter.class);
		logger.setLevel(Level.INFO);
		logger.info(msg);
		String log = capture.toString();
		assertTrue(log.contains("Test Pattern ****456 value"));
	}

	@Test
	public final void testEvaluateDate() {

		String msg = "Test Date Pattern 1234-56-78 value";
		BipLogger logger = BipLoggerFactory.getLogger(BipMaskingFilter.class);
		logger.setLevel(Level.INFO);
		logger.info(msg);
		String log = capture.toString();
		assertTrue(log.contains("Test Date Pattern ********78 value"));
	}
}
