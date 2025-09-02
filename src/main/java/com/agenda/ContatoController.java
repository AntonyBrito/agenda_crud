package com.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public ResponseEntity<Contato> criarContato(@Valid @RequestBody Contato contato) {
        Contato novoContato = contatoService.criarContato(contato);
        return new ResponseEntity<>(novoContato, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contato>> listarContatos() {
        List<Contato> contatos = contatoService.listarTodos();
        return ResponseEntity.ok(contatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarContatoPorId(@PathVariable Integer id) {
        Contato contato = contatoService.buscarPorId(id);
        return ResponseEntity.ok(contato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizarContato(@PathVariable Integer id, @Valid @RequestBody Contato contato) {
        Contato contatoAtualizado = contatoService.atualizarContato(id, contato);
        return ResponseEntity.ok(contatoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarContato(@PathVariable Integer id) {
        contatoService.deletarContato(id);
        return ResponseEntity.noContent().build();
    }
}
