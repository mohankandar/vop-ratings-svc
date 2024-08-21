package com.wynd.vop.ratingssvc.api.provider;

import com.wynd.vop.ratingssvc.api.RatingsSvcApi;
import com.wynd.vop.ratingssvc.api.model.v1.SampleRequest;
import com.wynd.vop.ratingssvc.api.model.v1.SampleResponse;
import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.framework.log.BipLoggerFactory;
import com.wynd.vop.framework.swagger.SwaggerResponseMessages;
import io.swagger.annotations.ApiParam;

/**
 * REST Service endpoint
 *
 * @author akulkarni
 */
@RestController
public class RatingsSvcResource implements RatingsSvcApi, SwaggerResponseMessages {

	/** Logger instance */
	private static final BipLogger LOGGER = BipLoggerFactory.getLogger(RatingsSvcResource.class);

	/** Logger "returning" message */
	private static final String LOG_MSG_RETURNING = "Returning providerResponse to consumer";

	/** The root path to this resource */
	public static final String URL_PREFIX = "/api/v1/ratingssvc";

	/** Data adapter between provider and service layers */
	@Autowired
	ServiceAdapter serviceAdapter;

	/** Properties for build information */
	@Autowired
	BuildProperties buildProperties;

	/**
	 * Log build information
	 */
	@PostConstruct
	public void postConstruct() {
		// Print build properties
		LOGGER.info(buildProperties.getName());
		LOGGER.info(buildProperties.getVersion());
		LOGGER.info(buildProperties.getArtifact());
		LOGGER.info(buildProperties.getGroup());
	}

	/**
	 * Registers fields that should be allowed for data binding.
	 *
	 * @param binder
	 *            Spring-provided data binding context object.
	 */
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.setAllowedFields("sampleInfo", "name", "participantId");
	}

	/**
	 * Search for Sample information by their participant ID.
	 * <p>
	 * CODING PRACTICE FOR RETURN TYPES - Platform auditing aspects support two return types.
	 * <ol>
	 * <li>An object that implements ProviderTransferObjectMarker, e.g.: SampleResponse
	 * <li>An object of type ResponseEntity&lt;ProviderTransferObjectMarker&gt;,
	 * e.g. a ResponseEntity that wraps some class that implements ProviderTransferObjectMarker.
	 * </ol>
	 * The auditing aspect won't be triggered if the return type in not one of the above.
	 *
	 * @param sampleRequest the sample info request
	 * @return the sample info response
	 */
	@Override
	public ResponseEntity<SampleResponse> sampleByPid(
			@ApiParam(value = "sampleRequest", required = true) @Valid @RequestBody final SampleRequest sampleRequest) {
		LOGGER.debug("sampleByPid() method invoked");

		SampleResponse providerResponse = serviceAdapter.sampleByPid(sampleRequest);
		// send provider response back to consumer
		LOGGER.debug(LOG_MSG_RETURNING);
		return new ResponseEntity<>(providerResponse, HttpStatus.OK);
	}

}
