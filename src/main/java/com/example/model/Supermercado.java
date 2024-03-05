
package com.example.model;

import com.example.validator.CodigoRepetido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import lombok.Data;


@Data
@Entity
@Table(name="supermercado")
public class Supermercado implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id_producto;
    
    @CodigoRepetido
    @Column(name="Codigo")
    @Pattern(regexp="^[A-Z]\\d{4}$", message="{valido.regexp.codigo.error}")
    @NotBlank(message="{NotBlank.supermercado.codigo}")
    private String codigo;
    
    @Column(name="Producto")
    @NotBlank(message="{NotBlank.supermercado.producto}")
    private String producto;
    
    @Column(name="Existencias")
    @NotBlank(message="{NotBlank.supermercado.existencias}")
    private String existencias;
    
    @Column(name="Precio")
    @Pattern(regexp="^\\d+(,\\d+)*$", message="{valido.regexp.error}")
    @NotBlank(message="{NotBlank.supermercado.precio}")
    private String precio;
    
   
}
