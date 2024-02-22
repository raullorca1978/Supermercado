package com.example.servicio;

import com.example.DAO.SupermercadoDAO;
import com.example.domain.Supermercado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SupermercadoServicioImp implements SupermercadoServicio {

    @Autowired
    private SupermercadoDAO supermercadoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Supermercado> listaProductos() {

        return (List<Supermercado>) supermercadoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Supermercado supermercado) {
            supermercadoDao.save(supermercado);
    }

    @Override
    @Transactional
    public void borrar(Supermercado supermercado) {
        supermercadoDao.delete(supermercado);
    }

    @Override
    @Transactional(readOnly = true)
    public Supermercado buscarSupermercado(Supermercado supermercado) {
        return supermercadoDao.findById(supermercado.getId_producto()).orElse(null);
    }
  
    @Override
    public List<Supermercado> buscarPorProducto(String palabraClave) {
        log.info("entra a buscar producto");
        return supermercadoDao.findByProducto(palabraClave);

    }

}
