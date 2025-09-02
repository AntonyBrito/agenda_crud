package com.agenda.testes;

import com.agenda.Contato;
import com.agenda.ContatoRepository;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Epic("Gerenciamento de Agenda de Contatos API")
@Feature("Endpoints de Contatos")
@DisplayName("Testes de Integração para ContatoController")
public class ContatoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContatoRepository contatoRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/contatos";
        contatoRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        contatoRepository.deleteAll();
    }

    @Test
    @Story("Criar e Buscar Contato")
    @DisplayName("Deve criar um contato e depois buscá-lo com sucesso")
    @Description("Testa o fluxo completo de criar um contato via POST e depois buscá-lo via GET.")
    public void deveCriarEBuscarContato() {
        // Given
        Contato novoContato = new Contato("Jules API", "987654321", "jules.api@test.com");

        // When
        ResponseEntity<Contato> responseCriar = criarContato(novoContato);
        Contato contatoCriado = responseCriar.getBody();

        // Then
        assertAll("Verificações de Criação e Busca",
            () -> assertEquals(HttpStatus.CREATED, responseCriar.getStatusCode()),
            () -> assertNotNull(contatoCriado),
            () -> assertNotNull(contatoCriado.getId()),
            () -> {
                ResponseEntity<Contato> responseBuscar = buscarContato(contatoCriado.getId());
                assertEquals(HttpStatus.OK, responseBuscar.getStatusCode());
                assertEquals(contatoCriado.getNome(), responseBuscar.getBody().getNome());
            }
        );
    }

    @Test
    @Story("Listar Contatos")
    @DisplayName("Deve listar todos os contatos")
    public void deveListarTodosContatos() {
        // Given
        criarContato(new Contato("Contato 1", "111", "c1@test.com"));
        criarContato(new Contato("Contato 2", "222", "c2@test.com"));

        // When
        ResponseEntity<List<Contato>> response = restTemplate.exchange(
            baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Contato>>() {});

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Story("Atualizar Contato")
    @DisplayName("Deve atualizar um contato existente")
    public void deveAtualizarContato() {
        // Given
        Contato contatoExistente = criarContato(new Contato("Original", "123", "orig@test.com")).getBody();
        Contato dadosAtualizados = new Contato("Atualizado", "123", "new@test.com");

        // When
        HttpEntity<Contato> requestUpdate = new HttpEntity<>(dadosAtualizados);
        ResponseEntity<Contato> response = restTemplate.exchange(
            baseUrl + "/" + contatoExistente.getId(), HttpMethod.PUT, requestUpdate, Contato.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Atualizado", response.getBody().getNome());
        assertEquals("new@test.com", response.getBody().getEmail());
    }

    @Test
    @Story("Deletar Contato")
    @DisplayName("Deve deletar um contato existente")
    public void deveDeletarContato() {
        // Given
        Contato contatoParaDeletar = criarContato(new Contato("Para Deletar", "555", "del@test.com")).getBody();

        // When
        restTemplate.delete(baseUrl + "/" + contatoParaDeletar.getId());

        // Then
        ResponseEntity<Contato> response = buscarContato(contatoParaDeletar.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Step("Cria um contato via API POST")
    private ResponseEntity<Contato> criarContato(Contato contato) {
        return restTemplate.postForEntity(baseUrl, contato, Contato.class);
    }

    @Step("Busca um contato por ID via API GET")
    private ResponseEntity<Contato> buscarContato(Integer id) {
        return restTemplate.getForEntity(baseUrl + "/" + id, Contato.class);
    }
}
