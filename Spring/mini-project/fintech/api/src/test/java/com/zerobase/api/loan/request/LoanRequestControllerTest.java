package com.zerobase.api.loan.request;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.api.loan.GenerateKey;
import com.zerobase.api.loan.encrypt.EncryptComponent;
import com.zerobase.api.loan.request.LoanRequestDto.LoanRequestInputDto;
import com.zerobase.domain.domain.UserInfo;
import com.zerobase.domain.repository.UserInfoRepository;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(LoanRequestControllerTest.class)
class LoanRequestControllerTest {

    private MockMvc mockMvc;

    private LoanRequestController loanRequestController;

    private GenerateKey generateKey;

    private EncryptComponent encryptComponent;

    private UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);

    private ObjectMapper objectMapper;

    @MockBean
    private LoanRequestService loanRequestService;

    private final String baseUrl = "/fintech/api/v1";

    @BeforeEach
    void setUp() {
        generateKey = new GenerateKey();
        encryptComponent = new EncryptComponent();
        loanRequestService = new LoanRequestServiceImpl(
            generateKey, userInfoRepository, encryptComponent
        );
        loanRequestController = new LoanRequestController(loanRequestService);

        mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();

        objectMapper = new ObjectMapper();
    }

    @DisplayName("유저 요청이 들어오면 정상 응답을 주어야 한다.")
    @Test
    void loanRequest() throws Exception {
        // given
        LoanRequestDto.LoanRequestInputDto loanRequestInputDto = LoanRequestInputDto.builder()
            .userName("TEST")
            .userIncomeAmount(10000L)
            .userRegistrationNumber("000101-1233456")
            .build();

        given(userInfoRepository.save(any()))
            .willReturn(Mockito.mock(UserInfo.class));

        // when // then
        mockMvc.perform(
                post(baseUrl + "/request")
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loanRequestInputDto))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}