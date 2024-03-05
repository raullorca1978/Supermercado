package com.example.validator;

import com.example.model.Supermercado;
import com.example.servicio.SupermercadoServicio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
       
        log.info("Codigo a revisar es....." + value);
        for (int i = 0; i<supermercados.size(); i++) {
            if(supermercados.get(i).getCodigo().toLowerCase().equals(value.toLowerCase())){
                log.info("Producto de mi lista numero: " + i + " con ID " + supermercados.get(i).getId_producto() + " ya contiene el CODIGO  " + supermercados.get(i).getCodigo());
                    validador = false;
            }
        }
        return validador;
    }

}
