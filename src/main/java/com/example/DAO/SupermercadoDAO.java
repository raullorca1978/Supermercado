
package com.example.DAO;

import com.example.domain.Supermercado;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SupermercadoDAO extends CrudRepository<Supermercado, Long>{

    //Buscar productos por palabra introducida
   List<Supermercado> findByProducto(String palabraClave);
    
}
