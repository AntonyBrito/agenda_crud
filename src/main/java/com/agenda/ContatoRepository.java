package com.agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

    /**
     * Verifica se um contato com o telefone especificado já existe.
     *
     * @param telefone O telefone a ser verificado.
     * @return true se o contato existir, false caso contrário.
     */
    boolean existsByTelefone(String telefone);

    /**
     * Busca um contato pelo nome.
     *
     * @param nome O nome a ser buscado.
     * @return Um Optional contendo o contato, se encontrado.
     */
    Optional<Contato> findByNome(String nome);
}
