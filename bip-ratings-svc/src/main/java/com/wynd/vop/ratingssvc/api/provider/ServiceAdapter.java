package com.wynd.vop.ratingssvc.api.provider;

import com.wynd.vop.ratingssvc.RatingsSvcService;
import com.wynd.vop.ratingssvc.api.model.v1.SampleRequest;
import com.wynd.vop.ratingssvc.api.model.v1.SampleResponse;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.transform.impl.SampleByPidDomainToProvider;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.framework.log.BipLoggerFactory;
import com.wynd.vop.framework.validation.Defense;
import com.wynd.vop.ratingssvc.transform.impl.SampleByPidProviderToDomain;

/**
 * An adapter between the provider layer api/model, and the services layer interface/model.
 *
 * @author aburkholder
 */
@Component
public class ServiceAdapter {
	/** Class logger */
	private static final BipLogger LOGGER = BipLoggerFactory.getLogger(ServiceAdapter.class);

	/** Transform Provider (REST) request to Domain (service) request */
	private SampleByPidProviderToDomain sampleByPidProvider2Domain = new SampleByPidProviderToDomain();
	/** Transform Domain (service) response to Provider (REST) response */
	private SampleByPidDomainToProvider sampleByPidDomain2Provider = new SampleByPidDomainToProvider();

	/** The service layer API contract for processing sampleByPid() requests */
	@Autowired
	@Qualifier("RATINGSSVC_SERVICE_IMPL")
	private RatingsSvcService ratingssvcService;

	/**
	 * Field defense validations.
	 */
	@PostConstruct
	public void postConstruct() {
		Defense.notNull(ratingssvcService);
		Defense.notNull(sampleByPidProvider2Domain);
		Defense.notNull(sampleByPidDomain2Provider);
	}

	/**
	 * Adapt the sampleByPid(..) request mapping method to the equivalent service layer method.
	 *
	 * @param sampleRequest - the Provider layer request model object
	 * @return SampleResponse - the Provider layer response model object
	 */
	SampleResponse sampleByPid(final SampleRequest sampleRequest) {
		// transform provider request into domain request
		LOGGER.debug("Transforming from provider sampleRequest to sampleDomainRequest");
		SampleDomainRequest domainRequest = sampleByPidProvider2Domain.convert(sampleRequest);

		// get domain response from the service (domain) layer
		LOGGER.debug("Calling ratingssvcService.sampleFindByParticipantID");
		SampleDomainResponse domainResponse = ratingssvcService.sampleFindByParticipantID(domainRequest);

		// transform domain response into provider response
		LOGGER.debug("Transforming from domainResponse to providerResponse");
		return sampleByPidDomain2Provider.convert(domainResponse);
	}
}
