package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.Produto;
import com.projeto.sistema.repositorios.ProdutoRepositorio;
import com.projeto.sistema.repositorios.CidadeRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoControle {
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private CidadeRepositorio cidadeRepositorio;// lista de cidade

    @GetMapping("/cadastroProduto")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produto/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("ListaCidades", cidadeRepositorio.findAll()); // lista de cidade
        return mv;
    }

    @GetMapping("/listarProduto")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produto/lista");
        mv.addObject("ListaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        return cadastrar(produto.get());
    }

    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(produto);
        }
        produtoRepositorio.saveAndFlush(produto);
        return cadastrar(new Produto());
    }

    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        produtoRepositorio.delete(produto.get());
        return listar();
    }

}
