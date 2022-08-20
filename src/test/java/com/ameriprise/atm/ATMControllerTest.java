package com.ameriprise.atm;

import com.ameriprise.atm.controller.AccountController;
import com.ameriprise.atm.controller.ErrorMessage;
import com.ameriprise.atm.controller.GlobalExceptionHandler;
import com.ameriprise.atm.dto.AccountDto;
import com.ameriprise.atm.dto.request.DepositRequest;
import com.ameriprise.atm.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ATMControllerTest {

    private MockMvc mvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private JacksonTester<AccountDto> accountDTO;

    private JacksonTester<ErrorMessage> errorMessage;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        AccountDto accountDto = new AccountDto(1L, "Vin", 1L, 1000.0);
        given(accountService.getAccountDetail(1L))
                .willReturn(Optional.of(accountDto));

        // when
        MockHttpServletResponse response = mvc.perform(get("/account/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                accountDTO.write(new AccountDto(1L, "Vin", 1L, 1000.0)).getJson());
    }

    @Test
    public void canRetrieveByIdWhenNotExists() throws Exception {
        // given
        given(accountService.getAccountDetail(1L))
                .willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mvc.perform(get("/account/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo(
                errorMessage.write(new ErrorMessage("INVALID_ACCOUNT", "Account number doesn't exist")).getJson());
    }

    @Test
    public void depositAmountWithInvalidAmount() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/account/deposit/1")
                .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(new DepositRequest(1L,-2000.0)))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                errorMessage.write(new ErrorMessage("INVALID_INPUT", "Amount can not be negative")).getJson());
    }

    @Test
    public void depositAmountWithValidAmount() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AccountDto accountDto = new AccountDto(1L, "Vin", 1L, 1000.0);
        given(accountService.deposit(Mockito.any()))
                .willReturn(Optional.of(accountDto));

        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/account/deposit/1")
                .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(new DepositRequest(1L,2000.0)))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                accountDTO.write( new AccountDto(1L, "Vin", 1L, 1000.0)).getJson());
    }

    @Test
    public void depositAmountWithInValidAccount() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AccountDto accountDto = new AccountDto(1L, "Vin", 1L, 1000.0);
        given(accountService.deposit(Mockito.any()))
                .willReturn(Optional.of(accountDto));

        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/account/deposit/1")
                .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(new DepositRequest(1L,2000.0)))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                accountDTO.write( new AccountDto(1L, "Vin", 1L, 1000.0)).getJson());
    }

    // given


}
