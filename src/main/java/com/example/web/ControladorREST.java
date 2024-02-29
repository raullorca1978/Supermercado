package com.example.web;

import com.example.model.Supermercado;

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
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Controller
@Slf4j
public class ControladorREST {

    @Autowired
    private SupermercadoServicio supermercadoServicio;

    
    @GetMapping("/")
    public String comienzo(Model model) {
        List<Supermercado> supermercados = supermercadoServicio.listaProductos();
        model.addAttribute("supermercados", supermercados);
        log.info("Mostramos los productos que ya tenemos en la BBDD");
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

    private String palabraClaveGuardada;
    
    @GetMapping("/buscarProducto")
    public String buscar(@RequestParam(value = "palabraClave") String palabraClave, Model model, Supermercado supermercado) {
        log.info("Mi palabra clave a buscar es: " + palabraClave);
        palabraClaveGuardada=palabraClave;
        
        if (palabraClave.isEmpty()) {
            return "busqueda";
        } else {
            List<Supermercado> supermercados = supermercadoServicio.listaProductos();
            List<Supermercado> supermercadoLista = new ArrayList();
            for (int i = 0; i < supermercados.size(); i++) {
                if (supermercados.get(i).toString().toLowerCase().contains(palabraClave.toLowerCase())) {
                    supermercadoLista.add(supermercados.get(i));
                    log.info("Producto de mi lista numero " + i + " tiene el valor a buscar " +palabraClave.toUpperCase()+ " en " + supermercados.get(i).toString());
                }
            }
            if(supermercadoLista.isEmpty()){
                log.info("No se ha encontrado "+palabraClave.toUpperCase()+" en el stock");
            }
            model.addAttribute("supermercados", supermercadoLista);
        }
        return "busqueda";
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
        log.info("Se procede a modificar el producto con id : " + supermercado.getId_producto());
        return "cambiar";
    }

    @GetMapping("/export/all")
    public ResponseEntity<InputStreamResource> exportExcel(){
        ByteArrayInputStream stream= supermercadoServicio.exportExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"Listado_Productos.xls\"");
        
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
    
    @GetMapping("/export/all/palabraClave")
    public ResponseEntity<InputStreamResource> exportExcel(String palabraClave){
        log.info("Mi palabra claveguardada es...................: " + this.palabraClaveGuardada);
        ByteArrayInputStream stream= supermercadoServicio.exportExcelPalabra(this.palabraClaveGuardada);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"Listado_Productos_"+palabraClaveGuardada.toLowerCase()+".xls\"");
        
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
 
}
