package com.example.servicio;

import com.example.DAO.SupermercadoDAO;
import com.example.model.Supermercado;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


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
        return supermercadoDao.findByProducto(palabraClave);

    }
    
    
    
    @Override
    public ByteArrayInputStream exportExcel(){
        String [] columns={"CODIGO","PRODUCTO","EXISTENCIAS","PRECIO"};
        
        Workbook workbook =new HSSFWorkbook();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        
        Sheet sheet=workbook.createSheet("Productos");
        Row row=sheet.createRow(0);
        
        for (int i =0; i<columns.length; i++){
            Cell cell=row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        
         List<Supermercado>supermercados=this.listaProductos();
        int initRow=1;
        for(Supermercado supermercado : supermercados){
            row=sheet.createRow(initRow);
            row.createCell(0).setCellValue(supermercado.getCodigo().toUpperCase());
            row.createCell(1).setCellValue(supermercado.getProducto().toUpperCase());
            row.createCell(2).setCellValue(supermercado.getExistencias().toUpperCase());
            row.createCell(3).setCellValue(supermercado.getPrecio().toUpperCase());
            initRow++;
        }
        try {
            workbook.write(stream);
            workbook.close();
            log.info("Exportado a EXCEL Correctamente");
        } catch (IOException ex) {
            Logger.getLogger(SupermercadoServicioImp.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Fallo en Exportado a EXCEL");
        }
        
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
