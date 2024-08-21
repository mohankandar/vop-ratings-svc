package com.wynd.vop.ratingssvc.transform.impl;

import com.wynd.vop.framework.messages.MessageSeverity;
import com.wynd.vop.ratingssvc.api.model.v1.SampleResponse;
import com.wynd.vop.ratingssvc.messages.RatingsSvcMessageKeys;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.model.SampleInfoDomain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SampleByPidDomainToProviderTest {

    SampleByPidDomainToProvider instance;

    private final Long PARTICIPANT_ID = 1L;

    @Before
    public void setUp() {
        instance = new SampleByPidDomainToProvider();
    }

    @Test
    public void testConvert() {
        // Setup
        SampleDomainResponse sampleDomainResponse = createSampleDomainResponse();

        // Execute Test
        SampleResponse response = instance.convert(sampleDomainResponse);

        // Verifications
        assertNotNull(response);
        assertNotNull(response.getSampleInfo());
        assertEquals("JOHN DOE", response.getSampleInfo().getName());
        assertEquals(PARTICIPANT_ID, response.getSampleInfo().getParticipantId());
        assertEquals(1, response.getMessages().size());
        assertEquals(MessageSeverity.INFO.value(), response.getMessages().get(0).getSeverity());
        assertEquals(Integer.toString(HttpStatus.OK.value()), response.getMessages().get(0).getStatus());
        Assert.assertEquals(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA.getKey(), response.getMessages().get(0).getKey());
    }

    private SampleDomainResponse createSampleDomainResponse() {
        SampleInfoDomain sampleInfoDomain = new SampleInfoDomain();
        sampleInfoDomain.setName("JOHN DOE");
        sampleInfoDomain.setParticipantId(PARTICIPANT_ID);

        SampleDomainResponse sampleDomainResponse = new SampleDomainResponse();
        sampleDomainResponse.setSampleInfo(sampleInfoDomain);
        sampleDomainResponse.addMessage(MessageSeverity.INFO, HttpStatus.OK, RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA,
                "");

        return sampleDomainResponse;
    }
}
