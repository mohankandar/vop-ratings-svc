package com.wynd.vop.ratingssvc;

import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;

/**
 * The contract interface for the domain (service) layer.
 *
 * @author aburkholder
 */
public interface RatingsSvcService {
	/**
	 * Search for the sample info by their Participant ID.
	 *
	 * @param sampleDomainRequest A SampleDomainRequest instance
	 * @return A SampleDomainResponse instance
	 */
	SampleDomainResponse sampleFindByParticipantID(SampleDomainRequest sampleDomainRequest);
}
