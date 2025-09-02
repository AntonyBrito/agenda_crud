package com.agenda.testes;

import com.agenda.Contato;
import com.agenda.ContatoRepository;
import com.agenda.ContatoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários do ContatoService")
public class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoService contatoService;

    @Test
    @DisplayName("Deve criar um contato com sucesso")
    void deveCriarContatoComSucesso() {
        Contato contato = new Contato("Jules", "12345", "jules@test.com");
        when(contatoRepository.existsByTelefone(contato.getTelefone())).thenReturn(false);
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        Contato novoContato = contatoService.criarContato(contato);

        assertNotNull(novoContato);
        assertEquals("Jules", novoContato.getNome());
        verify(contatoRepository).save(contato);
    }

    @Test
    @DisplayName("Não deve criar contato com telefone duplicado")
    void naoDeveCriarContatoComTelefoneDuplicado() {
        Contato contato = new Contato("Jules", "12345", "jules@test.com");
        when(contatoRepository.existsByTelefone(contato.getTelefone())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            contatoService.criarContato(contato);
        });

        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Deve listar todos os contatos")
    void deveListarTodosContatos() {
        when(contatoRepository.findAll()).thenReturn(List.of(new Contato(), new Contato()));
        List<Contato> contatos = contatoService.listarTodos();
        assertEquals(2, contatos.size());
    }

    @Test
    @DisplayName("Deve buscar um contato por ID")
    void deveBuscarContatoPorId() {
        Contato contato = new Contato("Jules", "123", null);
        contato.setId(1);
        when(contatoRepository.findById(1)).thenReturn(Optional.of(contato));

        Contato encontrado = contatoService.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals(1, encontrado.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(contatoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            contatoService.buscarPorId(99);
        });
    }

    @Test
    @DisplayName("Deve atualizar um contato existente")
    void deveAtualizarContatoExistente() {
        Contato existente = new Contato("Old Name", "123", "old@test.com");
        existente.setId(1);
        Contato atualizado = new Contato("New Name", "456", "new@test.com");

        when(contatoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(contatoRepository.save(any(Contato.class))).thenReturn(atualizado);

        Contato resultado = contatoService.atualizarContato(1, atualizado);

        assertNotNull(resultado);
        assertEquals("New Name", resultado.getNome());
        verify(contatoRepository).save(existente);
    }

    @Test
    @DisplayName("Deve deletar um contato existente")
    void deveDeletarContatoExistente() {
        when(contatoRepository.existsById(1)).thenReturn(true);
        doNothing().when(contatoRepository).deleteById(1);

        contatoService.deletarContato(1);

        verify(contatoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar ID inexistente")
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(contatoRepository.existsById(99)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            contatoService.deletarContato(99);
        });

        verify(contatoRepository, never()).deleteById(anyInt());
    }
}
