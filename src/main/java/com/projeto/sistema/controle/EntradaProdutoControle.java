package com.projeto.sistema.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.EntradaProduto;
import com.projeto.sistema.modelos.ItemEntrada;
import com.projeto.sistema.modelos.Produto;
import com.projeto.sistema.repositorios.EntradaProdutoRepositorio;
import com.projeto.sistema.repositorios.FornecedorRepositorio;
import com.projeto.sistema.repositorios.FuncionarioRepositorio;
import com.projeto.sistema.repositorios.ItemEntradaRepositorio;
import com.projeto.sistema.repositorios.ProdutoRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntradaProdutoControle {
    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;// lista de fornecedor

    @Autowired
    private ItemEntradaRepositorio itemEntradaRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;// lista de funcionario

    private List<ItemEntrada> listaItemEntrada = new ArrayList<ItemEntrada>();

    @GetMapping("/cadastroEntradaProduto")
    public ModelAndView cadastrar(EntradaProduto entradaProduto, ItemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/entradaProduto/cadastro");
        mv.addObject("EntradaProduto", entradaProduto);
        mv.addObject("ListaFornecedores", fornecedorRepositorio.findAll()); // lista de cidade
        mv.addObject("ListaFuncionarios", funcionarioRepositorio.findAll()); // lista de funcinario
        mv.addObject("ListaItemEntradas", itemEntradaRepositorio.findAll()); // lista de itemEntrda
        mv.addObject("ListaProdutos", produtoRepositorio.findAll()); // lista de produtos
        mv.addObject("ListaItemEntradas", this.listaItemEntrada);

        return mv;
    }

    @GetMapping("/listarEntradaProduto")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/entradaProduto/lista");
        mv.addObject("ListaEntradaProdutos", entradaProdutoRepositorio.findAll());
        return mv;
    }

    // @GetMapping("/editarEntradaProduto/{id}")
    // public ModelAndView editar(@PathVariable("id") Long id) {
    // Optional<EntradaProduto> entradaProduto =
    // entradaProdutoRepositorio.findById(id);
    // return cadastrar(entradaProduto.get());
    // }

    @PostMapping("/salvarEntradaProduto")
    public ModelAndView salvar(String acao, EntradaProduto entradaProduto, ItemEntrada itemEntrada, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(entradaProduto, itemEntrada);
        }

        if (acao.equals("itens")) {
            this.listaItemEntrada.add(itemEntrada);
        }else if (acao.equals("salvar")) {
            entradaProdutoRepositorio.saveAndFlush(entradaProduto);

            for(ItemEntrada it: listaItemEntrada ){
                it.setEntradaProduto(itemEntrada);
                itemEntradaRepositorio.saveAndFlush(itemEntrada);
                Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.getPrecoVenda();
            }
        }
        entradaProdutoRepositorio.saveAndFlush(entradaProduto);
        return cadastrar(new EntradaProduto(), new ItemEntrada());
        
    }

    // @GetMapping("/removerEntradaProduto/{id}")
    // public ModelAndView remover(@PathVariable("id") Long id) {
    // Optional<EntradaProduto> entradaProduto =
    // entradaProdutoRepositorio.findById(id);
    // entradaProdutoRepositorio.delete(entradaProduto.get());
    // return listar();
    // }

}
