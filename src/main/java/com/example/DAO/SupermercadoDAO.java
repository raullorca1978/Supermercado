
package com.example.DAO;

import com.example.model.Supermercado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupermercadoDAO extends JpaRepository<Supermercado, Long>{

    //List<Supermercado> ordenarPorCodigo();
}
