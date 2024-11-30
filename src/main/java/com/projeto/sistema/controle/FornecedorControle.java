package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.Fornecedor;
import com.projeto.sistema.repositorios.FornecedorRepositorio;
import com.projeto.sistema.repositorios.CidadeRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FornecedorControle {
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;
    @Autowired
    private CidadeRepositorio cidadeRepositorio;// lista de cidade

    @GetMapping("/cadastroFornecedor")
    public ModelAndView cadastrar(Fornecedor fornecedor) {
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/cadastro");
        mv.addObject("fornecedor", fornecedor);
        mv.addObject("ListaCidades", cidadeRepositorio.findAll()); // lista de cidade
        return mv;
    }

    @GetMapping("/listarFornecedor")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/lista");
        mv.addObject("ListaFornecedors", fornecedorRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarFornecedor/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
        return cadastrar(fornecedor.get());
    }

    @PostMapping("/salvarFornecedor")
    public ModelAndView salvar(Fornecedor fornecedor, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(fornecedor);
        }
        fornecedorRepositorio.saveAndFlush(fornecedor);
        return cadastrar(new Fornecedor());
    }

    @GetMapping("/removerFornecedor/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
        fornecedorRepositorio.delete(fornecedor.get());
        return listar();
    }

}
