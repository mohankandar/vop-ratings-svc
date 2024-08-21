package com.wynd.vop.ratingssvc.model.validators;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.framework.log.BipLoggerFactory;
import com.wynd.vop.framework.messages.MessageSeverity;
import com.wynd.vop.framework.messages.ServiceMessage;
import com.wynd.vop.framework.security.PersonTraits;
import com.wynd.vop.framework.security.SecurityUtils;
import com.wynd.vop.framework.validation.AbstractStandardValidator;
import com.wynd.vop.ratingssvc.exception.RatingsSvcServiceException;
import com.wynd.vop.ratingssvc.messages.RatingsSvcMessageKeys;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;

/**
 * Validates the PID input on the {@link SampleDomainResponse}.
 *
 * @see AbstractStandardValidator
 * @author aburkholder
 */
public class SampleDomainResponseValidator extends AbstractStandardValidator<SampleDomainResponse> {

	/** Class logger */
	private static final BipLogger LOGGER = BipLoggerFactory.getLogger(SampleDomainResponseValidator.class);

	/** The method that caused this validator to be invoked */
	private Method callingMethod;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wynd.vop.framework.validation.AbstractStandardValidator#validate(java.lang.Object, java.util.List)
	 */
	@Override
	public void validate(SampleDomainResponse toValidate, List<ServiceMessage> messages) {
		Object supplemental = getSupplemental(SampleDomainRequest.class);
		SampleDomainRequest request =
				supplemental == null ? new SampleDomainRequest() : (SampleDomainRequest) supplemental;

		// if response has errors, fatals or warnings skip validations
		if (toValidate != null && (toValidate.hasErrors()
				|| toValidate.hasFatals()
				|| toValidate.hasWarnings())) {
			return;
		}
		// check if empty response, or errors / fatals
		if ((toValidate == null) || (toValidate.getSampleInfo() == null)) {
			RatingsSvcMessageKeys key = RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_NOTNULL;
			LOGGER.info(key.getKey() + " " + key.getMessage());
			throw new RatingsSvcServiceException(key, MessageSeverity.FATAL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/*
		 * In a real-world service, it is highly unlikely that a user would be allowed
		 * to query for someone else's data. In general, responses should *always*
		 * contain only data for the logged-in person.
		 * Therefore, the checks below would typically throw an exception,
		 * not just set a warning.
		 */
		LOGGER.debug("Request PID: " + request.getParticipantID()
				+ "; Response PID: " + toValidate.getSampleInfo().getParticipantId()
				+ "; PersonTraits PID: "
				+ (SecurityUtils.getPersonTraits() == null ? "null" : SecurityUtils.getPersonTraits().getPid()));

		// check requested pid = returned pid
		if (!toValidate.getSampleInfo().getParticipantId().equals(request.getParticipantID())) {
			RatingsSvcMessageKeys key = RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_PID_INCONSISTENT;

			LOGGER.info(key.getKey() + " " + key.getMessage());
			messages.add(new ServiceMessage(MessageSeverity.WARN, HttpStatus.OK, key));
		}
		// check logged in user's pid matches returned pid
		PersonTraits personTraits = SecurityUtils.getPersonTraits();
		boolean hasTraits = personTraits != null
				&& StringUtils.isNotBlank(personTraits.getPid());
		boolean canValidate = toValidate.getSampleInfo() != null
				&& toValidate.getSampleInfo().getParticipantId() != null;

		if (hasTraits && canValidate
				&& !personTraits.getPid().equals(toValidate.getSampleInfo().getParticipantId().toString())) {

			RatingsSvcMessageKeys key = RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_PID_INVALID;
			LOGGER.info(key.getKey() + " " + key.getMessage());
			messages.add(new ServiceMessage(MessageSeverity.WARN, HttpStatus.OK, key));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wynd.vop.framework.validation.AbstractStandardValidator#setCallingMethod(java.lang.reflect.Method)
	 */
	@Override
	public void setCallingMethod(Method callingMethod) {
		this.callingMethod = callingMethod;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.wynd.vop.framework.validation.AbstractStandardValidator#getCallingMethod()
	 */
	@Override
	public Method getCallingMethod() {
		return this.callingMethod;
	}
}
