package com.example.validator;


import com.example.model.Supermercado;
import com.example.servicio.SupermercadoServicio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class CodigoRepetidoValidator implements ConstraintValidator<CodigoRepetido, String> {
    
    @Autowired
    private SupermercadoServicio supermercadoServicio;
    
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
         boolean validador=true;
       
        List<Supermercado> supermercados = supermercadoServicio.listaProductos();
        log.info("MI LISTA DE PRODUCTOS ES......"+supermercados.toString());
        log.info("MI VALOR ES....." + value);
        
        for (int i = 0; i<supermercados.size(); i++) {
            log.info("Producto de mi lista numero: " + i + " que tiene el CODIGO: " + supermercados.get(i).getCodigo());
            if(supermercados.get(i).getCodigo().toLowerCase().equals(value.toLowerCase())){
                    validador = false;
            }
        }
        return validador;
    }

}
