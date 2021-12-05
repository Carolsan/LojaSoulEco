package br.ufpb.lojaeco;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoulEcoTestTest {

    @Test
    void cadastraProduto() {
        SoulEco sistemaEco = new SoulEco();
        try {
            sistemaEco.cadastraProduto(new Produto("maçã",5,2,true));
        } catch(ProdutoJaExisteException e) {
            fail("Não poderia ter exceção");
        }
    }

    @Test
    void cadastraProduto2x(){
        SoulEco sistemaEco = new SoulEco();
        try {
            assertEquals(0, sistemaEco.todosProdutos().size());
            sistemaEco.cadastraProduto(new Produto("maçã",5,2,true));
            sistemaEco.cadastraProduto(new Produto("maçã",8,3,false));
            fail("produto já existe no sistema");
        } catch(ProdutoJaExisteException e) {
            assertEquals(1, sistemaEco.todosProdutos().size());
            assertEquals("Produto já existente", e.getMessage());
        }
    }

    @Test
    void cadastraCliente(){
        SoulEco sistemaEco = new SoulEco();
        try {
            //colocar a verificação se já existe nome
            sistemaEco.cadastraCliente(new Cliente("Adalberto", 777777, 14141411));
        } catch(ClienteJaExisteException e){
            fail("Não deveria gerar exceção");
        }
    }

    @Test
    void pesquisaCliente(){
        SoulEco sistemaEco = new SoulEco();
        try {
            assertEquals(0,sistemaEco.pesquisarClientes("Adalberto").size());
            sistemaEco.cadastraCliente(new Cliente("Adalberto", 777777, 14141411));
            Collection<Cliente> listaClientes = sistemaEco.pesquisarClientes("Adalberto");
            assertEquals(1,listaClientes.size());
        } catch( ClienteJaExisteException e){
            fail("Não deveria gerar exceção");
        }
    }

    @Test
    void removeCliente() {
        SoulEco sistemaEco = new SoulEco();
        try {
            assertEquals(0,sistemaEco.pesquisarClientes("Maria"));
            sistemaEco.cadastraCliente(new Cliente("Maria", 555, 1456));
            sistemaEco.removerCliente(555);
            Collection<Cliente> listaClientes = sistemaEco.pesquisarClientes("Maria");
            assertTrue(listaClientes.contains(new Cliente("Maria", 555, 1456)));
        } catch(ClienteJaExisteException | ClienteNaoExisteException  e) {
            fail("Não existe cliente no sistema");
        }
    }

    @Test
    void removeProduto(){
        SoulEco sistemaEco = new SoulEco();
        try{
            Produto p = new Produto("Eco bag", 25, 5, true);
            sistemaEco.cadastraProduto(p);
            assertEquals(p,sistemaEco.pesquisarProduto("Eco bag"));
            sistemaEco.removerProduto("Eco bag");

        } catch (ProdutoJaExisteException | ProdutoNaoExisteException e){
            fail("Não deveria gerar exceção");
        }


        Produto p2 = null;
        try {
            p2 = sistemaEco.pesquisarProduto("Eco bag");
            fail("Deveria lançar exceção");
        } catch (ProdutoNaoExisteException e) {
            //OK
        }
        assertEquals(0, sistemaEco.todosProdutos().size());
    }


}