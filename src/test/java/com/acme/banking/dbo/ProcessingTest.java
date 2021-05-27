package com.acme.banking.dbo;

import com.acme.banking.dbo.domain.Account;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.domain.SavingAccount;
import com.acme.banking.dbo.repository.ClientRepository;
import com.acme.banking.dbo.service.Processing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ProcessingTest {
    @Test
    public void shouldNotSaveClientWhenClientIsNull() {
        ClientRepository clientsRepositoryDummy = mock(ClientRepository.class);
        final Processing sut = new Processing(clientsRepositoryDummy);

        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.createClient(null)
        );
        assertEquals("client!", thrown.getMessage());
    }

    @Test
    public void shouldSaveClientWhenClientIsValid() {
        final String VALID_CLIENT_NAME = "valid expected client name";
        final Integer VALID_CLIENT_ID = 1;

        Client clientStub = mock(Client.class);
        when(clientStub.getName()).thenReturn(VALID_CLIENT_NAME);

        Client savedClientStub = mock(Client.class);
        when(savedClientStub.getName()).thenReturn(VALID_CLIENT_NAME);
        when(savedClientStub.getId()).thenReturn(VALID_CLIENT_ID);

        ClientRepository clientsRepositoryStub = mock(ClientRepository.class);
//        when(clientsRepositoryStub.save(any(Client.class))).thenReturn(clientStub)
        when(clientsRepositoryStub.save(clientStub)).thenReturn(savedClientStub);

        final Processing sut = new Processing(clientsRepositoryStub);


        final Client savedClient = sut.createClient(clientStub);

        assertNotNull(savedClient);
        assertEquals(VALID_CLIENT_NAME, savedClient.getName());
        assertEquals(VALID_CLIENT_ID, savedClient.getId());
    }

    @Test
    public void shouldTransferWhenAccountAreValid() {
        ClientRepository accountRepositoryMock = mock(ClientRepository.class);
        final Processing sut = new Processing(repo);

        sut.transfer(1, 2, 100);

        verify(accountRepositoryMock).update(any(Account.class)); //https://stackoverflow.com/questions/1142837/verify-object-attribute-value-with-mockito
    }
}
