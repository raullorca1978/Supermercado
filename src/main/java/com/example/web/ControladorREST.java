package com.example.web;

import com.example.domain.Supermercado;

import java.util.List;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.servicio.SupermercadoServicio;
import java.util.ArrayList;

@Controller
@Slf4j
public class ControladorREST {

    @Autowired
    private SupermercadoServicio supermercadoServicio;

    @GetMapping("/")
    public String comienzo(Model model) {

        List<Supermercado> supermercados = supermercadoServicio.listaProductos();
        model.addAttribute("supermercados", supermercados);
        return "indice";
    }

    @GetMapping("/nuevoProducto")
    public String nuevoProducto(Supermercado supermercado) {
        return "cambiar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Supermercado supermercado, BindingResult error) {
        if (error.hasErrors()) {
            log.error(error.getFieldErrors().toString());
            return "cambiar";
        }
        supermercadoServicio.save(supermercado);
        log.info("Producto guardado correctamente con ID: " + supermercado.getId_producto());
        return "redirect:/";
    }

    @GetMapping("/buscarProducto")
    public String buscar(@RequestParam(value = "palabraClave") String palabraClave, Model model, Supermercado supermercado) {
        log.info("mi palabra clave es................ " + palabraClave);
        log.info("producto: " + palabraClave);
        if (palabraClave.isEmpty()) {
            log.info(palabraClave);
            return "indice";
        } else {
            List<Supermercado> supermercados = supermercadoServicio.listaProductos();
            List<Supermercado> supermercadoLista = new ArrayList();
            for (int i = 0; i < supermercados.size(); i++) {
                log.info("mi producto obtenido" + supermercados.get(i).getProducto());
                if (supermercados.get(i).toString().toLowerCase().contains(palabraClave.toLowerCase())) {
                    supermercadoLista.add(supermercados.get(i));
                    log.info("numero" + i + " " + supermercados.get(i));
                }
            }
            model.addAttribute("supermercados", supermercadoLista);
            log.info(model.toString());
        }
        return "indice";
    }

    @GetMapping("/borrar")
    public String borrar(Supermercado supermercado) {
        supermercadoServicio.borrar(supermercado);
        log.info("Se ha eliminado el Producto con id: " + supermercado.getId_producto());
        return "redirect:/";
    }

    @GetMapping("/cambiar/{id_producto}")
    public String cambiar(Supermercado supermercado, Model model) {
        supermercado = supermercadoServicio.buscarSupermercado(supermercado);
        model.addAttribute("supermercado", supermercado);
        return "cambiar";
    }

}
