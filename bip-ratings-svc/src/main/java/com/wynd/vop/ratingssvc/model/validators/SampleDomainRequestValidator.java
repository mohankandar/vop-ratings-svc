package com.wynd.vop.ratingssvc.model.validators;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.framework.log.BipLoggerFactory;
import com.wynd.vop.framework.messages.MessageKeys;
import com.wynd.vop.framework.messages.MessageSeverity;
import com.wynd.vop.framework.messages.ServiceMessage;
import com.wynd.vop.framework.validation.AbstractStandardValidator;
import com.wynd.vop.ratingssvc.messages.RatingsSvcMessageKeys;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;

/**
 * Validates the PID input on the {@link SampleDomainRequest}.
 *
 * @see AbstractStandardValidator
 * @author aburkholder
 */
public class SampleDomainRequestValidator extends AbstractStandardValidator<SampleDomainRequest> {
	/** Class logger */
	private static final BipLogger LOGGER = BipLoggerFactory.getLogger(SampleDomainRequestValidator.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wynd.vop.framework.validation.AbstractStandardValidator#validate(java.lang.Object, java.util.List)
	 */
	@Override
	public void validate(SampleDomainRequest toValidate, List<ServiceMessage> messages) {
		// validate the request content (PID)
		Long pid = toValidate.getParticipantID();
		if (pid == null) {
			LOGGER.debug("PID is null");
			messages.add(new ServiceMessage(MessageSeverity.ERROR, HttpStatus.BAD_REQUEST,
					MessageKeys.VOP_VALIDATOR_NOT_NULL, super.getCallingMethodName() + "Participant ID"));
		} else if (pid <= 0) {
			LOGGER.debug("PID is <= 0");
			messages.add(new ServiceMessage(MessageSeverity.ERROR, HttpStatus.BAD_REQUEST,
					RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_PID_MIN));
		}
	}
}
