package com.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;

    @Autowired
    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contato criarContato(Contato contato) {
        if (contatoRepository.existsByTelefone(contato.getTelefone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contato com este telefone já existe.");
        }
        return contatoRepository.save(contato);
    }

    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    public Contato buscarPorId(Integer id) {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }

    public Contato atualizarContato(Integer id, Contato contatoAtualizado) {
        return contatoRepository.findById(id)
                .map(contatoExistente -> {
                    contatoExistente.setNome(contatoAtualizado.getNome());
                    contatoExistente.setTelefone(contatoAtualizado.getTelefone());
                    contatoExistente.setEmail(contatoAtualizado.getEmail());
                    return contatoRepository.save(contatoExistente);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado."));
    }

    public void deletarContato(Integer id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado.");
        }
        contatoRepository.deleteById(id);
    }
}
