package com.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Agenda {
    private final List<Contato> contatos = new ArrayList<>();
    private int proximoId = 1;

    /**
     * Adiciona um novo contato à agenda.
     *
     * @param contato O contato a ser adicionado.
     * @throws IllegalArgumentException Se o nome ou telefone forem nulos/vazios, ou se o contato já existir.
     */
    public void adicionarContato(Contato contato) {
        if (contato.getNome() == null || contato.getNome().trim().isEmpty() ||
            contato.getTelefone() == null || contato.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome e telefone são obrigatórios.");
        }

        if (contatos.stream().anyMatch(c -> c.equals(contato))) {
            throw new IllegalArgumentException("Contato com o mesmo nome e telefone já existe.");
        }

        contato.setId(proximoId++);
        contatos.add(contato);
    }

    /**
     * Retorna uma lista de todos os contatos.
     *
     * @return Uma lista não modificável de contatos.
     */
    public List<Contato> listarContatos() {
        return Collections.unmodifiableList(contatos);
    }

    /**
     * Busca contatos por nome ou e-mail.
     *
     * @param query O termo de busca.
     * @return Uma lista de contatos que correspondem à busca.
     */
    public List<Contato> buscarContato(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return contatos.stream()
                .filter(c -> c.getNome().toLowerCase().contains(lowerCaseQuery) ||
                             (c.getEmail() != null && c.getEmail().toLowerCase().contains(lowerCaseQuery)))
                .collect(Collectors.toList());
    }

    /**
     * Edita um contato existente.
     *
     * @param id           O ID do contato a ser editado.
     * @param dadosNovos As novas informações do contato.
     * @throws NoSuchElementException Se nenhum contato com o ID fornecido for encontrado.
     */
    public void editarContato(int id, Contato dadosNovos) {
        Contato contatoExistente = contatos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Contato não encontrado para o ID: " + id));

        contatoExistente.setNome(dadosNovos.getNome());
        contatoExistente.setTelefone(dadosNovos.getTelefone());
        contatoExistente.setEmail(dadosNovos.getEmail());
    }

    /**
     * Remove um contato da agenda pelo seu ID.
     *
     * @param id O ID do contato a ser removido.
     */
    public void removerContato(int id) {
        contatos.removeIf(c -> c.getId() == id);
    }
}
