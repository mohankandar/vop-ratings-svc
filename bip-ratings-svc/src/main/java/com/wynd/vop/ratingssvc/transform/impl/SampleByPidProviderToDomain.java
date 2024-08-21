package com.wynd.vop.ratingssvc.transform.impl;

import com.wynd.vop.framework.transfer.transform.AbstractProviderToDomain;
import com.wynd.vop.ratingssvc.api.model.v1.SampleRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;

/**
 * Transform a REST Provider {@link SampleRequest} into a service Domain {@link SampleDomainRequest} object.
 *
 * @author aburkholder
 */
public class SampleByPidProviderToDomain extends AbstractProviderToDomain<SampleRequest, SampleDomainRequest> {

	/**
	 * Transform a REST Provider {@link SampleRequest} into a service Domain {@link SampleDomainRequest} object.
	 * <p>
	 * {@inheritDoc AbstractProviderToDomain}
	 */
	@Override
	public SampleDomainRequest convert(SampleRequest domainObject) {
		SampleDomainRequest providerObject = new SampleDomainRequest();
		if (domainObject != null) {
			providerObject.setParticipantID(domainObject.getParticipantID());
		}
		return providerObject;
	}

}
