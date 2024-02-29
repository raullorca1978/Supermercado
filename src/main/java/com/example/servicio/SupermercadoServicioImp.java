package com.example.servicio;

import com.example.DAO.SupermercadoDAO;
import com.example.model.Supermercado;
import com.example.web.ControladorREST;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    public ByteArrayInputStream exportExcel() {
        String[] columns = {"CODIGO", "PRODUCTO", "EXISTENCIAS", "PRECIO"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Productos");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        
        List<Supermercado> supermercados = this.listaProductos();
        int initRow = 1;
        for (Supermercado supermercado : supermercados) {
            row = sheet.createRow(initRow);
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

    @Override
    public ByteArrayInputStream exportExcelPalabra(String palabraClave) {

        String[] columns = {"CODIGO", "PRODUCTO", "EXISTENCIAS", "PRECIO"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Productos");
        Row row = sheet.createRow(0);

        List<Supermercado> supermercados = this.listaProductos();
        List<Supermercado> supermercadoLista = new ArrayList();

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        if (palabraClave.isEmpty()) {
            log.error("ERRRORRRRR EN PALABRA CLAVE");
        } else {
            // List<Supermercado> supermercados = supermercadoServicio.listaProductos();
            //List<Supermercado> supermercadoLista = new ArrayList();
            for (int i = 0; i < supermercados.size(); i++) {
                if (supermercados.get(i).toString().toLowerCase().contains(palabraClave.toLowerCase())) {
                    supermercadoLista.add(supermercados.get(i));
                    // log.info("Producto de mi lista numero: " + i + " que tiene el valor a buscar " + supermercados.get(i));
                }
            }
        }

        int initRow = 1;
        for (int i = 0; i < supermercadoLista.size(); i++) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(supermercadoLista.get(i).getCodigo().toUpperCase());
            row.createCell(1).setCellValue(supermercadoLista.get(i).getProducto().toUpperCase());
            row.createCell(2).setCellValue(supermercadoLista.get(i).getExistencias().toUpperCase());
            row.createCell(3).setCellValue(supermercadoLista.get(i).getPrecio().toUpperCase());
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
