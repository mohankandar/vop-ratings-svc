package com.wynd.vop.ratingssvc.impl;

import com.wynd.vop.framework.log.BipLogger;
import com.wynd.vop.framework.messages.MessageKeys;
import com.wynd.vop.framework.messages.MessageSeverity;
import com.wynd.vop.ratingssvc.RatingsSvcTestUtils;
import com.wynd.vop.ratingssvc.messages.RatingsSvcMessageKeys;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.model.SampleInfoDomain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatingsSvcServiceImplTest {

    RatingsSvcServiceImpl instance;

    @Mock
    private BipLogger mockBipLogger;
    @Mock
    private CacheManager mockCacheManager;
    @Mock
    private Cache mockCache;
    @Mock
    private Cache.ValueWrapper mockValueWrapper;

/* Mocks utilized with the DB Component included
    @Mock
    private SampleDataHelper mockSampleDataHelper;
    @Mock
    private SampleData2 mockSampleData2;
*/

/* Mocks utilized with the Partner Component included
    @Mock
    private PartnerHelper mockPartnerHelper;
*/

    private final Long PARTICIPANT_ID = 1L;
    private final String EXCEPTION_MESSAGE = "Test Exception Message";

    @Before
    public void setUp() {
        instance = new RatingsSvcServiceImpl();

        MockitoAnnotations.initMocks(this);

        try {
            RatingsSvcTestUtils.setFinalStatic(RatingsSvcServiceImpl.class.getDeclaredField("LOGGER"), mockBipLogger);
        } catch (Exception e) {
            // Ignore and attempt to test without
        }
        ReflectionTestUtils.setField(instance, "cacheManager", mockCacheManager);
        /* Mocks utilized with the DB Component included
        ReflectionTestUtils.setField(instance, "sampleDataHelper", mockSampleDataHelper);
         */
        /* Mocks utilized with the Partner Component included
        ReflectionTestUtils.setField(instance, "partnerHelper", mockPartnerHelper);
         */
    }

    /* Test commented out by default, as it will break if a component is added without changing the setUp() function
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
     */

    /* Test utilized when the DB component is included
    @Test
    public void testPostConstruct_Failure_DB() {
        // Setup
        ReflectionTestUtils.setField(instance, "sampleDataHelper", null);

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            return;
        }

        // Verifications
        fail("A BipValidationRuntimeException should have been thrown here.");
    }
     */

    /* Test utilized when the Partner component is included
    @Test
    public void testPostConstruct_Failure_Partner() {
        // Setup
        ReflectionTestUtils.setField(instance, "partnerHelper", null);

        // Execute Test
        try {
            instance.postConstruct();
        } catch (BipValidationRuntimeException e) {
            return;
        }

        // Verifications
        fail("A BipValidationRuntimeException should have been thrown here.");
    }
     */

    @Test
    public void testSampleFindByParticipantID_Cached() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        SampleDomainResponse sampleDomainResponse = createSampleDomainResponse();

        when(mockCacheManager.getCache(anyString())).thenReturn(mockCache);
        when(mockCache.get(anyString())).thenReturn(mockValueWrapper);
        when(mockCache.get(anyString(), eq(SampleDomainResponse.class))).thenReturn(sampleDomainResponse);

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantID(sampleDomainRequest);

        // Verifications
        assertNotNull(response);
        assertEquals(sampleDomainResponse, response);
    }

    /* Test commented out by default, as it will break if a component is added without changing the setUp() function
    @Test
    public void testSampleFindByParticipantID_UnCached() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        int expectedNumMessages = 1;
        expectedNumMessages++; // Add one for the DB component branch
        expectedNumMessages++; // Add one for the Partner component branch
        Set<String> expectedServiceMessageKeys = new HashSet<>();
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA.getKey());
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_DATABASE_CALL_PERFORMED.getKey()); // Add for the DB component branch
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_PARTNER_MOCK_DATA.getKey()); // Add for the Partner component branch

        when(mockSampleDataHelper.getSampleDataForPid(PARTICIPANT_ID)).thenReturn(mockSampleData2); // Add for the DB component branch

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantID(sampleDomainRequest);

        // Verifications
        assertNotNull(response);
        assertNotNull(response.getSampleInfo());
        assertEquals("JANE DOE", response.getSampleInfo().getName());
        assertEquals(PARTICIPANT_ID, response.getSampleInfo().getParticipantId());

        assertEquals(expectedNumMessages, response.getMessages().size());

        Set<String> serviceMessageKeys = new HashSet<>();
        for(int i = 0; i < expectedNumMessages; i++) {
            ServiceMessage serviceMessage = response.getMessages().get(i);

            assertEquals(MessageSeverity.INFO, serviceMessage.getSeverity());
            assertEquals(HttpStatus.OK, serviceMessage.getHttpStatus());
            serviceMessageKeys.add(serviceMessage.getKey());
        }

        assertEquals(expectedServiceMessageKeys, serviceMessageKeys);
    }
     */

    /* Test commented out by default, as it will break if a component is added without changing the setUp() function
    @Test
    public void testSampleFindByParticipantID_UnCached_NoData() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        int expectedNumMessages = 1;
        expectedNumMessages++; // Add one for the DB component branch
        expectedNumMessages++; // Add one for the Partner component branch
        Set<String> expectedServiceMessageKeys = new HashSet<>();
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA.getKey());
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_DATABASE_CALL_RETURNED_NULL.getKey()); // Add for the DB component branch
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_PARTNER_MOCK_DATA.getKey()); // Add for the Partner component branch

        when(mockSampleDataHelper.getSampleDataForPid(PARTICIPANT_ID)).thenReturn(null); // Add for the DB component branch

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantID(sampleDomainRequest);

        // Verifications
        assertNotNull(response);
        assertNotNull(response.getSampleInfo());
        assertEquals("JANE DOE", response.getSampleInfo().getName());
        assertEquals(PARTICIPANT_ID, response.getSampleInfo().getParticipantId());

        assertEquals(expectedNumMessages, response.getMessages().size());

        Set<String> serviceMessageKeys = new HashSet<>();
        for(int i = 0; i < expectedNumMessages; i++) {
            ServiceMessage serviceMessage = response.getMessages().get(i);

            assertEquals(MessageSeverity.INFO, serviceMessage.getSeverity());
            assertEquals(HttpStatus.OK, serviceMessage.getHttpStatus());
            serviceMessageKeys.add(serviceMessage.getKey());
        }

        assertEquals(expectedServiceMessageKeys, serviceMessageKeys);
    }
     */

    /* Test utilized when the DB and/or Partner components is included
    @Test
    public void testSampleFindByParticipantID_UnCached_Exception() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        BipRuntimeException expectedRuntimeException = new BipRuntimeException(MessageKeys.VOP_GLOBAL_GENERAL_EXCEPTION,
                MessageSeverity.INFO, HttpStatus.OK, "");
        int expectedNumMessages = 1;
        expectedNumMessages++; // Add one for the DB component branch
        expectedNumMessages++; // Add one for the Partner component branch

        Set<String> expectedServiceMessageKeys = new HashSet<>();
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA.getKey());
        expectedServiceMessageKeys.add(MessageKeys.VOP_GLOBAL_GENERAL_EXCEPTION.getKey()); // Add for the DB and/or Partner component branch

        when(mockSampleDataHelper.getSampleDataForPid(PARTICIPANT_ID)).thenThrow(expectedRuntimeException);

        try {
            when(mockPartnerHelper.sampleFindByPid(sampleDomainRequest)).thenThrow(expectedRuntimeException);
        } catch (BipException e) {
            // Ignore
        }

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantID(sampleDomainRequest);

        // Verifications
        assertNotNull(response);
        assertNotNull(response.getSampleInfo());
        assertEquals("JANE DOE", response.getSampleInfo().getName());
        assertEquals(PARTICIPANT_ID, response.getSampleInfo().getParticipantId());

        assertEquals(expectedNumMessages, response.getMessages().size());

        Set<String> serviceMessageKeys = new HashSet<>();
        for(int i = 0; i < expectedNumMessages; i++) {
            ServiceMessage serviceMessage = response.getMessages().get(i);

            assertEquals(MessageSeverity.INFO, serviceMessage.getSeverity());
            assertEquals(HttpStatus.OK, serviceMessage.getHttpStatus());
            serviceMessageKeys.add(serviceMessage.getKey());
        }

        assertEquals(expectedServiceMessageKeys, serviceMessageKeys);
    }
     */

    /* Test commented out by default, as it will break if a component is added without changing the setUp() function
    @Test
    public void testSampleFindByParticipantID_Exception() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        RuntimeException expectedRuntimeException = new RuntimeException(EXCEPTION_MESSAGE);
        int expectedNumMessages = 1;
        expectedNumMessages++; // Add one for the DB component branch
        expectedNumMessages++; // Add one for the Partner component branch
        Set<String> expectedServiceMessageKeys = new HashSet<>();
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA.getKey());
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_DATABASE_CALL_PERFORMED.getKey()); // Add for the DB component branch
        expectedServiceMessageKeys.add(RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_PARTNER_MOCK_DATA.getKey()); // Add for the Partner component branch

        when(mockSampleDataHelper.getSampleDataForPid(PARTICIPANT_ID)).thenReturn(mockSampleData2); // Add for the DB component branch

        when(mockCacheManager.getCache(anyString())).thenReturn(mockCache);
        when(mockCache.get(anyString())).thenReturn(mockValueWrapper);
        when(mockCache.get(anyString(), eq(SampleDomainResponse.class))).thenThrow(expectedRuntimeException);

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantID(sampleDomainRequest);

        // Verifications
        verify(mockBipLogger).error(EXCEPTION_MESSAGE, expectedRuntimeException);
        assertNotNull(response);
        assertNotNull(response.getSampleInfo());
        assertEquals("JANE DOE", response.getSampleInfo().getName());
        assertEquals(PARTICIPANT_ID, response.getSampleInfo().getParticipantId());

        assertEquals(expectedNumMessages, response.getMessages().size());

        Set<String> serviceMessageKeys = new HashSet<>();
        for(int i = 0; i < expectedNumMessages; i++) {
            ServiceMessage serviceMessage = response.getMessages().get(i);

            assertEquals(MessageSeverity.INFO, serviceMessage.getSeverity());
            assertEquals(HttpStatus.OK, serviceMessage.getHttpStatus());
            serviceMessageKeys.add(serviceMessage.getKey());
        }

        assertEquals(expectedServiceMessageKeys, serviceMessageKeys);
    }
     */

    @Test
    public void testSampleFindByParticipantIDFallBack_Throwable() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();
        RuntimeException expectedRuntimeException = new RuntimeException(EXCEPTION_MESSAGE);

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantIDFallBack(sampleDomainRequest, expectedRuntimeException);

        // Verifications
        assertNotNull(response);
        assertNull(response.getSampleInfo());
        assertEquals(1, response.getMessages().size());
        assertEquals(MessageSeverity.WARN, response.getMessages().get(0).getSeverity());
        assertEquals(HttpStatus.OK, response.getMessages().get(0).getHttpStatus());
        assertEquals(MessageKeys.VOP_GLOBAL_GENERAL_EXCEPTION.getKey(), response.getMessages().get(0).getKey());
    }

    @Test
    public void testSampleFindByParticipantIDFallBack_NoThrowable() {
        // Setup
        SampleDomainRequest sampleDomainRequest = createSampleDomainRequest();

        // Execute Test
        SampleDomainResponse response = instance.sampleFindByParticipantIDFallBack(sampleDomainRequest, null);

        // Verifications
        verify(mockBipLogger).error(anyString(), any(Object.class));
        assertNotNull(response);
        assertNull(response.getSampleInfo());
        assertEquals(1, response.getMessages().size());
        assertEquals(MessageSeverity.WARN, response.getMessages().get(0).getSeverity());
        assertEquals(HttpStatus.OK, response.getMessages().get(0).getHttpStatus());
        assertEquals(MessageKeys.WARN_KEY.getKey(), response.getMessages().get(0).getKey());
    }

    private SampleDomainRequest createSampleDomainRequest() {
        SampleDomainRequest sampleDomainRequest = new SampleDomainRequest();
        sampleDomainRequest.setParticipantID(PARTICIPANT_ID);

        return sampleDomainRequest;
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
