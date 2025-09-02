package com.agenda.testes;

import com.agenda.Agenda;
import com.agenda.Contato;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Gerenciamento de Agenda de Contatos")
@Feature("Funcionalidades Principais")
@DisplayName("Testes de Aceitação da Agenda")
public class AgendaAcceptanceTest {

    private Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();
    }

    @Test
    @Story("Adicionar e Listar Contato")
    @DisplayName("Deve adicionar um contato e depois listá-lo com sucesso")
    @Description("Este teste simula o fluxo de um usuário adicionando um novo contato e verificando se ele aparece na lista de contatos.")
    void deveAdicionarEListarContato() {
        // Given
        Contato novoContato = criarContato("Jules", "111222333", "jules@universe.com");

        // When
        adicionarContatoNaAgenda(novoContato);

        // Then
        verificarContatoNaLista(novoContato);
    }

    @Test
    @Story("Adicionar, Editar e Verificar Contato")
    @DisplayName("Deve adicionar, editar e verificar as alterações em um contato")
    @Description("Este teste simula o fluxo de adicionar um contato, editá-lo e garantir que as alterações foram salvas.")
    void deveAdicionarEditarEVerificarContato() {
        // Given
        Contato contatoOriginal = criarContato("Maria", "999888777", "maria@test.com");
        adicionarContatoNaAgenda(contatoOriginal);
        int contatoId = contatoOriginal.getId();

        // When
        Contato dadosEditados = criarContato("Maria Silva", "999888777", "maria.silva@test.com");
        editarContatoNaAgenda(contatoId, dadosEditados);

        // Then
        verificarAlteracoesDoContato(contatoId, dadosEditados);
    }

    @Test
    @Story("Adicionar, Remover e Verificar Contato")
    @DisplayName("Deve adicionar um contato, removê-lo e verificar sua ausência")
    @Description("Este teste simula o fluxo de adicionar um contato, removê-lo e confirmar que ele não está mais na agenda.")
    void deveAdicionarRemoverEVerificarContato() {
        // Given
        Contato contatoParaRemover = criarContato("Carlos", "555666777", "carlos@test.com");
        adicionarContatoNaAgenda(contatoParaRemover);
        int contatoId = contatoParaRemover.getId();

        // When
        removerContatoDaAgenda(contatoId);

        // Then
        verificarRemocaoDoContato(contatoId);
    }

    @Step("Cria um novo objeto Contato com nome: {nome}, telefone: {telefone}, e-mail: {email}")
    private Contato criarContato(String nome, String telefone, String email) {
        return new Contato(nome, telefone, email);
    }

    @Step("Adiciona o contato '{contato.nome}' na agenda")
    private void adicionarContatoNaAgenda(Contato contato) {
        agenda.adicionarContato(contato);
    }

    @Step("Verifica se o contato '{contato.nome}' está na lista de contatos")
    private void verificarContatoNaLista(Contato contato) {
        assertTrue(agenda.listarContatos().contains(contato), "O contato deveria estar na lista.");
        assertEquals(1, agenda.listarContatos().size(), "A agenda deveria ter exatamente um contato.");
    }

    @Step("Edita o contato com ID {id} para ter os dados de '{dadosNovos.nome}'")
    private void editarContatoNaAgenda(int id, Contato dadosNovos) {
        agenda.editarContato(id, dadosNovos);
    }

    @Step("Verifica se os dados do contato com ID {id} foram atualizados para '{dadosEditados.nome}'")
    private void verificarAlteracoesDoContato(int id, Contato dadosEditados) {
        Contato contatoAtualizado = agenda.listarContatos().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        assertNotNull(contatoAtualizado, "O contato não deveria ser nulo.");
        assertEquals(dadosEditados.getNome(), contatoAtualizado.getNome(), "O nome deveria ter sido atualizado.");
        assertEquals(dadosEditados.getEmail(), contatoAtualizado.getEmail(), "O e-mail deveria ter sido atualizado.");
    }

    @Step("Remove o contato com ID {id} da agenda")
    private void removerContatoDaAgenda(int id) {
        agenda.removerContato(id);
    }

    @Step("Verifica se o contato com ID {id} foi removido da agenda")
    private void verificarRemocaoDoContato(int id) {
        boolean contatoAindaExiste = agenda.listarContatos().stream().anyMatch(c -> c.getId() == id);
        assertFalse(contatoAindaExiste, "O contato deveria ter sido removido da lista.");
        assertTrue(agenda.listarContatos().isEmpty(), "A agenda deveria estar vazia.");
    }
}
