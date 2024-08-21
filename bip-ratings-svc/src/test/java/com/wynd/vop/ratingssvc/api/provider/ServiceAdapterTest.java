package com.wynd.vop.ratingssvc.api.provider;

import com.wynd.vop.framework.exception.BipValidationRuntimeException;
import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.ratingssvc.RatingsSvcService;
import com.wynd.vop.ratingssvc.RatingsSvcTestUtils;
import com.wynd.vop.ratingssvc.api.model.v1.SampleRequest;
import com.wynd.vop.ratingssvc.api.model.v1.SampleResponse;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.transform.impl.SampleByPidDomainToProvider;
import com.wynd.vop.ratingssvc.transform.impl.SampleByPidProviderToDomain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ServiceAdapterTest {

    ServiceAdapter instance;

    @Mock
    private BipLogger mockBipLogger;
    @Mock
    private SampleByPidProviderToDomain mockSampleByPidProviderToDomain;
    @Mock
    private SampleByPidDomainToProvider mockSampleByPidDomainToProvider;
    @Mock
    private RatingsSvcService mockRatingsSvcService;
    @Mock
    private SampleRequest mockSampleRequest;
    @Mock
    private SampleDomainRequest mockSampleDomainRequest;
    @Mock
    private SampleDomainResponse mockSampleDomainResponse;
    @Mock
    private SampleResponse mockSampleResponse;

    @Before
    public void setUp() {
        instance = new ServiceAdapter();

        MockitoAnnotations.initMocks(this);

        try {
            RatingsSvcTestUtils.setFinalStatic(ServiceAdapter.class.getDeclaredField("LOGGER"), mockBipLogger);
        } catch (Exception e) {
            // Ignore and attempt to test without
        }
        ReflectionTestUtils.setField(instance, "sampleByPidProvider2Domain", mockSampleByPidProviderToDomain);
        ReflectionTestUtils.setField(instance, "sampleByPidDomain2Provider", mockSampleByPidDomainToProvider);
        ReflectionTestUtils.setField(instance, "ratingssvcService", mockRatingsSvcService);
    }

    @Test
    public void testPostConstruct_Success() {
        // Setup

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            // Verifications
            fail("A BipValidationRuntimeException should not have been thrown here.");
        }
    }

    @Test
    public void testPostConstruct_Failure1() {
        // Setup
        ReflectionTestUtils.setField(instance, "sampleByPidProvider2Domain", null);

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            return;
        }

        // Verifications
        fail("A BipValidationRuntimeException should have been thrown here.");
    }

    @Test
    public void testPostConstruct_Failure2() {
        // Setup
        ReflectionTestUtils.setField(instance, "sampleByPidDomain2Provider", null);

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            return;
        }

        // Verifications
        fail("A BipValidationRuntimeException should have been thrown here.");
    }

    @Test
    public void testPostConstruct_Failure3() {
        // Setup
        ReflectionTestUtils.setField(instance, "ratingssvcService", null);

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            return;
        }

        // Verifications
        fail("A BipValidationRuntimeException should have been thrown here.");
    }

    @Test
    public void testSampleByPid() {
        // Setup
        when(mockSampleByPidProviderToDomain.convert(mockSampleRequest)).thenReturn(mockSampleDomainRequest);
        when(mockRatingsSvcService.sampleFindByParticipantID(mockSampleDomainRequest)).thenReturn(mockSampleDomainResponse);
        when(mockSampleByPidDomainToProvider.convert(mockSampleDomainResponse)).thenReturn(mockSampleResponse);

        // Execute Test
        SampleResponse response = instance.sampleByPid(mockSampleRequest);

        // Verifications
        assertNotNull(response);
        assertEquals(mockSampleResponse, response);
    }
}
