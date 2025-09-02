package com.agenda.testes;

import com.agenda.Agenda;
import com.agenda.Contato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Agenda")
public class AgendaTest {

    private Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();
    }

    @Test
    @DisplayName("Deve adicionar um contato válido com sucesso")
    void deveAdicionarContatoValido() {
        Contato contato = new Contato("Jules", "123456789", "jules@example.com");
        agenda.adicionarContato(contato);
        assertEquals(1, agenda.listarContatos().size());
        assertEquals("Jules", agenda.listarContatos().get(0).getNome());
    }

    @Test
    @DisplayName("Não deve adicionar contato com nome vazio")
    void naoDeveAdicionarContatoComNomeVazio() {
        Contato contato = new Contato("", "123456789", "test@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agenda.adicionarContato(contato);
        });
        assertEquals("Nome e telefone são obrigatórios.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve adicionar contato com telefone vazio")
    void naoDeveAdicionarContatoComTelefoneVazio() {
        Contato contato = new Contato("Teste", "", "test@example.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agenda.adicionarContato(contato);
        });
        assertEquals("Nome e telefone são obrigatórios.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve adicionar contato duplicado")
    void naoDeveAdicionarContatoDuplicado() {
        Contato contato1 = new Contato("Jules", "123456789", "jules1@example.com");
        Contato contato2 = new Contato("Jules", "123456789", "jules2@example.com");
        agenda.adicionarContato(contato1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agenda.adicionarContato(contato2);
        });
        assertEquals("Contato com o mesmo nome e telefone já existe.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar todos os contatos")
    void deveListarContatos() {
        agenda.adicionarContato(new Contato("Jules", "123", "jules@test.com"));
        agenda.adicionarContato(new Contato("Maria", "456", "maria@test.com"));
        List<Contato> contatos = agenda.listarContatos();
        assertEquals(2, contatos.size());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia se não houver contatos")
    void deveRetornarListaVazia() {
        assertTrue(agenda.listarContatos().isEmpty());
    }

    @Test
    @DisplayName("Deve buscar contato por nome")
    void deveBuscarContatoPorNome() {
        agenda.adicionarContato(new Contato("Jules Verne", "123", "jules@test.com"));
        List<Contato> resultado = agenda.buscarContato("Jules");
        assertEquals(1, resultado.size());
        assertEquals("Jules Verne", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar contato por e-mail")
    void deveBuscarContatoPorEmail() {
        agenda.adicionarContato(new Contato("Jules", "123", "jules.verne@example.com"));
        List<Contato> resultado = agenda.buscarContato("verne@example.com");
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve retornar lista vazia se busca não encontrar resultados")
    void deveRetornarVazioParaBuscaSemResultado() {
        List<Contato> resultado = agenda.buscarContato("Inexistente");
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve editar um contato existente")
    void deveEditarContatoExistente() {
        Contato contatoOriginal = new Contato("Jules", "123", null);
        agenda.adicionarContato(contatoOriginal);
        int id = contatoOriginal.getId();

        Contato contatoEditado = new Contato("Jules Editado", "987", "edited@test.com");
        agenda.editarContato(id, contatoEditado);

        Contato contatoVerificado = agenda.listarContatos().get(0);
        assertEquals("Jules Editado", contatoVerificado.getNome());
        assertEquals("987", contatoVerificado.getTelefone());
        assertEquals("edited@test.com", contatoVerificado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar editar contato inexistente")
    void deveLancarExcecaoAoEditarInexistente() {
        assertThrows(NoSuchElementException.class, () -> {
            agenda.editarContato(999, new Contato("Fantasma", "000", null));
        });
    }

    @Test
    @DisplayName("Deve remover um contato existente")
    void deveRemoverContatoExistente() {
        Contato contato = new Contato("Para Remover", "111", null);
        agenda.adicionarContato(contato);
        assertEquals(1, agenda.listarContatos().size());

        agenda.removerContato(contato.getId());
        assertTrue(agenda.listarContatos().isEmpty());
    }

    @Test
    @DisplayName("Não deve alterar a lista ao tentar remover contato inexistente")
    void naoDeveAlterarListaAoRemoverInexistente() {
        agenda.adicionarContato(new Contato("Jules", "123", null));
        agenda.removerContato(999); // ID inexistente
        assertEquals(1, agenda.listarContatos().size());
    }
}
