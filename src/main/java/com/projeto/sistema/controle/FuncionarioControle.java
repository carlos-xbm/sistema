package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.Funcionario;
import com.projeto.sistema.repositorios.FuncionarioRepositorio;
import com.projeto.sistema.repositorios.CidadeRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FuncionarioControle {
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
    @Autowired
    private CidadeRepositorio cidadeRepositorio;// lista de cidade

    @GetMapping("/cadastroFuncionario")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionario/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("ListaCidades", cidadeRepositorio.findAll()); // lista de cidade
        return mv;
    }

    @GetMapping("/listarFuncionario")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/funcionario/lista");
        mv.addObject("ListaFuncionarios", funcionarioRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarFuncionario/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        return cadastrar(funcionario.get());
    }

    @PostMapping("/salvarFuncionario")
    public ModelAndView salvar(Funcionario funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(funcionario);
        }
        funcionarioRepositorio.saveAndFlush(funcionario);
        return cadastrar(new Funcionario());
    }

    @GetMapping("/removerFuncionario/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        funcionarioRepositorio.delete(funcionario.get());
        return listar();
    }

}
