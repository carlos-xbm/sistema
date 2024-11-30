package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.EntradaProduto;
import com.projeto.sistema.repositorios.EntradaProdutoRepositorio;
import com.projeto.sistema.repositorios.FornecedorRepositorio;
import com.projeto.sistema.repositorios.FuncionarioRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntradaProdutoControle {
    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;// lista de fornecedor

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;// lista de funcionario

    @GetMapping("/cadastroEntradaProduto")
    public ModelAndView cadastrar(EntradaProduto entradaProduto) {
        ModelAndView mv = new ModelAndView("administrativo/entradaProduto/cadastro");
        mv.addObject("entradaProduto", entradaProduto);
        mv.addObject("ListaFornecedores", fornecedorRepositorio.findAll()); // lista de cidade
        mv.addObject("ListaFuncionarios", funcionarioRepositorio.findAll()); // lista de funcinario
        return mv;
    }

    @GetMapping("/listarEntradaProduto")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/entradaProduto/lista");
        mv.addObject("ListaEntradaProdutos", entradaProdutoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarEntradaProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<EntradaProduto> entradaProduto = entradaProdutoRepositorio.findById(id);
        return cadastrar(entradaProduto.get());
    }

    @PostMapping("/salvarEntradaProduto")
    public ModelAndView salvar(EntradaProduto entradaProduto, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(entradaProduto);
        }
        entradaProdutoRepositorio.saveAndFlush(entradaProduto);
        return cadastrar(new EntradaProduto());
    }

    @GetMapping("/removerEntradaProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<EntradaProduto> entradaProduto = entradaProdutoRepositorio.findById(id);
        entradaProdutoRepositorio.delete(entradaProduto.get());
        return listar();
    }

}
