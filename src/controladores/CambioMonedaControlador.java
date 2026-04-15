package controladores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import modelos.CambioMoneda;
import servicios.CambioMonedaServicio;

public class CambioMonedaControlador {

    public static void graficar(JPanel pnlGrafica,
            List<CambioMoneda> cambios,
            String moneda,
            LocalDate desde, LocalDate hasta) {

        var datosFiltrados = CambioMonedaServicio.filtrar(cambios, moneda, desde, hasta);
        var datosGrafica = CambioMonedaServicio.getDatosGrafica(datosFiltrados);

        TimeSeries serie = new TimeSeries("Cambio en USD de " + moneda);
        for (var dato : datosGrafica.entrySet()) {
            var fecha = dato.getKey();
            var fechaSerie = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
            var valor = dato.getValue();
            serie.add(fechaSerie, valor);
        }

        TimeSeriesCollection series = new TimeSeriesCollection();
        series.addSeries(serie);

        JFreeChart graficador = ChartFactory.createTimeSeriesChart(
                "Gráfica de cambio en USD de " + moneda + " vs Fecha",
                "Fecha",
                "Cambio en USD",
                series);

        ChartPanel pnlGraficador = new ChartPanel(graficador);
        pnlGraficador.setPreferredSize(new Dimension(500, 270));

        pnlGrafica.removeAll();
        pnlGrafica.setLayout(new BorderLayout());
        pnlGrafica.add(pnlGraficador, BorderLayout.CENTER);
        pnlGrafica.revalidate();

    }

}
