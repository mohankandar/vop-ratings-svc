package com.wynd.vop.ratingssvc.model.validators;

import com.wynd.vop.framework.messages.MessageSeverity;
import com.wynd.vop.framework.messages.ServiceMessage;
import com.wynd.vop.framework.security.PersonTraits;
import com.wynd.vop.ratingssvc.exception.RatingsSvcServiceException;
import com.wynd.vop.ratingssvc.messages.RatingsSvcMessageKeys;
import com.wynd.vop.ratingssvc.model.SampleDomainRequest;
import com.wynd.vop.ratingssvc.model.SampleDomainResponse;
import com.wynd.vop.ratingssvc.model.SampleInfoDomain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class SampleDomainResponseValidatorTest {

    SampleDomainResponseValidatorOverride instance;

    @Mock
    private SecurityContext mockContext;
    @Mock
    private Authentication mockAuthentication;
    @Mock
    private PersonTraits mockPersonTraits;

    private final Long PARTICIPANT_ID = 1L;

    // Need to override an inherited, protected class method for testing
    private class SampleDomainResponseValidatorOverride extends SampleDomainResponseValidator {
        @Override
        public Object getSupplemental(final Class<?> clazz) {

            return createSampleDomainRequest();
        }
    }

    @Before
    public void setUp() {
        instance = new SampleDomainResponseValidatorOverride();

        MockitoAnnotations.initMocks(this);

        SecurityContextHolder.setContext(mockContext);
        when(mockContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(mockAuthentication.getPrincipal()).thenReturn(mockPersonTraits);
        when(mockPersonTraits.getPid()).thenReturn(Long.toString(PARTICIPANT_ID));
    }

    @Test
    public void testValidate_Success() {
        // Setup
        SampleDomainResponse sampleDomainResponse = createSampleDomainResponse();
        List<ServiceMessage> messagesList = new ArrayList<>();

        // Execute Test
        instance.validate(sampleDomainResponse, messagesList);

        // Verifications
        assertEquals(0, messagesList.size());
    }

    @Test
    public void testValidate_HasWarnings() {
        // Setup
        SampleDomainResponse sampleDomainResponse = createSampleDomainResponse();
        sampleDomainResponse.addMessage(MessageSeverity.WARN, HttpStatus.OK, RatingsSvcMessageKeys.VOP_SAMPLE_SERVICE_IMPL_RESPONDED_WITH_MOCK_DATA,
                "");
        List<ServiceMessage> messagesList = new ArrayList<>();

        // Execute Test
        instance.validate(sampleDomainResponse, messagesList);

        // Verifications
        assertEquals(0, messagesList.size());
    }

    @Test
    public void testValidate_IsNull() {
        // Setup
        List<ServiceMessage> messagesList = new ArrayList<>();

        // Execute Test
        try {
            instance.validate(null, messagesList);
        } catch (RatingsSvcServiceException e) {
            // Verifications
            assertEquals(RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_NOTNULL.getKey(), e.getExceptionData().getKey());
            assertEquals(MessageSeverity.FATAL, e.getExceptionData().getSeverity());
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getExceptionData().getStatus());
            return;
        }

        // Fail Case
        fail("An exception should have been thrown!");
    }

    @Test
    public void testValidate_PidInconsistent() {
        // Setup
        SampleDomainResponse sampleDomainResponse = createSampleDomainResponse();
        sampleDomainResponse.getSampleInfo().setParticipantId(PARTICIPANT_ID + 1);
        List<ServiceMessage> messagesList = new ArrayList<>();

        // Execute Test
        instance.validate(sampleDomainResponse, messagesList);

        // Verifications
        assertEquals(2, messagesList.size());
        assertEquals(MessageSeverity.WARN, messagesList.get(0).getSeverity());
        assertEquals(HttpStatus.OK, messagesList.get(0).getHttpStatus());
        assertEquals(RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_PID_INCONSISTENT.getKey(), messagesList.get(0).getKey());
        assertEquals(MessageSeverity.WARN, messagesList.get(1).getSeverity());
        assertEquals(HttpStatus.OK, messagesList.get(1).getHttpStatus());
        assertEquals(RatingsSvcMessageKeys.VOP_SAMPLE_REQUEST_PID_INVALID.getKey(), messagesList.get(1).getKey());
    }

    @Test
    public void testSetCallingMethod() {
        // Setup
        Method expectedCallingMethod = this.getClass().getEnclosingMethod();

        // Execute Test
        instance.setCallingMethod(expectedCallingMethod);

        // Verifications
        assertEquals(expectedCallingMethod, ReflectionTestUtils.getField(instance, "callingMethod"));
    }

    @Test
    public void testGetCallingMethod() {
        // Setup
        Method expectedCallingMethod = this.getClass().getEnclosingMethod();
        ReflectionTestUtils.setField(instance, "callingMethod", expectedCallingMethod);

        // Execute Test
        Method callingMethod = instance.getCallingMethod();

        // Verifications
        assertEquals(expectedCallingMethod, callingMethod);
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
