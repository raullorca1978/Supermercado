
package com.example.DAO;

import com.example.model.Supermercado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupermercadoDAO extends JpaRepository<Supermercado, Long>{

   //Busca productos por palabra clave
   List<Supermercado> findByProducto(String palabraClave);
    
}
