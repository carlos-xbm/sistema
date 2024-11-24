package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.Cidade;
import com.projeto.sistema.repositorios.CidadeRepositorio;
import com.projeto.sistema.repositorios.EstadoRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CidadeControle {
    @Autowired
    private CidadeRepositorio cidadeRepositorio;
    @Autowired
    private EstadoRepositorio estadoRepositorio;// lista de estado

    @GetMapping("/cadastroCidade")
    public ModelAndView cadastrar(Cidade cidade) {
        ModelAndView mv = new ModelAndView("administrativo/cidade/cadastro");
        mv.addObject("cidade", cidade);
        mv.addObject("ListaEstados", estadoRepositorio.findAll()); // lista de estado
        return mv;
    }

    @GetMapping("/listarCidade")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/cidade/lista");
        mv.addObject("ListaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarCidade/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepositorio.findById(id);
        return cadastrar(cidade.get());
    }

    @PostMapping("/salvarCidade")
    public ModelAndView salvar(Cidade cidade, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(cidade);
        }
        cidadeRepositorio.saveAndFlush(cidade);
        return cadastrar(new Cidade());
    }

    @GetMapping("/removerCidade/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepositorio.findById(id);
        cidadeRepositorio.delete(cidade.get());
        return listar();
    }

}