package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.projeto.sistema.modelos.ItemEntrada;
import com.projeto.sistema.repositorios.ItemEntradaRepositorio;
import com.projeto.sistema.repositorios.ProdutoRepositorio;
import com.projeto.sistema.repositorios.EntradaProdutoRepositorio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemEntradaControle {
    @Autowired
    private ItemEntradaRepositorio itemEntradaRepositorio;
    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;// lista de fornecedor

    @Autowired
    private ProdutoRepositorio produtoRepositorio;// lista de funcionario

    @GetMapping("/cadastroItemEntrada")
    public ModelAndView cadastrar(ItemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/itemEntrada/cadastro");
        mv.addObject("itemEntrada", itemEntrada);
        mv.addObject("ListaEntradaProdutos", entradaProdutoRepositorio.findAll()); // lista de cidade
        mv.addObject("ListaProdutos", produtoRepositorio.findAll()); // lista de funcinario
        return mv;
    }

    @GetMapping("/listarItemEntrada")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/itemEntrada/lista");
        mv.addObject("ListaItemEntradas", itemEntradaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarItemEntrada/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<ItemEntrada> itemEntrada = itemEntradaRepositorio.findById(id);
        return cadastrar(itemEntrada.get());
    }

    @PostMapping("/salvarItemEntrada")
    public ModelAndView salvar(ItemEntrada itemEntrada, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(itemEntrada);
        }
        itemEntradaRepositorio.saveAndFlush(itemEntrada);
        return cadastrar(new ItemEntrada());
    }

    @GetMapping("/removerItemEntrada/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<ItemEntrada> itemEntrada = itemEntradaRepositorio.findById(id);
        itemEntradaRepositorio.delete(itemEntrada.get());
        return listar();
    }

}
