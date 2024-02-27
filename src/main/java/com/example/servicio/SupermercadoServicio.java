
package com.example.servicio;

import com.example.model.Supermercado;
import java.io.ByteArrayInputStream;
import java.util.List;


public interface SupermercadoServicio {
   
    public void save(Supermercado supermercado);
    
    public void borrar (Supermercado supermercado);
    
    public Supermercado buscarSupermercado(Supermercado supermercado);
    
    public List<Supermercado> listaProductos();
  
   public List <Supermercado> buscarPorProducto(String palabraClave);
   
   public ByteArrayInputStream exportExcel();
   

    
    
}
