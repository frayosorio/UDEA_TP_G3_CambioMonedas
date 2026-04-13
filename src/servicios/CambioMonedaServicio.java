package servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import modelos.CambioMoneda;

public class CambioMonedaServicio {

    public static List<CambioMoneda> getDatos(String nombreArchivo) {
        try {
            var lineas = Files.lines(Paths.get(nombreArchivo));

            return lineas.skip(1)
                    .map(linea -> linea.split(","))
                    .map(textos -> new CambioMoneda(textos[0], LocalDate.parse(textos[1]),
                            Double.parseDouble(textos[2])))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
